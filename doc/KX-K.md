# Rapport du projet Citadelle (groupe K)
## Auteurs 
* [LE BIHAN Léo](https://github.com/LeBihanLeo)
* [BONNET Kilian](https://github.com/KilianBonnet)
* [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou)
* [IMAMI Ayoub](https://github.com/AyoubIMAMI)
<br>
<br>

# L’architecture
Afin de mieux comprendre comment notre projet fonctionne nous allons dans un premier temps faire une description rapide de chaque package du projet.  

## Package [game_interacor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor) 
Ce package correspond à ce qu’on pourrait grossièrement appeler “le bot”. La classe la plus importante dans ce package est la classe [Behaviour](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/Behaviour.java), c’est un peu comme le cerveau du “bot”, il va réfléchi, prendre les décisions et demander à la classe [Executor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/Executor.java) de réaliser une action sur la partie, comme prendre de l’or, piocher une carte, ou construire un district. Afin de prendre sa décision la classe [Behaviour](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/Behaviour.java) va utiliser un [CityManager](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/CityManagement.java) qui permet d’analyse la ville pour savoir si oui ou non on pourra par la suite construire tel ou tel bâtiment.

## Package [game](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game) 
Ce package contient tous les objets de la partie, le deck qui contient toutes les cartes districts _([DeckDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/DeckDistrict.java))_, le deck qui contient les 8 characters _([DeckCharacter](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/DeckCharacter.java))_, et la liste des joueurs présents dans la partie _([Player](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Player.java))_. Toutes ces informations sont rassemblées dans la classe [Board](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Board.java).

## Package [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) 
Ce package est le cœur mécanique de notre projet. Le chef d’orchestre est le [Controller](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/Controller.java), il va dans un premier temps demander à l’Initialiser _([Initializer](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/Initializer.java))_ d’instancier les éléments nécessaire pour créer une partie, ensuite le [Controller](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/Controller.java) va demander au [Round Manager](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/RoundManager.java) de lancer la partie, et une fois la partie terminée, le [Controller](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/Controller.java) demande au [Referee](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/Referee.java) de déterminer quel joueur à remporter la partie en se basant sur la ville de chaque joueur _([City](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine/City.java))_. Le [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) contient aussi les classes Character qui en fonction de leurs pouvoir font réaliser des actions sur la partie.

## Package [game_interactor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor)

## Package [output](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/output)
Ce package contient une seule classe qui est la classe [PrintCitadels](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/output/PrintCitadels.java) qui est chargé d'afficher le déroulement de la partie dans la console.

<br>

Maintenant que l’on en sait un peu plus sur les différents éléments présent dans notre projet, nous allons pouvoir voir comment ils communiquent les uns avec les autres afin de jouer une partie de citadelle.

<br>
<br>

# Informations utiles
<br>
<br>

# Répartition du travail
Afin d’assurer une robustesse dans le code, les différentes fonctionnalités, méthodes et classes ont un ou plusieurs tests associés. Ainsi, lorsque sera évoqué l’auteur d’une fonctionnalité, méthode ou classe cela implique aussi que ce dernier ait travaillé sur les tests associés. L'architecture du projet nous a permis de scinder ce dernier plusieurs blocs de code assez distinct. Au fur et à mesure du temps, chacun a pu se spécialiser dans une partie du code.

## Cartes de personnages
L'implémentation des cartes de personnages _(retrouvable dans le package [characters_class](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/characters_class))_ fut faite par [Mourad Karrakchou](https://github.com/MouradKarrakchou). L'implémentation des cartes personnages impliquent aussi l'implémentation des différents sorts de personnage.

### Issues associés :

| Isssue                                                                                     | Description                      | Date       |
|--------------------------------------------------------------------------------------------|----------------------------------|------------|
| [#11](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/11) | Ajout du roi                     | 21/11/2021 |
| [#12](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/12) | Ajout de l’assassin              | 23/11/2021 |
| [#13](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/13) | Ajout du voleur                  | 24/11/2021 |
| [#14](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/14) | Ajout du marchand                | 25/11/2021 |
| [#23](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/23) | Refactor de l’assassin et du roi | 24/11/2021 |
| [#25](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/25) | Ajout de l'architect             | 06/12/2021 |
| [#28](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/28) | Ajout du magicien                | 06/12/2021 |
| [#38](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/38) | Ajout de l'évêque                | 08/12/2021 |
| [#44](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/44) | Refactor des personnages         | 08/12/2021 |
| [#48](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/48) | Ajout du condottiere             | 08/12/2021 |


## Cartes de quartiers
Le la même façon que les cartes de personnages, l'implémentation des cartes de quartiers _(retrouvable dans le package [game](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game) et notamment dans le package [purple_districts](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/purple_districts))_ implique aussi l'implémentation des différents sorts de quartiers. Cette dernière fut réalisée par [Ayoub Imami](https://github.com/AyoubIMAMI).

### Issues associés :

| Isssue                                                                                     | Description                                    | Date       |
|--------------------------------------------------------------------------------------------|------------------------------------------------|------------|
| [#7](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/7)   | Ajout des quartiers ayant 8 différents prix    | 18/11/2021 |
| [#19](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/19) | Ajout des couleurs de quartier                 | 24/11/2021 |
| [#27](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/27) | Ajout du sort de quartier : Dragoport          | 03/12/2021 |
| [#34](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/34) | Ajout du sort de quartier : Université         | 05/12/2021 |
| [#39](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/34) | Ajout du sort de quartier : Cour des miracles  | 08/12/2021 |
| [#42](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/42) | Ajout du sort de quartier : École de magie     | 08/12/2021 |


## La représentation du jeu
La représentation jeu correspond à la façon dont le plateau de jeu de Citadelle a été pensé et implémenté dans le code. Ces différents aspects et choix de modélisation sont trouvables dans le package [game](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game). Cet élément regroupant à la fois joueurs et cartes, cette implémentation fut faite par l'ensemble du groupe. 

### Issues associés (les noms seront indiqués):
| Isssue  | Description  | Date | Nom |
|---------|--------------|------|-----|
| [#1](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/7) | Ajout des quartiers ayant 8 différents prix | 11/11/2021 | [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou) |
| [#2](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/2) | Ajout du deck de quartiers | 14/11/2021 | [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou) |
| [#4](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/4) | Ajout de la gestion des de l’or | 16/11/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |
| [#8](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/8) | Piocher deux cartes quartiers et en brûler une | 19/11/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |
| [#9](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/9) | Construire un quartier et passer son tour | 19/11/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#30](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/30) | Le joueur commence avec 4 cartes quartiers | 07/12/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |
| [#37](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/37) | Le deck de quartiers peut être mélanger| 08/12/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#52](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/52) | Les cartes personnages peuvent être brûlées | 16/12/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |

## La gestion du jeu
La gestion du jeu est un élément qui s’est vu séparé du jeu suite à un refactor au vu de la taille que cette dernière prenait au fur et à mesure des ajouts de code. La gestion du jeu _(trouvable dans le package [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine))_ fait la liaison entre la décision des robots et la modification de l’état du jeu. Comme l’implémentation du jeu, la gestion du jeu est un élément regroupant à la fois joueurs, cartes et choix des robots, cette implémentation a été faite par l'ensemble du groupe.

### Issues associés (les noms seront indiqués):
| Isssue  | Description  | Date | Nom |
|---------|--------------|------|-----|
| [#3](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/3) | Gestion de l'égalité | 14/11/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#5](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/5) | Gestion du dernier round | 16/11/2021 |  [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#6](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/6) | Mise à jour du système de recherche du gagnant | 14/11/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#8](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/6) | Gestion de la fin du jeu à 8 quartiers posés | 16/11/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#22](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/22) | Refactor de la classe d’initialisation | 27/11/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#29](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/29) | Refactor de la classe d’initialisation | 03/12/2021 |  [BONNET Kilian](https://github.com/KilianBonnet) |

## La gestion des interactions du jeu
Les éléments permettant les interactions avec le jeu se situent dans le package [game_interacor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor). Ces interactions _(gérées par [l’executor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/Executor.java))_ sont décidées en fonction du comportement associé à un joueur. Cette implémentation fut en grande partie menée par [LE BIHAN Léo](https://github.com/LeBihanLeo). Vers le début de ce mois de décembre, l’ajout de stratégies a été décidé et est en cours d’implémentation afin d’ajouter aux différents bots des stratégies plus ou moins complexes basées sur une analyse plus poussée des différents paramètres du jeu (joueurs, personnages, bâtiments construits, … ).

### Issues associés (les noms seront indiqués):
| Isssue  | Description  | Date | Nom |
|---------|--------------|------|-----|
| [#9](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/9) | Passer son tour après avoir construit un quarier | 18/11/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#10](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/10) | Stratégie de fin de jeu | 21/11/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#15](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/15) | Stratégie du choix de personnage | 24/11/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#20](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/20) | Mise à jour de la stratégie de fin de jeu/tour  | 01/12/2021 |  [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#21](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/21) | Bot pour rush les quartiers pas cher | 30/11/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#22](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/22) | Refactor de l'implémentation du bot  | 01/12/2021 | [LE BIHAN Léo](https://github.com/LeBihanLeo) |
| [#40](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/40) | Implémentation de la stratégie et de la prédiction | 14/12/2021 |  [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou) |
| [#47](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/47) | Façon intelligente d'uiliser le sort du Voleur | 17/12/2021 |  [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou) |
| [#49](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/19) | Façon intelligente d'uiliser le sort du Magicien | 17/12/2021 | [COLLET Philippe](https://github.com/collet) |
| [#50](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/50) | Façon intelligente d'uiliser le sort du Condottiere | 17/12/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |
| [#54](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/40) | Changement de la façon de choisir un personnage | 16/12/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |
| [#54](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/40) | Changement de la façon de choisir un personnage | 16/12/2021 | [IMAMI Ayoub](https://github.com/AyoubIMAMI) |

## La gestion de l’affichage du jeu
L’affichage du jeu permet de véhiculer rapidement les informations essentielles du jeu afin de pouvoir comprendre facilement l’état d’avancement de la partie. Cette implémentation a été en partie réalisée par [Kilian Bonnet](https://github.com/KilianBonnet) | en s’appuyant sur la librairie [JColor](https://github.com/dialex/JColor) conçue par [Diogo Nunes](https://github.com/dialex).

### Issues associés (les noms seront indiqués):
| Isssue  | Description  | Date | Nom |
|---------|--------------|------|-----|
| [#18](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/18) | Couleurs sur les logs | 26/11/2021 | [BONNET Kilian](https://github.com/KilianBonnet) |
| [#38](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/38) | Amélioration des logs | 07/12/2021 | [BONNET Kilian](https://github.com/KilianBonnet)  & [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou) |
| [#45](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/45) | Amélioration de la visibilité des logs | 14/12/2021 | [BONNET Kilian](https://github.com/KilianBonnet) | [LE BIHAN Léo](https://github.com/LeBihanLeo) |


# Fonctionnement de l’équipe
Globalement, nous n'avons jamais programmé, ou très peu lors des réunions du mercredi. Nous vérifiions plutôt l’avancement du projet et s’il y avait des choses à changer, enlever ou améliorer. Puis, nous écrivions la nouvelle [Milestone](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/milestones) et les [issues](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues?q=) sur lesquelles nous nous étions mis d’accord pendant la semaine de travail précédant la réunion du mercredi. Parfois, nous nous divisions le travail, deux membres du groupe écrivaient les [issues](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues?q=) pendant que les deux autres réfléchissaient à des améliorations.

<br>
Nous avons divisé notre Kanban en 4 parties: To do, In progress, Tests, Done. Au début, toutes les issues sont dans “To do”. Chacun choisissait une issue et la déplaçait donc dans “In progress” en faisant des commits au fur et à mesure que l’issue avançait. Puis, une fois l’implémentation faite, la personne chargée de l’issue la déplace dans “Tests” puis effectue les tests sur cette partie du programme. Une fois la nouvelle fonctionnalité testée, elle est déplacée dans dans “Done” et alors seulement nous pouvions close l’issue.

<br>
Pendant la semaine, tous les membres étaient conscients du travail à faire et chacun travaillait et gérait son temps comme bon lui semblait. Nous nous posions souvent des question entre nous pour connaître l’avis de chacun, obtenir de l’aide ou une information.

<br>
<br>

# Avancement du projet
## Règles métiers du jeux Citadelle 
### Fait
L’ensemble des personnages sont implementés avec leurs sorts. Les quartiers ont leurs couleurs et leur valeurs, nous avons aussi implementés les sorts spéciaux du Donjon, du Dracoport, de l'Université, de l'Ecole de Magie, de la Cour des miracles et de la Bibliothèque.

Tout les quartiers sont bien mit dans un deck qui est mélangé en début de partie. Les règles de l’ordre de choix des personnages a aussi été respecté ainsi que l’ordre de jeu de chacun des personnages.

<br>

### A faire
Il nous manque ainsi l'implémentation de certains quartiers qui ont des sorts spéciaux.

<br>

## Bots
### Fait
Nous avons 3 types de bots qui reposent sur un systeme de phase de jeu. Il y a 3 phases de jeux, début de partie, fin de partie et dernier tour, commun à tous les bots et reposant sur le nombre de bâtiments posés dans la ville de chaque joueurs.

* Le premier bot _(investor)_ tente d’accumuler le plus de points possible en cherchant a poser les bâtiments les plus chères. 
* Le deuxième bot _(rusher)_  tente de poser les bâtiments avec les plus petits coûts pour essayer d’obtenir le bonus le plus rapidement possible.
* Le troisième bot _(strategator)_  essaye de viser des joueurs en particulier et de prédire le personnages qu’ils ont choisit pour pouvoir les attaquer avec ses sorts. Le reste de ses choix sont fait de la même manière que le premier bot.
* _Certains mécanismes sont commun aux 3 bots comme le fait de ne pas piocher un quartier que l’on a déjà dans sa main ou que l’on a déjà construit._

<br>

### A faire
Ainsi, ce qu’il nous resterait à faire est de créer un superBot qui utiliserait les stratégies de nos 3 bots déjà existant ce qui est implementable grâce à l’architecture que nous avons. Nous pourrions aussi ajouter de nouvelle stratégie spéciales pour essayer de profiter le plus possible du sort spécial d’un personnage. Nous n’avons pas ajouté le fait de prendre en compte les personnages piochés (lors de la première partie du tour de jeu) avant nous ou après nous pour ensuite avoir une information plus précise sur les roles possibles de chacun.

<br>
<br>

# Rétrospective du code
Après 6 semaines de développement le projet est à une version stable, la dernière version du projet corespond à la [Milestone 6](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/milestone/6). Après plusieurs refractors nous avons acquis une base assez stable mais nous avons identifié certains points d'amélioration.

## Points négatifs et comment les améliorer
Notre classe [PrinterCitadels](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/output/PrintCitadels.java) est une classe ou toutes ses méthodes sont accessibles de manière static. Afin de régler ce problème nous avons imaginé une nouvelle classe dans le package [game_interactor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor) qui aurait pour tâche de "récolter” toutes les actions réalisées par le bot, les characters et les districts spéciaux. Cette classe renverrait une ArrayList<String> que l’on remonterait au [Round Manager](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_engine/RoundManager.java) à la fin du tours du [Behaviour](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_interactor/Behaviour.java), puis le [Round Manager](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game_engine/RoundManager.java) demanderait à [PrintCitadels](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/output/PrintCitadels.java) d’afficher le contenu de l’ArrayList.
  
  
Les responsabilités des classes [Character](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Character.java) ne sont pas très claires. Nous avons créé la classe [Character](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Character.java) et ses sous-classes sans réfléchir assez à comment les intégrer de manière optimale dans notre projet. Nous avions décidé que les Characters iraient dans le package [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) car ils ont un impact sur le déroulement de la partie en son cours _(par exemple l'Assassin qui va empêcher un joueur de jouer ou bien le Roi qui va choisir sa carte Character en premier au prochain tour)_. Nous avions aussi décidé que c’était l’entité [Player](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Player.java) qui possédait un  [Character](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Character.java). Si nous mettons ça bout à bout on remarque qu’un élément du [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) contient un élément du [game_interactor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor), qui contient un élément du [game](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game), qui contient lui même un élément du [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine).
  
<img src="https://user-images.githubusercontent.com/90778036/146634067-b46d0923-f5d6-4541-820e-58082f6ab4e4.png" width="512">
  
Malheureusement nous ne sommes pas encore parvenu à trouver une solution optimale à ce problème. Afin que la classe [Player](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/Player.java) ne possède plus un élément de [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine), nous avons opté pour la solution suivante :

<img src="https://user-images.githubusercontent.com/90778036/146634222-f26d8a56-cc33-4925-91f1-41e25c229524.png" width="512">
  
Nous avons aussi commencé à imaginer une nouvelle classe dans le [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) qui va communiquer avec les character pour réaliser les modification nécessaire sur le [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine), de manière à ce que ne soit plus directement les Character qui réalisent ces modifications.

<br>
  
## Points positifs
Concernant le [game_interacor](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_interactor), la manière dont nous avons choisi de découper les bots nous laisse maintenant la liberté de créer des nouveaux bot avec un comportement différent des précédents sans toucher au reste du code. Pour créer un nouveau bot nous avons juste à lui assigner différentes stratégies de jeu.
  
Le [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine) est bien découpé et les responsabilités sont bien répartis entre les classes du [game_engine](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/tree/master/src/main/java/fr/unice/polytech/citadelle/game_engine).
  
Nous avons su couper les districts violet en 3 catégories, [BonusDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/BonusDistrict.java), [SpellDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/SpellDistrict.java) et [ColorDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/ColorDistrict.java). Pour l'instant seul les [BonusDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/BonusDistrict.java) et les [ColorDistrict](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/blob/master/src/main/java/fr/unice/polytech/citadelle/game/ColorDistrict.java) sont implémentés et si nous avions à en ajouter, nous n'aurions pas non plus à modifier le reste du code : simplement à créer une nouvelle classe en rapport avec le nouveau district que l'on souhaite implémenter.
