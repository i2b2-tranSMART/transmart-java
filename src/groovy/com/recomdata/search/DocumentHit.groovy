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
package com.recomdata.search

import groovy.transform.CompileStatic
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.document.Document
import org.apache.lucene.search.Query
import org.apache.lucene.search.highlight.Highlighter
import org.apache.lucene.search.highlight.QueryScorer
import org.apache.lucene.search.highlight.SimpleFragmenter
import org.apache.lucene.search.highlight.SimpleHTMLFormatter

/**
 * Holds information about a document found during a search of a Lucene index.
 *
 * @author mmcduffie
 */
@CompileStatic
class DocumentHit {
	String creator
	String subject

	final Document doc
	final String highlightedText
	final int docId
	final String filePath
	final String fullText
	final String repository
	final double score
	final String title

	DocumentHit() {}

	DocumentHit(Document doc, int id, double score, Query query, Analyzer analyzer) {
		this.doc = doc
		docId = id
		this.score = score
		filePath = doc.get('path')
		title = doc.get('title')
		repository = doc.get('repository')

		Highlighter highlighter = new Highlighter(
				new SimpleHTMLFormatter('<span class="search-term">', '</span>'),
				new QueryScorer(query, 'contents'))
		highlighter.setTextFragmenter(new SimpleFragmenter(50))
		String summary = doc.get('contents')
		TokenStream tokenStream = analyzer.tokenStream('contents', new StringReader(summary))
		try {
			highlightedText = highlighter.getBestFragments(tokenStream, summary, 5, '...')
			fullText = highlightedText
		}
		catch (IOException ignored) {
			highlightedText = ''
			fullText = ''
		}
	}

	String getFilePath() {
		filePath.replace('<span class="search-term">', '').replace('</span>', '')
	}

	String getFileName() {
		String filePath = this.filePath
		int start = filePath.lastIndexOf('/')
		if (start > 0) {
			filePath = filePath.substring(start + 1, filePath.length())
		}
		filePath
	}
}
