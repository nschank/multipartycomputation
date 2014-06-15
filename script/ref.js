window.addEventListener("load", addReferenceListeners, true);

function addReferenceListeners()
{
	var elements = document.getElementsByClassName("reference");
	for(var i = 0; i < elements.length; i++)
	{
		var elem = elements.item(i);
		addDOMR(elem.getAttribute("data-citation"), elem);
	}
}

function addDOMR(id, elem)
{
	var reference = document.getElementById("citation"+id);
	if(reference === null) return false;
	
	var block = document.createElement("div");
	block.className="ref_block";
	
	block.innerHTML += "<h2>Reference</h2>";
	block.innerHTML += "<p>"+reference.innerHTML+"</p>";
	block.innerHTML += "<p><a href=\"" + authorLink(id) + "\">See more by this author</a></p>";
	
	block.innerHTML += "<p class=\"ref_goto_citations\"><a href=\"#referencelist\">See complete reference list</a></p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpen(block));
	block.addEventListener('mouseout',maybeClose(block));
        window.addEventListener('scroll', definitelyClose(block));
        elem.addEventListener('mouseover',mouseOverListener(elem,block));
	elem.addEventListener('mouseout',mouseOutListener(elem,block));
	return true;
}


