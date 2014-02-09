package isima.farouzza

import grails.converters.*
import groovy.json.JsonSlurper


class AuthenticationController {

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
			println "Request authentication fail ( invalid JSON text) "+new Date()// WWBD 
			render ([error:'Bad JSON request'] as JSON )
			return
		}	
		
		// Search member with this username and this password
		def c = Member.createCriteria()
		Member[] m = c.list() {
					eq('nickname',result.username)
					eq('password',result.password)
					}
		//No user with this login			
		if ( m.size() < 1 )
		{
			response.status = 401
			println "Request authentication fail ( no user/password corresponding ) "+new Date()// WWBD 
			render ([error:'Bad combinaison login/password'] as JSON )
		}
		else
		{
			response.status = 200
			println "Request authentication success "+new Date()// WWBD 
			render (m[0] as JSON)			
		} 
		
		
		
		
  }
 
}
