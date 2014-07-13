window.addEventListener("load", citeSources, true);

function citeSources()
{
	var elements = document.getElementsByClassName("reference");
	var referenceBlocks = document.createElement("div");
	var references = {refToBlock:{}, div:referenceBlocks};
	var toorder = {};
	var inorder = [];
	
	for(var i = 0; i < elements.length; i++)
	{
		var elem = elements.item(i);
		var cite = elem.getAttribute("data-citation");
		var citedElem = document.getElementById(cite);
		
		if(!citedElem) continue;
		if(toorder[cite] === undefined)
			toorder[cite] = inorder.push(citedElem);
		var link = "<a href=\"#" + cite + "\">[" + toorder[cite] + "]</a>";
		elem.innerHTML = link;
		addDOMR(elem.getAttribute("data-citation"), elem, references);
	}
	
	document.body.appendChild(references.div);
	var list = document.getElementById("referencelist");
	while(list.firstChild)
		list.removeChild(list.firstChild);
	inorder.forEach(function(elem) { list.appendChild(elem); });
}

function addDOMR(id, elem, references)
{
	var reference = document.getElementById(id);
	if(reference === null) return false;
	
	var block = references.refToBlock[id];
	
	if(!block)
	{
		block = document.createElement("div");
		block.className="ref_block";
		block.id="ref_block"+id;
		
		block.innerHTML += "<h2>Reference</h2>";
		block.innerHTML += "<p>"+reference.innerHTML+"</p>";
		block.innerHTML += "<p><a href=\"" + authorLink(id) + "\">See more by this author</a></p>";
	
		block.innerHTML += "<p class=\"ref_goto_citations\"><a href=\"#referencelist\">See complete reference list</a></p>";
		
		references.refToBlock[id] = block;
		references.div.appendChild(block);
		
		block.addEventListener('mouseover',keepOpen(block));
		block.addEventListener('mouseout',maybeClose(block));
		window.addEventListener('scroll', definitelyClose(block));
	}
    elem.addEventListener('mouseover',mouseOverListener(elem,block));
	elem.addEventListener('mouseout',mouseOutListener(elem,block));
	return true;
}
