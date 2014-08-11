var bs_z = 0;
var bs_open = [];

function bs_contains(lm) {
    var i = bs_open.length;
    while (i--)
		if (bs_open[i] === lm)
			return true;
    return false;
}

function bs_remove(lm) {
	var i = bs_open.indexOf(lm);
	if(i > -1)
		bs_open.splice(i,1);
}

function mouseOverListener(elem, block)
{
    return function()
    {
		block.style.display="block";
		block.style.left=Math.min(window.innerWidth-block.clientWidth, elem.getBoundingClientRect().left + 10)+"px";
		block.style.top=Math.min(window.innerHeight-block.clientHeight, elem.getBoundingClientRect().bottom)+"px";
		
		if(!bs_contains(block))
		{
			bs_open.push(block);
			bs_z++;
			block.style.zIndex = "" + bs_z;
		}
    };
	
}

function mouseOutListener(elem,block)
{
    return function(event)
    {
        var right = elem.getBoundingClientRect().right;
		block.style.display="none";
		if(bs_contains(block))
		{
			bs_remove(block);
			bs_z--;
		}
    };	
}

function keepOpen(block)
{
    return function()
    {
		block.style.display="block";
		if(!bs_contains(block))
		{
			bs_open.push(block);
			bs_z++;
			block.style.zIndex = "" + bs_z;
		}
    };
}

function definitelyClose(block)
{
    return function()
    {
		block.style.display="none";
		if(bs_contains(block))
		{
			bs_remove(block);
			bs_z--;
		}
    };
}

function maybeClose(block)
{
    return function(event)
    {
        var rect = block.getBoundingClientRect();
        if(rect.left < event.clientX && event.clientX < rect.right)
		if(rect.top < event.clientY && event.clientY < rect.bottom)
			return;
		block.style.display="none";
		if(bs_contains(block))
		{
			bs_remove(block);
			bs_z--;
		}
    };
}

function blockConstruct(blockContent, className)
{
	var classInsert = "";
	if(className)
		classInsert = " class=\"" + className + "\"";
	var html = "";
	for(var i = 0; i < blockContent.length; i++)
	{
		var thisInsert = classInsert;
		if(blockContent[i].className)
			thisInsert = " class=\"" + blockContent[i].className + "\"";
		switch(blockContent[i].type)
		{
			case "p":
				html += "<" + blockContent[i].type + thisInsert + ">" + blockContent[i].content + "</" + blockContent[i].type + ">";break;
			case "span":
				if(blockContent[i].href)
					html += "<a href=\"" + blockContent[i].href + "\">";
				html += "<" + blockContent[i].type + thisInsert + ">" + blockContent[i].content + "</" + blockContent[i].type + ">";
				if(blockContent[i].href)
					html+="</a>";
				break;
			case "ol": case "ul": case "li":
				html += "<" + blockContent[i].type + thisInsert + ">" + blockConstruct(blockContent[i].content) + "</" + blockContent[i].type + ">"; break;
			default: break;
		}
	}
	return html;
}