package isima.farouzza

import grails.converters.*
import groovy.json.JsonSlurper


class AuthenticationController {

     def show = { 
	 
		/*Member memb = new Member(nickname:"Elytio",password:"tulorapa",firstName:"R",lastName:"Thib",email:"thy@moi.fr",photo:"ddsdsd",birthday:new Date(),score:99)
		if (!memb.save()){
			memb.errors.each {
				println it
			}
		} */
		
		//println request.getReader().text
		String txt = request.getReader().text
		def result = new JsonSlurper().parseText(txt)
		Member[] m = Member.where {
            nickname == result.username && password == result.password
        }.findAll()	
		if ( m.size() < 1 )
		{
			response.status = 401
			render ([error:'Bad combinaison login/password'] as JSON )
		}
		else
		{
			response.status = 200
			render (m as JSON)
		} 
		
		
		
		
  }
 
}
