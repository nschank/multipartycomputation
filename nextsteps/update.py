#!/cygdrive/c/Python27/python
# Updates the current contents of codeDirTree.js to match the actual contents of the directory
# Does not need arguments

from os import listdir
from os.path import isdir, isfile, join

codetree = open("codeDirTree.js", "w")

codetree.write("//This file was built automatically by /nextsteps/update.py.\n")
codetree.write("//Please rerun that file in order to update this file.\n")
codetree.write("//This file is NOT intended to be altered manually.\n")

codetree.write("var tree = [")

def doDir(fullPath):
	comma = False
	for c in listdir(fullPath):
		if c[0]=='.':	# skip hidden files
			continue
			
		if comma:		# add a comma before all but the first file
			codetree.write(", ")
			
		codetree.write("{name:\"")
		codetree.write(c)
		codetree.write("\", type:")
		
		if isfile(join(fullPath,c)):
			codetree.write("\"file\"}")
		else:
			codetree.write("\"dir\", children:[")
			doDir(join(fullPath,c))
			codetree.write("]}")
		comma = True
		
doDir("code")
codetree.write("];")