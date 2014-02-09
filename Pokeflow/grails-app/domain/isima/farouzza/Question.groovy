package isima.farouzza

class Question extends Post {
	
	String title		
	
	Date modified
	
	static hasMany=[tags:Tag]

    static constraints = {
		
		title(blank:false,minLength:5,maxLength:50)	
		modified(blank:false)	
		author(blank:false)
		
    }
}