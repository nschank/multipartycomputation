window.addEventListener("load", addGlossaryListeners, true);

function addGlossaryListeners()
{
	var definable = document.getElementsByClassName("definable");
	for(var i = 0; i < definable.length; i++)
	{
		var word = definable.item(i).innerHTML;
		var definition = define(word);
		if(definition === null)
		{
			word = definable.item(i).id;
			definition = define(word);
		}
		if(definition === null) continue;
		addDOMG(i, word, definition);
		definable.item(i).id = "glossary"+i;
		addFunctionsG(definable.item(i));
	}
}

function toTitleCase(str)
{
    return str.charAt(0).toUpperCase() + str.substr(1).toLowerCase();
}

function addDOMG(id, word, definition)
{
	var block = document.createElement("div");
	block.className="gl_block";
	block.id = "glossary"+id+"block";
	
	block.innerHTML += "<h2 class=\"gl_word\">Definition: " + toTitleCase(word) + "</h2><p class=\"gl_definition\">";
	block.innerHTML += definition;
	block.innerHTML += "</p><p class=\"gl_help\">Visit the full <a href=\"glossary.html\">glossary</a>.</p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpenG);
	block.addEventListener('mouseout',maybeCloseG);
}

function addFunctionsG(word)
{
	word.addEventListener('mouseover',mouseOverListenerG);
	word.addEventListener('mouseout',mouseOutListenerG);
}

function mouseOverListenerG(event)
{
	var thisglossary = event.target;
	var thisBlock = document.getElementById(thisglossary.id+"block");
	thisBlock.style.display="block";
	thisBlock.style.left=thisglossary.getBoundingClientRect().right+"px";
	thisBlock.style.top=thisglossary.getBoundingClientRect().top+"px";
}

function mouseOutListenerG(event)
{
	var thisglossary = event.target;
	var right = thisglossary.getBoundingClientRect().right;
	var top = thisglossary.getBoundingClientRect().top;
	if(event.clientX > right && event.clientX < right+10)
		if(top < event.clientY)
			return;
	var thisBlock = document.getElementById(thisglossary.id+"block");
	thisBlock.style.display="none";
}

function keepOpenG(event)
{
	var thisBlock = event.target;
	if(event.target.className === "gl_block")
		thisBlock.style.display="block";
}

function maybeCloseG(event)
{
	var thisBlock = event.target;
	var rect = thisBlock.getBoundingClientRect();
	if(rect.left < event.clientX && event.clientX < rect.right)
		if(rect.top < event.clientY && event.clientY < rect.bottom)
			return;
	if(event.target.className === "gl_block")
		thisBlock.style.display="none";
}