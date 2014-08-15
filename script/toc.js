window.addEventListener("load", fillTOC, true);

var headings = ["H1","H2","H3","H4","H5"];
var headingName = /[\d\.]*\s*(.+?)[\.:]?$/;

function fillTOC()
{
	var table = buildTable();
	console.log(table);
	/*var ol = document.createElement("ol");
	ol.setAttribute('type', 'A');
	ol = buildNode(table,ol);
	
	var tocs = document.getElementsByClassName("main_toc");
	
	for(var i = 0; i < tocs.length; i++)
	{
		var toc = tocs.item(i);
		toc.innerHTML = "<h4 style=\"color:#333333\">Table of Contents</h4>";
		toc.appendChild(ol.cloneNode(true));
	}
	
	addNav(table);*/
}

function buildTable()
{
	var sections = document.getElementsByClassName("main_section");
	var table = [];
	
	for(var i = 0; i < sections.length; i++)
	{
		console.log(i);
		var section = sections.item(i);
		var sectionName = name(section);
		console.log(sectionName);
		if(!sectionName)
			continue;
		var children = subs(section);
		console.log(table);
		
		table.push({name:sectionName, id:section.id, children:children});
	}
	return table;
}

function name(section)
{
	var name = section.getAttribute("data-section-name")
	if(name)
		return name;
	var re = /<h(\d)>[\d\.]*\s*(.+?)<\/h\1>/; 
	var str = section.innerHTML;
	var m = re.exec(str);
	
	if(!m) return null;
	return m[2];
}

function subs(container)
{
	var table = [];
	var children = container.childNodes;
	for(var i = 0; i < children.length; i++)
	{
		var thisElement = children.item(i);
		var myChildren = subs(thisElement);
		if(thisElement.tagName === "SECTION")
		{
			var myname = name(thisElement);
			var id = thisElement.id;
			if(name && id)
			{
				table.push({name:myname, id:id, children:myChildren});
				continue;
			}
		}
		myChildren.forEach(function(elem){ table.push(elem); });
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
		
		var num;
		if(num = document.getElementById(table[i].id).getAttribute('data-section-number'))
			li.setAttribute('value', num);
		
		li.appendChild(link);
		if(table[i].children)
			li.appendChild(buildNode(table[i].children, document.createElement("ol")));
		list.appendChild(li);
	}
	return list;
}

function addNav(table)
{
	var cursor = {last:undefined, current:undefined};
	addNavHelper(cursor, table, 0);
	addNavDiv(cursor.current, cursor.last, undefined);
}

function addNavHelper(cursor, objectArray, index)
{
	if(!objectArray || objectArray.length <= index)
		return;
	if(cursor.current)
		addNavDiv(cursor.current, cursor.last, objectArray[index]);
	cursor.last = cursor.current;
	cursor.current = objectArray[index];
	addNavHelper(cursor, cursor.current.children, 0);
	addNavHelper(cursor, objectArray, index+1);
}

function addNavDiv(current, last, next)
{
	if(!current) return;
	var div = document.createElement("div");
	div.className = "rp_linkbox";
	var html = "<a href=\"#" + current.id + "\" title=\"Link to this section\">&#8592;</a>";
	if(last)
		html += "&nbsp;&nbsp;<a href=\"#" + last.id + "\" title=\"Previous section\">&#8593;</a>";
	if(next)
		html += "&nbsp;<a href=\"#" + next.id + "\" title=\"Next section\">&#8595;</a>";
	div.innerHTML = html;
	current.nameElement.parentNode.insertBefore(div, current.nameElement);
}