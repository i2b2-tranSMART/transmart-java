package com.recomdata.util

import groovy.transform.CompileStatic

/**
 * @author Florian
 */
@CompileStatic
class ExcelSheet {
	final String name
	List headers
	List values

	ExcelSheet() {}

	ExcelSheet(String name, List headers, List values) {
		this.name = name
		this.headers = headers
		this.values = values
	}
}
