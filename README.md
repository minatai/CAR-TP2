CAR-TP2
=======
Imane Khemici - Arthur Dewarumez

url de base pour toutes les requêtes :
	rest/api/ftp

Documentation
-------------
http://aredko.blogspot.fr/2013/01/going-rest-embedding-jetty-with-spring.html

Organisation
------------


Todo:
-----
Toutes les tâches suivante utilise le dossier courant du ftp. Par exemple pour afficher le contenu du répertoire test du répertoire courant,
il faut utiliser la requête suivante rest/api/ftp/folder/test.

- [ ] Trouver une méthode pour afficher le répertoire courant sans ajouter string particulière dans la requête.
- [ ] GET rest/api/ftp/folder/{name} -> affiche la liste des fichiers du répertoire. (Imane)
- [ ] GET rest/api/ftp/file/{name} -> retourne le fichier en utilisant le type mime application/octet-stream (Imane)

- [ ] POST rest/api/ftp/folder/create/{new_dir} -> crée un nouveau répertoire. Comment??? (arthur)
- [ ] POST rest/api/ftp/file/create/{file} -> crée nouveau fichier dans le répertoire donné (arthur)

- [ ] PUT rest/api/ftp/folder/add/ -> met à jour le répertoire. Utile??? (Imane)
- [ ] PUT rest/api/ftp/file/{file} -> met à jour le fichier dans le répertoire donné (Imane)

- [ ] DELETE rest/api/ftp/folder/delete/{name} -> supprime le répertoire (Imane)
- [ ] DELETE rest/api/ftp/file/delete/{name} -> supprime le fichier (Imane)

- [ ] Ajouter des liens pointant vers une ressource REST sur les résultats lors de l'affichage d'un répetoire

- [ ] Ajout d'un formulaire pour ajouter un fichier lors de l'affichage d'un répertoire

- [ ] Authentification http (idée de basehttp://{user}:{password}@url)
