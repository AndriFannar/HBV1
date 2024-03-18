## HBV1 - Team 1
# PHISIO-API
An API for a system that manages requests for physiotherapist clinics.
Supports creation of Users, new requests and the ability to answer health questionnaires to determine the urgency of the request.

### API Availability
The API is hosted on Render and can be accessed at the following [URL](https://hbv1-api.onrender.com/api/v1/).
* To make requests to the API, use the URL above (`https://hbv1-api.onrender.com/api/v1/`) and append the endpoint you want to access, which is found in the documentation for the Controllers.

There is also a website that makes use of the same codebase, available to view [here](https://hbv1.onrender.com/).

### Installation
This API uses Maven to manage dependencies and build the project. The IDE should be able to handle the Maven dependencies automatically.
The Java version used is Java 17.

### Build
To build the project, run the following command in the root directory of the project:
```mvn compile```

### Run
To run the project, run the following command in the root directory of the project:
```mvn spring-boot:run```

### Documentation
View the Maven site for the project to see design documents, dependencies and documentation [here](target/site/index.html).
The JavaDoc for the API can be found in the [docs](target/site/apidocs/index.html).

### Design Documents
It is also possible to view the design documents [here](src/site/markdown/UML.md).

### License
This project is licensed under the MIT License - see the [LICENSE](https://spdx.org/licenses/MIT.html)

