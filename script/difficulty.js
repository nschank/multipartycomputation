var difficulty;

if(!(difficulty = readCookie("difficulty")))
	setDifficulty(1);
	
function setDifficulty(d)
{
	if(d >0 && d < 7)
	{
		createCookie("difficulty",d,1);
		difficulty = d;
	}
}

/**
 * Lovingly copied from http://www.quirksmode.org/js/cookies.html
 */
function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

/**
 * Ditto
 */
function createCookie(name,value,days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}