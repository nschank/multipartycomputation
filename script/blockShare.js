function mouseOverListener(elem, block)
{
    return function()
    {
		block.style.display="block";
		block.style.left=Math.min(window.innerWidth-block.clientWidth, elem.getBoundingClientRect().left + 10)+"px";
		block.style.top=Math.min(window.innerHeight-block.clientHeight, elem.getBoundingClientRect().bottom)+"px";
    };
	
}

function mouseOutListener(elem,block)
{
    return function(event)
    {
        var right = elem.getBoundingClientRect().right;
		//if(event.clientX > right && event.clientX < right+10) return;
		block.style.display="none";
    };	
}

function keepOpen(block)
{
    return function()
    {
		block.style.display="block";
    };
}

function definitelyClose(block)
{
    return function()
    {
		block.style.display="none";
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
    };
}