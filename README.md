# School Saving Book

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Introduction
**School Saving Book** is a Spring Boot application that helps manage students' saving accounts in a school. This project is aimed at providing an easy way for students to keep track of their savings.

## Features
- User-friendly interface to manage saving accounts
- API endpoints for CRUD operations
- Custom exception handling
- Detailed logging and error messages
- Integration with Swagger for API documentation

## Installation
To get started with this project, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/yrhiday/school-saving-book.git
   ```
2. Navigate to project folder
   ```bash
   cd school-saving-book
   ```
3. Build the project
   ```bash
   mvn clean install
   ```
4. Run this application
   ```
   mvn spring-boot:run
   ```

## Usage
Once the application is running, you can access the API documentation at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API Documentation
Detailed API documentation can be found in the Swagger UI. Here are some example endpoints:

``GET /students: `` Retrieve a list of all students

``POST /students: ``Add a new student

`` GET /students/{id}: ``Retrieve a student by ID

``PUT /students/{id}: ``Update a student by ID

``DELETE /students/{id}: `` Soft delete a student by ID

## Contributing
Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.

2. Create a new branch 
    ```bash
    git checkout -b feature-branch
    ```
3. Make your changes and commit them commit
    ```bash
    git commit -m 'Add new feature'
    ```
4. Push to the branch 
    ```bash
    git push origin feature-branch
    ```

5. Create a new Pull Request.

## License
This project is licensed under the Apache License.
