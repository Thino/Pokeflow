package isima.farouzza

import grails.converters.*
import groovy.json.JsonSlurper

class QuestionController {		

     def create = { 
		
			
		// Getting the header with authentication string
		 def authString = request.getHeader('Authorization')    
         if(!authString){  
		 // if no header with authentication, raise error
            response.status = 401	
			println "Request create question fail ( invalid credentials) "+new Date()			
			render ([error:'Bad combinaison login/password'] as JSON )
			return
         }  
		 // getting credentials in the header
		def credentials = null
		try
		{
			def encodedPair = authString - 'Basic '  
			def decodedPair =  new String(encodedPair.decodeBase64());  
			credentials = decodedPair.split(':') 
		}
		catch (Exception e )
		{
			// No valid credentials, raise error
			response.status = 401	
			println "Request create question fail ( invalid credentials) "+new Date()			
			render ([error:'Bad combinaison login/password'] as JSON )
			return
		}	
		// Authentication
		
		def c = Member.createCriteria()
		Member[] m = c.list() {
					eq('nickname',credentials[0])
					eq('password',credentials[1].bytes.encodeBase64().toString())
					}
		
		if ( m.size() < 1 ) // if no member corresponding
		{
			response.status = 401	
			println "Request create question fail ( invalid credentials) "+new Date()			
			render ([error:'Bad combinaison login/password'] as JSON )
			return
		}
		
		// Parse JSON text
		String txt = request.getReader().text
		def result = null
		try
		{
			result = new JsonSlurper().parseText(txt)
		}
		catch (IllegalArgumentException iae )
		{
			// Bad JSON
			response.status = 400
			println "Request create question fail ( invalid JSON text) "+new Date()
			render ([error:'Bad JSON request'] as JSON )
			return
		}	
		
		// Creation of the question and association with members
		Question q = new Question(title:result.title,text:result.text,created:new Date(),modified:new Date())
		String[] tags = result.tags.split(" ")
		m[0].addToPosts(q)	
		m[0].save()		
		q.save()
		
		// Add of tags
		for ( t in tags)
		{
			Tag tt = new Tag(name:t)
			q.addToTags(tt)
			q.save()
			tt.save()
		}	
		
		println "Question added"
		
		response.status = 200		
		render "{\"id\":\"${String.valueOf(q.id) }\"}" 		
	} 

}
