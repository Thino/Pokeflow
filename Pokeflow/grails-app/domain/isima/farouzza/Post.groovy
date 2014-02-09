package isima.farouzza

abstract class Post {

	String text
	
	Date created
	
	static hasMany = [answers:Answer, votes:Vote]
	
	static belongsTo = [author:Member]
	
	//Allow to get the note of a post
	public int getNote()
	{
		int note = 0
		for ( v in votes )
			note=v.value?++note:--note
		
		return note
	}
	
    static constraints = {		
		text(blank:false,minLength:10,maxLength:1000)
		created(blank:false)		
    }
}
