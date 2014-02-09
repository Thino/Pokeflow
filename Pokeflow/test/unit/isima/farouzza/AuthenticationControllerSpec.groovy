package isima.farouzza

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AuthenticationController)
class AuthenticationControllerSpec extends Specification {

    def setup() {
	
		Member memb = new Member(nickname:"Elytio",password:"tulorapa",firstName:"R",lastName:"Thib",email:"thy43@hotmail.fr",photo:"",birthday:new Date(1990,11,3),score:0)
		if (!memb.save()){
			memb.errors.each {
				println it
			}
		} 

		
		
	
	
    }

    def cleanup() {
    }

    void "test auth"() {
	
	
	
    }
}
