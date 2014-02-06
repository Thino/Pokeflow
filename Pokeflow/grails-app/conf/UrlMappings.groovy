class UrlMappings {

	static mappings = {
	
	
	    "/rest/auth/session"(controller:"authentication", parseRequest = false) {
			action = [GET:"save", PUT:"update", DELETE:"delete", POST:"show"]      
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
