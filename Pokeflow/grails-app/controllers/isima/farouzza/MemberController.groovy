package isima.farouzza

import grails.converters.*
import groovy.json.JsonSlurper


class MemberController {

   def update = { 
		
			
		// Getting the header with authentication string
		 def authString = request.getHeader('Authorization')    
         if(!authString){  
		 // if no header with authentication, raise error
            response.status = 401	
			println "Request create user fail ( invalid credentials) "+new Date()			
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
			println "Request update user fail ( invalid credentials) "+new Date()			
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
			println "Request update user fail ( invalid credentials) "+new Date()			
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
			println "Request update user fail ( invalid JSON text) "+new Date()
			render ([error:'Bad JSON request'] as JSON )
			return
		}	
	
		// Verify that authentication matches with the id given
		if ( String.valueOf(m[0].id) != params.id )
		{
			response.status = 401	
			println "Request update user fail ( invalid credentials for modify this member) "+new Date()			
			render ([error:'Bad combinaison login/password'] as JSON )
			return
		
		}
		println result.password
		try
		{			
			m[0].password=result.password
			m[0].firstName=result.firstName
			m[0].lastName=result.lastName
			m[0].email =result.email
			if ( m[0].photo)
				m[0].photo = result.photo			
			m[0].birthday = Date.parse("dd/MM/yyyy",result.birthday)	
			if (!m[0].save()){			
				m[0].errors.each {
					println it
				}
				throw new Exception("Save problem")		
			}			
						
		}
		catch ( Exception exc)
		{
			response.status = 400	
			println "Request update user fail ("+exc.getMessage()+") "+new Date()			
			render "{\"error\":\"${exc.getMessage()}\"}"
			return
		}	
		
			
		
		println "Member updated"
		
		response.status = 200		
		render (m[0] as JSON) 		
	} 
}
