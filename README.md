# Alogmania-Judge: A Coding Platform

## Introduction

Alogmania-Judge is a comprehensive coding platform designed to provide an engaging and competitive programming experience. It offers various features such as problem browsing, a leaderboard system, problem submission, and an online compiler.

## Features

- **Browsing of Problems**: Users can browse and select from a wide range of coding problems.
- **Leaderboard System**: A dynamic leaderboard to track and display user rankings.
- **Problem Submission**: Users can submit their solutions to problems and get instant feedback.
- **Online Compiler**: Integrated online compiler to run and test code in real-time.

## Tech Stack

- **Backend**: Java and Python
- **Frontend**: React
- **Message Queue**: Kafka
- **Leaderboard System**: Redis
- **Containerization**: Docker

## Microservices Architecture

Alogmania-Judge is built using a microservices architecture, providing scalability and flexibility. The architecture includes:

1. **Gateway Service**: Handles all incoming requests and routes them to the appropriate services.
2. **Registry Service**: Keeps track of all running services and their instances.
3. **Authentication Service**: Manages user authentication and authorization.
4. **Problem Service**: Handles problem-related functionalities.
5. **User Microservice**: Manages user information and activities.
6. **Leaderboard Service**: Manages the leaderboard data and updates.
7. **Remote Code Execution (RCE) Engine**: Custom-built engine to execute user-submitted code (currently under development).

## Future Enhancements

- **Contest Creation**: A feature to create and manage coding contests.

## Code Execution Details

The RCE engine is custom-built for learning purposes and is still under development. While it guarantees code execution, it may have performance and reliability issues.

- **Asynchronous Code Execution**: Kafka is used to handle asynchronous code execution tasks.
- **Synchronous Microservice Communication**: Currently, microservice communication is synchronous, with plans to make it asynchronous in the future.

## How to Use

**Note:** The platform is still under development and has several known issues. Use it at your own risk.
**Note:** Avoid wasting your time here for now ,i will make the code much better and readable and then  i will allow contributions

### Prerequisites

- Kafka server
- Redis server
- Docker Engine or Docker Desktop

### Steps to Run
### avoid running because its hectic job for now , i will try to make the startup much easiers and better 
1. **Run the Kafka server, Redis server, and Docker engine**.
2. **Run Java Services**:
    1. Run the Registry Service.
    2. Run the API Gateway.
    3. Run the Authentication Service.
    4. Run the Problem Service.
    5. Run the User Microservice.
    6. Run the Leaderboard Service.
    7. Run the RCE Engine.
3. **Run Python Code** separately.
4. **Frontend Setup**:
    - Ensure all Java code dependencies are correctly added.
    - Note: The frontend code is not fully developed and may have issues.

## Conclusion

Alogmania-Judge is a work in progress with many features and improvements planned for the future. 



---
