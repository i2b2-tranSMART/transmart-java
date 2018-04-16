package com.recomdata.export

import groovy.transform.CompileStatic

/**
 * @author <a href='mailto:burt_beckwith@hms.harvard.edu'>Burt Beckwith</a>
 */
@CompileStatic
abstract class AbstractFiles {

	protected File getTempDir() {
		File tempDir = new File(System.getProperty('java.io.tmpdir'), 'datasetexplorer')
		if (!tempDir.exists()) {
			tempDir.mkdir()
		}
		tempDir
	}

	protected File createTempFile(String prefix, String suffix) {
		File.createTempFile prefix, suffix, tempDir
	}
}
