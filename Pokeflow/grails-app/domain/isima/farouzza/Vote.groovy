package isima.farouzza

class Vote {

	boolean value;
	
	static belongsTo = [post:Post, author:Member]	

    static constraints = {
    }
}
