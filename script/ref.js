window.addEventListener("load", addReferenceListeners, true);

function addReferenceListeners()
{
	var counter = 0;
	var elements = document.getElementsByClassName("reference");
	for(var i = 0; i < elements.length; i++)
	{
		var elem = elements.item(i);
		addDOMR(elem.id.substring(3), i);
		elem.id += "-"+i;
		addFunctionsR(elem);
	}
}

function addDOMR(id, secondid)
{
	var block = document.createElement("div");
	block.className="refBlock";
	block.id = "ref"+id+"-"+secondid+"block";
	
	block.innerHTML += "<h2>Reference</h2>";
	block.innerHTML += "<p>"+document.getElementById("citation"+id).innerHTML+"</p>";
	block.innerHTML += "<p><a href=\"" + authorLink(id) + "\">See more by this author</a></p>";
	
	block.innerHTML += "<p class=\"refrefer\"><a href=\"#references\">See complete reference list</a></p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpenR);
	block.addEventListener('mouseout',maybeCloseR);
}

function addFunctionsR(reference)
{
	reference.addEventListener('mouseover',mouseOverListenerR);
	reference.addEventListener('mouseout',mouseOutListenerR);
}

function mouseOverListenerR(event)
{
	var thisreference = event.target;
	if(thisreference.className != "reference") return;
	var thisBlock = document.getElementById(thisreference.id+"block");
	thisBlock.style.display="block";
	thisBlock.style.left=thisreference.getBoundingClientRect().right+"px";
	thisBlock.style.top=thisreference.getBoundingClientRect().top+"px";
}

function mouseOutListenerR(event)
{
	var thisreference = event.target;
	if(thisreference.className != "reference") return;
	var right = thisreference.getBoundingClientRect().right;
	if(event.clientX > right && event.clientX < right+10) return;
	var thisBlock = document.getElementById(thisreference.id+"block");
	thisBlock.style.display="none";
}

function keepOpenR(event)
{
	var thisBlock = event.target;
	if(event.target.className == "refBlock")
		thisBlock.style.display="block";
}

function maybeCloseR(event)
{
	var thisBlock = event.target;
	var rect = thisBlock.getBoundingClientRect();
	if(rect.left < event.clientX && event.clientX < rect.right)
		if(rect.top < event.clientY && event.clientY < rect.bottom)
			return;
	if(event.target.className == "refBlock")
		thisBlock.style.display="none";
}