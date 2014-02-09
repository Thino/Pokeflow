class UrlMappings {

	static mappings = {	
	
	
		"/rest/api/post/$id/answers"(controller:"answer", parseRequest = false) {
			action = [GET:"give"]      
        }
	
		"/rest/api/question/$id"(controller:"getQuestion", parseRequest = false) {
			action = [GET:"give"]      
        }	
	
	    "/rest/auth/session"(controller:"authentication", parseRequest = false) {
			action = [POST:"show"]      
        }
		
		"/rest/api/search"(controller:"search", parseRequest = false) {
			action = [POST:"show"]      
        }
		
		"/rest/api/question"(controller:"question", parseRequest = false) {
			action = [POST:"show"]      
        }
		
		
		

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
