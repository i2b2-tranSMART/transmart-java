grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'
	legacyResolve false

	repositories {
		mavenLocal() // Note: use 'grails maven-install' to install required plugins locally
		grailsCentral()
		mavenCentral()
		mavenRepo 'https://repo.transmartfoundation.org/content/repositories/public/'
	}

	dependencies {
		runtime 'com.oracle:ojdbc7:12.1.0.1', { export = false }
	}

	plugins {
		build ':release:3.1.0', ':rest-client-builder:2.1.0', {
			export = false
		}
	}
}
