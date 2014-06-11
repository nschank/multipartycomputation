window.addEventListener("load", addEquationListeners, true);

function addEquationListeners()
{
	var counter = 0;
	var equation = document.getElementById("equation"+counter);
	while(equation != null)
	{
		addFunctionsE(equation,counter);
		addDOME(equation, counter);
		
		counter++;
		equation = document.getElementById("equation"+counter);
	}
}

function addDOME(equation,id)
{
	var block = document.createElement("div");
	block.className="equationBlock";
	block.id = "equation"+id+"block";
	
	block.innerHTML += "<h2>Equation Info</h2>";
	block.innerHTML += fill(id);
	block.innerHTML += "<p class=\"equationhelp\">For more help reading equations like these, visit our <a href=\"equations.html\">equations page</a></p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpenE);
	block.addEventListener('mouseout',maybeCloseE);
}

function addFunctionsE(equation,counter)
{
	equation.addEventListener('mouseover',mouseOverListenerE(equation,counter));
	equation.addEventListener('mouseout',mouseOutListenerE(equation,counter));
}

function mouseOverListenerE(equation,counter)
{
	return function(event)
	{
		var thisBlock = document.getElementById("equation" + counter+"block");
		thisBlock.style.display="block";
		thisBlock.style.left=equation.getBoundingClientRect().right+"px";
		thisBlock.style.top=equation.getBoundingClientRect().top+"px";
	};
}

function mouseOutListenerE(equation,counter)
{
	return function(event)
	{
		var right = equation.getBoundingClientRect().right;
		var top = equation.getBoundingClientRect().top;
		if(event.clientX > right && event.clientX < right+10)
			if(top < event.clientY)
				return;
		var thisBlock = document.getElementById("equation" + counter+"block");
		thisBlock.style.display="none";
	};
}

function keepOpenE(event)
{
	var thisBlock = event.target;
	if(event.target.className == "equationBlock")
		thisBlock.style.display="block";
}

function maybeCloseE(event)
{
	var thisBlock = event.target;
	var rect = thisBlock.getBoundingClientRect();
	if(rect.left < event.clientX && event.clientX < rect.right)
		if(rect.top < event.clientY && event.clientY < rect.bottom)
			return;
	if(event.target.className == "equationBlock")
		thisBlock.style.display="none";
}