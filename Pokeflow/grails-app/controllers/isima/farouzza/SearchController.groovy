package isima.farouzza

import grails.converters.*
import groovy.json.JsonSlurper


class SearchController {	

		


    def show = { 
	
		
		// Get request text and parse it with JSON
		String txt = request.getReader().text
		def result = null
		try
		{
			result = new JsonSlurper().parseText(txt)
		}
		catch (IllegalArgumentException iae )
		{
			response.status = 400
			println "Request search fail ( invalid JSON text) "+new Date()// WWBD 
			render ([error:'Bad JSON request'] as JSON )
			return
		}		
		
		Question[] qs 
		
		// Search question witch contains the asked string
		if ( result.request != null && result.request.trim().length() )
		{
			def c = Question.createCriteria()			
			qs =  c.list(max:result.maxResults, offset:result.startAt) {
					or {				
					ilike('title', "%${result.request}%")
					ilike('text', "%${result.request}%")											
					}	
			}					
		}		
		else	
		{			
			qs = Question.list()
			//println qs[1].tags
		}
		
		
		
		// Sort by note
		qs.sort()
		qs.reverse(true)
	
		
		println "New search of ${result.request}. Results : ${qs.size()}"
		render (qs as JSON)	
		
	} 
			
			
	
	 
}
		

