

var def_internal = {
	"adaptive adversary":{title:"adaptive adversary", def:[{type:"p", content:"If a protocol is secure in the adaptive adversary model, that means that even if members of a protocol are allowed to choose to become malicious <i>during</i> the run of a protocol, they will still not succeed. This is in contrast to the static model, in which all parties must decide whether or not to be malicious before the protocol begins."}]},

	"algorithm":{title:"algorithm", def:[{type:"p", content:"A sequence of steps followed in order to determine a value. In complexity theory, an algorithm is any Turing machine that is guaranteed to halt."}]},

	"associativity":{title:"associativity", def:[{type:"p", content:"A function \\(f(\\cdot,\\cdot)\\) is associative if, for any \\(a,b,c\\), \\(f(f(a,b),c)=f(a,f(b,c))\\). In other words, application order of the function does not matter. Keep in mind that this does NOT mean that order of <i>inputs</i> doesn't matter; there is no guarantee that \\(f(a,b)=f(b,a)\\)."}]},
	
	"authenticated network":{title:"authenticated network", def:[{type:"p", content:"An authenticated network is one in which it is assumed that any party is always sure who sent a given message. This is often assumed in multiparty computation research, as it simplifies the exposition of a protocol when the addition of a simple signature scheme would ensure the same thing while merely complicating explanation."}]},
	
    "bias":{title:"bias", def:[{type:"p", content:"A systematic error which skews all values in a particular direction or towards a particular value."}]},
	
	"Blum integer":{title:"Blum integer", def:[{type:"p", content:"A Blum integer is any composite number \\(N\\) with two prime factors \\(p,q\\) where \\(p\\equiv q\\equiv3\\bmod4\\). Blum integers are important largely due to special properties their <span class=\"definable\" data-define=\"quadratic residue\">quadratic residues</span> hold."}]},
	
	"BPP":{title:"BPP", def:[{type:"p", content:"Bounded Probabilistic Polynomial time is the class of problems that can be solved by a probabilistic <span class=\"definable\">Turing machine</span> in polynomial time with an error upper-bounded by &frac13; over 1 run of the algorithm."}]},
	
	"broadcast channel":{title:"broadcast channel", def:[{type:"p", content:"A broadcast channel is a cryptographic primitive referring to any communication method that any party can write to and all parties can read from. This allows a protocol to very simply refer to the ability of any party to send a single message to every other party, while every party agrees that the same message was sent to everyone. Simplifies the exposition of a protocol, since Byzantine Agreement <span data-broken-link=true></span>allows for the same thing, but with many more complications."}]},
	
	"Byzantine fault":{title:"Byzantine fault", def:[{type:"p", content:"In distributed computing, a Byzantine fault is a mistake made by any single processor (or, metaphorically, any participant). Any type of mistake can be a Byzantine fault: purposeful, memory-based, communication-based, and so on. <span class=\"definable\" data-define=\"malicious adversary\">Malicious adversaries</span> make Byzantine faults."}]},
	
	"cartesian product":{title:"cartesian product", def:[{type:"p", content:"The Cartesian product of two sets is the set of all ordered pairs formed by taking an element from the first set followed by an element from the second set. As an example, a full deck of playing cards can be described as {A,2,3,4...K}\\(\\times\\){♠, ♥, ♦, ♣}. The Cartesian product is often extended into many dimensions, as in the expression \\(A\\times B\\times C\\times D\\), which would be the set of all ordered quadruples \\((a,b,c,d)\\) that can be formed from an \\(a\\in A, b\\in B, c\\in C, d\\in D\\)."}]},
	
	"clawfree":{title:"clawfree", def:[{type:"p", content:"A pair of <span class=\"definable\" data-define=\"trapdoor permutation\">trapdoor permutations</span> \\(f_1,f_2\\) is clawfree if there does not exist a PPT algorithm which can determine a triplet of values \\((x,y,z)\\) such that \\(f_1(x)=f_2(y)=z\\). Such a triplet is called a claw."}]},
    
	"coin flip":{title:"coin flip", def:[{type:"p", content:"A common metaphor in cryptography research to refer to the creation of a truly random bit: 1 (heads) or 0 (tails). Note that, without external stimulus, it is impossible for a computer to create true randomness. There will be a tutorial on <span data-broken-link=true>randomness</span>."}]},
    
	"collusion":{title:"collusion", def:[{type:"p", content:"Secret or illegal cooperation or conspiracy, especially in order to cheat or deceive others."}]},
	
	"combined oblivious transfer":{title:"combined oblivious transfer", def:[{type:"p", content:"A subproblem of <span class=\"definable\">oblivious transfer</span> in which Alice and Bob possess secrets \\(a,b\\) respectively, and Alice wants to compute \\(g(a,b)\\) for some \\(g\\), but Alice cannot not learn \\(b\\), and Bob cannot learn either \\(a\\) or \\(g(a,b)\\)."}]},
	
	"commitment":{title:"commitment", def:[{type:"p", content:"A commitment scheme is a cryptographic primitive in which Alice wants to ensure Bob does not try to change his secret value mid-computation in order to alter a result to his benefit. Alice asks Bob for a <i>commitment</i> to his secret value \\(x\\): a value \\(c\\) and fixed <span class=\"definable\">PPT</span> opening-algorithm \\(O\\) which satisfies the following properties:"}, {type:"ul", content:[{type:"li",content:[{type:"p",content:"\\(\\nexists\\) PPT algorithm \\(A\\) such that \\(A(c)=x\\). In other words, \\(c\\) doesn't break the privacy of \\(x\\) by itself."}]},{type:"li",content:[{type:"p",content:"Bob can later give Alice some \\(o\\) such that \\(O(C,o)=x\\). In other words, Bob knows a value which 'opens' the commitment, and proves to Alice that the value was what he started with."}]},{type:"li",content:[{type:"p",content:"\\(\\nexists\\) PPT cheating-algorithm that, given a commitment \\(c\\), can with more than <span class=\"definable\" data-define=\"negligible\">negligible</span> probability produce a second pair \\((\\bar{x},\\bar{o})\\) where \\(x\\neq\\bar{x}\\) and \\(O(C,\\bar{o})=\\bar{x}\\). In other words, Bob can't reliably produce <i>any</i> value that he could convince Alice he started with other than \\(x\\) itself."}]}]}]},
    
	"commutativity":{title:"commutativity", def:[{type:"p", content:"The state of being independent of order, when referring to a binary function. As an example, addition of numbers is commutative because \\(a+b=b+a\\;\\forall\\;a,b\\). Subtraction is not commutative because \\(a-b\\neq b-a\\) for most values of \\(a,b\\)."}]},
	
	"completeness":{title:"completeness", def:[{type:"p", content:"In complexity, completeness refers to the property of a problem being the 'hardest' or 'most expressive' in its class. In other words, this problem can be reduced to from any other problem within the class under which it is complete."}]},
	
	"computational indistinguishability":{title:"computational indistinguishability", def:[{type:"p", content:"Two distributions are computationally indistinguishable if there exists no polynomial time adversary which can accurately predict whether a particular element was chosen from one or the other. In this case 'accurately' specifically means that the probability the adversary is correct in any case is at best negligibly better than &frac12;. For distributions \\(h\\) and \\(k\\), this is sometimes written \\(h\\stackrel{comp}{\\equiv}k\\)."}]},
	
	"connectivity":{title:"connectivity", def:[{type:"p", content:"The connectivity of a graph \\(G\\) is the minimal number of nodes or edges which can be removed in order to make the graph disconnected. That is, if \\(G=(V,E)\\) has edge-connectivity \\(c\\), then:"},{type:"ol",content:[{type:"li",content:[{type:"p",content:"There is some pair of vertices \\(v_1,v_2\\) such that the minimum distance between them is exactly \\(c\\)."}]},{type:"li",content:[{type:"p",content:"In some graph \\(G'=(V,E')\\), where \\(|E'|=|E|-c\\), there exists no path connecting \\(v_1,v_2\\)."}]},{type:"li",content:[{type:"p",content:"There does not exist a \\(c'\\lt c\\) where the above conditions are true."}]}]}]},
	
	"cryptographic security":{title:"cryptographic security", def:[{type:"p", content:"A protocol is cryptographically secure if adversaries cannot learn the inputs of other parties within a bounded time, generally under certain assumptions about the hardness of a particular (cryptographic) problem. This is in contrast to <span class=\"definable\">information theoretic security</span>."}]},
	
	"cyclic code":{title:"cyclic code", def:[{type:"p", content:"A cyclic code is a type of <span class=\"definable\">error-correcting code</span> in which any codeword for \\(x\\) can be right or left shifted any number of times and still be a correct codeword for \\(x\\)."}]},
	
	"diameter of a graph":{title:"diameter of a graph", def:[{type:"p", content:"The diameter of a graph is the length of the shortest path between the two points most distant from each other in the graph. So for graph \\(G\\) with diameter \\(d\\):"},{type:"ol", content:[{type:"li",content:[{type:"p",content:"There is some pair of points \\(v_1,v_2\\) where the shortest path in \\(G\\) between them has length exactly \\(d\\)."}]},{type:"li",content:[{type:"p",content:"There is no pair of points \\(w_1,w_2\\) in \\(G\\) such that the distance between them is greater than \\(d\\)."}]}]}]},
	
	"Discrete Fourier Transform":{title:"Discrete Fourier Transform", def:[{type:"p", content:"The Discrete Fourier Transform of some function uses a set of 'equally spaced' points on the function (where the inputs are all intermediate powers of an \\(n\\)th <span class=\"definable\">root of unity</span>) to create a function within the frequency domain of the function."}]},
	
	"discrete logarithm problem":{title:"discrete logarithm problem", def:[{type:"p", content:"A discrete logarithm is any integer \\(k\\) solving the equation \\(b^k=g\\), where \\(b,g\\) are two elements in a <span class=\"definable\">group</span>. At current time, the discrete log problem is believed to be computationally hard; that is, no method currently exists that solves it in time polynomial in the size of the group."}]},
    
	"encryption":{title:"encryption", def:[{type:"p", content:"A method of representing a message (generally called <i>plaintext</i> or \\(m\\)) as an encoded message (<i>ciphertext</i> or \\(c\\)) such that the following properties are true:"},{type:"ol",content:[{type:"li",content:[{type:"p",content:"There exists a <b>decryption</b> algorithm (often called \\(D\\))"}]},{type:"li",content:[{type:"p",content:"Using the decryption algorithm, any \\(c\\) is mapped back to its original message \\(m\\)"}]},{type:"li",content:[{type:"p",content:"Without the decryption algorithm, no adversary (\\(\\mathcal{A}\\)) can learn \\(m\\) from \\(c\\) with more than <span class=\"definable\">negligible</span> probability."}]}]},{type:"p",content:"This is the most common definition of encryption, but many exist in research to serve many different purposes. The first lesson in the tutorial section goes through several other common ones."}]},
	
	"error-correcting code":{title:"error-correcting code", def:[{type:"p", content:"An error-correcting code is some way of encoding data with redundancy so that, even over a noisy channel (or in the face of <span class=\"definable\" data-define=\"Byzantine fault\">Byzantine faults</span>), the data can be decoded correctly. Formally, an error-correcting code consists of some encoding function \\(C\\,:\\;\\Sigma^n\\mapsto\\Sigma^k\\) and decoding/correcting function \\(D\\,:\\;\\Sigma^k\\mapsto\\Sigma^n\\), where \\(\\Sigma\\) is the alphabet of the code (often a finite field), \\(n\\) is the <i>block length</i> of the code, and \\(k\\) is the <i>message length</i>. \\(C,D\\) are an error-correcting code with a <i>minimum distance</i> of \\(d\\) if \\(D(N^{\\frac{d-1}{2}}(C(x)))=x\\) for all \\(N\\), where we define the noise function \\(N^y\\,:\\;\\Sigma^x\\mapsto\\Sigma^x\\) to return its input with no more than \\(y\\) bits flipped. The output of the encoding function is a <i>codeword</i>."}]},
	
	"Fermat-Euler theorem":{title:"Fermat-Euler theorem", def:[{type:"p", content:"The Fermat-Euler theorem is a generalization of Fermat's Little Theorem which states that: $$\\operatorname{gcd}(a,\\,n)\\quad\\Rightarrow\\quad a^{\\operatorname\\phi(n)}\\equiv1\\pmod n.$$"}]},
	
	"GF(2)":{title:"GF(2)", def:[{type:"p", content:"The <span class=\"definable\">group</span> of two elements, generally \\(\\{0,1\\}\\). Has two group operations: AND (multiplication) and XOR (addition)."}]},
	
	"group":{title:"group", def:[{type:"p", content:"In abstract algebra, a set \\(G\\) is a group under a well-defined binary operation \\(*\\) if:"},{type:"ul", content:[{type:"li",content:[{type:"p",content:"There is an <i>identity</i> element \\(e\\) such that, for any element \\(g\\in G, g*e=e*g=g\\)"}]},{type:"li",content:[{type:"p",content:"The set is <i>closed</i> under \\(*\\): for any \\(g_1,g_2\\in G, g_1*g_2\\in G\\)."}]},{type:"li",content:[{type:"p",content:"Any element \\(g\\in G\\) has a unique <i>inverse</i> \\(g^{-1}\\) such that \\(g*g^{-1}=g^{-1}*g=e\\)."}]},{type:"li",content:[{type:"p",content:"The group is <i>associative</i> under the operation: for any \\(g_1,g_2,g_3\\in G,\\)\\((g_1*g_2)*g_3=g_1*(g_2*g_3)\\)."}]}]}]},
	
	"hardcore bit":{title:"hardcore bit", def:[{type:"p", content:"The hardcore bit (or hardcore predicate) of a <span class=\"definable\">one-way function</span> \\(f\\) is a function \\(b : Domain_f\\mapsto\\{0,1\\}\\) such that \\(\\exists\\) PPT algorithm \\(B\\) where \\(B(x)=b(x)\\), but \\(\\nexists\\) PPT algorithm \\(F\\) where \\(F(f(x))=b(x)\\). In other words, its some way of producing a bit from \\(x\\) so that there's no way to guess what that bit will be from \\(f(x)\\) that's more reliable than flipping a coin."}]},
	
	"indistinguishable":{title:"indistinguishable", def:[{type:"p", content:"Informally, two sets or distributions may be called indistinguishable if there doesn't exist an efficient (<span class=\"definable\">PPT</span>) algorithm for differentiating them that has more than <span class=\"definable\">negligible</span> advantage."}]},
	
	"information theoretic security":{title:"information theoretic security", def:[{type:"p", content:"A protocol is information theoretically secure (more technically, private) if an adversary with infinite computing power could not determine the inputs of the other parties. Information theoretic security essentially implies that a protocol truly gives other parties absolutely no useful information about the inputs that it secures. This is in contrast to <span class=\"definable\">cryptographic security</span>."}]},
	
	"injective":{title:"injective", def:[{type:"p", content:"An injective function (sometimes a one-to-one function or just an injection) is a function which preserves distinctness; that is, if two inputs are distinct, their outputs must also be distinct. More formally, a function \\(f\\) is injective iff \\(f(x)=f(y)\\Rightarrow x=y\\)."}]},
	
	"interpolation":{title:"interpolation", def:[{type:"p", content:"Interpolation is the process of using a finite set of data points in order to produce new data points. This is often used in the form of polynomial interpolation, where a unique polynomial of degree \\(d+1\\) or less can perfectly interpolate any set of \\(d\\) points, i.e. the interpolation function contains those exact points."}]},
	
	"isomorphic":{title:"isomorphic", def:[{type:"p", content:"Being of identical shape and structure. In the case of graphs, two graphs are isomorphic if and only if there is a one-to-one mapping of vertices and edges between the two graphs. More informally, it could be said that one graph is a 'relabelling' of the other."}]},
	
	"Jacobi symbol":{title:"Jacobi symbol", def:[{type:"p", content:"The Jacobi symbol of \\(m\\) modulo \\(n\\) (often written \\(\\left(\\frac m n\\right)\\)) is a way to describe \\(m\\)'s <span class=\"definable\" data-define=\"quadratic residue\">quadratic residuosity</span> modulo \\(n\\). All \\(m\\) with Jacobi symbol -1 are quadratic nonresidues, though the the converse is not true."}]},
    
	"malicious adversary":{title:"malicious adversary", def:[{type:"p", content:"An adversary who deviates from the rules of a protocol."}]},
	
	"mental poker":{title:"mental poker", def:[{type:"p", content:"A common metaphor in cryptography research to refer to playing a fair game (often poker, as the name would suggest) over a distance without the need of a trusted third party to facilitate. Physical cards would be the third party in a real game of poker."}]},
	
	"multiplicativity":{title:"multiplicativity", def:[{type:"p", content:"A function \\(f\\) is multiplicative if \\(\\forall \\,a,b,\\;f(ab)=f(a)f(b)\\)."}]},
    
	"negligible":{title:"negligible", def:[{type:"p", content:"A function \\(\\mu(x)\\) is negligible if \\(\\forall \\,c&gt;0\\;\\exists\\,n\\) such that, \\(\\forall x>n,\\quad|\\mu(x)| &lt; \\frac{1}{x^c}\\). More informally, a function is negligible if no inverse polynomial approaches 0 as quickly as it does. As another, again more formal definition, \\(\\mu(x)\\) is negligible if \\(\\mu(x)\\in o(\\frac{1}{poly(x)})\\)."}]},
	
	"NP":{title:"NP", def:[{type:"p", content:"The set of all decision problems which can be verified in time polynomial in the length of the input. More intuitively, it is the set of all questions such that, given a possible answer to the question, we can efficiently determine whether the answer is correct. As an example, the question \"What are the factors of x?\" is in NP because, given a set of factors, we just need to multiply them together to check if we get x. Notice that this is an example of a question that we cannot necessarily <i>solve</i> efficiently, but can still <i>verify</i> efficiently."}]},
	
	"NP-complete":{title:"NP-complete", def:[{type:"p", content:"The set of all decision problems which are in <span class=\"definable\">NP</span>, but are also at least as hard as the hardest problem in NP: that is, a problem in NP-complete can be used as a subroutine to solve any problem in NP."}]},
	
	"oblivious transfer":{title:"oblivious transfer", def:[{type:"p", content:"A type of protocol between 2 parties in which party A communicates some set \\(M\\) of messages to party B, with the guarantee that party B will receive either one or no \\(m\\in M\\), and party A will not learn which message \\(m\\) was received (if any). Oblivious transfer is <span class=\"definable\" data-define=\"completeness\">complete</span> in the class of multiparty computation."}]},
    
	"one-way function":{title:"one-way function", def:[{type:"p", content:"A function which is easy to evaluate but hard to invert. We will have more information about one-way functions soon, and they will be part of a tutorial."}]},
	
	"one-way permutation":{title:"one-way permutation", def:[{type:"p", content:"A one-way <span class=\"definable\">permutation</span> is a <span class=\"definable\">one-way function</span> \\(f\\) that satisfies the property that \\(Domain_f=Codomain_f\\)."}]},
	
	"PPT":{title:"PPT", def:[{type:"p", content:"Probabilistic Polynomial Time"}]},
	
	"passive adversary":{title:"passive adversary", def:[{type:"p", content:"An adversary who calculates and communicates according to the rules of a protocol, but additionally attempts to do extra calculation in order to break the privacy constraint."}]},
	
	"permutation":{title:"permutation", def:[{type:"p", content:"Any bijective (onto and 1-1) function with the same domain and range. In other words, any function that arranges its entire domain into a one-way cycle (or group of cycles) and, on any input, returns whatever is 'next' in the cycle."}]},
	
	"polynomial ring":{title:"polynomial ring", def:[{type:"p", content:"A polynomial ring \\(E[X]\\) is the ring of polynomial equations where the indeterminate (variable) is \\(X\\) and the coefficients of the polynomial are all elements of the ring \\(E\\)."}]},
	
	"polynomial time":{title:"polynomial time", def:[{type:"p", content:"A protocol, algorithm, or Turing machine runs in polynomial time if, for input of size \\(n\\), its average time complexity is in \\(O(n^k)\\) for a constant \\(k\\). In general, an algorithm that runs in polynomial time is called 'efficient' or 'fast'. If a protocol can be brute-forced in polynomial-time, it is generally considered insecure. An algorithm that can be calculated in polynomial time is in the complexity class <b>P</b>."}]},
    
	"privacy constraint":{title:"privacy constraint", def:[{type:"p", content:"A constraint on what information (that you own) can be learned by other parties who are members of the computation."}]},
    
	"probability density function":{title:"probability density function", def:[{type:"p", content:"A function (often called \\(p(i)\\)) used to calculate the probability of a particular instance \\(i\\) within the entire domain of possible inputs \\(I\\). Must satisfy the constraints that:"},{type:"ol",content:[{type:"li",content:[{type:"p",content:"Given any \\(i\\in I\\), \\(0\\leq p(i)\\leq 1\\)"}]},{type:"li",content:[{type:"p",content:"\\(\\sum\\limits_{i\\in I} p(i) = 1\\)"}]}]},{type:"p",content:"Basically, all possibilities must have a well-defined probability, and no possibilities may be missing."}]},
	
	"quadratic residue":{title:"quadratic residue", def:[{type:"p", content:"An integer \\(q\\) is a quadratic residue \\(\\bmod{n}\\) if there exists some \\(x\\) such that \\(q \\equiv x^2\\pmod{n}\\). Determining whether a number is a quadratic residue \\(\\bmod{n}\\) for a <span class=\"definable\">Blum integer</span> \\(n\\) is not known to be as hard as factoring, but is considered to be computationally very difficult."}]},
	
	"root of unity":{title:"root of unity", def:[{type:"p", content:"An \\(n\\)th root of unity in a field is any element \\(e\\) such that \\(e^n=i\\), where \\(i\\) is the identity element in the field. A primitive \\(n\\)th root of unity in a field is some \\(\\omega\\) such that \\(\\omega^n=i\\) but there is no positive integer \\(k\\lt n\\) such that \\(\\omega^k=i\\). \\(n\\)-th roots of unity in the complex numbers \\(\\mathbb C\\) are the \\(n\\)th roots of 1, and are of the form \\(\\cos\\left(\\frac{2x\\pi}{n}\\right)+i\\sin\\left(\\frac{2x\\pi}{n}\\right)\\;\\forall\\;x\\in[0,n-1]\\)."}]},
	
	"secrecy network":{title:"secrecy network", def:[{type:"p", content:"A network in which it is assumed that any party can communicate with any other party without anyone else knowing what was said. Often assumed in multiparty computation, as the use of an encryption scheme makes this relatively simple while significantly complicating explanation of the protocol."}]},
	
    "security constraint":{title:"security constraint", def:[{type:"p", content:"A constraint on what information (that you own) can be learned by other parties who are not intended to be members of the computation."}]},
    
	"singular matrix":{title:"singular matrix", def:[{type:"p", content:"A singular matrix is one with a determinant of 0, and therefore no inverse. That is, iff a matrix \\(M\\) is singular, \\(\\operatorname{det}(M)=0\\) and \\(\\nexists N\\) s.t. \\(MN=I\\)."}]},
	
	"stochastic":{title:"stochastic", def:[{type:"p", content:"randomly determined; having a random probability distribution or pattern that may be analyzed statistically but may not be predicted precisely."}]},
	
	"straight-line program":{title:"straight-line program", def:[{type:"p", content:"A program that does not loop. All circuit diagrams (which do not contain memory circuits) can be expressed as straight-line programs."}]},
	
	"synchronous network":{title:"synchronous network", def:[{type:"p", content:"A synchronous network is one in which it is assumed that all members of the network have a perfectly synchronized clock, and messages between members are received either instantly or within a particular verifiable time frame."}]},
    
	"transcendental":{title:"transcendental", def:[{type:"p", content:"non-algebraic; not the root of a non-zero polynomial equation with rational coefficients. All transcendental numbers are irrational, and technically most irrational numbers are transcendental, but most irrational numbers commonly used are non-transcendental. The most commonly used transcendental numbers are \\(\\pi\\) and \\(e\\)."}]},
	
	"trapdoor function":{title:"trapdoor function", def:[{type:"p", content:"Informally, a trapdoor function is a function that can be computed efficiently, but cannot be inverted efficiently without using a piece of special information called the 'trapdoor'. More formally, a function \\(f\\) is a trapdoor function if, for \\(\\forall x\\in Domain_{f,k}\\) (where \\(k\\) is the security parameter) \\(\\exists y\\) such that \\(\\exists c_0\\) where \\(T(f(x))\\in O(k^{c_0})\\), \\(\\nexists c_1\\) where \\(T(f^{-1}(x))\\in O(k^{c_1})\\), and \\(\\exists\\) algorithm \\(A\\) and integer \\(c_2\\) such that \\(A(f(x),y)=f^{-1}(x)\\) and \\(T(A)\\in O(k^{c_2})\\)."}]},
	
	
	"trapdoor permutation":{title:"trapdoor permutation", def:[{type:"p", content:"A trapdoor <span class=\"definable\">permutation</span> is a <span class=\"definable\">trapdoor function</span> \\(f\\) which satisfies the property that \\(Domain_f = Codomain_f\\)."}]},
	
	"trusted party":{title:"trusted party", def:[{type:"p", content:"Any entity not participating directly in a protocol on which both parties can depend to be totally honest and to maintain complete privacy."}]},
    
	"Turing machine":{title:"Turing machine", def:[{type:"p", content:"A theoretical machine often used in computer science to analyze the complexity or limits of a computation. It is imagined as an infinitely long 'tape' separated into discrete boxes or squares. At any given moment, there is a specific square being 'scanned'; based on the symbol within that square, the machine may choose either to write to that square or not, and then to move either left or right one square."}]},
	
	"WLOG":{title:"WLOG", def:[{type:"p", content:"without loss of generality"}]},
	
	"Vandermonde matrix":{title:"Vandermonde matrix", def:[{type:"p", content:"A Vandermonde matrix is an \\(m\\times n\\) in which every row is a geometric progression. It is of the form: $$\\left[\\begin{array}{ccccc} 1 & \\alpha_1 & \\alpha_1^2 & \\cdots & \\alpha_1^{n-1}\\\\1 & \\alpha_2 & \\alpha_2^2 & \\cdots & \\alpha_2^{n-1}\\\\\\vdots & \\vdots & \\vdots & \\ddots & \\vdots\\\\1 & \\alpha_n & \\alpha_n^2 & \\cdots & \\alpha_{n-1}^m\\end{array}\\right].$$ It has some special properties that make it useful; in particular, if all the \\(\\alpha\\)'s are distinct, then the matrix is invertible. Additionally, it transforms the coefficient matrix of any polynomial of degree less than \\(n-1\\) to the values of that polynomial at the points given by the \\(\\alpha\\)'s. These two facts taken together mean that, with unique \\(\\alpha\\)'s, the inverse Vandermonde matrix will <span class=\"definable\" data-define=\"interpolation\">interpolate</span> a polynomial's coefficients from its values."}]},
	
	"zero knowledge proof":{title:"zero knowledge proof", def:[{type:"p", content:"A zero knowledge proof is a cryptographic primitive in which Peggy (the prover) proves something to Victor (the verifier) without revealing any information other than the fact that the statement is true. Let's say that the statement that Peggy is trying to prove is a predicate \\(P\\) over her private inputs \\(x\\in X\\), and she is trying to convince Victor that \\(P(X)=1\\). We say that a proof is zero knowledge if, after the proof is over: "},{type:"ul", content:[{type:"li",content:[{type:"p",content:"Victor is convinced. More formally, the probability that Peggy lied, based on the information conveyed to him during the protocol, is <span class=\"definable\">negligible</span>."}]},{type:"li",content:[{type:"p",content:"Victor has learned nothing he couldn't have learned from just trusting Peggy in the first place. More formally, the probability distribution that Victor can create over the possible value of \\(X\\) from the information received during the protocol is <span class=\"definable\">indistinguishable</span> from the probability distribution he could create knowing only that \\(P(X)=1\\)."}]}]}]}
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