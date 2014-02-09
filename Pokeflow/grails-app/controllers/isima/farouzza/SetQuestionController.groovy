package isima.farouzza
import grails.converters.*
import groovy.json.JsonSlurper

class SetQuestionController {

    def modify() {
	
		long id = Long.parseLong(params.id)
		if ( Question.get(id) == null )
		{
				response.status = 400	
				println "No question with this id "			
				render ([error:'No question with this id'] as JSON )
				return	
			
		}
			
		// Getting the header with authentication string
		 def authString = request.getHeader('Authorization')    
         if(!authString){  
		 // if no header with authentication, raise error
            response.status = 401	
			println "Request edit question fail ( invalid credentials) "+new Date()			
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
			println "Request edit question fail ( invalid credentials) "+new Date()			
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
			println "Request edit question fail ( invalid credentials) "+new Date()			
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
			println "Request edit question fail ( invalid JSON text) "+new Date()
			render ([error:'Bad JSON request'] as JSON )
			return
		}	
	
		// Verify that authentication matches with the id given
		if ( Question.get(id).author.id != m[0].id && !m[0].admin )
		{
			response.status = 401	
			println "Request edit question fail (Only an admin or the author can edit this question) "+new Date()			
			render ([error:'Only an admin or the author can edit this question'] as JSON )
			return		
		}

		Question q = Question.get(id)
		q.text=result.text
		if (!q.save()){			
			q.errors.each {
			println it
			}	
			response.status = 204		
			render ([message:'Save problem'] as JSON )			
		}	
		else
		{
			println "Question edited"
			response.status = 200		
			render ([message:'Question updated'] as JSON )
		}
		
		
		
		

	}
}
