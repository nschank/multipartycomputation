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
	block.addEventListener('mouseover',keepOpenR(block));
	block.addEventListener('mouseout',maybeCloseR(block));
        window.addEventListener('scroll', definitelyCloseR(block));
        elem.addEventListener('mouseover',mouseOverListenerR(elem,block));
	elem.addEventListener('mouseout',mouseOutListenerR(elem,block));
	return true;
}


function mouseOverListenerR(elem, block)
{
    return function()
    {
	block.style.display="block";
	block.style.left=elem.getBoundingClientRect().right+"px";
	block.style.top=elem.getBoundingClientRect().top+"px";
    };
	
}

function mouseOutListenerR(elem,block)
{
    return function(event)
    {
        var right = elem.getBoundingClientRect().right;
	if(event.clientX > right && event.clientX < right+10) return;
	block.style.display="none";
    };	
}

function keepOpenR(block)
{
    return function()
    {
	block.style.display="block";
    };
}

function definitelyCloseR(block)
{
    return function()
    {
	block.style.display="none";
    };
}

function maybeCloseR(block)
{
    return function(event)
    {
        var rect = block.getBoundingClientRect();
        var thisBlock = event.target;
        if(rect.left < event.clientX && event.clientX < rect.right)
		if(rect.top < event.clientY && event.clientY < rect.bottom)
			return;
	if(event.target.className === "ref_block")
		thisBlock.style.display="none";
    };
}