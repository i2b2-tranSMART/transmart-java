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

package com.recomdata.export

import groovy.transform.CompileStatic

/**
 * @author mmcduffie
 */
@CompileStatic
class GwasFiles {

	final File tmpDir
	final String fileAccessUrl
	final String fileNameRoot

	final File mapFile
	final File pedFile
	File assocFile
	final File reportFile
	final File sessionFile

	List<String> chromList = []
	List<Integer> datasetCountList = []
	List<Integer> patientCountList = []

	GwasFiles(String gpFileDirName, String gpFileAccessUrl) throws IOException {
		fileAccessUrl = gpFileAccessUrl

		tmpDir = new File(gpFileDirName)
		if (!tmpDir.exists()) {
			tmpDir.mkdir()
		}

		reportFile = File.createTempFile('gwas_', '.report.htm', tmpDir)

		fileNameRoot = reportFile.name.substring(0, reportFile.name.indexOf('.'))
		mapFile = new File(gpFileDirName, fileNameRoot + '.map')
		pedFile = new File(gpFileDirName, fileNameRoot + '.ped')
		sessionFile = new File(gpFileDirName, fileNameRoot + '.session.xml')
	}

	String getFileUrlWithSecurityToken(File file, String userName) {
		// The URL in the XML document need to have & escaped by &amp
		// IGV openSession routine uses the extension of a file or a URL to determine the file type. Put the file name at the end of URL.
		fileAccessUrl + '?user=' + userName +
				'&amp;hash=' + URLEncoder.encode(userName + file.length(), 'UTF-8') +
				'&amp;file=' + URLEncoder.encode(file.name, 'UTF-8')
	}
}
