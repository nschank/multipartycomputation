window.addEventListener("load", orderFootnotes, true);

function orderFootnotes()
{
	//Ensure there are footnotes
	var list = document.getElementById("footnotelist");
	if(!list) return;
	
	var elements = document.getElementsByClassName("footnote");
	var toorder = {};
	var inorder = [];
	
	for(var i = 0; i < elements.length; i++)
	{
		var elem = elements.item(i);
		var footnote = elem.getAttribute("data-footnote");
		var pointsTo = document.getElementById("footnote" + footnote);
		
		if(!pointsTo)
		{
			elem.parentNode.removeChild(elem);
			i--;
			continue;
		}
		if(toorder[footnote] === undefined)
			toorder[footnote] = inorder.push({content:pointsTo, pointsBack:[]});
		inorder[toorder[footnote]-1].pointsBack.push(i);
		elem.id = "fref" + i;
		var link = "<a href=\"#footnote" + footnote + "\">" + toorder[footnote] + "</a>";
		elem.innerHTML = link;
	}
	
	
	while(list.firstChild)
		list.removeChild(list.firstChild);
	inorder.forEach(function(elem) 
		{
			var pointUp = "";
			elem.pointsBack.forEach(function(backRef) { pointUp += "<a href=\"#fref" + backRef + "\">[^]</a>"; });
			elem.content.innerHTML = pointUp + " " + elem.content.innerHTML;
			list.appendChild(elem.content); 
		});
}