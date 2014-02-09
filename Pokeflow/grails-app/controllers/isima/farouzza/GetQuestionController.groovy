package isima.farouzza

import grails.converters.*

class GetQuestionController {

    def give = {
		// Render the question with the id of the URL
		Question q = Question.get(params.id)
		render (q as JSON ) 
	
	}
}
