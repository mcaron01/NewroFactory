Formation: newro-factory    
===========================  

# Content
Ce document contient une séquence d'étapes et de fonctionnalités à implémenter dans une webapp nommée newro-factory.

Voici le macro-planning et les différentes étapes de ce projet:  
 * t0    - Début du projet
 * t0+3  - Architecture de base, CLI (Add / Edit features), Logging
 * t0+11  - Web UI, Maven, Unit Tests, jQuery Validation, Backend Validation
 * t0+15 - Search, OrderBy, Transactions, Connection-Pool 
 * t0+20 - Spring integration
 * t0+24 - Spring MVC integration, JDBC Template, i18n
 * t0+31 - Maven Multi-modules, Spring Security, Hibernate ORM (JPA, Criteria, QueryDSL, Spring Data JPA)
 * t0+34 - Web Services, end of project
 * t0+44 - Front (Vue.JS, Angular ou React)
 * t0+45 - Présentation commerciale du projet

# Installation

## 1. Base de données
Créer un serveur **MySQL** local.  
Exécuter les scripts **1-SCHEMA.sql**, **2-PRIVILEGES.sql**, **3-ENTRIES.sql** et **4-ENTRIES-QUESTIONS.sql** qui se trouvent dans config/db.  

Schema créé: **newro-factory-db**
Utilisateur créé: `adminnewro`
avec le mot de passe : `Qw€rty1234`

## 2. IDE  
### 2.1. Eclipse  
- Ajouter votre projet au workspace courant: **File** -> **Import** -> **Existing projects into workspace**    
- Créer un nouveau serveur Tomcat 10.0 
- Dans les propriétés du projet sélectionner **Project facets**, convertir votre projet en faceted form, et cocher **Dynamic Web Module** (5.0) et **Java** (17)
- Sélectionner l'onglet **Runtime**  (dans le menu **project facets** précédent)  et cocher le Tomcat 10.0 créé précédemment comme environnement d'éxécution.

## 3. Git repository
- Créer votre compte gitlab **[HERE](https://git.excilys.com)**, et créer un nouveau projet git nommé "newro-factory".  
- Après le commit initial ajouter un commit avec .gitignore adéquat

Vous êtes prêts à démarrer à coder !

## 4. Démarrage
### 4.1. Contexte

Votre client, Jacques B, chargé de former les stagiaires de son entreprise, vous a demandé de réaliser une application lui permettant de suivre et proposer du contenu à ses stagiaires. Les stagiaires sont identifiés par un nom, un prenom, une date d'arrivée dans l'entreprise, une date de fin de formation et une promotion d'appartenance (ex : février 2022). Toutes les données sont obligatoires sauf la date de fin de formation, la promotion doit obligatoirement être une promotion déjà existante, la date de fin de formation doit forcément être après la date d'arrivée dans l'entreprise. La liste des stagiaires doit pouvoir être modifiée en ajoutant un stagiaire, en supprimer un, en modifier un. Etant donné qu'il peut y avoir beaucoup de stagiaires au fil des années la liste doit pouvoir être pagniée. Dans un premier temps la liste des promotions pourra être stockée en dur dans la base de données et n'aura pas besoin d'être modifiée. Le model contient aussi des cours découpés en chapitres et des questions correspondantes à des chapitres, dans un premier temps ces listes seront aussi figées mais paginées.

### 4.2. Client en ligne de commande

La première itération sera dédiée à implémenter une première version fonctionnelle de votre application avec une interface en ligne de commande uniquement. 

L'interface devra proposer les fonctionnalités suivantes : 

- Lister les stagiaires
- Lister les promotions
- Afficher le détail d'un stagiaire
- Afficher le détail d'une question
- Créer un stagiaire
- Editer un stagiaire
- Supprimer un stagiaire


#### 4.2.1. Démarrage

Vous organiserez votre projet en différents packages comme model, persistence, service, ui, mapper, ...
Utilisez le design pattern Singleton où il pourrait avoir du sens et implémentez votre couche de persistence pour gérer votre connexion à la base de données.

#### 4.2.2. Pagination
Maintenant que les fonctionnalités principales de votre application fonctionnent, impleméntez la pagination. Nous vous recommandons l'utilisation d'une classe Page contenant vos entités et vos informations de pagination.

#### 4.2.3. CODE REVIEW 1, logging (t0 + 3 days)

Points d'attention : Architecture, Singleton, Validation, Date API, Sécurisation des saisies utilisateur, Javadoc, Commentaires, Logging

### 4.3. CLI + Interface Web

Maintenant que votre squelette backend fonctionne nous souhaitons ajouter une seconde interface plus "User-friendly" orientée Web.
Comme vous allez avoir besoin de plus en plus de librairies vous devriez utiliser un gestionnaire de build. De plus, tester votre application est primordial, vous devriez donc, à minima, implémenter des tests unitaires avant d'aller plus loin. Il en va de même pour le logging.
Ensuite vous pourrez travailler à implémenter toutes les fonctionnalités avec les pages fournies en utilisant les JSP, les Servlets, JSTL, ...

#### 4.3.1. Maven, Logging & Unit testing
Réusinez votre structure de projet pour correspondre aux standards de Maven (Conseil : vous devriez quitter Eclipse, supprimer tous les fichiers propres à Eclispe comme .settings, déplacer vos dossiers, valider vos modification avec la commande mvn install puis ré-importer votre projet avec File -> Import -> Existing maven project)
Ajouter les librairies nécessaires comme mysql-connector, Junit, Mockito, Slf4j et créez les classes de test pour les classes que vous aviez déjà développé (N.B. : ceci est contre les bonnes pratiques de TDD. Vous devriez toujours développer vos tests en même temps que vos fonctionnalités)

Créer des classes de test implique de prendre en compte TOUTES les possibilités : appels illégitimes, appels légitimes avec des données invalides, appels légitimes avec des données valides.
Ajouter et configurer le module Maven Checkstyle ves les fichiers checkstyle.xml et suppressions.xml fournis dans /config/checkstyle

#### 4.3.2. Implémenter la liste de stagiaires et l'ajout de stagiaire dans l'interface Web
En utilisant le template fourni dans /static intégrez les fonctionnalités précédentes en utilisant les Servlets, les JPSs, JSTL.
Utilisez des DTO (Data Transfer Object) pour tranférer seulement les données nécessaires aux JSPs. 
Implémentez la liste de stagiaires (paginée) et l'ajout de stagiaire.  

#### 4.3.3. Validation = sécurisation
Implémentez la validation aussi bien côté front (jQuery) que back

#### 4.3.4. CODE REVIEW 2 (t0 + 11 days)
Points importants : structure Maven ? Library scopes? Architecture (daos, mappers, services, models, dtos, controllers, exceptions, validators)? Validation? Unit test coverage? JSTL Tags and HTML documents structure.  

#### 4.3.5. Connection pool, Transactions

Ajoutez un pool de connexions (HikariCP), mettez vos credentials dans un fichier properties externe. Implémentez une gestion des transactions solide.

#### 4.3.6. Implémenter toutes les autres fonctionnalités Web
Implémentez la mise à jour, la suppression et le nombre total de stagiaires. Ainsi que la liste de chapitres, la liste de question et le détail d'une question

#### 4.3.7. Implémenter la recherche et l'OrderBy
Le champ de recherche peut filtrer soit par stagiaire soit par promotion.

#### 4.3.8. Ajouter la suppression d'une question à la CLI
Dans l'interface en ligne de commance, ajoutez une fonctionnalité qui permets de supprimer une questions et toutes les propositions qui lui sont liées. Attention : il est interdit d'utiliser les CASCADE SQL.

#### 4.3.9. CODE REVIEW 3 (t0 + 15 jours)
Points importants: Maven structure? Library scopes? Architecture (daos, mappers, services, models, dtos, controllers, exceptions, validators)? Validation? Unit test coverage? Search and order by design choices? JSTL Tags and HTML documents structure.  
Point about Threading (Connections, concurrency), and Transactions.

### 4.4. Introduction au DevOps - Création d'une Pipeline via GitLab-CI

Cette partie va vous permettre de vous initier à la mise en place d'une CI/CD basique via GitLab-CI. L'objectif est de sensibiliser les developpeur à la mentalité DevOps. Tout au long de votre projet il sera par ailleurs nécéssaire de mettre à jour votre pipeline au fur et à mesure des évolutions (Multimodule, Spring, Test Front etc.) Via les fonctionnalités de GitLab-CI, créez un fichier .gitlab-ci.yml et intégrez y un build Maven incluant les test unitaires ainsi que le checkstyle. N'oubliez pas de vous documenter sur le fonctionnement des Runner GitLab et de contacter Max Bauer pour la mise en place de ces derniers. 

#### 4.4.1. Build 

Dans le fichier .gitlab-ci.yml, intégrer le build Maven de votre application.

#### 4.4.2. Test 

Dans le fichier .gitlab-ci.yml, intégrer les tests unitaires de votre application.

#### 4.4.3. Checkstyle 

Dans le fichier .gitlab-ci.yml, intégrer une analyse checkstyle de votre application.

### 4.5. Introduction au Spring Framework

#### 4.5.1. Spring
Utilisez Spring pour gérer le cycle de vie de vos objets et vos transactions. 
Important : utilisez slf4j pour afficher les logs spring. N'oubliez pas de configurer logback.
Remplacer votre pool de Connexion par une Datasource configurée dans le context Spring.
Quels problèmes rencontrez vous ? Etudiez et notez toutes les solutions possibles pour résoudre le problème d'injection de dépendance dans une Servlet.
En gagnant en temps de développement grâce à Spring vous allez désormais pouvoir ajouter de nouvelles fonctionnalités : 
- lister tous les chapitres présents en base de données 
- lister toutes les questions présentes en base de données
- afficher le détail d'une question avec ses propositions
Attention : ne remplacez pas vos Servlets par d'autres classes. Vos controllers doivent toujours étendre HttpServlet

#### 4.5.2. CODE REVIEW 4 + Spring integration (t0 + 20 jours)
Comment une webapp est démarrée, comment Spring se démarre. Explication du problème courant des différents contextes.

#### 4.5.3. JDBCTemplate
Changez votre implémentation de DAO pour utiliser JDBCTemplate de spring-jdbc pour faire vos requêtes.

#### 4.5.4. Spring MVC
Vous pouvez maintenant abandonner les Servlets et utiliser Spring MVC comme Controller pour votre Webapp. Utilisez les annotations de validation de Spring MVC pour valider vos DTOs. Ajouter des pages d'erreur customisées.

#### 4.5.5. i18n
Implémentez les fonctionnalités de Spring multi-langues (Français / Anglais)

#### 4.5.6. CODE REVIEW 5 (t0 + 24 jours)
Points importants : comment avez vous séparé vos contextes Spring / Spring MVC ? Comment passer d'une langue à une autre ? Avez-vous utilisé les annotations Spring-mvc ainsi que les forms et les models ?

### 4.6. Multi module, ORM, and Security

#### 4.6.1. Hibernate
Ajoutez l'ORM Hibernate à votre projet (géré par Spring). Vous pouvez choisir une des API parmi HQL, Criteria, QueryDSL et Spring Data JPA.

#### 4.6.2. Maven multi-module
Maintenant que votre application se complexifie cela pourrait être intéressant de la séparer en multi-modules.
Découpez votre projet maven en 6 modules différents (nous vous recommandons de quitter votre IDE et faire ces changements à la main).
Attention : vous allez aussi avoir besoin de séparer vos fichiers d'applicationContext : en effet chaque module devrait pouvoir fonctionner seul. Vous pouvez créer les modules suivants : core, persistence, service, binding, webapp, console.

#### 4.6.3. Security
Ajoutez Spring Security à votre projet. Choisissez une approche stateless et ajouter un UserDAO supplémentaire avec une table SQL pour stocker et retrouver les informations de Login.
Utilisez Digest HTTP.

#### 4.6.4. CODE REVIEW 6 (t0 + 31 jours)
Points importants: Quelle API était la plus efficace pour vos requêtes ? Quelles étaient les failles de chaque API ?

### 4.7. Web Services, REST API

Vous pouvez désormais ajouter des contrôleurs REST API à votre application. Leur rôle sera de servir de la donnée brute au format JSON au lieu de JSPs.

#### 4.7.3. CODE REVIEW 7 (t0 + 34 jours)
Points d'améliorations avant la release finale. Vue d'ensemble de la qualité de code et des améliorations possibles.

### 4.8. Front End
Créez une autre projet sur votre Gitlab : **newro-factory-front**.

**newro-factory-front** est une Single Page Application (SPA) qui permets la gestion complète des stagiaires et promotion pour un utilisateur admin

Vous devez choisir l'un de ces 3 frameworks front :
* Vue.JS
* Angular
* React

This SPA must use the webservices you just created.

This SPA must be responsive. You can use any css library or create your own CSS.

Don't forget that this project is not on your tomcat, and that the address of your webservices may change from an environment to another.

#### 4.8.1 [Angular](https://angular.io/)

Angular is the rework of AngularJS framework. It's a brand new framework that is component oriented.

It is recommended to write code using [Typescript](https://www.typescriptlang.org/), a typed and object oriented Javascript. But you can still use latest Javascript.

##### [Angular-CLI](https://github.com/angular/angular-cli)
You can bootstrap your Angular project with Angular-CLI tools.

Angular-CLI provides you a pre-configured boilerplate project with all the tools generally used like Karma, Protractor and Jasmine for your tests, the compiler for Typescript and Sass and a first sample component.

For the use of Angular-CLI, we invite you to read the manual on the Angular-CLI github page.

##### Base architecture

In Angular, almost everything is a component, you must think your application like a house (which is a component, basically, your app.component) composed of walls (components too) which are themselves composed of bricks , etc ...
Each room of your house could be a module (see [documentation](https://angular.io/guide/architecture) for more informations)

We recommend you to follow the [style guide of John Papa](https://github.com/johnpapa/angular-styleguide/blob/master/a2/README.md) (same person than AngularJS style guide)

##### Usefull links
- [Angular Tower of Heroes Tutorial](https://angular.io/tutorial)
- [Angular Docs](https://angular.io/guide/architecture)
- [Angular Style guide](https://github.com/johnpapa/angular-styleguide/blob/master/a2/README.md)

#### 4.8.2 [React](https://reactjs.org/)

With React you will introduce yourself to functional programming, latest JS features, and highly scalable single page apps.

##### Components

React is the view layer of your SPA.

As with angular [create-react-app](https://github.com/facebookincubator/create-react-app) will allow you to bootstrap a React app with (almost) no pain.

Drop some [react-bootstrap](http://react-bootstrap.github.io/) or [material UI](http://www.material-ui.com/#/) in it, and you'll be good to go.

Then, you will have to look at the UI you want to build, and separate it in components. You can look at the concepts of smart/dumb containers/components.

Here's my advice :
- Make small, reusable and logic-free components. Put them in "components" directory. (Your UI library will already provide you a lot of them)
- Make business-aware and complex components. Put them in "containers" directory.
- Make layout components which organizes the components in page. Put them in "page" directory.

Maybe [this](http://havesome-react.surge.sh) will help.

API calls: axios
Tests: Jest, enzyme, look at snapshot tests. [cheatsheet](https://gist.github.com/yoavniran/1e3b0162e1545055429e)

##### [Redux](http://redux.js.org/)

Redux concept is not bound to React, but you'll hardly make an interesting application without a simple way to manage the data you display. I said simple. Not easy...

Consider spending some time to understand redux before continuing. Maybe [this](http://havesome-redux.surge.sh/) will help.

When you got it, you can use [ducks](https://github.com/erikras/ducks-modular-redux) architecture (like re-ducks with English accent) to organize your files.
And follow the [Flux Standard Action](https://github.com/acdlite/flux-standard-action) convention for creating your actions.

Use react-redux to connect your components to redux.
Use redux-thunk to make asynchronous action dispatch.

NB: Page's role can also be extended to make the call to the API to hydrate the page on load. For example, you want to display edit page. You need the current data, so you'll want to load it in the first place. You can dispatch an action hydrate your store (copy a portion of the db) in the componentDidMount lifecycle hook.

#### 4.8.3 [VueJS](https://vuejs.org/)


### 4.9. Ré-usinage final, UX et présentation finale

#### 4.9.1. UX
A vous de réfléchir à la meilleure UX possible pour votre application et la mettre en place !

### 4.9.2. Présentation finale (t0 + 48 jours)

But : présenter une version qui peut être déployée aux utilisateurs. Gardez à l'esprit que votre newro-factory a pour but de remplacer quelque chose qui était fait manuellement ou via Excel. Vos public est fonctionnel et non pas technique.

Chaque équipe présentera son projet. La présentation consiste en 3 parties : 
- la présentation produit, d'un point de vue utilisateur (non-technique). Vous nous présentez votre newro-factory et nous dites ce qu'il fait et comment il a été fait
- une démonstration. Attention le public est en droit de vous interrompre pour vous demander d'essayer ou de lui montrer quelque chose
- une revue technique : vous allez rassurer votre client sur ce qu'il a payé. Donnez lui les informations techniques et les métriques nécessaire qui lui permettront de pensez "ils sont compétents, ils ont fait le job et je suis confiant sur le fait que ce soit maintenable et bien codé"

**Attention**: cette présentation n'est pas une restituion de ce que vous avez fait. C'est une **simulation** d'une présentation d'un projet que vous livreriez à votre client.

# 5. Optional modules

## 5.1. Threadlocal (2 days)
Replace existing connection logic with a ThreadLocal object. 

## 5.2. Performance Challenge with Gatling (3 days)
Now is the time to start evaluating your global application performance with a stress-test campain.
Using Gatling, you have one day to stress-test your web application (gatling test and directions present in the folder gatling-test) and use tools like VisualVM to improve the performances. See the relevant README file for more explanations. For now, choose the simulation without Spring Security.

## 5.3. Continuous Integration / Continuous Delivery (6 days)
We want to setup a continuous integration/delivery  system for our webapp with [Jenkins](https://jenkins-ci.org/) and [Docker](https://www.docker.com). Each time we push on master we want Jenkins to retrieve the changes, compile, test on a specific environment, build and push the new image to a registry, then automatically deploy the new image on the Cloud.

### 5.3.1 Jenkins & Docker

Create two Docker images : 
- One to compile, test and package your webapp
- One for the MySQL containing you test data

The both will need to communicate so the unit tests can access to the tests data

Install and configure a Jenkins and create a job that starts the build process everytime a push is performed on master.

### 5.3.2 Your app in docker

Create two Docker images : 
- one for your webapp
- one for your MySQL in production mode 

Use DockerHub and configure your Jenkins so it pushes your images on DockerHub

### 5.3.3 Continuous Delivery
Create four Docker images: one for jenkins, one for compilation and tests, one for production (tomcat) and one for the mysql. Push them to DockerHub.

- Connect with your login to [Docker Cloud](https://cloud.docker.com/) 

- Create a [free account](https://aws.amazon.com/fr/free/) on Amazon Web Services.

- [Link](https://docs.docker.com/docker-cloud/getting-started/link-aws/) your Amazon Web Services account to deploy node clusters and nodes using Docker Cloud’s dashboard. Be careful when choosing the type of node on Docker Cloud, select 't2.micro' under the conditions of free AWS account.

- Observe the diagram below to properly configure the architecture of Docker containers to set up the continuous delivery:
![image](http://s32.postimg.org/iio0ls66t/Continuous_delivery.png)

- Below the activity diagram to figure out all the process:
![image](http://s32.postimg.org/ijyeykoyd/CDProcess_Diagram.png)

## 5.4. Android

Use your webservices already existing to desing an android app

## 5.5. Jooq

Replace your existing ORM with Jooq

## 5.6. Scala

Replace your existing back-end with Scala

## 5.7 Thymeleaf

Replace your existing front-end with Thymeleaf

## 5.8 Microservices

Split you back-end into microservices

## 5.9 Batches
