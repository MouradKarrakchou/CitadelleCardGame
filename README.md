# La Citadelle
Projet Citadelle en lien avec le cours d'introduction sur le developpement logiciel.

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
Fonctionnalités réalisés sur tout le jeu :
Ce qui a été fait:
-Les 8 personnages ainsi que leur sorts
-Tous les bâtiments ainsi que leur couleur / famille
-Les sorts de 6 bâtiments violet sur 10:
    -Cour des miracles
    -Bibliothèque
    -Ecole de Magie
    -Université
    -Dracoport
    -Donjon
-La facon dont les cartes sont brulées en fonction du nombre de joueurs.
-Départager à l'aide des roles en cas d'égalité de point.
-Gestion du dernier tour de jeu avec un joueur qui pose son huitième batiment.
-Comptabilisation du score avec les bonus de fin de partie.
+toute les règles de bases(commencer avec 4cartes,2golds/Piocher golds ou carte ...)
Ce qui n'a pas été fait:
-Les sorts de 4 bâtiments violets:
	-Laboratoire
	-Manufacture
	-Observatoire
	-Cimetière
## Résumé des fonctionnalités pour les logs
1. Remplacement de tout les sysout par des logs au niveau INFO, ce qui nous permet d'afficher ou non certains log en jouant avec les niveaux du logger
2. Ajout de 2 nouvelles classes pour les 1000 games:
  * StatsBot qui gères les stats d'un bot
  * Statisticator fait tourner les 1000 games et affiche les stats à la fin.
## Résumé des fonctionnalités pour les statistiques en CSV
* AYOUB A TOI DE JOUER
## Résumé des fonctionnalités pour le bot spécifique demandé
### Problématique
Comment ajouter un très grand nombre de stratégie sur un état du jeu précis sans créer de répétition en ayant un code un peu malin?
### Conception
Afin de répondre à la problématique nous avons décidé de réaliser une banque de donnée qui contiendrait toute les stratégie et le bot irait piocher dans cette banque la stratégie qui lui convient le mieux.
1. Nous avons donc implémenté un bot Richalphonse qui se base sur un bot random mais qui sait jouer.
2. Nous avons créé un classe Situation qui possède des paramètre qui définisse un état particulier de la partie (ex: ordre de jeu, carte brulée, or du joueur etc...) ainsi qu'une carte Character à piocher et la cible sur laquel utiliser le sort s'il y en a un.
3. Nous avons créer une classe SituationLibrairie qui stocke toute des Situation. La SituationLibrairie est remplis par la classe SituationInitializer. De plus la classe possède des méthodes de filtrer afin de renvoyer une liste Situation en fonction de certain critère
4. La dernière classe créé est la classe RichalphonseStrategy qui a pour role de faire l'intermédiaire entre la librairie de Situation et le bot Richalphonse. Richalphonse demande à RichalphonseStrategy quel est la meilleur Situation pour le tour actuel, RichalphonseStrategy calcul certaines informations (ex: nombre de carte de certains joueur, or Districts posé etc...) et fait une recherche filtrée dans la librairie pour récupérer la meilleur Situation possible.
### Ajout des stratégie
## Richalphonse VS Strategator
### Résultat
### Analyse

