# **Mealer**

Projet Mealer pour la classe SEG 2505 (Groupe 16)

## Authors
@author Hakim <br />
@author Stéphane <br />
@author Nathan Gawargy <br />
@author Astrid <br />
@author Brice <br />
@author Emma <br />

## Livrable 1
**Administrator Login Credentials:** <br />
Email: admin@email.com <br />
Password: mealeradmin09! <br />

Le 1er livrable a pour but de modéliser l'inscription et la connexion d'un nouvel utilisateur (cuisinier ou client) à l'application. Nous avons utilisé Firebase Authentication pour enregistrer des nouveaux utilisateurs et les informations de chaque utilisateur sont stockés dans le Realtime Database (à l'exception du mot de passe). La connexion de chaque utilisateur nécessite une adresse courriel et un mot de passe.
<img src = app/src/Images/UML.jpg>

## Livrable 2
**Administrator Login Credentials:** <br />
Email: admin@email.com <br />
Password: mealeradmin09! <br />

Ce livrable a pour but d'implémenter la plupart de la fonctionalité de l'administrateur. L'administrateur peut voir une liste de plainte associée à différentes cuisiniers (chefs) et peut suspendre un chef pour un temps spécifique (1, 5, ou 15 jours ou une suspension indéfinie). L'administrateur peut aussi ignorer la plainte. Il est important à noter qu'une liste de plainte à été créée dans Firebase (Voir Image ci-dessous).
<img src = app/src/Images/ComplaintList.jpg>
<img src = app/src/Images/UML2.jpg>

## Livrable 3
**Administrator Login Credentials:** <br />
Email: admin@email.com <br />
Password: mealeradmin09! <br />
**Chef Login Credentials:** <br />
Email: bob@jeff.ca <br />
Password: bobjeff <br />

Le livrable 3 a pour but d'implémenter la fonctionalité du cuisinier. Un cuisinier est maintenant capable d'ajouter des repas à un menu. Il a le choix de mettre un repas disponible pour être acheter par les clients. Le cuisinier peut aussi enlever et modifier des repas dans son menu. Il n'est pas possible d'enlever un repas disponible du menu, mais il faut changer la disponibilité de ce repas en premier, puis on peut l'enlever du menu. Chaque chef possède un objet de type menu (Voir Image ci-dessous). 
<img src = app/src/Images/Livrable3Image.jpg>

<img src = app/src/Images/UML3.jpg>

## Livrable 4
**Administrator Login Credentials:** <br />
Email: admin@email.com <br />
Password: mealeradmin09! <br />
**Chef Login Credentials:** <br />
Email: mario@kart.ca <br />
Password: mariokart <br />
**Client Login Credentials:** <br />
Email: mike@grant.ca <br />
Password: mikegrant <br />

Le livrable 4 a pour but d'implémenter la fonctionnalité du client ainsi que l'intégration à l'application. Le client peut rechercher et commander des repas proposés par des cuisiniers non-suspendus. Les cuisiniers peuvent mettre à jour la commande et spécifier le temps de livraison. Après avoir reçu leur repas, les clients peuvent soumettre une note d'évaluation pour un repas et on l'option de soumettre une plainte contre le cuisinier auquel ils ont acheté leur repas. Le cuisinier a maintenant un profil qui indique ces informations ainsi que le nombre de repas vendus et sa note d'évaluation.
<img src = app/src/Images/EditedFinalDiagram.jpg>

<img src = app/src/Images/ChefProfile.jpg>
