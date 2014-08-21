window.addEventListener("load", addGlossaryListeners, true);

/**
 * Looks for all words marked as 'definable' and attempts to create definition boxes for them.
 */
function addGlossaryListeners()
{
	var allDefinables = document.getElementsByClassName("load-definable");
	
	//Attempt to organize the DOM a bit better by putting the definition boxes in their own div; 
	// we will put the div in the DOM immediately, however, because they are all styled as
	// 'display:none' and 'position:fixed', so they add very little overhead.
	var blockDiv = document.createElement("div");
	document.body.appendChild(blockDiv);
	
	for(var i = 0; i < allDefinables.length; i++)
	{
		//The span to (possibly) make a popup for
		var span = allDefinables.item(i);
		
		//Try data-define first; if it isn't present or doesn't work, we use innerHTML
		var word = span.getAttribute("data-define");
		var definitionObject;
		if(!word || !(definitionObject = define(word)))
		{
			word = span.innerHTML;
			definitionObject = define(word);
		}
		
		//The following occurs only if a word is not in the glossary.
		if(definitionObject === null) 
		{
			console.log("Developer note: word \"" + word + "\" missing from dictionary.");
			continue;
		}
		
		createFloatingDiv(blockDiv, span, 
			"gl_block" + definitionObject.blockName, 
			defBlockConstructor(definitionObject),
			metaOpenPredicate(definitionObject.difficulty));
			
		span.setAttribute("data-define", word)
		span.setAttribute("data-difficulty", definitionObject.difficulty);
		span.className += " loaded-definable";
	}
	MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
	
	updateGlossary(difficulty);
}

/**
 Creates a constructor which can be called by blockShare.js's "createFloatingDiv" method in order
 to make a definition block.
 */
function defBlockConstructor(definitionObject)
{
	return function()
	{
		var block = document.createElement("div");
		block.className="gl_block";

		var h2 = document.createElement("h2");
		h2.className = "gl_word";
		h2.innerHTML = "Definition: " + definitionObject.title;
		block.appendChild(h2);
		
		blockConstruct(block, definitionObject.def, "gl_definition");

		var help = document.createElement("p");
		help.className = "gl_help";
		help.innerHTML = "Visit the full <a href=\"http://www.multipartycomputation.org/glossary.html\">glossary</a>.";
		block.appendChild(help);

		return block;
	}
}

function updateGlossary(difficulty)
{
	var allDefinables = document.getElementsByClassName("loaded-definable");
	
	for(var i = 0; i < allDefinables.length; i++)
	{
		var updateSpan = allDefinables.item(i);
		
		if(difficulty > updateSpan.getAttribute("data-difficulty"))
			$(updateSpan).removeClass("definable");
		else $(updateSpan).addClass("definable");
	}
}

