package isima.farouzza

class Question extends Post implements Comparable  {

	
	String title		
	
	Date modified
	
	static hasMany=[tags:Tag]
	
	
	
	// Render a string which contais all the tags of a question
	private String tagsToString()
	{
		StringBuilder sb = new StringBuilder();
		for ( t in tags )
		{
			sb.append(t.name)
			sb.append(" ")
		}
		return sb.toString()	
	}
	
	//To compare 2 questions with the note ( Vote+ - Vote- )
	int compareTo(o) {

        if(o instanceof Question) {
            Question q = (Question) o	
			if ( q.getNote() < this.getNote() )
				return 1
			else if ( q.getNote() > this.getNote() )
				return -1
			return 0            
        } else
            return 0
    }

    static constraints = {
		
		title(blank:false,minLength:5,maxLength:50)	
		modified(blank:false)	
		author(blank:false)
		
    }
}