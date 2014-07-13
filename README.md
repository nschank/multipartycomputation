#Multiparty Computation
This is a website intended to document, summarize, and contextualize the cryptographic process of multiparty computation. This is an independent project being funded by Brown University's [UTRA program](https://www.brown.edu/academics/college/fellowships/utra/) under faculty advisor John Savage. Until August 31st, this project and website will be under the sole maintenance of the student researcher Nicolas Schank, so we will not be taking pull requests. After that date, any willing and interested participants would be welcome.

We are hosted [here](http://multipartycomputation.org/). Nick can be contacted at [multipartycomputationorg@gmail.com](mailto:multipartycomputationorg@gmail.com).

##Layout
The site has three main sections:
- Research
- Learning
- Next Steps
A description of their intended contents, purposes, and layout follows.

###Research
This is currently the main focus of the site, and almost all changes are being made here (as of 12 July 2014).

####Storage
All "major" research papers get their own page, which should be named [id].html, under /research/papers/. Their PDF, if present, should be stored as /research/papers/pdf/[id].pdf.
Every research paper is stored as a single object in /script/researchList.js, and all are stored in a large array (which is ordered, in the file, in id-number order). This file is loaded by /script/researchLoad.js for usage by the four navigation pages under /research/.
Research paper objects have the following properties:
- *abstractText*: A string which contains the entire (HTML- and LaTeX-capable) text of this paper's abstract (or introduction, if applicable).
- *authors*: An (ordered) array of strings, where each string is the name of one author who worked on the paper.
- *hasPage*: (default: false) A boolean value which indicates whether the paper has a page. If not set or set to a falsy value, the navigation pages will not attempt to link to this paper.
- *id*: A number that specifies the location of that paper's webpage, if one is present. Must be unique.
- *include*: (default: true) A boolean value indicating whether or not to include this paper in navigation. Set to false to make all pages ignore this paper. Currently, all papers considered 'related but not major' have this set to false. When the site is more public, we will probably shift to only including those papers which actually have a webpage.
- *length*: The length of this paper (in published pages).
- *self_ref*: (default: "") A string which includes a citation for this paper. Not currently used, but should be included if hasPage is true for possible later usage.
- *tags*: An (unordered, though preferably alphabetical) array of tags which relate to this paper's content. If this paper's page is not finished, MUST include the tag "Unsorted". We will get to standardizing tags soon.
- *year*: The year when this paper was published.

####Individual Pages
Research pages (/research/papers/[id].html) consist of two main components, several optional components, and a few small (and mostly automatic) components. A template can be found at /research/papers/template.html.
Research pages always include:
- An **Overview** section, which consists of:
	- Introduction, a small introduction into who made the paper, why, and a very quick introduction to its effects on the field.
	- Goals and Results, self explanatory.
	- Assumptions, a description of what assumptions (and under what model) this paper's results require.
	- Definitions, a list of verbatim (or slightly contextualized) definitions made by the paper. Each should link to the section in which that word/phrase was defined.
	- Theorems, a list of the paper's theorems. Each should link to that theorem's statement in the paper.
	- Protocols, if applicable, a description of each protocol formulated in the paper. See the template page for a sample description. Each should link to the section where the protocol was first formulated.
	- Further Reading, self explanatory.
	- Referencing This Paper, self explanatory.

##Design Choices

###Use of JavaScript
This was done because this site is meant to be easily extensible and changeable. Normally, such extensibility would be done with server-side code; alas, we have no server (keep in mind, a single college student is hosting this site alone). As such, we must depend on JavaScript, lest we fall back to pure HTML. If we ever get independent funding for a server, we will of course move to a more consistent and safe client/server model.
We avoided the use of jQuery mostly because most JavaScript was very simple and it really wouldn't have added much.
We note that JavaScript will likely always be required for most useful pages anyway, due to our usage of MathJax. We currently have no intention of switching away from MathJax.

##Implementations
We have another branch, intended for implementation, at impl_publish. Any changes there should also be pulled into the submodule in gh-pages under /nextsteps/code. In order to reflect the change in the JavaScript tree used by the website's code viewer, /nextsteps/update.py must be run after any updates to the submodule.

##Dependencies
- MathJax (almost everywhere, for LaTeX rendering)
- PrismJS (code coloration on /nextsteps/codeViewer.html)