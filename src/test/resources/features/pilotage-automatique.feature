#order: 1
#language: fr

Fonctionnalité: Pilotage automatique

  NOTE: Le système de coordonnées utilisé ainsi que l'ensemble des instructions sont définis en introduction.

  IMPORTANT: Les indexes des tondeuses lors de la vérification des résultats commencent à 0.

  Scénario: Execution des instructions par le programme.

    Etant donné une pelouse de largeur 6 et de hauteur 6
    Et une tondeuse initialement en position 1, 2, N qui doit exécuter les instructions GAGAGAGAA
    Et une tondeuse initialement en position 3, 3, E qui doit exécuter les instructions AADAADADDA
    Quand les instructions sont exécutées
    Alors la position finale de la tondeuse 1 doit être 1 3 N
    Et la position finale de la tondeuse 2 doit être 5 1 E

