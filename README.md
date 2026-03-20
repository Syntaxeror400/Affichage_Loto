# Affichage Loto v1.3.0
Un projet de logiciel d'affichage pour une grille de loto (1-90) avec une multitude de fonctionnalités et d'options d'affichage.

Requiert Java 15 ou ultérieur pour être utilisé. Il est possible de vérifier la version de Java avec la commande "java -version".

Le package Splash (https://github.com/Syntaxeror400/Splash) est requis pour la compilation du projet. Il n'est **pas** requis si l'on souhaite uniquement utiliser l'application.

## Fonctionnalitées
* Affichage des nombres de 1 à 90 dans une grille avec démarquage pour le dernier nombre affiché.
* Historique des entrées et annulation/restauration possible pour l'ensemble de l'affichage.
* Vérification des nombres avec affichage des nombres correctes et incorrects
* Affichage secondaire pour le confort de l'opérateur
* Fenêtre de correction pour des modifications rapides impossible autrement
* Affichage récapitulatif du nombre de cases correctes, incorrectes et requises (1 ligne = 5, 2 lignes = 10, 3 lignes = 15)
* Ecran d'attente lorsque la grille n'est pas utilisée, possibilité d'utiliser des images provenant d'un dossier
* Boutons de tests pour facilement préparer le logiciel avant l'évènement
* Ensemble d'options sauvegardables et automatiquement chargées
* Sauvegarde possible de la liste des nombres sortis
* Fonctionne sur toutes les plateformes (principalement testé sur MacOS)
* Grande quantité de raccourcis clavier utilisables
* Logiciel intégralement en français

## Options modifiables dans le logiciel
* Police d'écriture, taille des caractères et type (gras/classique)
* Dossier de sauvegarde des nombres sortis
* Couleur de fond des cases (cases normales / dernier nombre sorti / nombres incorrectes)
* Couleur du texte des cases (cases normales / nombres correctes / nombres incorrectes)
* Possibilité de colorer le fond de case pour les nombres correctes
