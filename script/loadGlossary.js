window.addEventListener("load", loadGlossary, true);

function loadGlossary()
{
	Object.keys(def_internal).forEach(addWord);
}

function addWord(word)
{
	var block = document.createElement("div");
	block.className = "main_window main_fullwidth";
	
	var term = document.createElement("div");
	term.className = "gl_term";
	term.innerHTML = "<h3>" + def_internal[word].title + "</h3>";
	
	var def = document.createElement("div");
	def.className = "gl_def";
	def.innerHTML = blockConstruct(def_internal[word].def);
	
	block.appendChild(term);
	block.appendChild(def);
	
	document.getElementById("gl_append").appendChild(block);
}