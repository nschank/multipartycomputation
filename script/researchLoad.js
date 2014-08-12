window.addEventListener("load", initResearchSection, true);

function initResearchSection()
{
	var to = location.hash;
	var authorSection = document.getElementById("researchByAuthor");
	var titleSection = document.getElementById("researchByTitle");
	var yearSection = document.getElementById("researchByYear");
	var tagSection = document.getElementById("researchByTag");

	researchInfo.forEach(createSection(authorSection,titleSection,yearSection,tagSection));
	sortChildren(titleSection);
	sortChildren(authorSection);
	sortChildren(yearSection);
	sortChildren(tagSection);
	
	if(authorSection)
		for(var child in authorSection.childNodes)
			sortChildren(authorSection.childNodes.item(child));
	if(tagSection)
		for(var child in tagSection.childNodes)
			sortChildren(tagSection.childNodes.item(child));
	location.hash = to;
}

function createSection(authorSection,titleSection,yearSection,tagSection)
{	
	return function(paper)
	{
		if(!paper.hasPage && !paper.include) 
			return;
		var researchPiece = document.createElement("div");
		researchPiece.className = "rl_element";
		var str = "";
		if(paper.hasPage)
			str += "<a class=\"rl_title\" href=\"papers/" + paper.id + ".html\">";
		else str += "<span class=\"rl_title\">";
		str += paper.title;
		if(paper.hasPage)
			str += "</a>";
		else str += "</span>";
		str += "<br /><span class=\"rl_year\">" + paper.year + "</span>" + authorsOf(paper, 0);
		str += "<div class=\"rl_length\">" + paper.length + " pages</div>";
		str += "<div class=\"expandable\"><input id=\"exp-" + paper.id + "\" type=\"checkbox\" /><label for=\"exp-" + paper.id + "\">Abstract</label><div class=\"exp_content\">" + paper.abstractText + "</div></div>";
		researchPiece.innerHTML = str;
                
		if(authorSection)
			addToAuthors(paper, authorSection, researchPiece);
		if(titleSection)
			addSection(paper, titleSection, researchPiece, function(p)
				{
					return "papers:title:" + p.title;
				});
		if(yearSection)
			addSection(paper, yearSection, researchPiece, function(p)
				{
					return "papers:year:" + p.year;
				});
		if(tagSection)
			addToTags(paper, tagSection, researchPiece);
	};
}

function authorsOf(paper, from)
{
	if(paper.authors.length <= from) return "";
	else
	{
		var currentAuthor = paper.authors[from];
		var linkedAuthor;
		if(!authorList[currentAuthor] || authorList[currentAuthor].length == 0)
			linkedAuthor = "<span class=\"rl_author\">" + currentAuthor + "</span>";
		else linkedAuthor = "<a class=\"rl_author\" href=\"" + authorList[currentAuthor] + "\">" + currentAuthor + "</a>";
		if(paper.authors.length-1 === from) return linkedAuthor;
		else return linkedAuthor + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + authorsOf(paper,from+1);
	}
}

function lastFirst(str)
{
	var pos = str.lastIndexOf(" ");
	if(pos == -1) return str;
	else return removeSpaces(str.substring(pos+1)+str.substring(0,pos));
}

function addToAuthors(paper, section, node)
{
	for(var i = 0; i < paper.authors.length; i++)
	{
		var authorSub = document.getElementById("research:author:" + lastFirst(paper.authors[i]));
		if(!authorSub)
		{
			var newAuthorDiv = document.createElement("div");
			newAuthorDiv.id = "research:author:" + lastFirst(paper.authors[i]);
			newAuthorDiv.innerHTML = "<h2 class=\"rl_heading\">" + paper.authors[i] + "</h2>";
			section.appendChild(newAuthorDiv);
			authorSub = newAuthorDiv;
		}
		var addnode = node.cloneNode(true);
		addnode.id = "papers:author:" + lastFirst(paper.authors[i]) + ":" + paper.year + ":" + paper.id;
		authorSub.appendChild(addnode);
	}
}

function addToTags(paper, section, node)
{
	for(var i = 0; i < paper.tags.length; i++)
	{
		var tagSub = document.getElementById("research:tag:" + removeSpaces(paper.tags[i]));
		if(!tagSub)
		{
			var newTagDiv = document.createElement("div");
			newTagDiv.id = "research:tag:" + removeSpaces(paper.tags[i]);
			newTagDiv.innerHTML = "<h2 class=\"rl_heading\">" + paper.tags[i] + "</h2>";
			section.appendChild(newTagDiv);
			tagSub = newTagDiv;
		}
		var addnode = node.cloneNode(true);
		addnode.id = "papers:tag:" + paper.tags[i] + ":" + paper.year + ":" + paper.id;
		tagSub.appendChild(addnode);
	}
}

function removeSpaces(str)
{
	if(str.length == 0) return "";
	else if(!/[A-Za-z]/.test(str.charAt(0))) return removeSpaces(str.substring(1));
	else return str.charAt(0) + removeSpaces(str.substring(1));
}
	
function addSection(paper, section, node, createID)
{
	var addnode = node.cloneNode(true);
	addnode.id = createID(paper);
	section.appendChild(addnode);
}

function sortChildren(element)
{
	if(!element) return;
	var items = element.childNodes;
	var itemsArr = [];
	for (var i in items) {
		if (items[i].nodeType === 1) { // get rid of the whitespace text nodes
			itemsArr.push(items[i]);
		}
	}

	itemsArr.sort(function(a, b) {
	  return a.id === b.id
			  ? 0
			  : (a.id > b.id ? 1 : -1);
	});

	for (i = 0; i < itemsArr.length; ++i) {
	  element.appendChild(itemsArr[i]);
	}
}