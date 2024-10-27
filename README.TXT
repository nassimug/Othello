# Othello Game en Java

Ce projet est une implémentation du jeu Othello, développée en Java en utilisant la bibliothèque graphique Swing pour créer une interface visuelle 2D intuitive. Ce jeu permet à deux joueurs de s'affronter en alternant les coups sur un plateau de 64 cases. Le joueur avec le plus de pions de sa couleur sur le plateau à la fin de la partie est déclaré gagnant.

## Table des Matières

1. [Présentation du Jeu](#présentation-du-jeu)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies Utilisées](#technologies-utilisées)
4. [Installation](#installation)
5. [Difficultés et Améliorations Possibles](#difficultés-et-améliorations-possibles)

## Présentation du Jeu

Othello est un jeu de stratégie à deux joueurs qui se joue sur un plateau 8x8. Chaque joueur possède des pions bicolores, noirs et blancs. Le but du jeu est de capturer le maximum de pions de l'adversaire en les entourant avec ses propres pions. Le jeu se termine lorsque plus aucun joueur ne peut poser de pions, et celui ayant le plus de pions de sa couleur remporte la partie.

### Règles de Base

1. Le joueur avec les pions noirs commence.
2. Les pions de l'adversaire sont retournés si un pion est posé de manière à encercler ceux de l'adversaire.
3. Le jeu se termine lorsqu’aucun joueur ne peut poser de pion, et le gagnant est celui avec le plus de pions visibles.

## Fonctionnalités

- **Fonction `CoupAutoriser`** : Vérifie les mouvements possibles dans les huit directions à partir d'une position donnée sur le plateau et retourne les pions de l'adversaire si les conditions sont remplies.
- **Fonction `CasePossible`** : Affiche les coups possibles en ajoutant un petit pion rouge là où un mouvement est autorisé pour le joueur actuel.
- **Fonction `ComptePion`** : Compte le nombre de pions noirs, blancs et les cases vides sur le plateau, fournissant ainsi un score en temps réel.

## Technologies Utilisées

- **Java** : Langage de programmation principal.
- **Swing** : Bibliothèque graphique utilisée pour créer l'interface utilisateur.
- **Git et GitLab** : Utilisés pour la gestion de version et la collaboration.

## Installation

1. Clonez le dépôt du projet :
   ```bash
   git clone https://github.com/nassimug/Othello/
2. Importez le projet dans votre IDE Java préféré (par exemple, VSCode, IntelliJ).
3. Compilez et exécutez le projet pour lancer le jeu Othello.
