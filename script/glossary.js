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
			word = definable.item(i).getAttribute("data-define");
			definition = define(word);
		}
		if(definition === null) continue;
		addDOMG(definable.item(i), word, definition);
	}
}

function toTitleCase(str)
{
    return str.charAt(0).toUpperCase() + str.substr(1).toLowerCase();
}

function addDOMG(elem, word, definition)
{
	var block = document.createElement("div");
	block.className="gl_block";
	
	block.innerHTML += "<h2 class=\"gl_word\">Definition: " + toTitleCase(word) + "</h2><p class=\"gl_definition\">";
	block.innerHTML += definition;
	block.innerHTML += "</p><p class=\"gl_help\">Visit the full <a href=\"multipartycomputation.org/glossary.html\">glossary</a>.</p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpen(block));
	block.addEventListener('mouseout',maybeClose(block));
        window.addEventListener('scroll', definitelyClose(block));
        elem.addEventListener('mouseover',mouseOverListener(elem, block));
	elem.addEventListener('mouseout',mouseOutListener(elem, block));
}