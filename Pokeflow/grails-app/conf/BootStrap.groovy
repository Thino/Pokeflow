
import isima.farouzza.*
import grails.converters.*


class BootStrap {

	private String tagsToString(Tag[] tags)
	{
		StringBuilder sb = new StringBuilder();
		for ( t in tags )
		{
			sb.append(t.name)
			sb.append(" ")
		}
		return sb.toString()	
	}

    def init = { servletContext ->
		
		// Format the JSON parse of the object Question
		   JSON.registerObjectMarshaller(Question) {
                def returnArray = [:]
                returnArray['id'] = it.id				
                returnArray['author'] = it.author.nickname
				returnArray['created'] = it.created.format('dd/MM/yyyy HH:mm:ss')				
                returnArray['modified'] = it.modified.format('dd/MM/yyyy HH:mm:ss')	
				returnArray['title'] = it.title				
                returnArray['text'] = it.text
				returnArray['tags'] = tagsToString()
				returnArray['votes'] = it.votes.size()	
				returnArray['note'] = it.getNote()					
                returnArray['answers'] = it.answers.size()				
                return returnArray
        }
		
		// Format the JSON parse of the object Member
		   JSON.registerObjectMarshaller(Member) {
                def returnArray = [:]
                returnArray['id'] = it.id				
                returnArray['nickname'] = it.nickname
				returnArray['password'] = it.password.bytes.encodeBase64().toString()				
                returnArray['firstName'] = it.firstName	
				returnArray['lastName'] = it.lastName				
                returnArray['email'] = it.email
				returnArray['photo'] = it.photo			
                returnArray['birthday'] = it.birthday.format('dd/MM/yyyy')	
				returnArray['score'] = it.score					
                return returnArray
        }
	 
        
		// Allows to init the database with some example values
		Member m = new Member(nickname:"Elytio",password:"tulorapa",firstName:"R",lastName:"Thib",email:"thy@hotmail.fr",birthday:new Date(1990,11,3),score:0)
		Question q = new Question(text:"I have a yellow version of Pokemon and i want to know if it is possible to push the starter Pikachu to the level 100 ? If yes, how can i do it ??",created:new Date(),title:"How can i get a Pikachu level 100 ??",modified:new Date(),author:m)
		Answer a = new Answer(author:m,text:"Noob ! You just have to do many and many battles",created:new Date())
	
		
		if (!m.save()){
			m.errors.each {
				println it
			}
		}
		
		if (!a.save()){
			a.errors.each {
				println it
			}
		}
		
		
		q.addToAnswers(a)
		if (!q.save()){
			q.errors.each {
				println it
			}
		}
		
		
		q = new Question(text:"In the red version, can we have Mew ???? Where is he ?",created:new Date(),title:"Mew in red version ?",modified:new Date(),author:m)
		a = new Answer(author:m,text:"Get mew with the known bug with the Jadielle'teacher. But it's cheating !",created:new Date())
		Vote v = new Vote(value:true);
		q.addToVotes(v);
		m.addToVotes(v);
		q.addToAnswers(a)
		
		if (!a.save()){
			a.errors.each {
				println it
			}
		}	
		
		
		if (!q.save()){
			q.errors.each {
				println it
			}
		}
		
		if (!v.save()){
			v.errors.each {
				println it
			}
		}
		
		
		
		
		
		
    }
    def destroy = {
    }
}
