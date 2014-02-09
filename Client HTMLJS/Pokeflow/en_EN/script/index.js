/***********************************************************************
 * 
 * Onload function to determine what is usefull to launch on load.
 * 
 **********************************************************************/

window.onload = function()
{
	if (getCookie('search') != null)
	{
		doSearch(); /// If the user wants to search questions, we do it
	}
	else
	{
		getQuestions(null, 1, 100); /// Else we display all questions
	}
}


/***********************************************************************
 * 
 * Function to load the question the user clicked on when we arrive on
 * the question page.
 * 
 * In  : id - Id of the question to display
 * 
 * Out : null
 * 
 **********************************************************************/

function getQuestion(id)
{
	createCookie('questionId', id, 1);
	goTo('question');
}


/***********************************************************************
 * 
 * Executes the search.
 * 
 **********************************************************************/

function doSearch()
{
	getQuestions(getCookie('search'), 0, 100);
	deleteCookie('search'); /// Search done, remove cookie
}


/***********************************************************************
 * 
 * This function gets the list of all questions.
 * 
 * In  : query - Search query
 * 		 noPage - Number of the page to display
 * 		 nbResults - How many results we want
 * 
 * Out : null
 * 
 **********************************************************************/

function getQuestions(query, noPage, nbResults)
{
    var url = getCookie("ip") + '/rest/api/search'; /// Set url
    var client = setClient('POST', url, false); /// Set client
    if (client != null) /// If client set up
    {
		var data = {};
		/// We set the datas
		data['request'] = query;
		data['startAt'] = noPage*100;
		data['maxResults'] = 100;
		var request = JSON.stringify(data);
        client.send(request); /// Send request to server
        if (client.status == 200) /// If successful
        {
			var infos = JSON.parse(client.responseText); /// Store results
            displayQuestions(infos); /// Call the display
        }
        else
        {
			alert("Error " + client.status);
		}
    }
}


/***********************************************************************
 * 
 * Display the questions in the page
 * 
 * In  : questions - Datas to display
 * 
 * Out : null
 * 
 **********************************************************************/

function displayQuestions(questions)
{
    var questionsDiv = $("#questions")[0]; /// We get the div where to display
    var i = 0;
    while (typeof questions[i] != 'undefined') /// For each question
    {
		/// Div of the question
        var questionBlock = document.createElement('div');
        questionBlock.className = 'questionBlock';
        questionBlock.style.top = 75*i + 'px'; /// Place farther
        /// Paragraph of the votes
        var votesNb = document.createElement('p');
        votesNb.className = 'votesNb';
        votesNb.innerHTML = questions[i].votes; /// Write votes
        votesNb.appendChild(document.createElement('br'));
        votesNb.innerHTML += 'votes';
        questionBlock.appendChild(votesNb); /// Append
        /// Paragraph of the answers number
        var answersNb = document.createElement('p');
        answersNb.className = 'answersNb';
        answersNb.innerHTML = questions[i].answers; /// Write number
        answersNb.appendChild(document.createElement('br'));
        answersNb.innerHTML += 'answers';
        questionBlock.appendChild(answersNb);
        /// Link to the question
        var link = document.createElement('a');
        link.style.cursor = 'pointer';
        link.setAttribute('id', questions[i].id);
        link.onclick = function() { getQuestion(this.id); }; /// Function to go to the question
        /// Paragraph of the question title
        var question = document.createElement('p');
        question.className = 'question';
        question.style.overflow = 'hidden';
        question.innerHTML = questions[i].title; /// Write title
        link.appendChild(question); /// Append title to link
        questionBlock.appendChild(link); /// Append link to div
        /// Paragraph of the author
        var author = document.createElement('p');
        author.className = "author";
        author.innerHTML = questions[i].author; /// Write author's username
        questionBlock.appendChild(author); /// Append author to div
        /// Paragraph of the date
        var date = document.createElement('p');
        date.className = 'date';
        date.innerHTML = questions[i].created; /// Write creation data
        questionBlock.appendChild(date); /// Append date to div
        questionsDiv.appendChild(questionBlock); /// Append div to main section
        ++i; /// Next question
    }
}
