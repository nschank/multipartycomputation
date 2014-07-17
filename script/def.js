

var def_internal = {
	"adaptive adversary":"If a protocol is secure in the adaptive adversary model, that means that even if members of a protocol are allowed to choose to become malicious <i>during</i> the run of a protocol, they will still not succeed. This is in contrast to the static model, in which all parties must decide whether or not to be malicious before the protocol begins.",

	"algorithm":"A sequence of steps followed in order to determine a value. In complexity theory, an algorithm is any Turing machine that is guaranteed to halt.",

	"associativity":"A function \\(f(\\cdot,\\cdot)\\) is associative if, for any \\(a,b,c\\), \\(f(f(a,b),c)=f(a,f(b,c))\\). In other words, application order of the function does not matter. Keep in mind that this does NOT mean that order of <i>inputs</i> doesn't matter; there is no guarantee that \\(f(a,b)=f(b,a)\\).",
	
	"authenticated network":"An authenticated network is one in which it is assumed that any party is always sure who sent a given message. This is often assumed in multiparty computation research, as it simplifies the exposition of a protocol when the addition of a simple signature scheme would ensure the same thing while merely complicating explanation.",
	
    "bias":"A systematic error which skews all values in a particular direction or towards a particular value.",
	
	"Blum integer":"A Blum integer is any composite number \\(N\\) with two prime factors \\(p,q\\) where \\(p\\equiv q\\equiv3\\bmod4\\). Blum integers are important largely due to special properties their <span class=\"definable\" data-define=\"quadratic residue\">quadratic residues</span> hold.",
	
	"BPP":"Bounded Probabilistic Polynomial time is the class of problems that can be solved by a probabilistic <span class=\"definable\">Turing machine</span> in polynomial time with an error upper-bounded by &frac13; over 1 run of the algorithm.",
	
	"broadcast channel":"A broadcast channel is a cryptographic primitive referring to any communication method that any party can write to and all parties can read from. This allows a protocol to very simply refer to the ability of any party to send a single message to every other party, while every party agrees that the same message was sent to everyone. Simplifies the exposition of a protocol, since Byzantine Agreement <span data-broken-link=true></span>allows for the same thing, but with many more complications.",
	
	"cartesian product":"The Cartesian product of two sets is the set of all ordered pairs formed by taking an element from the first set followed by an element from the second set. As an example, a full deck of playing cards can be described as {A,2,3,4...K}\\(\\times\\){♠, ♥, ♦, ♣}. The Cartesian product is often extended into many dimensions, as in the expression \\(A\\times B\\times C\\times D\\), which would be the set of all ordered quadruples \\((a,b,c,d)\\) that can be formed from an \\(a\\in A, b\\in B, c\\in C, d\\in D\\).",
	
	"clawfree":"A pair of <span class=\"definable\" data-define=\"trapdoor permutation\">trapdoor permutations</span> \\(f_1,f_2\\) is clawfree if there does not exist a PPT algorithm which can determine a triplet of values \\((x,y,z)\\) such that \\(f_1(x)=f_2(y)=z. Such a triplet is called a claw.",
    
	"coin flip":"A common metaphor in cryptography research to refer to the creation of a truly random bit: 1 (heads) or 0 (tails). Note that, without external stimulus, it is impossible for a computer to create true randomness. There will be a tutorial on randomness.",
    
	"collusion":"Secret or illegal cooperation or conspiracy, especially in order to cheat or deceive others.",
	
	"combined oblivious transfer":"A subproblem of <span class=\"definable\">oblivious transfer</span> in which Alice and Bob possess secrets \\(a,b\\) respectively, and Alice wants to compute \\(g(a,b)\\) for some \\(g\\), but Alice cannot not learn \\(b\\), and Bob cannot learn either \\(a\\) or \\(g(a,b)\\).",
	
	"commitment":"A commitment scheme is a cryptographic primitive in which Alice wants to ensure Bob does not try to change his secret value mid-computation in order to alter a result to his benefit. Alice asks Bob for a <i>commitment</i> to his secret value \\(x\\): a value \\(c\\) and fixed <span class=\"definable\">PPT</span> opening-algorithm \\(O\\) which satisfies the following properties: <ul><li>\\(\\nexists\\) PPT algorithm \\(A\\) such that \\(A(c)=x\\). In other words, \\(c\\) doesn't break the privacy of \\(x\\) by itself.</li><li>Bob can later give Alice some \\(o\\) such that \\(O(C,o)=x\\). In other words, Bob knows a value which 'opens' the commitment, and proves to Alice that the value was what he started with.</li><li>\\(\\nexists\\) PPT cheating-algorithm that, given a commitment \\(c\\), can with more than <span class=\"definable\" data-define=\"negligible\">negligible</span> probability produce a second pair \\((\\bar{x},\\bar{o})\\) where \\(x\\neq\\bar{x}\\) and \\(O(C,\\bar{o})=\\bar{x}\\). In other words, Bob can't reliably produce <i>any</i> value that he could convince Alice he started with other than \\(x\\) itself.</li></ul>",
    
	"commutativity":"The state of being independent of order, when referring to a binary function. As an example, addition of numbers is commutative because \\(a+b=b+a\\;\\forall\\;a,b\\). Subtraction is not commutative because \\(a-b\\neq b-a\\) for most values of \\(a,b\\).",
	
	"completeness":"In complexity, completeness refers to the property of a problem being the 'hardest' or 'most expressive' in its class. In other words, this problem can be reduced to from any other problem within the class under which it is complete.",
	
	"computational indistinguishability":"Two distributions are computationally indistinguishable if there exists no polynomial time adversary which can accurately predict whether a particular element was chosen from one or the other. In this case 'accurately' specifically means that the probability the adversary is correct in any case is at best negligibly better than &frac12;. For distributions \\(h\\) and \\(k\\), this is sometimes written \\(h\\stackrel{comp}{\\equiv}k\\).",
	
	"cryptographic security":"A protocol is cryptographically secure if adversaries cannot learn the inputs of other parties within a bounded time, generally under certain assumptions about the hardness of a particular (cryptographic) problem. This is in contrast to <span class=\"definable\">information theoretic security</span>.",
	
	"discrete logarithm problem":"A discrete logarithm is any integer \\(k\\) solving the equation \\(b^k=g\\), where \\(b,g\\) are two elements in a <span class=\"definable\">group</span>. At current time, the discrete log problem is believed to be computationally hard; that is, no method currently exists that solves it in time polynomial in the size of the group.",
    
	"encryption":"A method of representing a message (generally called <i>plaintext</i> or \\(m\\)) as an encoded message (<i>ciphertext</i> or \\(c\\)) such that the following properties are true:<ol><li>There exists a <b>decryption</b> algorithm (often called \\(D\\))</li><li>Using the decryption algorithm, any \\(c\\) is mapped back to its original message \\(m\\)</li><li>Without the decryption algorithm, no adversary (\\(\\mathcal{A}\\)) can learn \\(m\\) from \\(c\\) with more than <span class=\"definable\">negligible</span> probability.</li></ol>This is the most common definition of encryption, but many exist in research to serve many different purposes. The first lesson in the tutorial section goes through several other common ones.",
	
	"GF(2)":"The <span class=\"definable\">group</span> of two elements, generally \\(\\{0,1\\}\\). Has two group operations: AND (multiplication) and XOR (addition).",
	
	"group":"In abstract algebra, a set \\(G\\) is a group under a well-defined binary operation \\(*\\) if:<ul><li>There is an <i>identity</i> element \\(e\\) such that, for any element \\(g\\in G, g*e=e*g=g\\)</li><li>The set is <i>closed</i> under \\(*\\): for any \\(g_1,g_2\\in G, g_1*g_2\\in G\\).</li><li>Any element \\(g\\in G\\) has a unique <i>inverse</i> \\(g^{-1}\\) such that \\(g*g^{-1}=g^{-1}*g=e\\).</li><li>The group is <i>associative</i> under the operation: for any \\(g_1,g_2,g_3\\in G,\\)\\((g_1*g_2)*g_3=g_1*(g_2*g_3)\\).</li></ul>",
	
	"hardcore bit":"The hardcore bit (or hardcore predicate) of a <span class=\"definable\">one-way function</span> \\(f\\) is a function \\(b : Domain_f\\mapsto\\{0,1\\}\\) such that \\(\\exists\\) PPT algorithm \\(B\\) where \\(B(x)=b(x)\\), but \\(\\nexists\\) PPT algorithm \\(F\\) where \\(F(f(x))=b(x)\\). In other words, its some way of producing a bit from \\(x\\) so that there's no way to guess what that bit will be from \\(f(x)\\) that's more reliable than flipping a coin.",
	
	"indistinguishable":"Informally, two sets or distributions may be called indistinguishable if there doesn't exist an efficient (<span class=\"definable\">PPT</span>) algorithm for differentiating them that has more than <span class=\"definable\">negligible</span> advantage.",
	
	"information theoretic security":"A protocol is information theoretically secure (more technically, private) if an adversary with infinite computing power could not determine the inputs of the other parties. Information theoretic security essentially implies that a protocol truly gives other parties absolutely no useful information about the inputs that it secures. This is in contrast to <span class=\"definable\">cryptographic security</span>.",
	
	"injective":"An injective function (sometimes a one-to-one function or just an injection) is a function which preserves distinctness; that is, if two inputs are distinct, their outputs must also be distinct. More formally, a function \\(f\\) is injective iff \\(f(x)=f(y)\\Rightarrow x=y\\).",
	
	"interpolation":"Interpolation is the process of using a finite set of data points in order to produce new data points. This is often used in the form of polynomial interpolation, where a unique polynomial of degree \\(d+1\\) or less can perfectly interpolate any set of \\(d\\) points, i.e. the interpolation function contains those exact points.",
	
	"isomorphic":"Being of identical shape and structure. In the case of graphs, two graphs are isomorphic if and only if there is a one-to-one mapping of vertices and edges between the two graphs. More informally, it could be said that one graph is a 'relabelling' of the other.",
	
	"Jacobi symbol":"The Jacobi symbol of \\(m\\) modulo \\(n\\) (often written \\(\\left(\\frac m n\\right)\\)) is a way to describe \\(m\\)'s <span class=\"definable\" data-define=\"quadratic residue\">quadratic residuosity</span> modulo \\(n\\). All \\(m\\) with Jacobi symbol -1 are quadratic nonresidues, though the the converse is not true.",
    
	"malicious adversary":"An adversary who deviates from the rules of a protocol.",
	
	"mental poker":"A common metaphor in cryptography research to refer to playing a fair game (often poker, as the name would suggest) over a distance without the need of a trusted third party to facilitate. Physical cards would be the third party in a real game of poker.",
	
	"multiplicativity":"A function \\(f\\) is multiplicative if \\(\\forall \\,a,b,\\;f(ab)=f(a)f(b)\\).",
    
	"negligible":"A function \\(\\mu(x)\\) is negligible if \\(\\forall \\,c&gt;0\\;\\exists\\,n\\) such that, \\(\\forall x>n,\\quad|\\mu(x)| &lt; \\frac{1}{x^c}\\). More informally, a function is negligible if no inverse polynomial approaches 0 as quickly as it does. As another, again more formal definition, \\(\\mu(x)\\) is negligible if \\(\\mu(x)\\in o(\\frac{1}{poly(x)})\\).",
	
	"NP":"The set of all decision problems which can be verified in time polynomial in the length of the input. More intuitively, it is the set of all questions such that, given a possible answer to the question, we can efficiently determine whether the answer is correct. As an example, the question \"What are the factors of x?\" is in NP because, given a set of factors, we just need to multiply them together to check if we get x. Notice that this is an example of a question that we cannot necessarily <i>solve</i> efficiently, but can still <i>verify</i> efficiently.",
	
	"NP-complete":"The set of all decision problems which are in <span class=\"definable\">NP</span>, but are also at least as hard as the hardest problem in NP: that is, a problem in NP-complete can be used as a subroutine to solve any problem in NP.",
	
	"oblivious transfer":"A type of protocol between 2 parties in which party A communicates some set \\(M\\) of messages to party B, with the guarantee that party B will receive either one or no \\(m\\in M\\), and party A will not learn which message \\(m\\) was received (if any). Oblivious transfer is <span class=\"definable\" data-define=\"completeness\">complete</span> in the class of multiparty computation.",
    
	"one-way function":"A function which is easy to evaluate but hard to invert. We will have more information about one-way functions soon, and they will be part of a tutorial.",
	
	"one-way permutation":"A one-way <span class=\"definable\">permutation</span> is a <span class=\"definable\">one-way function</span> \\(f\\) that satisfies the property that \\(Domain_f=Codomain_f\\).",
	
	"PPT":"Probabilistic Polynomial Time",
	
	"passive adversary":"An adversary who calculates and communicates according to the rules of a protocol, but additionally attempts to do extra calculation in order to break the privacy constraint.",
	
	"permutation":"Any bijective (onto and 1-1) function with the same domain and range. In other words, any function that arranges its entire domain into a one-way cycle (or group of cycles) and, on any input, returns whatever is 'next' in the cycle.",
	
	"polynomial time":"A protocol, algorithm, or Turing machine runs in polynomial time if, for input of size \\(n\\), its average time complexity is in \\(O(n^k)\\) for a constant \\(k\\). In general, an algorithm that runs in polynomial time is called 'efficient' or 'fast'. If a protocol can be brute-forced in polynomial-time, it is generally considered insecure. An algorithm that can be calculated in polynomial time is in the complexity class <b>P</b>.",
    
	"privacy constraint":"A constraint on what information (that you own) can be learned by other parties who are members of the computation.",
    
	"probability density function":"A function (often called \\(p(i)\\)) used to calculate the probability of a particular instance \\(i\\) within the entire domain of possible inputs \\(I\\). Must satisfy the constraints that:<ol><li>Given any \\(i\\in I\\), \\(0\\leq p(i)\\leq 1\\)</li><li>\\(\\sum\\limits_{i\\in I} p(i) = 1\\)</li></ol>Basically, all possibilities must have a well-defined probability, and no possibilities may be missing.",
	
	"quadratic residue":"An integer \\(q\\) is a quadratic residue \\(\\bmod{n}\\) if there exists some \\(x\\) such that \\(q \\equiv x^2\\pmod{n}\\). Determining whether a number is a quadratic residue \\(\\bmod{n}\\) for a <span class=\"definable\">Blum integer</span> \\(n\\) is not known to be as hard as factoring, but is considered to be computationally very difficult.",
	
	"secrecy network":"A network in which it is assumed that any party can communicate with any other party without anyone else knowing what was said. Often assumed in multiparty computation, as the use of an encryption scheme makes this relatively simple while significantly complicating explanation of the protocol.",
	
    "security constraint":"A constraint on what information (that you own) can be learned by other parties who are not intended to be members of the computation.",
    
	"stochastic":"randomly determined; having a random probability distribution or pattern that may be analyzed statistically but may not be predicted precisely.",
	
	"straight-line program":"A program that does not loop. All circuit diagrams (which do not contain memory circuits) can be expressed as straight-line programs.",
	
	"synchronous network":"A synchronous network is one in which it is assumed that all members of the network have a perfectly synchronized clock, and messages between members are received either instantly or within a particular verifiable time frame.",
    
	"transcendental":"non-algebraic; not the root of a non-zero polynomial equation with rational coefficients. All transcendental numbers are irrational, and technically most irrational numbers are transcendental, but most irrational numbers commonly used are non-transcendental. The most commonly used transcendental numbers are \\(\\pi\\) and \\(e\\).",
	
	"trapdoor function":"Informally, a trapdoor function is a function that can be computed efficiently, but cannot be inverted efficiently without using a piece of special information called the 'trapdoor'. More formally, a function \\(f\\) is a trapdoor function if, for \\(\\forall x\\in Domain_{f,k}\\) (where \\(k\\) is the security parameter) \\(\\exists y\\) such that \\(\\exists c_0\\) where \\(T(f(x))\\in O(k^{c_0})\\), \\(\\nexists c_1\\) where \\(T(f^{-1}(x))\\in O(k^{c_1})\\), and \\(\\exists\\) algorithm \\(A\\) and integer \\(c_2\\) such that \\(A(f(x),y)=f^{-1}(x)\\) and \\(T(A)\\in O(k^{c_2})\\).",
	
	
	"trapdoor permutation":"A trapdoor <span class=\"definable\">permutation</span> is a <span class=\"definable\">trapdoor function</span> \\(f\\) which satisfies the property that \\(Domain_f = Codomain_f\\).",
	
	"trusted party":"Any entity not participating directly in a protocol on which both parties can depend to be totally honest and to maintain complete privacy.",
    
	"Turing machine":"A theoretical machine often used in computer science to analyze the complexity or limits of a computation. It is imagined as an infinitely long 'tape' separated into discrete boxes or squares. At any given moment, there is a specific square being 'scanned'; based on the symbol within that square, the machine may choose either to write to that square or not, and then to move either left or right one square.",
	
	"WLOG":"without loss of generality",
	
	"zero knowledge proof":"A zero knowledge proof is a cryptographic primitive in which Peggy (the prover) proves something to Victor (the verifier) without revealing any information other than the fact that the statement is true. Let's say that the statement that Peggy is trying to prove is a predicate \\(P\\) over her private inputs \\(x\\in X\\), and she is trying to convince Victor that \\(P(X)=1\\). We say that a proof is zero knowledge if, after the proof is over: <ul><li>Victor is convinced. More formally, the probability that Peggy lied, based on the information conveyed to him during the protocol, is <span class=\"definable\">negligible</span>.</li><li>Victor has learned nothing he couldn't have learned from just trusting Peggy in the first place. More formally, the probability distribution that Victor can create over the possible value of \\(X\\) from the information received during the protocol is <span class=\"definable\">indistinguishable</span> from the probability distribution he could create knowing only that \\(P(X)=1\\).</li></ul>"
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