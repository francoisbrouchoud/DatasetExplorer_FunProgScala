# Projet d'interprétation de données avec Scala
Ce projet utilise le paradigme de la programmation fonctionnelle en Scala pour analyser et interpréter un jeu de données tiré d'un fichier CSV. Ce fichier contient des informations sur les propriétés immobilières en Angleterre.
Plusieurs requêtes fonctionelles sont effectuées pour explorer le jeu de données.

## Schéma de Classes
Le schéma suivant illustre le modèle de données que nous avons construit pour ce projet, basé sur les informations extraites du fichier CSV. Ce modèle utilise les concepts d'héritage via les traits et les énumérations en Scala.

![Class Diagram Property](ClassDiagramProperty.png)

## Features
L'exploration des données s'est faite au travers des 5 requêtes suivantes : 

- Afficher le prix par mètre carré de chaque propriété.
- Afficher le prix moyen par type de maison.
- Listage des propriétés par type de maison.
- Afficher le nombre de pièces pondéré pour les propriétés de plus de 8000m2.
- Afficher le nombre de types de maison pour une ville donnée.

Nous avons également utilisé de la programmation asynchrone pour obtenir le nombre de types de maison pour deux villes différentes.

## Contexte du Projet
Ce projet a été réalisé par Céline Vialard et François Brouchoud dans le cadre du cours "Concepts avancés et techniques actuelle" du bachelor en informatique de gestion à la HES-SO Valais.



