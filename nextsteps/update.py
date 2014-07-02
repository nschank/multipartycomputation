# updates the current contents of codetree.js to match the actual contents of the directory

from os import listdir
from os.path import isdir, isfile, join

codetree = open("codetree.js", "w")

codetree.write("var tree = [")

def doDir(fullPath):
	comma = False
	for c in listdir(fullPath):
		if comma:
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