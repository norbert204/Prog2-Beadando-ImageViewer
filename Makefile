build:
	javac Main.java
	jar cvf ImageViewer.jar *.class
	jar --update --verbose --file ImageViewer.jar --main-class Main
