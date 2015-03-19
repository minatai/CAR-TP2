CAR-TP2
=======
Imane Khemici - Arthur Dewarumez

Documentation
-------------
http://aredko.blogspot.fr/2013/01/going-rest-embedding-jetty-with-spring.html

Fonctionnement
--------------

1. Il faut lancer la classe starter dans un terminal.
		
		java restFTP.main.Starter

2. Ensuite, il faut lancer le serveur FTP

		java serveur.Serveur {le répetoire du serveur FTP}

3. Pour manipuler le serveur, il faut utiliser l'url suivante : http://localhost:8080/rest/api/ftp/folder/ si vous souhaiter manipuler des répertoires, http://localhost:8080/rest/api/ftp/file/ si vous souhaiter manipuler des fichiers, http://localhost:8080/rest/api/ftp/file/ si vous souhaiter supprimer des répertoires.


4. Lors de l'utilisation des ces URL, des identifiants peuvent êtres nécéssaire, vous pouvez utiliser ceux ci dessous

	* User Name : arctarus 
	* Password : test
Utilisation
-----------
Différents outils sont utilisable pour l'utilisation de l'API : une client REST tels que RESTClient, ou la commande curl.

Nous détaillerons ici le fonctionnement de la commande curl
	
	POST fichier :
	--------------
	curl -X POST -H "Content-Type: text/plain" -T "nom de fichier a ajouter" http://arctarus:test@localhost:8080/rest/api/ftp/file/{le dossier du serveur ou vous voulez mettre votre fichier}		
	
	POST dossier :
	--------------
	curl -X POST -H "Content-Type: text/plain" http://arctarus:test@localhost:8080/rest/api/ftp/folder/{le dossier du serveur ou vous voulez mettre votre fichier}/{nameDir}
	
	DELETE dossier ou fichier :
	---------------------------
	curl -X DELETE -H "Content-Type: text/plain" http://arctarus:test@localhost:8080/rest/api/ftp/delete/{chemin vers le d/f}/{name}

	PUT fichier :
	-------------
	curl -X PUT -H "Content-Type: text/plain" -T "nom de fichier a ajouter" http://arctarus:test@localhost:8080/rest/api/ftp/file/{le dossier du serveur ou vous voulez mettre votre fichier}



Fonctionnement des tests
------------------------

Les tests ont été écrit avec le framework JUnit4.

Pour lancer les test, il faut d'abord lancer le serveur REST et FTP comme décrit ci dessus.

Les tests doivent êtres lancé directement depuis eclipse

Bugs connus:
------------

* Apres le telechargement d'un fichier dans l'interface HTML, nous pouvons pas revenir en arriere. Le serveur s'arrête.
* L'exécution de la classe DeleteTEST bloque lors de l'exécution de la première méthode de test.

Travail non fait:
-----------------

 En maniere generale, le tp à été implementé completement à part quelques bugs signalés dans la partie Bugs connus.

 L'implementation des tests unitaires n'est pas complete. Par exemple, le service FTP aurait put bénéficier de test.

	
