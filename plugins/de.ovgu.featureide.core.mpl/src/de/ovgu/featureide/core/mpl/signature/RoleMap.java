/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2013  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 * 
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.core.mpl.signature;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.prop4j.And;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.SatSolver;
import org.sat4j.specs.TimeoutException;

import de.ovgu.featureide.core.mpl.JavaInterfaceProject;
import de.ovgu.featureide.core.mpl.MPLPlugin;
import de.ovgu.featureide.core.mpl.signature.abstr.AbstractFieldSignature;
import de.ovgu.featureide.core.mpl.signature.abstr.AbstractMethodSignature;
import de.ovgu.featureide.core.mpl.signature.abstr.AbstractRole;
import de.ovgu.featureide.core.mpl.signature.abstr.AbstractSignature;
import de.ovgu.featureide.core.mpl.signature.java.JavaClassCreator;
import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.editing.NodeCreator;

/** 
 * Maps the feature names to a list of role signatures.
 * 
 * @author Sebastian Krieter
 */
public class RoleMap {
	private final HashMap<AbstractSignature, AbstractSignature> 
		signatureSet = new HashMap<AbstractSignature, AbstractSignature>();
	private final HashMap<String, FeatureRoles> 
		featureRoleMap = new HashMap<String, FeatureRoles>();

	private final LinkedList<AbstractSignature> 
		childSignatures = new LinkedList<AbstractSignature>();
	
	private final JavaInterfaceProject interfaceProject;
	
	public RoleMap(JavaInterfaceProject interfaceProject) {
		this.interfaceProject = interfaceProject;
	}
	
	public RoleMap(RoleMap roleMap, ViewTag viewTag) {
		this(roleMap.interfaceProject);
		for (Entry<String, FeatureRoles> rolesEntrySet : roleMap.featureRoleMap.entrySet()) {
			FeatureRoles reducedRoles = getRoles(rolesEntrySet.getKey());
			for (AbstractRole role : rolesEntrySet.getValue()) {
				if (role.getSignature().hasViewTag(viewTag)){
					reducedRoles.add(role.reduce(viewTag));					
				}
			}
		}
	}
	
	public RoleMap(RoleMap roleMap) {
		this(roleMap, null);
	}
	
	public void addRole(AbstractRole role) {
		String s = role.getFeatureName();
		getRoles(s).add(role);
	}
	
	public FeatureRoles getRoles(String featurename) {
		return getRoles(interfaceProject.getFeatureModel().getFeature(featurename));
	}
	
	public FeatureRoles getRoles(Feature feature) {
		FeatureRoles ret = featureRoleMap.get(feature.getName());
		if (ret == null) {
			ret = new FeatureRoles(feature);
			featureRoleMap.put(feature.getName(), ret);
		}
		return ret;
	}

	public ProjectSignature generateSignature(List<String> featureList, ViewTag viewTag) {
		ProjectSignature javaSig = new ProjectSignature(viewTag);
		
		if (featureList == null) {
			for (FeatureRoles roles : featureRoleMap.values()) {
				for (AbstractRole role : roles) {
					javaSig.addRole(role);
				}
			}
		} else {
			for (String featureName : featureList) {
				FeatureRoles roles = featureRoleMap.get(featureName);
				if (roles != null) {
					for (AbstractRole role : roles) {
						javaSig.addRole(role);
					}
				}
			}
		}
		return javaSig;
	}
	
	public ProjectSignature generateSignature() {
		return generateSignature(null, null);
	}
	
	public ProjectSignature generateSignature(List<String> featureList) {
		return generateSignature(featureList, null);
	}
	
	public ProjectSignature generateSignature(ViewTag viewTag) {
		return generateSignature(null, viewTag);
	}
	
	public ProjectSignature generateSignature2(List<String> featureList, ViewTag viewTag) {
		ProjectSignature javaSig = new ProjectSignature(viewTag);
		javaSig.setaClassCreator(new JavaClassCreator());
		
		if (featureList == null) {
			for (AbstractSignature sig : childSignatures) {
				javaSig.addSignature(sig);
			}
		} else {
			for (AbstractSignature sig : signatureSet.keySet()) {
				for (String feature : featureList) {
					if (sig.getFeatures().contains(feature)) {
						javaSig.addSignature(sig);
						break;
					}
				}
			}
		}
		return javaSig;
	}
	
	public ProjectSignature[] extendedSignature() {
		Configuration conf; {
			Configuration curConf = interfaceProject.getConfiguration();
			conf = new Configuration(curConf, curConf.getFeatureModel(), true);
		}
		Set<Feature> unselectedFeatures = conf.getUnSelectedFeatures();
		ProjectSignature[] extendedSignatures = new ProjectSignature[unselectedFeatures.size() + 1];
		int i = 0;
		for (Feature feature : unselectedFeatures) {
			conf.setManual(feature.getName(), Selection.SELECTED);
			extendedSignatures[i++] = ka(conf.getSelectedFeatures());
			conf.setManual(feature.getName(), Selection.UNSELECTED);
		}
		extendedSignatures[i] = ka(conf.getSelectedFeatures());
		return extendedSignatures;
	}
	
	private ProjectSignature ka(Set<Feature> selectedFeatures) {
		ProjectSignature projectSig = new ProjectSignature(null);
		projectSig.setaClassCreator(new JavaClassCreator());
		
		Node[] fixClauses = new Node[selectedFeatures.size() + 1];
		int i = 0;
		for (Feature feature : selectedFeatures) {
			fixClauses[i++] = new Literal(feature.getName(), true);
		}
		fixClauses[i] = NodeCreator.createNodes(interfaceProject.getFeatureModel());
		
		for (AbstractSignature sig : childSignatures) {
			Node[] clauses = new Node[sig.getFeatures().size() + fixClauses.length];
			int j = 0;
			for (String featureName : sig.getFeatures()) {
				clauses[j++] = new Literal(featureName, false);
			}
			System.arraycopy(fixClauses, 0, clauses, j, fixClauses.length);
			
			SatSolver solver = new SatSolver(new And(clauses), 1000);
			try {
				if (!solver.isSatisfiable()) {
					projectSig.addSignature(sig);
				}
			} catch (TimeoutException e) {
				MPLPlugin.getDefault().logError(e);
			}
			
		}
		return projectSig;
	}

	@Deprecated
	public void addDefaultViewTag(String name) {
		final ViewTag 
			classViewTag = interfaceProject.getViewTagPool().getViewTag(name, 3),
			methodViewTag = interfaceProject.getViewTagPool().getViewTag(name, 2),
			fieldViewTag = interfaceProject.getViewTagPool().getViewTag(name, 1);
		
		for (FeatureRoles roles : featureRoleMap.values()) {
			for (AbstractRole role : roles) {
				role.getSignature().addViewTag(classViewTag);
				for (AbstractSignature member : role.getMembers()) {
					if (member instanceof AbstractFieldSignature) {
						member.addViewTag(fieldViewTag);
					} else if (member instanceof AbstractMethodSignature) {
						member.addViewTag(methodViewTag);
					}
				}
			}
		}
	}

	public AbstractSignature getSignatureRef(AbstractSignature sig) {
		AbstractSignature sigRef = signatureSet.get(sig);
		if (sigRef == null) {
			signatureSet.put(sig, sig);
			return sig;
		} else {
			return sigRef;
		}
	}
	
	public Collection<AbstractSignature> getSignatures() {
		return signatureSet.keySet();
	}	
}