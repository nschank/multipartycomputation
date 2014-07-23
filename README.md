#MPC Library
##Introduction
This is a library of Multiparty Computation implementations meant to do the following:

1. Demonstrate protocols through runnable demonstrations
2. Provide code samples and templates for programmers who need to create something similar
3. (Eventually) provide an actually usable network of code for anyone who needs to run a multiparty computation

##Format
Currently, all code is being implemented in Java. For the moment, that will not be changing.

Participants in a protocol are represented through a _Participant._ Currently, Participants are purely theoretical, in that
there is no (simple) way for a real party to represent themselves through a Participant class. Participants follow
_Protocols_ in order to determine the value of some function of their inputs. Protocols consist of some _Instructions_,
given to Participants when they "learn" the Protocol, which they calculate locally in order to follow the Protocol step
by step.

For didactic purposes, Protocols and Participants currently use _Historys_ in order to represent the run of the Protocol
step by step. During the run, Participants tell their History of any calculations they make, values they send, or things 
they learn, and Historys can be easily printed either separately or together as one Protocol's entire history.

##Protocols

###Multi-Party Computation
- Two-Party comparison between two values from 1 to 127 (i.e. i < j, i > j)
- The "fourth-grade" protocol: determines from any number of participants who has a requited "crush"
###Other
- Oblivious transfer of one bit

##To Do
- Create a Participant scheme for real users
- Make Protocols easier to run multiple times, or many Protocols by a single Participant
