package isima.farouzza

class Answer extends Post {
	
	boolean validate = false
	
	static belongsTo = [post:Post]
	
    static constraints = {
		
    }
}
