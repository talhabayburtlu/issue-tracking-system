ITS (Issue Tracking System)
===========================
**A platform designed for agile organizations to track their progress built with centralizaed cloud microservice architecture.**  

The architecture includes a discovery service where other microservices can register theirselves and communicate through this registry. A gateway is placed to distribute incoming requests to proper microserivces. 
With the help of keycloak integration, ITS users and projects are registered through keycloak panel. Finally the management of issue tracking mechanisms implemented in issue tracking service.  

ITS has the capability of the applied best practices like:
* Microservice architecture with registery mechanism
* Command based design for business logic implementation
* Messaging systems like Kafka used between services for asynchronous operations
* AWS Bucket integration for storing attachments
* Paginative searching mechanisms
* Caching for frequently used repository calls
* Centralized exception messages
* Localization for exception messages


# Architecture

![ITS_ENTITY_RELATIONS-Architecture drawio](https://github.com/user-attachments/assets/7f260d84-b928-44e1-aad7-782fcf13888a)

## :door: Gateway Service
Responsible for redirecting client requests to relative micro services and returns the response.

  
## :bookmark: Discovery Service
Responible for holding a registry for other microservices to registery theirselves to communicate. Each microservice can be served on dynamic ports and they can not communicate directly because of that.
Discovery service handles this registration operation.

  
## :outbox_tray: Keycloak Integration Service
Responsible for management and synchronization of users, projects and user roles that are used in Issue Tracking Service via Kafka. 
* When a user or group is created or delated in Keycloak, it's information is sent to ITS for handling the creation of the user and project.
* When a user is linked to group, it's information is sent to ITS for linking user and project by generating a membership.
* When a predefined role is selected to be created or deleted for a user linked to a group, it's information sent to ITS for generating relative role to be create for that membership

  
## :calendar: Issue Tracking Service
Responsible for agile organizations to track their progress by generating issues of the projects they are in.
* Users that participates into a project can manage issues. They can do that if they are a membership of that proeject.
* A project can have categories, states and sprints that issues can be configured with.
* An issue have participants are composed of creator, assignees, verifiers, reviewers and watchers.
* An issue can have estimation time which participants can spent their time on the issue.
* Every action taken on issue is logged as activity and can be reviewed by fetching them.
* An issue can have attachments and it's comments can also have attachments.

  
## :email: Notification Service [Will be implemented in 2nd stage of the proejct] :construction:
Will be responsible for sending sms and mail to the selected batch of recepients.  

  
## :pencil2: Logging Service [Will be implemented in 2nd stage of the proejct] :construction:
Will be responsible for centralized logging mechanism for other microservices to log their statements.  

  
## :ticket: Ticket Tracking Service [Will be implemented in 3rd stage of the proejct] :construction:
Will be responsible for managing resolvation of customer tickets and create related issues in issue tracking system.  


  
# Entity & Relation Structure
![ITS_ENTITY_RELATIONS-ENTITY_RELATIONS drawio (2)](https://github.com/user-attachments/assets/5388d694-d000-48de-9ef8-3709e8d70c6e)

# Commands (Use Cases)
![ITS_ENTITY_RELATIONS-COMMANDS drawio](https://github.com/user-attachments/assets/e98abc2d-c89f-4f97-ba90-c95c8d46bd4a)




