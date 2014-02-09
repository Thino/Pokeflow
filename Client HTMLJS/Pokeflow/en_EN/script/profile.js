/***********************************************************************
 * 
 * Operations to launch at the load.
 * 
 **********************************************************************/

window.onload = function()
{
	if (getCookie('secret') == null) /// If user not logged in
	{
		goTo('index'); /// Back to index
	}
	displayProfile(); /// Display user's profile
}


/***********************************************************************
 * 
 * Display the user's informations in the page.
 * 
 **********************************************************************/

function displayProfile()
{
	/// Get informations of the current user.
	var currentUser = JSON.parse(getCookie('currentUser'));
	$("#nickname")[0].innerHTML = currentUser.nickname;
	$("#firstNameP")[0].innerHTML = currentUser.firstName;
	$("#lastNameP")[0].innerHTML = currentUser.lastName;
	$("#mailP")[0].innerHTML = currentUser.email;
	$("#birthdayP")[0].innerHTML = currentUser.birthday;
	$("#score")[0].innerHTML = currentUser.score;
}


/***********************************************************************
 * 
 * Updates an user.
 * 
 **********************************************************************/

function update()
{
	var data = {};
	var updated = false; /// Check if the user changed something
	var currentUser = JSON.parse(getCookie('currentUser'));
	if ($("#password2")[0].value != '')
	{
		data['password'] = btoa($("#password2")[0].value);
		updated = true;
	}
	else
	{
		data['password'] = currentUser.password;
	}
	if ($("#firstName")[0].value != '')
	{
		data['firstName'] = $("#firstName")[0].value;
		updated = true;
	}
	else
	{
		data['firstName'] = currentUser.firstName;
	}
	if ($("#lastName")[0].value != '')
	{
		data['lastName'] = $("#lastName")[0].value;
		updated = true;
	}
	else
	{
		data['lastName'] = currentUser.lastName;
	}
	if ($("#mail")[0].value != '')
	{
		data['email'] = $("#mail")[0].value;
		updated = true;
	}
	else
	{
		data['email'] = currentUser.email;
	}
	if ($("#birthDate")[0].value != '')
	{
		data['birthday'] = $("#birthDate")[0].value;
		updated = true;
	}
	else
	{
		data['birthday'] = currentUser.birthday;
	}
	if (updated) /// If he changed something
	{
		var url = getCookie('ip') + '/rest/api/user/' + currentUser.id; /// Set url
		var client = setClient('PUT', url, true); /// Set client
		if (client != null) /// If client is okay
		{
			var request = JSON.stringify(data);
			client.send(request); /// Send request
			if (client.status == 200) /// If successful
			{
				var currentUser = JSON.parse(client.responseText); /// Get informations sent back
				createCookie('currentUser', client.responseText, 1); /// Update cookies
				createCookie('secret', btoa(currentUser.nickname + ':' ) + currentUser.password, 1);
				alert('Profile updated !'); /// Tell user
				goTo('profile'); /// Reload page
			}
			else
			{
				alert('Error ' + client.status);
			}
		}
	}
}
