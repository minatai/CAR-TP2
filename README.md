CAR-TP2
=======
Imane Khemici - Arthur Dewarumez

Documentation
-------------
http://aredko.blogspot.fr/2013/01/going-rest-embedding-jetty-with-spring.html

Fonctionnement
--------------

-Il faut lancer la class starter dans un terminal.
		
		java restFTP.main.Starter

Ensuite, il faut lancer le tp serveur dans un autre terminal.

		java serveur.Serveur "le dossier que vous voulez prendre comme serveur"

-Apres avoir lancer ces deux classes, nous avons une interface HTML qui gere les GET : Directories et Files.
-Afin d'y accéder, il faut ouvrir un navigateur et mettre le lien "http://localhost:8080/rest/api/ftp/folder/" dans la barre de recherche.
-Une fenetre d'authentification apparait.
	Les identifiants :

		User Name : arctarus 
		Password : test
-Apres la validation des identifiants, vous etes connecté sur le serveur et tout les fichiers existants apparaissent.
-À partir de cette interface, vous pouvez consulter tout les dossiers et telecharger les fichiers.

-Afin d'( ajouter/supprimer/mettre à jour) un (fichier/ dossier), vous devez utiliser un terminal.
	Vous lancez un terminal et vous avez qu'à utiliser les commandes suivantes :
	
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

Les tests ont été écrit avec le framework JUnit4

Pour lancer les test, il faut d'abord lancer le serveur et starter avec la commande suivante :
		
		java restFTP.main.Starter

		java serveur.Serveur "le dossier que vous voulez prendre comme serveur"

Puis lancer les test. Vous pouvez les éxécuter directement à l'aide d'éclipse.

Bugs connus:
------------

	-Apres le telechargement d'un fichier dans l'interface HTML, nous pouvons pas revenir en arriere. Le serveur s'arrete.
	
