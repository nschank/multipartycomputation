window.addEventListener("load", addEquationListeners, true);

function addEquationListeners()
{
    var elements = document.getElementsByClassName("equation");
    for(var i = 0; i < elements.length; i++)
    {
            var elem = elements.item(i);
            var num = elem.getAttribute("data-equation");
            if(num !== null)
		addDOME(elem, num);
    }
}

function addDOME(equation,id)
{
	var block = document.createElement("div");
	block.className="equationBlock";
	
	block.innerHTML += "<h2>Equation Info</h2>";
	block.innerHTML += fill(id);
	block.innerHTML += "<p class=\"equationhelp\">For more help reading equations like these, visit our <a href=\"equations.html\">equations page</a></p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpenE(block));
	block.addEventListener('mouseout',maybeCloseE(block));
        equation.addEventListener('mouseover',mouseOverListenerE(equation,block));
	equation.addEventListener('mouseout',mouseOutListenerE(equation,block));
}

function mouseOverListenerE(equation,block)
{
	return function(event)
	{
		block.style.display="block";
		block.style.left=equation.getBoundingClientRect().right+"px";
		block.style.top=equation.getBoundingClientRect().top+"px";
	};
}

function mouseOutListenerE(equation,block)
{
	return function(event)
	{
		var right = equation.getBoundingClientRect().right;
		var top = equation.getBoundingClientRect().top;
		if(event.clientX > right && event.clientX < right+10)
			if(top < event.clientY)
				return;
		block.style.display="none";
	};
}

function keepOpenE(block)
{
    return function(event)
    {
        block.style.display="block";
    };		
}

function maybeCloseE(block)
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