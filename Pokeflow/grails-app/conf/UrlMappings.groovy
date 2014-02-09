class UrlMappings {

	static mappings = {	
	
		"/rest/api/post/$id"(controller:"post", parseRequest = false) {
			action = [DELETE:"delete"]      
        }
	
	
		"/rest/api/user/${id}"(controller:"member", parseRequest = false) {
			action = [PUT:"update"]      
        }
		
		"/rest/api/post/${id}/answers"(controller:"answer", parseRequest = false) {
			action = [GET:"give"]      
        }
	
		"/rest/api/question/${id}"(controller:"getQuestion", parseRequest = false) {
			action = [GET:"give"]      
        }	
		
		
	
	    "/rest/auth/session"(controller:"authentication", parseRequest = false) {
			action = [POST:"show"]      
        }
		
		"/rest/api/search"(controller:"search", parseRequest = false) {
			action = [POST:"show"]      
        }
		
		"/rest/api/question"(controller:"question", parseRequest = false) {
			action = [POST:"create"]      
        }
		
		
		
		

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
