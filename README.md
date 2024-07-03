# Football Oracle: Your AI-Powered Player Analytics tool

Football Oracle is a cutting-edge web application that transforms the way football enthusiasts and professionals gather player insights. By leveraging advanced AI and natural language processing, Football Oracle provides comprehensive, data-driven football player analytics on demand.  Whether you're a football fan, a fantasy football manager, or a just a freak loving this game as I do, Football Oracle empowers you with the knowledge you need and elevate your understanding of this beautiful game.

## Key User Features

* Intuitive Player Search: Easily find detailed player analytics reports on any football player, from rising stars to seasoned veterans.
* AI-Generated Analytics: Receive comprehensive player analyses, including strengths, weaknesses, and potential impact on the team.
* Personalized Ratings and Reviews: Share your own expert opinions by rating and commenting on reports, fostering a vibrant community of football enthusiasts.
* Robust User Profiles: Manage your personal information, track your rating history, and update your reviews with ease.
* Community Interaction: Explore the ratings and comments of other users to gain diverse perspectives and insights.

## How It Works

1. Login or Sign Up: Create a free account to access the full suite of Football Oracle features.
2. Search for Players: Use the intuitive search bar to find the player you're interested in.
3. Explore Scouting Reports: Dive into AI-generated analytics that provide in-depth analysis of player attributes, statistics, and performance.
4. Rate and Review: Share your thoughts by rating reports and leaving comments, contributing to a growing knowledge base for the community.
5. Connect with Others: Browse the profiles of other users to discover new players and learn from their insights.

## Key Technical Features

* Spring Boot Framework: Utilizes the Spring Boot framework for building a scalable and production-ready application.
* PostgreSQL Database: Manages football-related data using a PostgreSQL database, ensuring reliable data storage and retrieval.
* JWT Authentication: Implements JSON Web Token (JWT) for secure authentication and authorization.
* Hibernate ORM: Leverages Hibernate for efficient and transparent object-relational mapping.
* RESTful APIs: Provides a set of RESTful APIs for seamless integration with other systems and services.
* SonarQube Integration: Continuously inspects code quality using SonarQube, ensuring adherence to best practices and standards.
* Flyway Migration: Uses Flyway for database version control, allowing easy management of database schema changes.
* Logging: Implements comprehensive logging using various logging frameworks to facilitate monitoring and debugging.
* Docker Support: Easily deployable using Docker, with a docker-compose.yml file for setting up the application and SonarQube services.

## Functional and Non-Functional Requirements
Please see the [Functional and Non-Functional Requirements](adr/FR_and_NFR_list.md) for detailed information on the features and performance goals of Football Oracle.

## Use Cases
Please see the [Use Cases](adr/use_cases.md) for detailed information on the use cases of Football Oracle.

## Object and Classes
Please see the [Object and Classes](adr/object_and_classes.md) for detailed information on the object and classes of Football Oracle.

## Entity Relationship Diagram
Please see the [Entity Relationship Diagram](adr/ER_diagram.md) for detailed information on the entity relationship diagram of Football Oracle.

## Project Structure

```
📂 sabit-shaiholla/football-oracle/ - central repository of the project
├── 📂 adr/ - store of architectural decision records (ADRs)
├── 📂 backend/ - backend of the project (Spring Boot)
│   ├── 📂 src/ - source folder of BE project
│   │   ├── 📂 main/java/com.oracle.football - main package of the project
│   │   │   ├── 📂 controller/ - controllers of the project
│   │   │   ├── 📂 config/ - configurations of the project
│   │   │   ├── 📂 dto/ - data transfer objects of the project
│   │   │   ├── 📂 exception/ - exceptions of the project
│   │   │   ├── 📂 jwt/ - JWT configurations of the project
│   │   │   ├── 📂 security/ - security configurations of the project
│   │   │   ├── 📂 model/ - models of the project
│   │   │   ├── 📂 repository/ - repositories of the project
│   │   │   ├── 📂 service/ - services of the project
│   │   │   └── 📂 FootballApplication.java - entry point of the project
│   │   ├── 📂 resources/ - resources folder of main package of BE project
│   │   │   ├── 📂 application.yaml - configurations of the project
│   │   │   └── 📂 db/migration - database migration scripts
│   │   ├── 📂 test/java/com.oracle.football - test package of the project
│   │   │   ├── 📂 Player/ - tests related to Player entity
│   │   │   ├── 📂 User/ - tests related to User entity
│   │   │   ├── 📂 PlayerReport/ - tests related to PlayerReport entity
│   │   │   └── 📂 FootballApplicationTests.java - entry point of the tests
│   └── 📂 pom.xml - dependencies of the project
└── 📂 frontend/ - frontend of the project (HTML, CSS, JavaScript)
    └── 📂 football-oracle-fe/ - frontend of the project on React
        ├── 📂 src/ - source folder of FE project
        │   ├── 📂 components/ - components of the project
        │   ├── 📂 services/ - services of the project
        │   ├── 📂 assets/ - assets of the project
        │   ├── 📂 Home.jsx - Home page component of the project
        │   ├── 📂 main.jsx - entry point of the project
        │   └── 📂 User.jsx - User page component of the project
        ├── 📂 package.json - dependencies of the project
        └── 📂 README.md - readme for FE of the project
    
```

## Example view of the project
![Example view of the project](.github/images/site_example.png)

## Architecture

1. User Interaction
* Users interact with the Front-End application.
* Users can search for players, view reports, and add reviews
2. Front-End Application
* The React app sends requests to the Back-End using RESTful APIs.
* It handles routing, state management, and UI rendering. 
3. Back-End Application
* The Spring Boot application processes requests from the Front-End.
* It handles business logic, data validation, and communication with the database.
* Controllers manage endpoints for user authentication, player data retrieval, and reviews.
* Services contain the business logic.
* Repositories interact with the PostgreSQL database.
4. Data Fetching Logic
* Database Check: The Back-End checks the PostgreSQL database for player data.
* Fetch from WebPage: If data is not present, it fetches data from the WebPage (FBREF.com).
* Fetch from Gemini Pro API: After getting data from the WebPage, it requests player analytics reports via external API. 
* Save to Database: The fetched data and reports are saved in the database for future queries.
5. External API Interaction
* The Back-End interacts with Gemini Pro API to fetch and process player data when not found in the database.

## Technology Stack

* Front-End: HTML, CSS, JavaScript
* Back-End: Spring Boot (Java)
* Database: PostgreSQL
* External API: Gemini Pro API (Google)

## Installation

### Pre-requisites
* Java 17: Ensure you have JDK 17 installed.
* Maven: Ensure you have Maven installed.
* Docker: Ensure you have Docker installed.
* Node.js and npm: Ensure you have Node.js and npm installed. You can download them from [Node.js official site](https://nodejs.org/en).

To get started with the Football Oracle project, follow these steps:

1. Clone the repository:
```
git clone https://github.com/sabit-shaiholla/football-oracle.git
cd football-oracle
```
2. Set up environment variables:
Create a .env file in the root directory with the necessary environment variables.
```
JWT_SECRET=your_jwt_secret (at least 256 bits)
GOOGLE_CLOUD_PROJECT_ID=your_project_id (Google Cloud Project ID)
GOOGLE_CLOUD_REGION=your_region (Google Cloud Region)
SPRING_DATASOURCE_URL=your_postgres_url (PostgreSQL URL)
SPRING_DATASOURCE_USERNAME=your_postgres_username (PostgreSQL Username)
SPRING_DATASOURCE_PASSWORD=your_postgres_password (PostgreSQL Password)
SONAR_TOKEN=your_sonar_token (SonarQube token)
```
3. Start PostgreSQL and SonarQube:
Use Docker Compose to start the PostgreSQL and SonarQube services:
```
docker-compose up -d
```
4. Build and run the back-end application:
Use Maven to build and run the application:
```
mvn clean install
mvn spring-boot:run
```
This command will start the PostgreSQL database and SonarQube services in detached mode.
5. Navigate to the front-end directory:
```
cd frontend/football-oracle-fe
```
6. Install npm dependencies:
Use npm to install the necessary dependencies for the front-end:
```
npm install
```
7. Run the front-end application:
Start the front-end application using the following command:
```
npm run dev
```
8. Run SonarQube:
Ensure SonarQube is running and then execute:
```
mvn sonar:sonar -Dsonar.token=SONAR_TOKEN
```
6. Access the Application and SonarQube:
* Back-end Application: The application should be running at http://localhost:8080.
* Front-end Application: The front-end should be running at http://localhost:3000.
* SonarQube: The SonarQube dashboard can be accessed at http://localhost:9000 with the default credentials (you will be asked to update the default password):
  * Username: admin
  * Password: admin

## Contributing
I welcome contributions from the community! If you're a football enthusiast, data scientist, or developer, there are plenty of ways to get involved.

## Get Started

Ready to elevate your football knowledge? Visit Football Oracle today and experience the future of football analytics.
[Link to deployed app - TBD]