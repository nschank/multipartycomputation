window.addEventListener("load", createTreeView, true);

var nodeNum = 0;

function createTreeView(event)
{
	var treediv = document.getElementById("codeviewtree");
	var ul = document.createElement("ul");
	
	tree.forEach(addLi(ul, "code"));
	treediv.appendChild(ul);
}

function pathToStr(path)
{
	if(path.length == 0) return "";
	else if(path.charAt(0) == '/') return pathToStr(path.substring(1));
	else return path.charAt(0) + pathToStr(path.substring(1));
}

function addLi(addTo, path)
{
	return function(elem)
	{
		var li = document.createElement("li");
		
		if(elem.type === "dir")
		{
			var n = "node" + pathToStr(path + "/" + elem.name);
			var input = document.createElement("input");
			input.setAttribute("type", "checkbox");
			input.id = n;
			
			var label = document.createElement("label");
			label.setAttribute("for", n);
			label.innerHTML = elem.name;
			
			li.appendChild(input);
			li.appendChild(label);
			var ul = document.createElement("ul");
			elem.children.forEach(addLi(ul, path + "/" + elem.name));
			li.appendChild(ul);
			addTo.appendChild(li);
		}
		else
		{
			var ul = document.createElement("ul");
			var link = document.createElement("a");
			link.setAttribute("href", "javascript:setCodeTo(\"" + path + "/" + elem.name+"\");");
			link.setAttribute("class", "tree_file");
			link.setAttribute("style", "font-size:12pt");
			link.innerHTML = elem.name;
			li.appendChild(link);
			ul.appendChild(li);
			addTo.appendChild(ul);
		}
	};
}

function setCodeTo(path)
{
	nodeNum = 0;
	var holder = document.getElementById("codeholder");
	holder.removeChild(document.getElementById("currentcode"));
	var newcode = document.createElement("pre");
	newcode.setAttribute("data-src", path);
	newcode.id = "currentcode";
	holder.appendChild(newcode);
	doLoadSrc();
}

