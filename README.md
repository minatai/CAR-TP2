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
- [ ] Trouver une méthode pour afficher le répertoire courant sans ajouter string particulière dans la requête.
- [ ] GET rest/api/ftp/folder/{name} -> affiche la liste des fichiers du répertoire. (Imane)
- [ ] GET rest/api/ftp/file/{name} -> retourne le fichier en utilisant le type mime application/octet-stream (Imane)

- [*] POST rest/api/ftp/folder/{new_dir} -> crée un nouveau répertoire. (arthur)
- [ ] POST rest/api/ftp/file/{file} -> crée nouveau fichier dans le répertoire donné (arthur)

- [ ] PUT rest/api/ftp/folder/ -> met à jour le répertoire. Utile??? (Imane)
- [ ] PUT rest/api/ftp/file/{file} -> met à jour le fichier dans le répertoire donné (Imane)

- [ ] DELETE rest/api/ftp/folder/{name} -> supprime le répertoire (Imane)
- [ ] DELETE rest/api/ftp/file/{name} -> supprime le fichier (Imane)

- [ ] Ajouter des liens pointant vers une ressource REST sur les résultats lors de l'affichage d'un répetoire

- [ ] Ajout d'un formulaire pour ajouter un fichier lors de l'affichage d'un répertoire

- [ ] Authentification http (idée de basehttp://{user}:{password}@url)
