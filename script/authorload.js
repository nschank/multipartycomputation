window.addEventListener("load", loadAuthorInfo, true);


function loadAuthorInfo()
{
    var allAuthorLinks = document.getElementsByClassName("rp_author");
	
	for(var i = 0; i < allAuthorLinks.length; i++)
	{
		var span = allAuthorLinks.item(i);
		var author = span.innerHTML;
		if(authorList[author])
			span.innerHTML = "<a href=\"" + authorList[author] + "\">" + author + "</a>";
	}
}