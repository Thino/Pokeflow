package isima.farouzza

class Member {
	
	String nickname
	
	String password
	
	String firstName
	
	String lastName
	
	String email
	
	String photo
	
	Date birthday
	
	int score = 0
	
	//static hasMany=[vote:Vote]	

    static constraints = {
		nickname(blank:false,minLength:1,maxLength:30)
		password(blank:false,minLength:1,maxLength:30)
		firstName(blank:false,minLength:1,maxLength:30)
		lastName(blank:false,minLength:1,maxLength:30)
		email(blank:false,email:true)
		photo(maxLength:100)
		birthday(blank:false)
		score(blank:false)		
    }
}