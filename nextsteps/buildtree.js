window.addEventListener("load", createTreeView, true);

var nodeNum = 0;

var tree = [{type:"dir", name:"src", 
	children:[
		{type:"dir", name:"nschank",
		children:[
			{type:"dir", name:"crypto",
			children:[
			
			]},
			{type:"dir", name:"util",
			children:[
				{type:"file", name:"NIterators.java"}
			]},
			{type:"dir", name:"note",
			children:[
				{type:"file", name:"Immutable.java"}
			]}
		]}
	]}];

function createTreeView(event)
{
	var treediv = document.getElementById("codeviewtree");
	var ul = document.createElement("ul");
	
	tree.forEach(addLi(ul));
	treediv.appendChild(ul);
}

function addLi(addTo)
{
	return function(elem)
	{
		var li = document.createElement("li");
		
		if(elem.type === "dir")
		{
			var n = "node" + (nodeNum++);
			var input = document.createElement("input");
			input.setAttribute("type", "checkbox");
			input.id = n;
			
			var label = document.createElement("label");
			label.setAttribute("for", n);
			label.innerHTML = elem.name;
			
			li.appendChild(input);
			li.appendChild(label);
			var ul = document.createElement("ul");
			elem.children.forEach(addLi(ul));
			li.appendChild(ul);
			addTo.appendChild(li);
		}
		else
		{
			var ul = document.createElement("ul");
			var link = document.createElement("a");
			link.setAttribute("href", "#");
			link.setAttribute("class", "tree_file");
			link.innerHTML = elem.name;
			li.appendChild(link);
			ul.appendChild(li);
			addTo.appendChild(ul);
		}
		
		
	};
}

