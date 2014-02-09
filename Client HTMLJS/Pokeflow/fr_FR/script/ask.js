/***********************************************************************
 * 
 * Launches needed functions at the load of the page.
 * 
 **********************************************************************/

window.onload = function()
{
	if (getCookie('secret') == null) /// If user isn't logged in (secret = null)
	{
		goTo('index'); /// Back to the index
	}
}

/***********************************************************************
 * 
 * Creates a new question in the server's database.
 * 
 **********************************************************************/

function ask()
{
	var url = getCookie('ip') + '/rest/api/question'; /// Ressource's URL
	var client = setClient('POST', url, true); /// Client setup
	if (client != null) /// If client is okay
	{
		var data = {};
		/// We fill the mandatory fields
		data['title'] = $("#subject")[0].value;
		data['text'] = $("#text")[0].value;
		data['tags'] = $("#tags")[0].value;
		var request = JSON.stringify(data);
		client.send(request); /// We send the request to the server
		if (client.status == 200) /// If successful
		{
			alert('Votre question a été postée !'); /// Tell the user
			var infos = JSON.parse(client.responseText); /// Get the infos
			createCookie('questionId', infos.id, 1); /// Save question id
			goTo('question'); /// Go to the question page to see the new question
		}
		else
		{
			alert('Error ' + client.status); /// Display error
		}
	}
}
