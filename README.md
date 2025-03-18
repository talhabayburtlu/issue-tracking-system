ITS (Issue Tracking System)
===========================
**A platform designed for agile organizations to track their progress built with centralized cloud microservice architecture.**  

The architecture includes a discovery service where other microservices can register themselves and communicate through registry. A gateway is placed to distribute incoming requests to proper microservices. 
With the help of keycloak integration, ITS users and projects are managed through keycloak panel. Finally, the management of issue tracking mechanisms are implemented in issue tracking service.  

ITS has the capability of the applied best practices like:
* Microservice architecture with cloud gateway
* Command based design for business logic implementation
* Messaging systems like Kafka used between services for asynchronous operations
* AWS Bucket integration for storing attachments
* Paginative searching mechanism
* Caching for frequently used repository calls
* Centralized exception messages
* Localization for exception messages


# Architecture

![ITS_ENTITY_RELATIONS-Architecture drawio](https://github.com/user-attachments/assets/7f260d84-b928-44e1-aad7-782fcf13888a)


## Initial Setup
This project uses AWS S3 bucket to manage attachments of issues. So individual bucket properties must be given.
In `issue-tracking-service/src/main/resources` you need to create `application-bucket.properties` file with contents below:
```
aws.s3.endpoint.url=
aws.s3.access.key=
aws.s3.secret.key=
aws.s3.bucket.name=
```

Also project uses spring mail configuration to send mails. Individual mail username and password configuration must be given in `notification-service/src/main/resources` named `application-mail.properties`:
```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=
spring.mail.password=
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

For Keycloak to run in production mode TLS must be configured with certificates. To initialize certificates:
```
mkdir -p certificates
cd ./certificates
openssl req -newkey rsa:2048 -nodes -keyout kc.key.pem -x509 -days 3650 -out kc.crt.pem
cd ..
```

Finally, for building and deploying microservice containers:
```
docker-compose up -d
```

You don't need to worry about database initialization, `multiple-databases.sh` handles relevant database creations.
Also, `its-auth` realm for keycloak is auto imported when docker container is deployed.  


## :door: Gateway Service
Responsible for redirecting client requests to relative microservices and returns the response.

  
## :bookmark: Discovery Service
Responsible for holding a registry for other microservices to register themselves to communicate. Each microservice obtain unique identifier for introducing themselves to other microservices.
Discovery service handles this registration operation.

  
## :outbox_tray: Keycloak Integration Service
Responsible for management and synchronization of users, projects and user roles that are used in Issue Tracking Service via Kafka. 
* When a user or group is created or deleted in Keycloak, it's information is sent to ITS for handling the creation of the user and project.
* When a user is linked to group, it's information is sent to ITS for linking user and project by generating a membership.
* When a predefined role is selected to be created or deleted for a user linked to a group, it's information sent to ITS for generating relative role to be create for that membership

  
## :calendar: Issue Tracking Service
Responsible for agile organizations to track their progress by generating issues of the projects they are in.
* Users that participates into a project can manage issues. They can do that if they are a membership of that project.
* A project can have categories, states and sprints that issues can be configured with.
* An issue have participants are composed of creator, assignees, verifiers, reviewers and watchers.
* An issue can have estimation time which participants can spend their time on the issue.
* Every action taken on issue is logged as activity and can be reviewed by fetching them.
* An issue can have attachments and it's comments can also have attachments.

  
## :email: Notification Service
Responsible for sending  mail to the selected batch of recipients. Notification requests are accepted via kafka connection and can send 
multiple mail templates to multiple users. Any individual request saved in database with successful state or failed state with related exception messages.

  
## :pencil2: Logging Service [Scheduled for 2nd Iteration of Project] :construction:
Will be responsible for centralized logging mechanism for other microservices to log their statements.  

  
## :ticket: Ticket Tracking Service [Scheduled for 3rd Iteration of Project] :construction:
Will be responsible for handling resolution process of customer tickets and creating related issues in issue tracking system.  


  
# Entity & Relation Structure
![ITS_ENTITY_RELATIONS-ENTITY_RELATIONS drawio (2)](https://github.com/user-attachments/assets/5388d694-d000-48de-9ef8-3709e8d70c6e)

# Commands (Use Cases)
![ITS_ENTITY_RELATIONS-COMMANDS drawio](https://github.com/user-attachments/assets/e98abc2d-c89f-4f97-ba90-c95c8d46bd4a)




