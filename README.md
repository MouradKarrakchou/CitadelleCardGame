# La Citadelle
Projet Citadelle en lien avec le cours de developpement logiciel.

## Authors
- Leo Le Bihan
- Ayoub Imami
- Mourad Karrakchou
- Kilian Bonnet

# Execution
## Maven Commands
- `mvn clean package`
- `mvn exec : java`

------------------------------------
## Résumé des fonctionnalités sur tout le jeu
### Ce qui a été fait:
- Les 8 personnages ainsi que leur sorts
* Tous les bâtiments ainsi que leur couleur / famille
* Les sorts de 6 bâtiments violet sur 10:
	* Cour des miracles
	* Bibliothèque
	* Ecole de Magie
	* Université
	* Dracoport
	* Donjon
* La façon dont les cartes sont brulées en fonction du nombre de joueurs.
* Départager à l'aide des rôles en cas d'égalité de points.
* Gestion du dernier tour de jeu avec un joueur qui pose son huitième bâtiment.
* Comptabilisation du score avec les bonus de fin de partie.
* Ainsi que toute les règles de bases (commencer avec 4 cartes et 2 golds, prendre des golds ou des cartes ...)
### Ce qui n'a pas été réalisé:
* Les sorts de 4 bâtiments violets:
	* Laboratoire
	* Manufacture
	* Observatoire
	* Cimetière
## Résumé des fonctionnalités pour les logs
* Remplacement de tous les sysout par des logs au niveau INFO, ce qui nous permet d'afficher ou non certains log en jouant avec les niveaux du logger
* Ajout de 2 nouvelles classes pour les 1000 parties:
  * StatsBot qui gère les statistiques d'un bot
  * Statisticator fait tourner les 1000 parties et affiche les statistiques globales à la fin.
## Résumé des fonctionnalités pour les statistiques au format CSV
* Sur la première ligne du fichier CSV, on a les champs suivants : Le nom du bot (nom qui indique son type / sa stratégie), le rang de 1 à 7 (7 colonnes car il y a 7 joueurs au maximum), le win-rate, le nombre de parties jouées, le score total et le score moyen. Les lignes suivantes sont les informations concernant les bots qui ont joué.
Si un bot joue pour la première fois, ses statisques sont ajoutées au fichier CVS. Dès qu'un Bot qui a déjà joué joue encore une fois, ses statistiques sont mises à jour.

* Le nombre total de parties jouées nous permet de calculer 2 choses:
	* le win-rate, avec le nombre de fois qu'un bot est arrivé premier
	* le score moyen, avec le score total cumulé de toutes les parties
* On récupère ensuite le nombre de victoires sur un certain nombre de parties jouées, le win-rate ainsi que le score moyen dans le fichier CSV pour les afficher.

## Résumé des fonctionnalités pour le bot spécifique demandé
### Problématique
Comment ajouter un très grand nombre de stratégies sur un état du jeu précis sans créer de répétition en ayant un code un peu malin ?
### Conception
Afin de répondre à la problématique nous avons décidé de réaliser une banque de données qui contiendrait toutes les stratégies et le bot irait piocher dans cette banque la stratégie qui lui conviendrait le mieux.
1. Nous avons donc implémenté un bot Richalphonse qui se base sur un bot random mais qui sait jouer.
2. Nous avons créé une classe Situation qui possède des paramètre qui définisse un état particulier de la partie (ex: ordre de jeu, carte brulée, or du joueur etc...) ainsi qu'une carte Character à piocher et la cible sur laquel utiliser le sort s'il y en a un.
3. Nous avons créer une classe SituationLibrairie qui stocke toutes les Situation. La SituationLibrairie est remplie par la classe SituationInitializer. De plus la classe possède des méthodes de filtrage afin de renvoyer une liste Situation en fonction de certains critères.
4. La dernière classe créée est la classe RichalphonseStrategy qui a pour role de faire l'intermédiaire entre la librairie de Situation et le bot Richalphonse. Richalphonse demande à RichalphonseStrategy quel est la meilleure Situation pour le tour actuel, RichalphonseStrategy calcule certaines informations (ex: nombre de cartes de certains joueurs, or et districts posés etc...) et fait une recherche filtrée dans la librairie pour récupérer la meilleure Situation possible.
### Ajout des stratégie
Afin d'ajouter une nouvelle stratégie au bot Richalphonse, il suffit d'ajouter une nouvelle entrée dans la classe SituationInitializer, créer une nouvelle Situation en fonction des paramètres qui nous interessent.
## Richalphonse VS Strategator
### Résultat
Strategator: 36% de winrate <br>
Richalphonse: 25% de winrate <br>
### Analyse
La stratégie de Richalphonse est basée à 100% sur les conseils du site TricTrac. D'après les statistiques, notre bot nommé Strategator a un meilleur winrate que le bot Richalphonse. Cela peut s'expliquer par le fait que le bot Richalphonse suppose que chaque bot va faire le meilleur choix à chaque tour, cependant le meilleur choix pour Richalphonse n'est pas le même que pour Strategator, donc une partie du gameplay de Richalphonse ne fonctionne pas contre nos bots.

