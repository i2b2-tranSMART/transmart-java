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
class GenePatternFiles extends AbstractFiles {

	private static final int FLUSH_COUNT = 100

	final File clsfile
	final File gctfile
	final File csvfile
	protected PrintStream clsPS
	protected PrintStream gctPS
	protected PrintStream csvPS

	private int gctFlushCount = 0
	private int csvFlushCount = 0

	GenePatternFiles() throws IOException {
		clsfile = createTempFile('gp_df_', '.cls')
		gctfile = createTempFile('gp_df_', '.gct')
		csvfile = createTempFile('gp_df_exp_', '.csv')
	}

	void writeClsFile(String subjectIds1, String subjectIds2) throws IOException {
		FileOutputStream fos = new FileOutputStream(clsfile)
		PrintStream ps = new PrintStream(fos)

		if (subjectIds1 && subjectIds2) {
			StringTokenizer st1 = new StringTokenizer(subjectIds1, ',')
			StringTokenizer st2 = new StringTokenizer(subjectIds2, ',')

			int total = st1.countTokens() + st2.countTokens()
			ps.println total + ' 2 1'
			ps.println '# S1 S2'
			while (st1.hasMoreTokens()) {
				st1.nextToken()
				ps.print '0 '
			}
			while (st2.hasMoreTokens()) {
				st2.nextToken()
				ps.print '1 '
			}
		}
		else {
			StringTokenizer st
			if (subjectIds1 != null) {
				st = new StringTokenizer(subjectIds1, ',')
			}
			else {
				st = new StringTokenizer(subjectIds2, ',')
			}

			int count = st.countTokens()
			ps.println count + ' 1 1'
			ps.println '# subset1'
			while (st.hasMoreTokens()) {
				st.nextToken()
				ps.print '0 '
			}
		}

		ps.print '\n'

		fos.close()
	}

	void writeGctFile(HeatMapTable table, Boolean addMeans) throws IOException {
		FileOutputStream fos = new FileOutputStream(gctfile)
		table.writeToFile '\t', new PrintStream(fos, true), addMeans
		fos.flush()
		fos.close()
	}

	void writeGctFile(HeatMapTable table) throws IOException {
		FileOutputStream fos = new FileOutputStream(gctfile)
		table.writeToFile '\t', new PrintStream(fos, true)
		fos.flush()
		fos.close()
	}

	void openGctFile() throws IOException {
		gctPS = new PrintStream(new BufferedOutputStream(
				new FileOutputStream(gctfile)), true)
	}

	void createGctHeader(Integer rows, String[] ids, String delimiter) {
		gctPS.println '#1.2'

		gctPS.println rows + delimiter + ids.length
		gctPS.print 'NAME' + delimiter + 'Description'

		for (String id in ids) {
			gctPS.print delimiter + id
		}

		gctPS.print '\n'
	}

	void writeToGctFile(String value) {
		gctPS.println value
		gctFlushCount++
		if (gctFlushCount > FLUSH_COUNT) {
			gctPS.flush()
			gctFlushCount = 0
		}
	}

	void closeGctFile() {
		gctPS.close()
	}

	void openCSVFile() throws IOException {
		csvPS = new PrintStream(new BufferedOutputStream(
				new FileOutputStream(csvfile)), true)
	}

	void createCSVHeader(String[] ids, String delimiter) {
		csvPS.print 'NAME' + delimiter + 'Description'
		for (String id in ids) {
			csvPS.print delimiter + id
		}
		csvPS.print '\n'
	}

	void writeToCSVFile(String value) {
		csvPS.println value
		csvFlushCount++
		if (csvFlushCount > FLUSH_COUNT) {
			csvPS.flush()
			csvFlushCount = 0
		}
	}

	void writeToCSVFile(String[] ids, String delimiter, String value) {
		csvPS.print 'NAME' + delimiter + 'Description'
		for (String id in ids) {
			csvPS.print delimiter + id
		}
		csvPS.print '\n'
		csvPS.println value
		csvFlushCount++
		if (csvFlushCount > FLUSH_COUNT) {
			csvPS.flush()
			csvFlushCount = 0
		}
	}

	void closeCSVFile() {
		csvPS.flush()
		csvPS.close()
	}

	boolean openClsFile() throws IOException {
		clsPS = new PrintStream(new FileOutputStream(clsfile))
		true
	}

	void writeToClsFile(String value) {
		clsPS.println value
	}

	void closeClsFile() {
		clsPS.flush()
		clsPS.close()
	}

	String getCSVFileName() {
		csvfile.name
	}
}
