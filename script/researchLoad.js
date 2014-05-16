window.addEventListener("load", initResearchSection, true);

function initResearchSection()
{
	researchInfo.forEach(createSection);
}

function createSection(paper)
{
	var authorSection = document.getElementById("researchByAuthor");
	var titleSection = document.getElementById("researchByTitle");
	var yearSection = document.getElementById("researchByYear");
	var tagSection = document.getElementById("researchByTag");
	
	var researchPiece = document.createElement("div");
	researchPiece.className = "paper";
	researchPiece.innerHTML = paper.authors[0] + paper.year + paper.title;
	
	addToAuthors(paper, authorSection, researchPiece);
	addSection(paper, titleSection, researchPiece, function(p)
		{
			return "papers:title:" + p.title;
		});
	addSection(paper, yearSection, researchPiece, function(p)
		{
			return "papers:year:" + p.year;
		});
	addToTags(paper, tagSection, researchPiece);
	
	sortChildren(titleSection);
	sortChildren(authorSection);
	sortChildren(yearSection);
	sortChildren(tagSection);
	
	for(var child in authorSection.childNodes)
		sortChildren(child);
	for(var child in tagSection.childNodes)
		sortChildren(child);
}

function addToAuthors(paper, section, node)
{
	for(var i = 0; i < paper.authors.length; i++)
	{
		var authorSub = document.getElementById("research:author:" + paper.authors[i]);
		if(!authorSub)
		{
			var newAuthorDiv = document.createElement("div");
			newAuthorDiv.id = "research:author:" + paper.authors[i];
			newAuthorDiv.innerHTML = "<h2>" + paper.authors[i] + "</h2>";
			section.appendChild(newAuthorDiv);
			authorSub = newAuthorDiv;
		}
		var addnode = node.cloneNode(true);
		addnode.id = "papers:author:" + paper.authors[i] + ":" + paper.year + ":" + paper.id;
		authorSub.appendChild(addnode);
	}
}

function addToTags(paper, section, node)
{
	for(var i = 0; i < paper.tags.length; i++)
	{
		var tagSub = document.getElementById("research:tag:" + paper.tags[i]);
		if(!tagSub)
		{
			var newTagDiv = document.createElement("div");
			newTagDiv.id = "research:tag:" + paper.tags[i];
			newTagDiv.innerHTML = "<h2>" + paper.tags[i] + "</h2>";
			section.appendChild(newTagDiv);
			tagSub = newTagDiv;
		}
		var addnode = node.cloneNode(true);
		addnode.id = "papers:tag:" + paper.tags[i] + ":" + paper.year + ":" + paper.id;
		tagSub.appendChild(addnode);
	}
}
	
function addSection(paper, section, node, createID)
{
	var addnode = node.cloneNode(true);
	addnode.id = createID(paper);
	section.appendChild(addnode);
}

function sortChildren(element)
{
	var items = element.childNodes;
	var itemsArr = [];
	for (var i in items) {
		if (items[i].nodeType == 1) { // get rid of the whitespace text nodes
			itemsArr.push(items[i]);
		}
	}

	itemsArr.sort(function(a, b) {
	  return a.id == b.id
			  ? 0
			  : (a.id > b.id ? 1 : -1);
	});

	for (i = 0; i < itemsArr.length; ++i) {
	  element.appendChild(itemsArr[i]);
	}
}