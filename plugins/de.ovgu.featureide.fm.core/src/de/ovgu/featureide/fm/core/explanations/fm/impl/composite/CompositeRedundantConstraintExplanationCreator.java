/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2017  FeatureIDE team, University of Magdeburg, Germany
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
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.core.explanations.fm.impl.composite;

import java.util.Collection;

import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.explanations.fm.RedundantConstraintExplanation;
import de.ovgu.featureide.fm.core.explanations.fm.RedundantConstraintExplanationCreator;

/**
 * Implements {@link RedundantConstraintExplanationCreator} through composition.
 * 
 * @author Timo G&uuml;nther
 */
public class CompositeRedundantConstraintExplanationCreator extends CompositeFeatureModelExplanationCreator<RedundantConstraintExplanationCreator>
		implements RedundantConstraintExplanationCreator {

	/**
	 * Constructs a new instance of this class.
	 * 
	 * @param composites the explanation creators to compose
	 */
	public CompositeRedundantConstraintExplanationCreator(Collection<RedundantConstraintExplanationCreator> composites) {
		super(composites);
	}

	@Override
	public IConstraint getSubject() {
		return (IConstraint) super.getSubject();
	}

	@Override
	public RedundantConstraintExplanation getExplanation() throws IllegalStateException {
		return (RedundantConstraintExplanation) super.getExplanation();
	}
}
