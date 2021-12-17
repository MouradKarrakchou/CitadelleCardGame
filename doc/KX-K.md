# Rapport du projet Citadelle (groupe K)
## Auteurs 
* [LE BIHAN Léo](https://github.com/LeBihanLeo)
* [BONNET Kilian](https://github.com/KilianBonnet)
* [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou)
* [IMAMI Ayoub](https://github.com/AyoubIMAMI)

# L’architecture


# Informations utiles

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

| Isssue                                                                                     | Description                                                     | Date       |
|--------------------------------------------------------------------------------------------|-----------------------------------------------------------------|------------|
| [#7](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/7)   | Ajout des quartiers ayant 8 différents prix                     | 18/11/2021 |
| [#19](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/19) | Ajout des couleurs de quartier                                  | 24/11/2021 |
| [#27](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/27) | Ajout du sort de quartier : Dragoport                           | 03/12/2021 |
| [#34](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/34) | Ajout du sort de quartier : Université                          | 05/12/2021 |
| [#42](https://github.com/pns-si3-projects/projet2-ps5-21-22-ps5-21-22-projet2-k/issues/42) | Ajout du sort de quartier : Cour des miracles et École de magie | 25/11/2021 |


## La représentation du jeu
La représentation jeu correspond à la façon dont le plateau de jeu de Citadelle a été pensé et implémenté dans le code. Ces différents aspects et choix de modélisation sont trouvables dans le package game. Cet élément regroupant à la fois joueurs et cartes, cette implémentation fut faite par l'ensemble du groupe. 

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

# Fonctionnement de l’équipe

# Avancement du projet
