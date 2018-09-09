# order: 2
# language: fr
# noinspection SpellCheckingInspection
Fonctionnalité: Lecture d'un fichier.

  NOTE: Le système de coordonnées utilisé ainsi que l'ensemble des instructions sont définis en introduction.

  IMPORTANT: Les indexes des tondeuses lors de la vérification des résultats commencent à 0.

  Scénario: L'invocation du parseur de fichier doit permettre de produire une configuration valide.

    Etant donné un fichier de description de la pelouse situé dans src/test/resources/descriptions/lawn6x6-2mowers.txt
    Quand le fichier est lu par l'application
    Alors on doit retrouver une pelouse de taille 6x6
    Et une tondeuse à l'indexe 0 en position 1, 2, N qui doit exécuter les instructions GAGAGAGAA
    Et une tondeuse à l'indexe 1 en position 3, 3, E qui doit exécuter les instructions AADAADADDA
