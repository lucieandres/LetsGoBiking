# Guide d'utilisation de l'application Let's Go Biking

Avant de commencer, assurez-vous d'avoir correctement configuré l'environnement.

## Prérequis
- **ActiveMQ** doit être en cours d'exécution pour recevoir les instructions de l'itinéraire.
- Exécutez le point batch (`letsgobiking.bat`).

## Utilisation
1. Au démarrage, choisissez une adresse d'origine en saisissant son numéro :
    - **1** : Polytech Nice Sophia
    - **2** : Lyon Brasserie Georges
    - **3** : Bruxelles Atomium
    - **4** : Nice Place Masséna
    - **5** : Autres *(saisir une adresse manuellement)*

2. Choisissez une adresse de destination de la même manière.

3. Une fenêtre s'ouvre affichant la carte avec l'itinéraire complet.
   Cliquez sur la fenêtre.

4. **Étapes à suivre :**
    - Appuyez sur le bouton "Display Next Step" pour afficher les instructions et la carte zoomée en cohérence, permettant d'aller jusqu'à la station la plus proche pour récupérer un vélo.
    - Suivez l'itinéraire pas à pas : de la première station à la seconde, puis de la station à votre destination.

## Remarques
- Observez les instructions dans la zone de texte et la carte zoomée à chaque étape pour faciliter votre itinéraire.
- En sélectionnant Lyon Brasserie Georges - Bruxelles Atomium, vous aurez un exemple parfait pour un itinéraire en vélo. 
- En sélectionnant Polytech Nice Sophia - Nice Place Masséna, vous aurez un exemple idéal où la marche est préferable.

## A propos :
- Stockage des stations ayant un vélo siponible (ou une place), puis recherche parmi celles-ci des 5 stations les plus proches en distance, puis recherche parmi ces 5 de celle ayant l'itinéraire le plus court.
- Itineraire bleu pour la marche, rouge pour lé vélo.
- Point de départ bleu, point d'arrivée rouge.
