/***********************************************************************
 * 
 * Operations to do at load.
 * 
 **********************************************************************/

window.onload = function()
{
	getQuestion(); /// Get informations of the question.
}


/***********************************************************************
 * 
 * Get all informations from server.
 * 
 **********************************************************************/

function getQuestion()
{
	var url = getCookie('ip') + '/rest/api/question/' + getCookie('questionId'); /// Set url
	var client = setClient('GET', url, false); /// Set client
	if (client != null) /// If client ok
	{
		client.send(null); /// Send request
		if (client.status == 200) /// If successful
		{
			var infos = JSON.parse(client.responseText); /// Store informations
			displayQuestion(infos); /// Display informations
		}
	}
}


/***********************************************************************
 * 
 * Shows or hides the edit window
 * 
 **********************************************************************/

var editDisplayed = false; /// Check if edit window is displayed or not

function displayEdit()
{
	if (editDisplayed) /// If displayed
	{
		$("#question .edit form")[0].style.display = 'none'; /// Hide
		editDisplayed = false; /// Change state
	}
	else
	{
		$("#question .edit form")[0].style.display = 'inline-block'; /// Show
		editDisplayed = true;
	}	
}


/***********************************************************************
 * 
 * Shows or hides the window to add answer
 * 
 **********************************************************************/

var addAnswerDisplayed = false; /// Check if window is displayed or not

function displayAddAnswer()
{
	if (addAnswerDisplayed) /// If displayed
	{
		$("#addAnswer")[0].style.display = 'none'; /// Hide
		addAnswerDisplayed = false; /// Change state
	}
	else
	{
		$("#addAnswer")[0].style.display = 'inline-block'; /// Show
		addAnswerDisplayed = true;
	}
}


/***********************************************************************
 * 
 * Changes a question.
 * 
 **********************************************************************/

function edit()
{
	var url = getCookie('ip') + '/rest/api/question/' + getCookie('questionId'); /// Set url
	var client = setClient('PUT', url, true); /// Set client
	if (client != null) /// If client ok
	{
		var data = {};
		data['text'] = $("#question .edit textarea")[0].innerHTML; /// Set text
		var request = JSON.stringify(data);
		client.send(request); /// Send request to the server
		if (client.status == 200) /// If successful
		{
			alert('Question edited !'); /// Tell user
			goTo('question'); /// Display the page
		}
		else
		{
			alert('Error ' + client.status);
		}
	}
}


/***********************************************************************
 * 
 * Deletes a post (question or answer).
 * 
 **********************************************************************/

function deletePost(id)
{
	if (!confirm('Are you sure ?')) /// Check if user didn't missclicked
	{
		return;
	}
	var postId;
	if (typeof(id) == 'undefined') /// If id is defined, it's an answer
	{
		postId = getCookie('questionId'); /// Get question id
	}
	else
	{
		postId = id; /// Get answer id
	}
	var url = getCookie('ip') + '/rest/api/post/' + postId; /// Set url
	var client = setClient('DELETE', url, true); /// Set client
	if (client != null) /// If client ok
	{
		client.send(null); /// Send datas
		if (client.status == 204) /// If delete successful
		{
			if (typeof(id) == 'undefined') /// If question
			{
				alert('Question deleted !'); /// Tell user
				goTo('index'); /// Go to index
			}
			else
			{
				alert('Answer deleted !'); /// Tell user
				goTo('question'); /// Go to question
			}
		}
		else
		{
			alert('Error ' + client.status);
		}
	}
}


/***********************************************************************
 * 
 * This function displays question's informations
 * 
 * In  : infos - Informations of the question
 * 
 **********************************************************************/

function displayQuestion(infos)
{
	$("#title")[0].innerHTML = infos.title;
	$("#author")[0].innerHTML = infos.author;
	$("#question .text p")[0].innerHTML = infos.text;
	$("#question .edit textarea")[0].innerHTML = infos.text;
	$("#tags p")[0].innerHTML = 'Tags : ' + infos.tags;
	$("#mainSec")[0].className = 'post' + infos.id;
	var currentUser = JSON.parse(getCookie('currentUser')); /// Get user infos
	if (infos.author == currentUser.nickname) /// If user wrote the question
	{
		$("#question .edit")[0].style.display = 'inline-block'; /// Show the edit button
		$("#delete")[0].style.display = 'inline-block'; /// Show the delete button
	}
	if (currentUser.admin == 'true') /// If user is admin
	{
		$("#delete")[0].style.display = 'inline-block'; /// Display delete button
	}
	if (infos.answers != 0) /// If the question has answers
	{
		displayAnswers(infos.id, 1); /// Display !
	}
}


/***********************************************************************
 * 
 * Display all answers recursively.
 * 
 * In  : id - Id of the parent post
 * 		 depth - Depth of the answer
 * 
 **********************************************************************/

function displayAnswers(id, depth)
{
	var url = getCookie('ip') + '/rest/api/post/' + id + '/answers'; /// Set url
	var client = setClient('GET', url, false); /// Set client
	var currentUser = JSON.parse(getCookie('currentUser')); /// Get user infos
	if (client != null) /// If client ok
	{
		client.send(null); /// Send request
		if (client.status == 200) /// If successful
		{
			var infos = JSON.parse(client.responseText); /// Get answers
			for (var i = 0; i<infos.length; ++i) /// For each answer
			{
				var parent = $(".post" + id)[0]; /// Get parent by id
				/// Answer's div
				var answer = document.createElement('div');
				answer.className = 'answer post' + infos[i].id; /// Set classname with id
				answer.style.left = 15 + 5*depth + 'px'; /// Set position according to depth
				var color = 232 - 6*depth; /// And color
				answer.style.background = 'rgb(' + color + ',' + color + ',' + color + ')';
				/// Vote's div
				var vote = document.createElement('div');
				vote.className = 'vote';
				/// Img of the up arrow
				var imgUp = document.createElement('img');
				imgUp.alt = 'up';
				imgUp.src = '../img/up.png';
				/// Paragraph of the vote number
				var nbVote = document.createElement('p');
				nbVote.className = 'nbVote';
				nbVote.innerHTML = infos[i].votes; /// Write votes
				/// Img of the down arrow
				var imgDown = document.createElement('img');
				imgDown.alt = 'down';
				imgDown.src = '../img/down.png';
				/// Append elements to the vote's div
				vote.appendChild(imgUp);
				vote.appendChild(nbVote);
				vote.appendChild(imgDown);
				answer.appendChild(vote); /// Append vote's div to answer
				/// Div containing text
				var text = document.createElement('div');
				text.className = 'text';
				text.style.width = 671 - 10*depth + 'px'; /// Set width
				/// Text paragraph
				var textP = document.createElement('p');
				textP.innerHTML = infos[i].text; /// Write text
				text.appendChild(textP); /// Append text to div
				/// Author paragraph
				var author = document.createElement('p');
				author.innerHTML = infos[i].author; /// Write author
				author.style.fontStyle = 'italic';
				text.appendChild(author); /// Append author to div
				answer.appendChild(text); /// Append div to answer
				/// Button to reply
				var replyImg = document.createElement('img');
				replyImg.src = '../img/reply.png';
				replyImg.style.cursor = 'pointer';
				replyImg.setAttribute('id', infos[i].id);
				replyImg.onclick = function() { displayAddAnswer(this.id); }; /// Set function to display window to add answer
				answer.appendChild(replyImg); /// Append button to answer
				/// If user is author or user is admin
				if ((infos[i].author == currentUser.nickname) || (currentUser.admin == 'true'))
				{
					/// Create delete button
					var deleteImg = document.createElement('img');
					deleteImg.src = '../img/delete.png';
					deleteImg.style.cursor = 'pointer';
					deleteImg.setAttribute('id', infos[i].id);
					deleteImg.onclick = function() { deletePost(this.id); }; /// Set to delete post if clicked
					answer.appendChild(deleteImg); /// Append to answer
				}
				parent.appendChild(answer); /// Append answer to parent post
				if (infos[i].answers != 0) /// If answer has got answers
				{
					displayAnswers(infos[i].id, depth+1); /// Gotta display'em all !
				}
			}
		}
	}
}
