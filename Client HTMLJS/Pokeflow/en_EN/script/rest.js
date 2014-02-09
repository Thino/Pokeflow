/***********************************************************************
 * 
 * Function to add the user
 * 
 **********************************************************************/

function identify()
{
	var url = getCookie('ip') + '/rest/auth/session'; /// Set url
	var data = {};
	/// We store the username and the password
    var username = $("#username")[0].value;
    var password = $("#password")[0].value;
	
	/// We store it in an object in order to convert it in a json format
	data['username'] = username;
	data['password'] = btoa(password);
	var request = JSON.stringify(data);
	var client = setClient('POST', url, false); /// Set client
	if (client != null) /// If client ok
	{
		client.send(request); /// Send request
		if (client.status == 200) /// If successful
		{
			var currentUser = client.responseText; /// Get infos
			createCookie('secret', btoa(username + ':' + password), 1); /// Create login cookie
			createCookie('currentUser', currentUser, 1); /// Save user infos
			goTo('index'); /// Go to index page
		}
		else if (client.status == 401)
		{
			alert("Invalid credentials.");
		}
		else
		{
			alert("Erreur " + client.status);
		}
	}
}

/***********************************************************************
 * 
 * This function implements the connection to a client through a 
 * XMLHttpRequest and manages the settings of the request headers
 * 
 * In  : method - Request method (POST, GET, PUT, DELETE)
 *       url - url of the request
 * 		 secure - Tell if connexion has to be secure
 * 
 * Out : client - The XMLHttpRequest when it's done and ready to use
 * 
 **********************************************************************/

function setClient(method, url, secure)
{
	var client = null;
	/// We get the user's informations
	var secret = getCookie('secret');
	if (!secure || (secure && (secret != null)))
	{
		/// We declare a new XMLHttpRequest to send our datas
		client = new XMLHttpRequest();
		/// We open the client with the method and the url
		client.open(method, url, false);
		/// We set the content-type header to JSON
		client.setRequestHeader('Content-Type', 'application/json');
		if (secure) /// If user has to be logged in
		{
			/// We set the authorization header to Basic HTML and append the login in 64
			client.setRequestHeader('Authorization', 'Basic ' + secret);
		}
	}
	/// And then we return the client which is ready to go (Solveig)
	return client;
}
