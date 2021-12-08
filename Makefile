build:
	find . -type f -name '*.html' -exec rm {} \;
	javac Main.java
	java Main images

removeHtmls:
	find . -type f -name '*.html' -exec rm {} \;