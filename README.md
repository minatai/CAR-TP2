CAR-TP2
=======
Imane Khemici - Arthur Dewarumez

url de base pour toutes les requêtes :
	rest/api/ftp

Todo:
-----
- [ ] GET rest/api/ftp/{dir} -> affiche la liste des fichiers du répertoire.
- [ ] GET rest/api/ftp/{file} -> retourne le fichier en utilisant le type mime application/octer-stream

- [ ] POST rest/api/ftp/{dir} -> crée un nouveau répertoire. Comment???
- [ ] POST rest/api/ftp/[{dir}/]*{file} -> crée nouveau fichier dans le répertoire donné

- [ ] PUT rest/api/ftp/{dir} -> met à jour le répertoire. Utile???
- [ ] PUT rest/api/ftp/[{dir}/]*{file} -> met à jour le fichier dans le répertoire donné

- [ ] DELETE rest/api/ftp/[{dir}/]* -> supprime le répertoire
- [ ] DELETE rest/api/ftp/[{dir}/]*{file} -> supprime le fichier

- [ ] Ajouter des liens pointant vers une ressource REST sur les résultats lors de l'affichage d'un répetoire

- [ ] Ajout d'un formulaire pour ajouter un fichier lors de l'affichage d'un répertoire

- [ ] Authentification http (idée de basehttp://{user}:{password}@url)
