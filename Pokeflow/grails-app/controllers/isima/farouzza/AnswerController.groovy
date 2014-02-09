package isima.farouzza

import grails.converters.*

class AnswerController {

    def give = {
		
		// render all the answers of a Post ( which could be an answer or a question )
		Post p = Post.get(params.id)			
		render (p.answers as JSON ) 
	
	}
}
