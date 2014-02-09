package isima.farouzza

class Tag {

	// The name of the tag
	String name;	
	
	static belongsTo = [question:Question]

    static constraints = {
    }
}
