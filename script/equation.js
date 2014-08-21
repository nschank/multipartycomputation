window.addEventListener("load", addEquationListeners, true);

/**
 * Finds all elements with the "load-equation" class and, if they:
 *	- Have a data-equation attribute
 *  - That data-equation attribute matches with a defined equation
 *  - Have a difficulty greater than or equal to the user's difficulty
 * Those elements are added to the "equation" class and have an attached floating block.
 */
function addEquationListeners()
{
	//All equations set for loading
    var allEquations = document.getElementsByClassName("load-equation");
	
	//Where to put all the floating divs.
	var blockDiv = document.createElement("div");
	document.body.appendChild(blockDiv);
	
    for(var i = 0; i < allEquations.length; i++)
    {
		var equation = allEquations.item(i);
		var equationID = equation.getAttribute("data-equation");
		if(!equationID) continue;
		
		var equationInfo = fill(equationID);
		
		createFloatingDiv(blockDiv, equation, 
			"eq_block" + equationInfo.blockName, 
			eqBlockConstructor(equationInfo),
			metaOpenPredicate(equationInfo.difficulty));
		equation.setAttribute("data-difficulty",equationInfo.difficulty);
		equation.className += " loaded-equation";
    }
	MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
	
	updateEquations(difficulty);
}

/**
 Creates a constructor which can be called by blockShare.js's "createFloatingDiv" method in order
 to make an equation block.
 */
function eqBlockConstructor(equationInfo)
{
	return function()
	{
		var block = document.createElement("div");
		block.className="equationBlock";

		var h2 = document.createElement("h2");
		h2.innerHTML = "Equation Info";
		block.appendChild(h2);

		blockConstruct(block, equationInfo.info, "equationinfo");
		return block;
	}
}

function updateEquations(difficulty)
{
	var allEquations = document.getElementsByClassName("loaded-equation");
	
	for(var i = 0; i < allEquations.length; i++)
	{
		var updateSpan = allEquations.item(i);
		
		if(difficulty > updateSpan.getAttribute("data-difficulty"))
			$(updateSpan).removeClass("equation");
		else $(updateSpan).addClass("equation");
	}
}