#order: 2
#language: fr

Fonctionnalité: Lecture de fichiers de description

  Scénario: Production d'une configuration valide à partir d'un fichier.

    Etant donné un fichier de description de la pelouse situé dans src/test/resources/descriptions/lawn6x6-2mowers.txt
    Quand le fichier est lu par l'application
    Alors on doit retrouver une pelouse de taille 6x6
    Et une tondeuse à l'indexe 0 en position 1, 2, N qui doit exécuter les instructions GAGAGAGAA
    Et une tondeuse à l'indexe 1 en position 3, 3, E qui doit exécuter les instructions AADAADADDA
