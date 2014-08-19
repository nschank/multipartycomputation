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
This is currently the main focus of the site, and almost all changes are being made here (as of 19 August 2014).

####Storage
#####Research Papers
All "major" research papers get their own page, which should be located at `/research/papers/\[id\].html`. Their PDF, if present, should be stored as `/research/papers/pdf/\[id\].pdf`. Liability will not be considered until early September.

A list of research papers present on the site is stored as an array of paper objects in `/script/researchList.js`. The array is ordered (for human legibility) in order of the `id` attribute. This file is loaded by `/script/researchLoad.js` for usage by the four navigation pages under `/research/`.

Research paper objects have the following properties:

- `*abstractText*`: A string which contains the entire (HTML- and LaTeX-capable) text of this paper's abstract (or introduction, if applicable). **This will be replaced by [constructible arrays](#construct) soon.**
- `*authors*`: An (ordered) array of strings, where each string is the name of one author who worked on the paper.
- `*hasPage*`: (default: `false`) A boolean value which indicates whether this research paper has a page about it. If not set, or if set to a falsy value, the navigation pages will not attempt to link to this paper.
- `*id*`: A number that specifies the location of that paper's webpage, if one is present. Must be unique.
- `*include*`: (default: `false`) A boolean value indicating whether or not to include this paper in navigation. If neither this nor `hasPage` is true, then this paper is not displayed in research navigation. Use this to make a research paper findable on the site, but not have the navigation pages link to nothing.
- `*length*`: The length of this paper (in published pages).
- `*tags*`: An (unordered, though preferably alphabetical) array of tags which relate to this paper's content. If this paper's page is not finished, MUST include the tag "Unsorted". We have an incomplete list of tags below.
- `*year*`: The year when this paper was published.

#####Authors
Authors also have their own list at `authorList.js`: a dictionary going from author name to their unique URL (usually their homepage at their university). If an author is not a key in the dictionary, or returns a blank string, the author's name is not added as a link.

####Individual Pages
Research pages (`/research/papers/[id].html`) consist of two main components, several optional components, and a few small (and mostly automatic) components. A template can be found at `/research/papers/template.html`. 

Research pages always include:

- An **Overview** section, which consists of:
	- _Introduction_: A small introduction into who made the paper, why, and a very quick introduction to its effects on the field. If an important theorem was proven, it may be stated as a single statement.
	- _Goals and Results_: Self explanatory.
	- _Assumptions_: A description of what assumptions (and under what model) this paper's results are correct. 
	- _Definitions_: A list of verbatim (or slightly contextualized) definitions made by the paper. Each should link to the section in which that word/phrase was defined.
	- _Theorems_: A list of the paper's theorems. Each should link to that theorem's statement in the paper.
	- _Protocols_ (if applicable): A description of each protocol formulated in the paper. See the template page for a sample description. Each should link to the section where the protocol was first formulated. Bits exchanged and runtime will be formalized further very soon.
	- _Further Reading_: Self explanatory.
	- _Referencing This Paper_: Self explanatory. Also see our preferred [type of reference](#how_to_ref).
- An **Annotated Paper** section, which is a transcription of either the page or the extended abstract of the page (the former is, of course, preferred.)
	- A single webpage should only contain 20 pages of equations (for loading-time reasons), so if more than that is to be added, it should be split into multiple pages. Since we have not dealt with such an issue yet, we may consider doing either only an extended overview, or a regular overview followed by a summary (as opposed to a transcription).
	- See the "Alterations During Transcription" page [\(`/research/changes.html`\)](http://multipartycomputation.org/research/changes.html) for more information about what specific things are changed during transcription.
	- Annotations (i.e. paragraphs of class `rp_annotation`) should be added to clarify, contextualize, or otherwise correct anything within the original (`rp_original`) text.
	- Proofs are annotations, and should ALWAYS be added, if known.

Research pages may also contain:

- Appendices, self explanatory
- Additional notes; any subject brought up within a paper that is used often may need to be explained, but may take too much space within the paper (e.g. specific linear algebra, a specific group or ring, etc.) This is likely being moved to combine with the **Cheat Sheet**.
- Additional overview sections (e.g. if a single protocol is very significant and needs more than the small protocol template)

###Learning
The learning section is intended to be similar to an online textbook. Planning for chapters and sections happens below.

1. Introduction to MPC
	1. Millionaire's Problem
	2. Voting
	3. Fourth Graders
	4. Basic Definition
2. More Formal Definitions
	1. Primitives Used
	2. Communication
	3. A Definition
	4. Proof Types
3. Proofs In Action
	1. Proof of Fourth Grader Protocol Security (Crypto)
	2. Proof of Verifiable Secret Sharing Scheme Security (I.T.)
4. Adversaries	

###Next Steps
Next Steps, not-very-surprisingly, is mostly being dealt with last. It does, however, house the implementation section. For more information, see [Implementations](#impl).

##Site-Wide Features
###Definitions 
Cryptography often involves complex math and very specific definitions. To allow users a convenient way of keeping track of those definitions, a glossary is built into the website that allows readers to see the definition of select words by mousing over them.

####Dependencies
- _difficulty.js_
- _blockShare.js_
- _def.js_
- _glossary.js_

####Providing a Definition
If we want to define some word _"xxx"_, we must provide _glossary.js_ with the definition of that word. We can do so in two places:

1) If the word is not going to be used across the site, we can define a local, page-only definition within the `self_def` object on the page. We simply set `self_def\["xxx"\]=\{definition object\}`. This definition will always take precedence.
2) If a word _is_ going to be used on several pages, simply add it to the _def.js_ page, which is simply a large object mapping words to their definition objects.

####Definition Objects
A definition object is a JavaScript object which will be used to build definition blocks. They have the following properties:

- *`blockName`*: A unique attribute (site-wide) that will be used to create the floating block's `id` attribute. Must follow the naming conventions of `id` within the DOM (so alphanumerical characters plus the underscore only), and will cause unpredictable results if any two words ever share a `blockName`. To prevent this, any words defined in `self_def` are recommended to start with the page title or some unique feature that will prevent a collision.
- *`def`*: A _constructible_ array, see [Constructible Objects](#construct). `def` may contain LaTeX, equation explanations, and other words to define: they will be rendered correctly.
- *`difficulty`*: See the [Difficulty](#difficulty) section, below.
- *`title`*: The word being defined, in a 'prettied up' form. As an example of why the word is not simply used, the `title` attribute will be processed for equations and html tags that are awkward to use as a key in the object.

####Marking a Definable Element
The _def.js_ script loads definitions onto the page. It begins by finding all elements marked with the class `load-definable`; at each of these elements, it then chooses a word to define:

1. If the element has a `data-define` attribute, the value of that attribute is the word to define.
2. If the element does not, _or the value of `data-define` did not have a definition_, then the entire `innerHTML` is chosen as the word to define.

If a definition is found, and the defined word has a difficulty level coherent with the user's chosen difficulty \(see [Difficulty][]\), then the element will:

- Be marked with the class `definable`
	+ Currently, this entails a dotted, black underline which becomes red on mouse-over.
- On mouseover, a floating definition box, filled using the definition object, will appear below the element.

All words with the same definition share a single definition box, so there is little overhead to defining the same word many times on a single page. If an element marked for loading a definition either does not have a definition, or was not difficult enough, then it remains unaltered (that is, it will look like normal text) and `console.log` marks the word that could not be defined.

####Glossary
A glossary of terms is automatically built from all the definitions in `def.js`. We may shortly allow specific definitions to "opt out" of being included in the glossary; additionally, we may allow `difficulty` to matter within the glossary as well (it is currently ignored).

###Equation Explanations
Authors tend to define an equation or notation once, then use it religiously for the remainder of a paper or section. In many cases, this notation is unique or rarely used, which can make following a paper unnecessarily confusing. Equation explanations are built into the site so that any equations which are confusing can be easily explained by simply mousing over it.

####Dependencies
- _difficulty.js_
- _blockShare.js_
- _equation.js_
- The presence of a function called `fill`; included in templates automatically.

####Providing an Explanation
All equation explanations should be included as the output of the `fill\(eq-id\)` function on a page which uses that explanation. The `fill` function should intake an equation-id, and return an equationInfo object. equationInfo objects consist of the following:

- *`blockName`*: A unique attribute that will be used to create the floating block's `id` attribute. Must follow the naming conventions of `id` within the DOM (so alphanumerical characters plus the underscore only), and will cause unpredictable results if any two equations share a `blockName`.
- *`difficulty`*: See the [Difficulty](#difficulty) section, below.
- *`info`*: A _constructible_ array, see [Constructible Objects](#construct). `info` will render internal LaTeX, but not other equation explanations or definitions.

####Marking an Equation to Explain
_equation.js_ iterates through all elements on the page with the class `load-equation`. All elements which mark an equation-id using the `data-equation` attribute are checked; if an equationInfo object is found for that equation-id \(using `fill`\), then:

- The element will be marked with the `equation` class
	+ Currently, this entails a dashed, black underline which becomes red on mouse-over.
- On mouseover, a floating equation explanation box, filled using equationInfo, will appear below the element.

All equations with the same equation-id share a single explanation box, so there is little overhead to explaining the same equation many times on a single page.

####Known Bugs
MathJax doesn't play nicely with `load-equation`s that are wrapped $$_as a block_$$. This isn't too much of an issue yet, so we've chosen to ignore it.

###Difficulty	{#difficulty}
A feature has been recently added to make the site friendly to researchers (who may find overuse of definitions/equations annoying) just as much as to complete beginners (who likely need them). A site-wide cookie called `difficulty` is used to determine what features on a page to load, and which to skip. The difficulty value should be an integer between 1 and 6 (inclusive), where:

1. For beginners: load everything.
2. Knowledge equivalent to an undergraduate; some familiarity with common lingo (e.g. malicious vs. passive adversaries).
3. Undergraduate; familiarity with cryptography lingo and more formal definitions, like encryption or commitment.
4. Graduate level; reserved mostly for highly specific mathematical concepts or for simpler words introduced only for a single paper.
5. Researcher; mostly just words introduced and used within a single paper.
6. Turn off special features.

All definitions and equations have `difficulty` attributes as well, which should **never** be 6. Any word/equation which has a `difficulty` _less_ than the user's set difficulty will not have its special features loaded.

We currently do NOT have a way for a reader to change their `difficulty`, which is 1 by default. This will be implemented very shortly, once John and I talk about how to do so most effectively.

###References
####Dependencies
- _ref.js_
- _blockShare.js_

####Making a Citation
All citations must be list elements within a list with `id="referencelist"`. Each citation (list element) must have an `id` attribute that is unique to it. Within the list element, a normal text citation should be given: our favorite method of citation is given [here](#how_to_ref). If the paper being cited has its own page on the site, it should be linked to.

####Referencing a Citation
To reference a citation within the text, one should use either a _sup_ or _span_ tag with the class `reference`. Additionally, the attribute `data-citation` must be used, and must be equal to the `id` attribute of the citation being referenced. The contents of the tag _do not actually matter and will be automatically replaced_: good practice is either to set the contents as "\[?\]", to make bad references easy to find, or to use the numbering of the citation list, like "\[5\]".

References are automatically numbered and linked to the correct citation at the bottom of the page. The citation list at the bottom is also automatically reordered to match the order of references within the text; if a citation is never referenced, it is not displayed. A floating div containing the citation's text will be displayed if a reference is moused over.

###Footnotes
####Dependencies
- _fnote.js_

####Writing a Footnote
All footnotes must be list elements within a list with `id="footnotelist"`. Each footnote (list element) must have an `id` attribute that is unique to it. Within the list element, any content can be added. Footnotes which are original to the text should be differentiated from annotation footnotes using the `class` attribute. (That is not yet formalized.)

####Referencing a Footnote
Reference a footnote using a _sup_ tag with the class `footnote` and the attribute `data-footnote`, whose value should be the `id` attribute of the footnote being referenced.

Footnotes will be renumbered automatically to match the in-text order. Footnotes will additionally have links pointing back up to all references to that footnote, in order. Footnotes do not have floating divs.

###Tables of Contents
####Dependencies
- _toc.js_

####Adding a Table to Fill
Instead of writing up an entire table of contents at every location where one is needed on the page, simply add the following line:

>`<div class="main_toc"></div>`

It will be filled in automatically.

####Marking a Section
The Table of Contents filler only pays attention to _section_ tags. In order to mark a section of the document, simply surround the contents of that section in a _section_ tag with the following properties:

- `*id*`: Required to allow for linking.
- `*data-section-name*` (recommended): The name of this section, to be included as the actual text within the table of contents. Recommended for runtime considerations. If no `data-section-name` attribute is present, the section is named by the first result for the capturing group within the regular expression `/<h\(\\d\)>\[\\d\\.\]*\\s*\(.+?\)<\\/h\\1>/`; in other words, the contents of the first heading tag.
- `*data-section-number*` (optional): Allows you to specify which number this section should be overall. Otherwise, the script will number this section as either 1 (if the first section) or the number of the previous section plus one.

####Top Sections
The script does not search the document automatically; every 'top section' - so, for example, every chapter, or top-level sections like _References_ - must mark themselves by setting the attribute `class="top_section"`. The contents of these sections will automatically be searched to find their children/subsections.

####Navigation Buttons
Navigation buttons (three arrows: left, up, and down) are added at the top right of each section. The left arrow will always link to the containing section. Up and down will point to previous and next sections, if they exist. The up and down arrows will not be added if the current section does not have a previous or next section, respectively.

##Design Choices
###Use of JavaScript
This was done because this site is meant to be easily extensible and changeable. Normally, such extensibility would be done with server-side code; alas, we have no server (keep in mind, a single college student is hosting this site alone). As such, we must depend on JavaScript, lest we fall back to pure HTML. If we ever get independent funding for a server, we will of course move to a more consistent and safe client/server model.
We avoided the use of jQuery mostly because most JavaScript was very simple and it really wouldn't have added much.
We note that JavaScript will likely always be required for most useful pages anyway, due to our usage of MathJax. We currently have no intention of switching away from MathJax.

###Type of Reference 	{#how_to_ref}
We follow a particular type of reference for the papers we have pages for: 

>F. Lastname, F. Lastname..., and F. Lastname. [Title of Paper in Title Case](link_to_research_page_if_present) (extended abstract, if accurate). _Full Name of Publication of Origin_, pp. 0-100. Publisher. Year.

This was done for no real reason other than a lack of consistency between different reference lists; we simply went with one particularly concise version seen in one paper, and stuck with it.

### Constructible Objects	{#construct}
A very simple nesting data structure was created to allow for style to change consistently across all definitions and equation explanations. Each object represents a single HTML element; a "constructible array" is any array of these objects, which will be rendered in order. A constructible object can be built into any of five (technically six) tags, based on its "type": _p,li,ol,ul,_ or _span_. All constructibles can specify a `className` that overrides the default styling class being used to style the object, which is specified based on where it is being built. All constructibles should have `content`, which is either a string (for _p_ or _span_ types) or another constructible array (for _li,ol,ul_ types). Span types have a special optional attribute, `href`, which wraps the entire span in an _a_ tag with that value as its `href` attribute. This allows for _span_s to be headings (e.g. Definition headers) that link elsewhere without causing underlines/miscoloration.

##To Do
We have a separate file for things to do, at `/todo`. Honestly, it's not really intended to be useful right now; it will be fixed up this week.

##Implementations		{#impl}
We have another branch, intended for implementation, at `impl_publish`; there is a matching `impl_working` as well. Any changes there should also be pulled into the submodule in `gh-pages` under `/nextsteps/code`. In order to reflect the change in the JavaScript tree used by the website's code viewer, `/nextsteps/update.py` must be run after any updates to the submodule. So, to summarize:

1. Commits are made in `impl_working`.
2. Merge with `impl_publish` to make those changes publicly.
3. `cd /nextsteps/code; git pull`
4. `cd ../; ./update.py`
5. `cd ../; git commit -m "New commits in implementation section"` 

##Dependencies
- MathJax (almost everywhere, for LaTeX rendering)
- PrismJS (code coloration on `/nextsteps/codeViewer.html`)