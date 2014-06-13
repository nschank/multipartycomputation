

var def_internal = {
    "bias":"Still left to define",
    "commutativity":"",
    "mental poker":"Still left to define",
    "negligible":"Still left to define",
    "privacy constraint":"",
    "security constraint":"",
    
    "coin flip":"A common metaphor in cryptography research to refer to the creation of a truly random bit: 1 (heads) or 0 (tails). Note that, without external stimulus, it is impossible for a computer to create true randomness. There will be a tutorial on randomness.",
    "collusion":"Secret or illegal cooperation or conspiracy, especially in order to cheat or deceive others.",
    "encryption":"A method of representing a message (generally called <i>plaintext</i> or \\(m\\)) as an encoded message (<i>ciphertext</i> or \\(c\\)) such that the following properties are true:<ol><li>There exists a <b>decryption</b> algorithm (often called \\(D\\))</li><li>Using the decryption algorithm, any \\(c\\) is mapped back to its original message \\(m\\)</li><li>Without the decryption algorithm, no adversary (\\(\\mathcal{A}\\)) can learn \\(m\\) from \\(c\\) with more than <span class=\"definable\">negligible</span> probability.</li></ol>This is the most common definition of encryption, but many exist in research to serve many different purposes. The first lesson in the tutorial section goes through several other common ones.",
    "one-way function":"A function which is easy to evaluate but hard to invert. We will have more information about one-way functions soon, and they will be part of a tutorial.",
    "permutation":"Any bijective (onto and 1-1) function with the same domain and range. In other words, any function that arranges its entire domain into a one-way cycle (or group of cycles) and, on any input, returns whatever is 'next' in the cycle.",
    "stochastic":"randomly determined; having a random probability distribution or pattern that may be analyzed statistically but may not be predicted precisely"
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