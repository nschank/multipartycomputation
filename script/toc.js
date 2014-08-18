window.addEventListener("load", fillTOC, true);

var re = /<h(\d)>[\d\.]*\s*(.+?)<\/h\1>/; 

/**
 * Finds all divs of class "main_toc", and fills them with a table of contents built from the section tags within the document.
 */
function fillTOC()
{
	var table = buildTable();
	
	var ol = document.createElement("ol");
	ol.setAttribute('type', 'A');
	ol = buildNode(table,ol);
	
	var h4 = document.createElement("h4");
	h4.innerHTML = "<h4 style=\"color:#333333\">Table of Contents</h4>";
	
	var tocs = document.getElementsByClassName("main_toc");
	
	for(var i = 0; i < tocs.length; i++)
	{
		var toc = tocs.item(i);
		toc.appendChild(h4.cloneNode(true));
		toc.appendChild(ol.cloneNode(true));
	}
	addNav(table);
}

/**
 * Searches all elements with class top_section and the children thereof to build a table of contents table.
 */
function buildTable()
{
	var sections = document.getElementsByClassName("top_section");
	var table = [];
	
	for(var i = 0; i < sections.length; i++)
	{
		var section = sections.item(i);
		var sectionName = name(section);
		if(!sectionName)
			continue;
		var children = subs(section);
		
		table.push({name:sectionName, id:section.id, children:children});
	}
	return table;
}

/**
 * The name of a section is preferably the contents of its data-section-name attribute, or barring that, the contents of the first heading within it.
 */
function name(section)
{
	var name = section.getAttribute("data-section-name")
	if(name)
		return name;
	var str = section.innerHTML;
	var m = re.exec(str);
	
	if(!m) return null;
	return m[2];
}
 
/**
 * Searches all the children of a container for sections with names and ids.
 */
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

/**
 * Builds the table of contents list from the table
 */
function buildNode(table, list)
{
	for(var i = 0; i < table.length; i++)
	{
		var li = document.createElement("li");
		var link = document.createElement("a");
		link.setAttribute("href","#" + table[i].id);
		link.innerHTML = table[i].name;
		
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

/**
 * Iterates through the table and adds a "current section", "prev section", and "next section" link at every id in the table.
 */
function addNav(table)
{
	var cursor = {last:undefined, current:undefined};
	addNavHelper(cursor, table, 0);
	//addNavHelper misses the last element
	addNavDiv(cursor.last, cursor.current, undefined);
}

/**
 * The current level being iterated at is objectArray. Children are checked first, since "next" is depth-first. cursor is the current
 * cursor object. Index is the index within the objectArray where the next element at the current level is located.
 */
function addNavHelper(cursor, objectArray, index)
{
	if(!objectArray || objectArray.length <= index)
		return;
	
	//Add navigation points if there is a current element
	if(cursor.current)
		addNavDiv(cursor.last, cursor.current, objectArray[index]);
		
	//Iterate to next element
	cursor.last = cursor.current;
	cursor.current = objectArray[index];
	//Check children first
	addNavHelper(cursor, cursor.current.children, 0);
	//Then do next at this level
	addNavHelper(cursor, objectArray, index+1);
}

/**
 * Adds the navigation buttons to a section. Last, next, and current are as expected. The navigation buttons are added to an rp_linkbox that is added as the
 * first child within document.getElementById(current.id)
 */
function addNavDiv(last, current, next)
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
	var elem = document.getElementById(current.id);
	elem.insertBefore(div, elem.firstChild);
}