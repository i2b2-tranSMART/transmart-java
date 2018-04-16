/*************************************************************************
 * tranSMART - translational medicine data mart
 *
 * Copyright 2008-2012 Janssen Research & Development, LLC.
 *
 * This product includes software developed at Janssen Research & Development, LLC.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software  * Foundation, either version 3 of the License, or (at your option) any later version, along with the following terms:
 * 1.	You may convey a work based on this program in accordance with section 5, provided that you retain the above notices.
 * 2.	You may convey verbatim copies of this program code as you receive it, in any medium, provided that you retain the above notices.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS    * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 ******************************************************************/

package com.recomdata.plugins

import groovy.transform.CompileStatic

@CompileStatic
class PluginDescriptor {
	public static final String PLUGIN_MAP = 'PLUGIN_MAP'

	String dataAccessObject
	String view

	/**
	 * What converting method to use, and a list of the steps in the process.
	 */
	Map<String, List<String>> converter

	/**
	 * What processing method to use, and a list of the steps in the process.
	 */
	Map<String, List<String>> processor

	/**
	 * Name of the plugin.
	 */
	String name

	/**
	 * Data types this plugin utilizes.
	 */
	Map<String, List<String>> dataTypes

	/**
	 * ID used to pull the descriptor out of session.
	 */
	String id

	/**
	 * What processing method to use, and the steps in the process.
	 */
	Map<String, List<String>> renderer

	/**
	 * Map of parameters to the names of the HTML inputs.
	 * We will run a replace statement to sub in the value from the form for the string used as the key here.
	 */
	Map<String, String> variableMapping

	/**
	 * Map of data types to the names of the HTML inputs.
	 * We will evaluate the key to see if it is true, if it is, then we add the value to the list of data files to retrieve.
	 */
	Map<String, String> dataFileInputMapping

	/**
	 * Whether to pivot the clinical data or not.
	 */
	boolean pivotData
}
