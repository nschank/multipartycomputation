window.addEventListener("load", addGlossaryListeners, true);

function addGlossaryListeners()
{
	var definable = document.getElementsByClassName("definable");
	var glossary = document.createElement("div");
	for(var i = 0; i < definable.length; i++)
	{
		var word = definable.item(i).innerHTML;
		var definition = define(word);
		if(definition === null)
		{
			word = definable.item(i).getAttribute("data-define");
			definition = define(word);
		}
		if(definition === null) continue;
		addDOMG(definable.item(i), word, definition, glossary);
	}
	document.body.appendChild(glossary);
}

function toTitleCase(str)
{
    return str.charAt(0).toUpperCase() + str.substr(1).toLowerCase();
}

function g_removeSpaces(str)
{
	if(str.length == 0) return "";
	else if(str.charAt(0) == ' ') return g_removeSpaces(str.substring(1));
	else return str.charAt(0) + g_removeSpaces(str.substring(1));
}

function addDOMG(elem, word, definition, glossary)
{
	var block = document.getElementById("gl_block"+g_removeSpaces(word));
	
	if(block === null)
	{
		var block = document.createElement("div");
		block.className="gl_block";
		block.id = "gl_block" + g_removeSpaces(word);
		
		block.innerHTML += "<h2 class=\"gl_word\">Definition: " + toTitleCase(word) + "</h2><p class=\"gl_definition\">";
		block.innerHTML += definition;
		block.innerHTML += "</p><p class=\"gl_help\">Visit the full <a href=\"multipartycomputation.org/glossary.html\">glossary</a>.</p>";
		glossary.appendChild(block);
		block.addEventListener('mouseover',keepOpen(block));
		block.addEventListener('mouseout',maybeClose(block));
        window.addEventListener('scroll', definitelyClose(block));
	}
	elem.addEventListener('mouseover',mouseOverListener(elem, block));
	elem.addEventListener('mouseout',mouseOutListener(elem, block));
}