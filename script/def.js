

var def_internal = {
    "bias":"A systematic error which skews all values in a particular direction or towards a particular value.",
	
	"cartesian product":"The Cartesian product of two sets is the set of all ordered pairs formed by taking an element from the first set followed by an element from the second set. As an example, a full deck of playing cards can be described as {A,2,3,4...K}\\(\times\\){♠, ♥, ♦, ♣}. The Cartesian product is often extended into many dimensions, as in the expression \\(A\\times B\\times C\\times D\\), which would be the set of all ordered quadruples \\((a,b,c,d)\\) that can be formed from an \\(a\in A, b\\in B, c\\in C, d\\in D\\).",
    
	"coin flip":"A common metaphor in cryptography research to refer to the creation of a truly random bit: 1 (heads) or 0 (tails). Note that, without external stimulus, it is impossible for a computer to create true randomness. There will be a tutorial on randomness.",
    
	"collusion":"Secret or illegal cooperation or conspiracy, especially in order to cheat or deceive others.",
    
	"commutativity":"The state of being independent of order, when referring to a binary function. As an example, addition of numbers is commutative because \\(a+b=b+a   \\forall a,b\\). Subtraction is not commutative because \\(a-b\\neq b-a\\) for most values of \\(a,b\\).",
    
	"encryption":"A method of representing a message (generally called <i>plaintext</i> or \\(m\\)) as an encoded message (<i>ciphertext</i> or \\(c\\)) such that the following properties are true:<ol><li>There exists a <b>decryption</b> algorithm (often called \\(D\\))</li><li>Using the decryption algorithm, any \\(c\\) is mapped back to its original message \\(m\\)</li><li>Without the decryption algorithm, no adversary (\\(\\mathcal{A}\\)) can learn \\(m\\) from \\(c\\) with more than <span class=\"definable\">negligible</span> probability.</li></ol>This is the most common definition of encryption, but many exist in research to serve many different purposes. The first lesson in the tutorial section goes through several other common ones.",
    
	"mental poker":"A common metaphor in cryptography research to refer to playing a fair game (often poker, as the name would suggest) over a distance without the need of a trusted third party to facilitate. Physical cards would be the third party in a real game of poker.",
    
	"negligible":"A function \\(\\mu(x\\) is negligible if \\(\\exists c,n\\) such that, \\(\\forall x>n |\\mu(x)| &lt; \\frac{1}{x^c}\\). More informally, a function is negligible if no inverse polynomial approaches 0 as quickly as it does.",
    
	"one-way function":"A function which is easy to evaluate but hard to invert. We will have more information about one-way functions soon, and they will be part of a tutorial.",
    
	"permutation":"Any bijective (onto and 1-1) function with the same domain and range. In other words, any function that arranges its entire domain into a one-way cycle (or group of cycles) and, on any input, returns whatever is 'next' in the cycle.",
    
	"privacy constraint":"A constraint on what information (that you own) can be learned by other parties who are members of the computation.",
    
	"probability density function":"A function (often called \\(p(i)\\)) used to calculate the probability of a particular instance \\(i\\) within the entire domain of possible inputs \\(I\\). Must satisfy the constraints that:<ol><li>Given any \\(i\\in I\\), \\(0\lte p(i)\lte 1\\)</li><li>\\(\\sum\\limits_{i\\in I} p(i) = 1\\)</li></ol>Basically, all possibilities must have a well-defined probability, and no possibilities may be missing.",
    "security constraint":"A constraint on what information (that you own) can be learned by other parties who are not intended to be members of the computation (or <i>adversaries</i>).",
    "stochastic":"randomly determined; having a random probability distribution or pattern that may be analyzed statistically but may not be predicted precisely",
    "transcendental":"non-algebraic; not the root of a non-zero polynomial equation with rational coefficients. All transcendental numbers are irrational, and technically most irrational numbers are transcendental, but most irrational numbers commonly used are non-transcendental. The most commonly used transcendental numbers are \\(\\pi\\) and \\(e\\).",
    "Turing machine":"A theoretical machine often used in computer science to analyze the complexity or limits of a computation. It is imagined as an infinitely long 'tape' separated into discrete boxes or squares. At any given moment, there is a specific square being 'scanned'; based on the symbol within that square, the machine may choose either to write to that square or not, and then to move either left or right one square."
};

var self_def = {};

function define(word)
{
    if(self_def[word] !== undefined)
        return self_def[word];
    if(def_internal[word] !== undefined)
        return def_internal[word];
    return null;
}