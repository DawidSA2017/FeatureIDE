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
package de.ovgu.featureide.fm.core.explanations.config;

import de.ovgu.featureide.fm.core.configuration.SelectableFeature;

/**
 * Generates explanations for automatic selections in configurations. These detail why a given feature must be selected or unselected given the other feature
 * selections.
 *
 * @author Timo G&uuml;nther
 */
public interface AutomaticSelectionExplanationCreator extends ConfigurationExplanationCreator {

	/**
	 * Returns the automatic selection to explain.
	 *
	 * @return the automatic selection to explain
	 */
	@Override
	public SelectableFeature getSubject();

	/**
	 * Sets the automatic selection to explain
	 *
	 * @param subject the automatic selection to explain
	 * @throws IllegalArgumentException if the subject is not an instance of {@link SelectableFeature}
	 */
	@Override
	public void setSubject(Object subject) throws IllegalArgumentException;

	/**
	 * Returns an explanation why the specified automatic selection is necessary.
	 */
	@Override
	public AutomaticSelectionExplanation getExplanation() throws IllegalStateException;
}
