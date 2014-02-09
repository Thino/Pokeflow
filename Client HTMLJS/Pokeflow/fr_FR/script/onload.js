/***********************************************************************
 * 
 * Specific script for the header. Here because of chrome not launching
 * some onload events.
 * 
 **********************************************************************/

var ip = getCookie('ip'); /// Get ip
if (ip == null) /// If ip not set
{
	/// We set the ip
	createCookie('ip', 'http://localhost/Pokeflow', 1);
}

var secret = getCookie('secret'); /// Get secret
var logB = $("#log")[0]; /// Get button1
var profileB = $("#profile")[0]; /// Get button2
if (secret != null) /// If user is logged in
{
	logB.onclick = function() { logOut(); }; /// Button1 set to log out
	logB.innerHTML = "Déconnexion";
	profileB.onclick = function() { goTo('profile'); }; /// Button2 set to go to profile
	profileB.innerHTML = "Mon profil";
	$("#ask")[0].style.display = 'inline-block'; /// Show button3
}
else
{
	logB.onclick = function() { showPopUp(true); }; /// Button1 set to show login popup
	logB.innerHTML = "Connexion";
	profileB.onclick = function() { goTo('register'); }; /// Button2 set to register
	profileB.innerHTML = "Inscription";
	$("#ask")[0].style.display = 'none'; /// Hide button3
}
