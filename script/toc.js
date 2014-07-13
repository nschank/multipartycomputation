window.addEventListener("load", fillTOC, true);

var headings = ["H1","H2","H3","H4","H5"];
var headingName = /[\d\.]*\s*(.+?)[\.:]?$/;

function fillTOC()
{
	var table = buildTable();
	var ol = document.createElement("ol");
	ol.setAttribute('type', 'A');
	ol = buildNode(table,ol);
	
	var tocs = document.getElementsByClassName("main_toc");
	
	for(var i = 0; i < tocs.length; i++)
	{
		var toc = tocs.item(i);
		toc.innerHTML = "<h4 style=\"color:#333333\">Table of Contents</h4>";
		toc.appendChild(ol.cloneNode(true));
	}
}

function buildTable()
{
	var sections = document.getElementsByClassName("main_section");
	var table = [];
	
	for(var i = 0; i < sections.length; i++)
	{
		var section = sections.item(i);
		var sectionName = name(section);
		if(!sectionName)
			continue;
		var children = subs(sectionName.parentNode);
		
		table.push({name:sectionName.innerHTML, id:section.getAttribute('id'), children:children});
	}
	return table;
}

function name(section)
{
	var children = section.children;
	for(var i = 0; i < children.length; i++)
	{
		if(headings.indexOf(children.item(i).tagName) > -1
			&& headingName.test(children.item(i).innerHTML))
			return children.item(i);
		var dfs = name(children.item(i));
		if(dfs) return dfs;
	}
	return undefined;
}

function subs(container)
{
	var children = container.children;
	var table = [];
	
	for(var i = 0; i < children.length; i++)
	{
		if(children.item(i).tagName !== "SECTION") continue;
		var sectionName = name(children.item(i));
		if(!sectionName) continue;
		var sectionId = children.item(i).getAttribute('id');
		if(!sectionId) continue;
		var sectionChildren = subs(children.item(i));
		
		table.push({name:sectionName.innerHTML, id:sectionId, children:sectionChildren});
	}
	
	return table;
}

function buildNode(table, list)
{
	for(var i = 0; i < table.length; i++)
	{
		var li = document.createElement("li");
		var link = document.createElement("a");
		link.setAttribute("href","#" + table[i].id);
		link.innerHTML = headingName.exec(table[i].name)[1];
		
		li.appendChild(link);
		if(table[i].children)
			li.appendChild(buildNode(table[i].children, document.createElement("ol")));
		list.appendChild(li);
	}
	return list;
}