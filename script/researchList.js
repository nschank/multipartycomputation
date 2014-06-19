var researchInfo = [
	{authors:["Shailesh Vaya"], title:"(Unconditional) Secure Multiparty Computation with Man-in-the-middle Attacks", year:2010.5, id:0, tags:["Unsorted"], length:18},
	{authors:["Rashid Sheikh", "Beerendra Kumar", "Durgesh Kumar Mishra"], title:"A Distributed k-Secure Sum Protocol for Secure Multi-Party Computation", year:2010, id:1, tags:["Unsorted"], length:5},
	{authors:["Rashid Sheikh", "Beerendra Kumar", "Durgesh Kumar Mishra"], title:"A Modified ck-Secure Sum Protocol for Multi-Party Computation", year:2010, id:2, tags:["Unsorted"], length:5},
	{authors:["Durgesh Kumar Mishra", "Neha Koria", "Nikhil Kapoor", "Ravish Bahety"], title:"A Secure Multi-Party Computation Protocol for Malicious Computation Prevention for preserving privacy during Data Mining", year:2009, id:3, tags:["Unsorted"], length:6},
	{authors:["Tal Geula Malkin"], title:"A Study of Secure Database Access and General Two-Party Computation", year:2000, id:4, tags:["Unsorted"], length:216},
	
	{authors:["Ran Canetti", "Uri Feige", "Oded Goldreich", "Moni Naor"], title:"Adaptively Secure Multi-party Computation", year:1996, id:5, length:30, tags:["Unsorted"]},
	{authors:["Sanjam Garg", "Amit Sahai"], title:"Adaptively Secure Multi-Party Computation with Dishonest Majority", year:-1, id:6, length:26, tags:["Unsorted"]},
	{authors:["Yehuda Lindell", "Benny Pinkas"], title:"An Efficient Protocol for Secure Two-Party Computation in the Presence of Malicious Adversaries", year:2007.5, id:7, length:27, tags:["Unsorted"]},
	{authors:["T.R. Srinath", "Magendra Pratap Singh", "Alwyn Roshan Pais"], title:"Anonymity and Verifiability in Multi-Attribute Reverse Auction", year:2011, id:8, length:8, tags:["Unsorted"]},
	{authors:["Vinod M. Prabhakaran", "Manoj M. Prabhakaran"], title:"Assisted Common Information: Further Results", year:2011.5, id:9, length:8, tags:["Unsorted"]},
	
	{authors:["Rashid Sheikh", "Beerendra Kumar", "Durgesh Kumar Mishra"], title:"Changing Neighbors k-Secure Sum Protocol for Secure Multi-Party Computation", year:-1.5, id:10, length:5, tags:["Unsorted"]},
	{authors:["Dinh Tien Tuan Anh", "Quach Vinh Thanh", "Anwitaman Datta"], title:"CloudMine: Multi-Party Privacy-Preserving Data Analytics Service", year:2013.5, id:11, length:24, tags:["Unsorted"]},
	{authors:["Claude Crépeau", "Jeroen van de Graaf", "Alain Tapp"], title:"Committed Oblivious Transfer and Private Multi-Party Computation", year:1995, id:12, length:14, tags:["Unsorted"]},
	{authors:["Eytan H. Modiano", "Anthony Ephremides"], title:"Communication Complexity of Secure Distributed Computation in the Presence of Noise", year:1992, id:13, length:10, tags:["Unsorted"]},
	{authors:["Mika Göös", "Toniann Pitassi"], title:"Communication Lower Bounds via Critical Block Sensitivity", year:2013.5, id:14, length:33, tags:["Unsorted"]},
	
	{authors:["Moni Naor", "Kobbi Nissim"], title:"Communication Preserving Protocols for Secure Function Evaluation", year:2001, id:15, length:10, tags:["Unsorted"]},
	{authors:["Martin Hirt", "Ueli Maurer"], title:"Complete Characterization of Adversaries Tolerable in Secure Multi-Party Computation", year:1997, id:16, length:10, tags:["Unsorted"]},
	{authors:["Michael Ben-Or", "Shafi Goldwasser", "Avi Wigderson"], title:"Completeness Theorems for Non-Cryptographic Fault-Tolerant Distributed Computation", year:1988, id:17, length:10, tags:["Unsorted"]},
	{authors:["Ivan Damgård", "Yuval Ishai"], title:"Constant-Round Multiparty Computation Using a Black-Box Pseudorandom Generator", year:2005, id:18, length:20, tags:["Unsorted"]},
	{authors:["Ronald Cramer", "Ivan Damgård", "Stefan Dziembowski", "Martin Hirt", "Tal Rabin"], title:"Efficient Multiparty Computations Secure Against an Adaptive Adversary", year:1999, id:19, length:16, tags:["Unsorted"]},
	
	{authors:["Martin Hirt", "Ueli Maurer", "Bartosz Przydatek"], title:"Efficient Secure Multi-Party Computation", year:2000, id:20, length:18, tags:["Unsorted"]},
	{authors:["Yehuda Lindell"], title:"General Composition and Universal Composability in Secure Multi-Party Computation", year:2003, id:21, length:30, tags:["Unsorted"]},
	{authors:["Ronald Cramer","Ivan Damgård", "Ueli Maurer"], title:"General Secure Multi-Party Computation from and Linear Secret-Sharing Scheme", year:2000.5, id:22, length:19, tags:["Unsorted"]},
	{authors:["Boaz Barak","Amit Sahai"], title:"How To Play Almost Any Mental Game Over The Net - Concurrent Composition via Super-Polynomial Simulation", year:2005, id:23, length:50, tags:["Unsorted"]},
	
	{authors:["Claudio Orlandi"], title:"Is Multiparty Computation Any Good In Practice?", year:2011, id:25, length:6, tags:["Unsorted"]},
	{authors:["Yehuda Lindell"], title:"Lower Bounds and Impossibility Results for Concurrent Self Composition", year:2005, id:26, length:45, tags:["Unsorted"]},
	{authors:["Matthias Fitzi","Juan A. Garay", "Ueli Maurer", "Rafail Ostrovsky"], title:"Minimal Complete Primitives for Secure Multi-Party Computation", year:2001.5, id:27, length:21, tags:["Unsorted"]},
	{authors:["Markus Jakobson","Ari Juels"], title:"Mix and Match: Secure Function Evaluation via Ciphertexts", year:2000, id:28, length:16, tags:["Unsorted"]},
	{authors:["Durgesh Kumar Mishra","Samiksha Shukla"], title:"Multi-Agent Model using Secure Multi-Party Computing in e-Governance", year:2009, id:29, length:5, tags:["Unsorted"]},
	
	{authors:["Ronald Cramer", "Ivan Damgård", "Jesper B. Nielsen"], title:"Multiparty Computation from Threshold Homomorphic Encryption", year:2001.5, id:30, length:20, tags:["Unsorted"]},
	{authors:["Elette Boyle", "Shafi Goldwasser", "Abhishek Jain", "Yael Tauman Kalai"], title:"Multiparty Computation Secure Against Continual Memory Leakage", year:2012, id:31, length:20, tags:["Unsorted"]},
	{authors:["Gilad Asharov", "Abhishek Jain", "Daniel Wichs"], title:"Multiparty Computation with Low Communication, Computation and Interaction via Threshold FHE", year:2012, id:32, length:48, tags:["Unsorted"]},
	{authors:["Martin Hirt"], title:"Multi-Party Computation: Efficient Protocols, General Adversaries, and Voting", year:2001, id:33, length:174, tags:["Unsorted"]},
	{authors:["David Chaum","Claude Crépeau", "Ivan Damgård"], title:"Multiparty Unconditionally Secure Protocols", year:1988.5, id:34, length:10, tags:["Unsorted"]},
	
	{authors:["Ran Canetti","Ivan Damgård","Stefan Dziembowski","Yuval Ishai","Tal Malkin"], title:"On adaptive vs. non-adaptive security of multiparty protocols", year:2001.5, id:35, length:18, tags:["Unsorted"]},
	{authors:["Deepesh Data","Vinod M. Prabhakaran","Manoj M. Prabhakaran"], title:"On the Communication Complexity of Secure Computation", year:2013, id:36, length:28, tags:["Unsorted"]},
	{authors:["Yehuda Lindell"], title:"On the Composition of Secure Multi-Party Protocols", year:2002, id:37, length:143, tags:["Unsorted"]},
	{authors:["Daniel Apon","Jonathan Katz","Alex J. Malozemoff"], title:"One-Round Multi-Party Communication Complexity of Distinguishing Sums", year:2013.5, id:38, length:9, tags:["Unsorted"]},
	{authors:["Adriana López-Alt","Eran Tromer","Vinod Vaikuntanathan"], title:"On-the-Fly Multiparty Computation on the Cloud via Multikey Fully Homomorphic Encryption", year:2012.5, id:39, length:44, tags:["Unsorted"]},
	
	{authors:["Christian Cachin","Jan Camenisch"], title:"Optimistic Fair Secure Computation", year:2000.5, id:40, length:19, tags:["Unsorted"]},
	{authors:["Yevgeniy Dodis","Silvio Micali"], title:"Parallel Reducibility for Information-Theoretically Secure Computation", year:2000.5, id:41, length:19, tags:["Unsorted"]},
	{authors:["Danny Bickson","Danny Dolev","Genia Bezman"], title:"Peer-to-Peer Secure Multi-Party Numerical Computation", year:2008.5, id:42, length:10, tags:["Unsorted"]},
	{authors:["Danny Bickson","Trachy Reinman", "Danny Dolev", "Benny Pinkas"], title:"Peer-to-Peer Secure Multi-Party Numerical Computation Facing Malicious Adversaries", year:2009.5, id:43, length:16, tags:["Unsorted"]},
	{authors:["Ivan Damgård","Yuval Ishai","Mikkel Krøigaard"], title:"Perfectly Secure Multiparty Computation and the Computational Overhead of Cryptography", year:2007.5, id:44, length:32, tags:["Unsorted"]},
	
	{authors:["Martin Hirt","Ueli Maurer","Bartosz Przydatek"], title:"Player Elimination: How to mke Cheating Harmless in Multi-Party Computation", year:1999, id:45, length:17, tags:["Unsorted"]},
	{authors:["Martin Hirt","Ueli Maurer"], title:"Player Simulation and General Adversary Structures in Perfect Multiparty Computation", year:1999, id:46, length:30, tags:["Unsorted"]},
	{authors:["Manas A. Pathak","Bhiksha Raj"], title:"Privacy-Preserving Protocols for Eigenvector Computation", year:2010.5, id:47, length:14, tags:["Unsorted"]},
	{authors:["Yehuda Lindell"], title:"Protocols for Bounded-Concurrent Secure Two-Party Computation in the Plain Model", year:2004, id:48, length:48, tags:["Unsorted"]},
	{authors:["Andrew C. Yao"], title:"Protocols for Secure Computations", year:1982, id:49, length:5, hasPage:true, tags:["Protocols","Probabilistic Computation", "Significant Papers","Two-Party Computation"]},
	
	{authors:["Roger Colbeck"], title:"Quantum and Relativistic Protocols for Secure Multi-Party Computation", year:2006, id:50, length:150, tags:["Unsorted","Quantum","Thesis"]},
	{authors:["Ramij Rahaman","Marcin Wies'niak","Marek Zukowski"], title:"Quantum Anonymous Veto with Hardy Paradox", year:-1, id:51, length:6, tags:["Unsorted","Anonymous Veto","Quantum"]},
	{authors:["Michael T. Goodrich"], title:"Randomized Shellsort: A Simple Data-Oblivious Sorting Algorithm", year:2010.5, id:52, length:23, tags:["Unsorted",
	"Sorting","Protocols"]},
	{authors:["Martin Hirt","Ueli Maurer"], title:"Robustness for Free in Unconditional Multi-Party Computation", year:2001, id:53, length:15, tags:["Unsorted"]},
	{authors:["Mariana Raykova"], title:"Secure Computation in Heterogeneous Environments: How to Bring Multiparty Computation Closer to Practice?", year:2012, id:54, length:316, tags:["Unsorted","Thesis"]},
	
	{authors:["Ran Canetti","Rafail Ostrovsky"], title:"Secure Computation with Honest-Looking Parties: What if nobody is truly honest?", year:1999, id:55, length:18, tags:["Unsorted"]},
	{authors:["Dan Kenigsberg","Tal Mor"], title:"Secure Controlled Teleportation", year:2006.5, id:56, length:3, tags:["Unsorted","Quantum"]},
	{authors:["Ronald Cramer","Ivan Damgård"], title:"Secure Distributed Linear Algebra in a Constant Number of Rounds", year:2001.5, id:57, length:18, tags:["Unsorted"]},
	{authors:["Tamir Tassa"], title:"Secure Mining of Association Rules in Horizontally Distributed Databases", year:2011.5, id:58, length:18, tags:["Unsorted"]},
	{authors:["Oded Goldreich"], title:"Secure Multi-Party Computation", year:2002, id:59, length:110, tags:["Unsorted"]},
	
	{authors:["Ronald Cramer","Ivan Damgård","Jesper Buus Nielsen"], title:"Secure Multiparty Computation and Secret Sharing: An Information Theoretic Approach", year:2013, id:60, length:382, tags:["Unsorted","Book"]},
	{authors:["Wenliang Du","Mikhail J. Atallah"], title:"Secure Multi-Party Computation Problems and Their Applications: A Review and Open Problems", year:2001.5, id:61, length:10, tags:["Unsorted"]},
	{authors:["Peter Bogetoft", "Dan Lund Christensen", "Ivan Damgård", "Martin Geisler", "Thomas Jakobsen","Mikkel Krøigaard", "Janus Dam Nielsen", "Jesper Buus Nielsen", "Kurt Nielsen", "Jakob Pagter","Michael Schwartzbach", "Tomas Toft"], title:"Secure Multiparty Computation Goes Live", year:2008, id:62, length:13, tags:["Unsorted"]},
	{authors:["Ueli Maurer"], title:"Secure Multi-Party Computation Made Simple", year:-1, id:2001.5, length:15, tags:["Unsorted"]},
	{authors:["Joan Feigenbaum","Yuval Ishai","Tal Malkin","Kobbi Nissim","Martin J. Strauss","Rebecca N. Wright"], title:"Secure Multiparty Computation of Approximations", year:2001, id:64, length:30, tags:["Unsorted","Approximation"]},
	
	{authors:["Klearchos Loukopoulos","Daniel E. Browne"], title:"Secure Multi-Party Computation with a Dishonest Majority via Quantum Means", year:2010.5, id:65, length:12, tags:["Unsorted"]},
	{authors:["Mikhail J. Atallah","Wenliang Du"], title:"Secure Multi-Party Computational Geometry", year:2001, id:66, length:16, tags:["Unsorted"]},
	{authors:["Claude Crépeau","Daniel Gottesman","Adam Smith"], title:"Secure Multiparty Quantum Computation", year:2002, id:67, length:9, tags:["Unsorted","Quantum"]},
	{authors:["Tony Thomas"], title:"Secure Two-Party Protocols for Point Inclusion Problem", year:2007, id:68, length:9, tags:["Unsorted"]},
	{authors:["Dmitry Gavinsky","Tsuyoshi Ito","Guoming Wang"], title:"Shard Randomness and Quantum Communication in the Multi-Party Model", year:2013.5, id:69, length:14, tags:["Unsorted"]},
	
	{authors:["Rosario Gennaro","Michael O. Rabin","Tal Rabin"], title:"Simplified VSS and Fast-track Multiparty Computations with Applications to Threshold Cryptography", year:1997.5, id:70, length:23, tags:["Unsorted"]},
	{authors:["Ran Canetti"], title:"Studies in Secure Multiparty Computation and Applications", year:1996, id:71, length:34, tags:["Unsorted"]},
	{authors:["B. Hanmanthu","B. Raghu Ram","P. Niranjan"], title:"Third Party Privacy Preserving Protocol for Perturbation Based Classification of Vertically Fragmented Data Bases", year:-1, id:72, length:5, tags:["Unsorted"]},
	{authors:["Matthias Fitzi","Martin Hirt","Ueli Maurer"], title:"Trading Correctness for Privacy in Unconditional Multi-Party Computation", year:1998, id:73, length:16, tags:["Unsorted"]},
	{authors:["Dominique Unruh"], title:"Universally Composable Quantum Multi-Party Computation", year:2009, id:74, length:33, tags:["Unsorted"]},
	
	{authors:["Ran Canetti","Yehuda Lindell","Rafail Ostrovsky","Amit Sahai"], title:"Universally Composable Two-Party and Multi-Party Secure Computation", year:2002, id:75, length:81, tags:["Unsorted"]},
	{authors:["Tal Rabin","Michael Ben-Or"], title:"Verifiable Secret Sharing and Multiparty Protocols with Honest Majority", year:1989, id:76, length:13, tags:["Unsorted"]},
	{authors:["Yehuda Lindell","Benny Pinkas"], title:"A Proof of Security of Yao’s Protocol for Two-Party Computation", year:2006, id:77, length:25, tags:["Unsorted"]},
	{authors:["Ivan Damgård","Marcel Keller", "Enrique Larraia", "Valerio Pastro", "Peter Scholl", "Nigel P. Smart"], title:"Practical Covertly Secure MPC for Dishonest Majority – or: Breaking the SPDZ Limits", year:2013, id:78, length:45, tags:["Unsorted"]},
	{authors:["Juan A. Garay","Philip MacKenzie", "Ke Yang"], title:"Efficient and Secure Multi-Party Computation with Faulty Majority and Complete Fairness", year:2004, id:79, length:27, tags:["Unsorted"]},
	
	{authors:["Ronald Cramer","Ivan Damgård", "Jesper Buus Nielsen"], title:"Multiparty Computation, an Introduction", year:2009, id:80, length:83, tags:["Unsorted"]},
	{authors:["Oded Goldreich","Silvio Micali", "Avi Wigderson"], title:"How To Play Any Mental Game", year:1987, hasPage:true, id:81, length:12, tags:["Unsorted"]}
	//24
];