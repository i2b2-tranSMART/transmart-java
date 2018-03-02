grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		mavenLocal() // Note: use 'grails maven-install' to install required plugins locally
		grailsCentral()
		mavenCentral()
		mavenRepo 'https://repo.transmartfoundation.org/content/repositories/public/'
		mavenRepo 'http://52north.org/maven/repo/releases' // to resolve the excluded gnujaxp dependency
	}

	dependencies {
		compile 'axis:axis:1.4', {
			excludes 'axis-saaj', 'commons-logging'
		}
		compile 'com.jcraft:jsch:0.1.54'
		compile 'com.thoughtworks.xstream:xstream:1.3', {
			excludes 'xpp3_min'
		}
		compile 'commons-net:commons-net:3.3'
		compile 'jfree:jfreechart:1.0.11', {
			excludes 'gnujaxp', 'jcommon', 'junit'
		}
		compile 'net.sf.opencsv:opencsv:2.3'
		compile 'org.apache.lucene:lucene-core:2.4.0'
		compile 'org.apache.lucene:lucene-highlighter:2.4.0'
		compile 'org.apache.poi:poi:3.1-FINAL'
		compile 'org.json:json:20090211'
	}

	plugins {
		build ':release:3.1.2', ':rest-client-builder:2.1.1', {
			export = false
		}
	}
}
