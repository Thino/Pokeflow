package isima.farouzza
import grails.converters.*
import groovy.json.JsonSlurper

class PostController {
	
    def delete = {	
		long id = Long.parseLong(params.id)
		if ( Post.get(id) == null )
			{
				response.status = 400	
				println "No posts with this id "			
				render ([error:'No post with this id'] as JSON )
				return	
			
			}
			
		// Getting the header with authentication string
		 def authString = request.getHeader('Authorization')    
         if(!authString){  
		 // if no header with authentication, raise error
            response.status = 401	
			println "Request delete post fail ( invalid credentials) "+new Date()			
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
			println "Request delete post fail ( invalid credentials) "+new Date()			
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
			println "Request delete post fail ( invalid credentials) "+new Date()			
			render ([error:'Bad combinaison login/password'] as JSON )
			return
		}
		
	
		// Verify that authentication matches with the id given
		if ( Post.get(id).author.id != m[0].id && !m[0].admin )
		{
			response.status = 401	
			println "Request delete post fail (Only an admin or the author can delete this post) "+new Date()			
			render ([error:'Only an admin or the author can delete this post'] as JSON )
			return		
		}
		
		Post p = Post.get(id)
		p.delete()
		println "Post deleted"
		response.status = 204		
		render ([message:'Post deleted'] as JSON )
			
	}	
	
}
