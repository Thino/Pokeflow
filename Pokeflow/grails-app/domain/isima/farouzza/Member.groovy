package isima.farouzza

class Member {
	
	String nickname
	
	String password
	
	String firstName
	
	String lastName
	
	String email
	
	boolean admin = false
	
	String photo = ""
	
	Date birthday
	
	int score = 0
	
	static hasMany=[posts:Post, votes:Vote]	

    static constraints = {
		nickname(blank:false,minLength:1,maxLength:30)
		password(blank:false,minLength:1,maxLength:30)
		firstName(blank:false,minLength:1,maxLength:30)
		lastName(blank:false,minLength:1,maxLength:30)
		email(blank:false,email:true)		
		birthday(blank:false)
		score(blank:false)		
    }
}