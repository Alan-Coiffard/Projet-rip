make: 
	cd /media/alan/Partage/UBO/projet-rip; /usr/bin/env /usr/lib/jvm/java-11-openjdk-amd64/bin/java --module-path /media/alan/Partage/UBO/projet-rip/serveur/bin -m serveur/serveurRip.Serveur 

build: 
	javac serveur\serveur\src\serveurRip\*.java -d serveur\serveur\bin 

run: 
	java -cp serveur\serveur\bin serveurRip.Serveur
