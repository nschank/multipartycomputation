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
	block.innerHTML += "<span class=\"equationInfo\">" + fill(id) + "</span>";
	block.innerHTML += "<p class=\"equationhelp\">For more help reading equations like these, visit our <a href=\"equations.html\">equations page</a></p>";
	document.body.appendChild(block);
	block.addEventListener('mouseover',keepOpen(block));
	block.addEventListener('mouseout',maybeClose(block));
        window.addEventListener('scroll', definitelyClose(block));
        equation.addEventListener('mouseover',mouseOverListener(equation,block));
	equation.addEventListener('mouseout',mouseOutListener(equation,block));
}