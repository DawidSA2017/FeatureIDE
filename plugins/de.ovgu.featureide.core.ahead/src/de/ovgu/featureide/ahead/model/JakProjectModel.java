/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2009  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.ahead.model;

import java.util.HashMap;

import org.eclipse.core.resources.IFile;

import featureide.core.jakprojectmodel.IClass;
import featureide.core.jakprojectmodel.IFeature;
import featureide.core.jakprojectmodel.IJakModelElement;
import featureide.core.jakprojectmodel.IJakProjectModel;

/**
 * The model of a jak project
 * 
 * @author Tom Brosch
 * 
 */
public class JakProjectModel extends JakModelElement implements IJakProjectModel {

	HashMap<IFile, Class> classesMap;
	HashMap<String, Class> classes;
	HashMap<String, Feature> features;
	private String projectName;

	/**
	 * Creates a new instance of a jak project
	 * 
	 * @param name
	 *            Name of the project
	 */
	public JakProjectModel(String name) {
		classesMap = new HashMap<IFile, Class>();
		classes = new HashMap<String, Class>();
		features = new HashMap<String, Feature>();
		projectName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * featureide.core.jakprojectmodel.IJakProject#getNumberOfSelectedFeatures()
	 */
	public int getNumberOfSelectedFeatures() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see featureide.core.jakprojectmodel.IJakProject#getSelectedFeatures()
	 */
	public IFeature[] getSelectedFeatures() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see featureide.core.jakprojectmodel.IJakProject#getNumberOfFeatures()
	 */
	public int getNumberOfFeatures() {
		return features.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see featureide.core.jakprojectmodel.IJakProject#getFeatures()
	 */
	public IFeature[] getFeatures() {
		IFeature[] featureArray = new Feature[features.size()];
		int pos = 0;
		for (IFeature f : features.values()) {
			featureArray[pos++] = f;
		}
		return featureArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * featureide.core.jakprojectmodel.IJakProject#getFeature(java.lang.String)
	 */
	public IFeature getFeature(String featureName) {
		if (!features.containsKey(featureName))
			return null;
		return features.get(featureName); 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see featureide.core.jakprojectmodel.IJakProject#getNumberOfClasses()
	 */
	public int getNumberOfClasses() {
		return classesMap.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see featureide.core.jakprojectmodel.IJakProject#getClasses()
	 */
	public IClass[] getClasses() {
		IClass[] classArray = new Class[classes.size()];
		int pos = 0;
		for (IClass c : classes.values()) {
			classArray[pos++] = c;
		}
		return classArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * featureide.core.jakprojectmodel.IJakProject#getClass(org.eclipse.core
	 * .resources.IFile)
	 */
	public IClass getClass(IFile file) {
		if (!classesMap.containsKey(file))
			return null;
		IClass c = classesMap.get(file);
		c.setJakfile(file);
		return c;
	}

	public String getName() {
		return projectName;
	}

	public IJakModelElement[] getChildren() {
		return getClasses();
	}

	public boolean hasChildren() {
		return getNumberOfClasses() > 0;
	}

	public void markObsolete() {
		//do nothing
	}

}
