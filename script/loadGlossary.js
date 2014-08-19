window.addEventListener("load", loadGlossary, true);

function loadGlossary()
{
	Object.keys(def_internal).forEach(addWord);
}

function addWord(word)
{
	var defInfo = def_internal[word];

	var block = document.createElement("div");
	block.className = "main_window main_fullwidth";
	
	var term = document.createElement("div");
	term.className = "gl_term";
	
	var h3 = document.createElement("h3");
	h3.innerHTML = defInfo.title;
	term.appendChild(h3);
	
	var def = document.createElement("div");
	def.className = "gl_def";
	blockConstruct(def, defInfo.def, "gl_definition");
	
	block.appendChild(term);
	block.appendChild(def);
	
	document.getElementById("gl_append").appendChild(block);
}