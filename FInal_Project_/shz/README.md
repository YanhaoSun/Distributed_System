# Name of Team: SHZ
### Team members:
Yanhao Sun-19205434-yanhao.sun@ucdconnect.ie

Zhao Tong-23203139-zhao.tong@ucdconnect.ie

Jiayu He-20205655-jiayu.he@ucdconnect.ie

Link of Video and Report: https://drive.google.com/drive/folders/1YKkb1rKDCDASTonJrlvNcoe39RGu9Uvf?usp=drive_link
## Bank System


### 1. Basic Function
We have implemented a banking system that combines REST and Message-Oriented Middleware (MOM) technologies to achieve its functionalities.  
The system consists of three main components:
- User Service: user login and registration
- Basic Service: includes deposit and withdraw service, get user balance and transaction records, and send request to purchase financial products.
- Financial Product Service: generate recommended investment amounts based on user information, return product information, and process purchase requests.

The user service includes a microservice named "login".  
The basic service includes a microservice named "basic".  
The financial product service consists of a financial product management microservice named "broker" and three specific financial product microservices: "bond", "fund", and "stock". Each microservice has its own database.  
The three microservices can interact with each other using REST technology, while interactions among the four microservices of the financial product service use MOM (Message-Oriented Middleware) technology.
### 2. Project Execution
#### (1) Install the project
```bash
mvn clean install
```
#### (2) Start Container
If needed, use the following commands to check and remove the existing containers.
```bash
docker ps -a
docker rm -f $(docker ps -aq)
```
After that, start the containers:
```bash
docker-compose up --build
```
*Note:  
-- The containers are of quite large volume, and the building process may take a significant amount of time.  
-- Sometimes, there might be errors where microservices are created before their databases, despite adding health checks and dependencies in the Docker Compose file. If this happens, try restarting the containers using the above command.  
-- We recommend running the containers on macOS. (The project has been successfully tested on MacBook by two team members, but the ActiveMQ container used for MOM technology may not run correctly on Windows systems.)
-- Make sure activemq starts successfully*

#### (3) Test Microservices
Use the following command to start the client for interaction with the banking system:
```bash
mvn compile spring-boot:run -pl client
```
You can then follow the prompts to register, log in, and perform other operations.