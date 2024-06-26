## HBV1 - Team 1
# Physiotherapist Registration Website
A website for a system that manages requests for physiotherapist clinics made with Java Spring.
Supports creation of Users, new requests and the ability to answer health questionnaires to determine the urgency of the request.
This main branch is the final version of the project since December 2023.

### Website Availability
~The website is hosted on Render and can be accessed [here](https://hbv1.onrender.com/).~
* No longer hosted online.

### API
This project has been converted to an RESTful API, which was developed from January 2024 to April 2024. To view details about the API please check out the `api` branch [here](https://github.com/AndriFannar/HBV1/tree/api).

### Installation
This Java Spring Application uses Maven to manage dependencies and build the project. The IDE should be able to handle the Maven dependencies automatically.
The Java version used is Java 17.

### Build
To build the project, run the following command in the root directory of the project:
```mvn compile```

### Run
To run the project, run the following command in the root directory of the project:
```mvn spring-boot:run```
* Note: The project requires a database to run, which is no longer hosted by the team. Before running the project, make sure that a PostgreSQL database is available and change the information in `application.properties`  to match.

### Documentation
View the Maven site for the project to see design documents, dependencies and documentation (made for the API) [here](https://andrifannar.github.io/HBV1/target/site/index.html).

The JavaDoc for the API can be found in the [docs](https://andrifannar.github.io/HBV1/target/site/apidocs/index.html).

### Design Documents
It is also possible to view the design documents [here](src/site/markdown/UML.md).

### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE), and [SPDX](https://spdx.org/licenses/MIT.html)

