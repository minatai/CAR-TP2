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
- [ ] Trouver une méthode pour afficher le répertoire courant sans ajouter string particulière dans la requête.
- [ ] GET rest/api/ftp/folder/{name} -> affiche la liste des fichiers du répertoire. (Imane)
- [ ] GET rest/api/ftp/file/{name} -> retourne le fichier en utilisant le type mime application/octet-stream (Imane)

- [*] POST rest/api/ftp/folder/{new_dir} -> crée un nouveau répertoire. (arthur)
- [*] POST rest/api/ftp/file/{directory/file} -> crée nouveau fichier dans le répertoire donné (arthur)

- [ ] PUT rest/api/ftp/folder/ -> met à jour le répertoire. Utile??? (Imane)
- [ ] PUT rest/api/ftp/file/{file} -> met à jour le fichier dans le répertoire donné (Imane)

- [*] DELETE rest/api/ftp/folder/{name} -> supprime le répertoire (Arthur)
- [*] DELETE rest/api/ftp/file/{name} -> supprime le fichier (Arthur)

- [ ] Ajouter des liens pointant vers une ressource REST sur les résultats lors de l'affichage d'un répetoire

- [ ] Ajout d'un formulaire pour ajouter un fichier lors de l'affichage d'un répertoire

- [*] Page de login. Utilisation d'un session ID pour récupérer la bonne session ftp.(arthur)
- [ ] modifier le FTPService pour acceder un grand nombre de connection. (Utilisation de HashMap?)(arthur)
- [ ] page de déconnexion.(arthur)
