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
All "major" research papers get their own page, which should be named [id].html, under /research/papers/. Their PDF, if present, should be stored as /research/papers/pdf/[id].pdf. Liability will not be considered until early August.
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
- A transcription of either the page or the extended abstract of the page. 
	- A single webpage should only contain 20 pages of equations (for loading-time reasons), so if more than that is to be added, it should be split into multiple pages. Since we have not dealt with such an issue yet, we may consider doing either only an extended overview, or a regular overview followed by a summary (as opposed to a transcription).
	- See the "Alterations During Transcription" (/research/changes.html) for more information about what specific things are changed during transcription.
	- Annotations (i.e. paragraphs of class 'rp_annotation') should be added to clarify, contextualize, or correct anything within the original ('rp_original') text.
	- Proofs are annotations, and should ALWAYS be added, if known.
- Definitions {class: "definable", optional data-define: \[word to define\] \(if no data-define, then innerHTML is used\)} for words that should be explained or that were defined in the text, but not as officially as a term to be put in the 'definition' section.
	- Definitions used in many pages should be put in /script/def.js.
	- Definitions used only in the one page should be put in the JavaScript object self_def, in the form self_def["word"]="definition"
	- A word should only be 'definable' in its first usage in every section.
	- Definitions _can_ contain both equations and other definitions within them.
- Equation explanations (class: "equation", required data-equation=[equation name])
	- Equations that are difficult to remember (e.g. some variable "h" defined several sections ago) or difficult to understand should have an explanation attached.
	- Equations should be explained in _every_ usage.
	- Equations cannot contain _either_ definable words or other equation explanations.
	- Equation explanations should be enclosed in paragraphs.
- References (class: "reference", required data-citation=[citation-id])
	- Reference numbering is dealt with automatically. Citations will point to the reference at the bottom with identical (string) id to their 'data-citation'. Any citation with no matching reference should display as [?].
	- The reference list will be reordered automatically. Any reference not cited will not be displayed.
	- No particular type of reference has been chosen as the site's preference yet; that is yet to come.
Research pages may contain:
- Appendices, self explanatory
- Footnotes, self explanatory
- Additional notes; any subject brought up within a paper that is used often may need to be explained, but may take too much space within the paper (e.g. specific linear algebra, a specific group or ring, etc.)
- Additional overview sections (e.g. if a single protocol is very significant and needs more than the small protocol template)

###Next Steps
Next Steps, not-very-surprisingly, is mostly being dealt with last. It does, however, house the implementation section. For more information, see the Implementations section at the bottom of this README.

###Learning
The learning section is intended to be similar to an online textbook. A tiny amount of information is there, but most of the construction of the textbook is intended for significantly later.
	
##Design Choices

###Use of JavaScript
This was done because this site is meant to be easily extensible and changeable. Normally, such extensibility would be done with server-side code; alas, we have no server (keep in mind, a single college student is hosting this site alone). As such, we must depend on JavaScript, lest we fall back to pure HTML. If we ever get independent funding for a server, we will of course move to a more consistent and safe client/server model.
We avoided the use of jQuery mostly because most JavaScript was very simple and it really wouldn't have added much.
We note that JavaScript will likely always be required for most useful pages anyway, due to our usage of MathJax. We currently have no intention of switching away from MathJax.

##To Do
We have a separate file for things to do, at /todo.

##Implementations
We have another branch, intended for implementation, at impl_publish. Any changes there should also be pulled into the submodule in gh-pages under /nextsteps/code. In order to reflect the change in the JavaScript tree used by the website's code viewer, /nextsteps/update.py must be run after any updates to the submodule.

##Dependencies
- MathJax (almost everywhere, for LaTeX rendering)
- PrismJS (code coloration on /nextsteps/codeViewer.html)