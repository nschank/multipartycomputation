window.addEventListener("load", addGlossaryListeners, true);

/**
 * Looks for all words marked as 'definable' and attempts to create definition boxes for them.
 */
function addGlossaryListeners()
{
	var definable = document.getElementsByClassName("definable");
	
	//Attempt to organize the DOM a bit better by putting the definition boxes in their own div; 
	// we will put the div in the DOM immediately, however, because they are all styled as
	// 'display:none' and 'position:fixed', so they add very little overhead.
	var glossary = document.createElement("div");
	document.body.appendChild(glossary);
	
	for(var i = 0; i < definable.length; i++)
	{
		//Try the actual text being surrounded first; barring that, use data-define.
		var word = definable.item(i).innerHTML;
		var definition = define(word);
		if(definition === null && definable.item(i).getAttribute("data-define"))
		{
			word = definable.item(i).getAttribute("data-define");
			definition = define(word);
		}
		//The following occurs only if a word is not in the glossary.
		if(definition === null) 
		{
			console.log("Developer note: word \"" + word + "\" missing from dictionary.");
			continue;
		}
		addDOMG(definable.item(i), word, definition, glossary);
	}
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
	var block = document.getElementById("gl_block" + g_removeSpaces(word));
	
	if(!block)
	{
		block = document.createElement("div");
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