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
- [ ] Retourner des code d'erreur HTTP pour toutes les méthode de l'interface REST.
- [x] Trouver une méthode pour afficher le répertoire courant sans ajouter string particulière dans la requête.
- [x] GET rest/api/ftp/folder/{name} -> affiche la liste des fichiers du répertoire. (Imane)(Il y a toujours a un soucis)
- [x] GET rest/api/ftp/file/{name} -> retourne le fichier en utilisant le type mime application/octet-stream (Imane)

- [x] POST rest/api/ftp/folder/{new_dir} -> crée un nouveau répertoire. (arthur)
- [x] POST rest/api/ftp/file/{directory/file} -> crée nouveau fichier dans le répertoire donné (arthur)

- [x] PUT rest/api/ftp/file/{file} -> met à jour le fichier dans le répertoire donné (Imane)

- [x] DELETE rest/api/ftp/folder/{name} -> supprime le répertoire (Arthur)
- [x] DELETE rest/api/ftp/file/{name} -> supprime le fichier (Arthur)

- [ ] Ajouter des liens pointant vers une ressource REST sur les résultats lors de l'affichage d'un répetoire

- [ ] Ajout d'un formulaire pour ajouter un fichier lors de l'affichage d'un répertoire

- [ ] Les Tests

- [ ] 

- [x] Authentification d'utilisateur.
