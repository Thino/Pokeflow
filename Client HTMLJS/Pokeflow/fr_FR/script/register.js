/***********************************************************************
 * 
 * Function to add an user to the database
 * 
 **********************************************************************/

function addUser()
{
	var data = {};
	/// Store values for the request
	data['nickname'] = $("#username2")[0].value;
	data['password'] = btoa($("#password2")[0].value);
	data['firstName'] = $("#firstName")[0].value;
	data['lastName'] = $("#lastName")[0].value;
	data['email'] = $("#mail")[0].value;
	data['birthday'] = $("#birthDate")[0].value;
	var url = getCookie('ip') + '/rest/api/user' /// Set url
	var client = setClient('POST', url, false); /// Set client
	if (client != null) /// If client ok
	{
		var request = JSON.stringify(data);
		client.send(request); /// Set request
		if (client.status == 200) /// If successful
		{
			var currentUser = JSON.parse(client.responseText); /// Get infos
			createCookie('currentUser', currentUser, 1); /// Create user cookie
			createCookie('secret', btoa(currentUser.nickname + ':' ) + currentUser.password, 1); /// And session cookie
			alert('Votre compte a été crée !'); /// Tell user
			goTo('profile'); /// Go to his profile
		}
		else
		{
			alert('Error ' + client.status);
		}
	}
}
