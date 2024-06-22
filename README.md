Alogmania-Judge: A Coding Platform
Introduction
Alogmania-Judge is a comprehensive coding platform designed to provide an engaging and competitive programming experience. It offers various features such as problem browsing, a leaderboard system, problem submission, and an online compiler.

Features
Browsing of Problems: Users can browse and select from a wide range of coding problems.
Leaderboard System: A dynamic leaderboard to track and display user rankings.
Problem Submission: Users can submit their solutions to problems and get instant feedback.
Online Compiler: Integrated online compiler to run and test code in real-time.
Tech Stack
Backend: Java and Python
Frontend: React
Message Queue: Kafka
Leaderboard System: Redis
Containerization: Docker
Microservices Architecture
Alogmania-Judge is built using a microservices architecture, providing scalability and flexibility. The architecture includes:

Gateway Service: Handles all incoming requests and routes them to the appropriate services.
Registry Service: Keeps track of all running services and their instances.
Authentication Service: Manages user authentication and authorization.
Problem Service: Handles problem-related functionalities.
User Microservice: Manages user information and activities.
Leaderboard Service: Manages the leaderboard data and updates.
Remote Code Execution (RCE) Engine: Custom-built engine to execute user-submitted code (currently under development).
Future Enhancements
Contest Creation: A feature to create and manage coding contests.
Code Execution Details
The RCE engine is custom-built for learning purposes and is still under development. While it guarantees code execution, it may have performance and reliability issues.

Asynchronous Code Execution: Kafka is used to handle asynchronous code execution tasks.
Synchronous Microservice Communication: Currently, microservice communication is synchronous, with plans to make it asynchronous in the future.
How to Use
Note: The platform is still under development and has several known issues. Use it at your own risk.

Prerequisites
Kafka server
Redis server
Docker Engine or Docker Desktop
Steps to Run
Run the Kafka server, Redis server, and Docker engine.
Run Java Services:
Run the Registry Service.
Run the API Gateway.
Run the Authentication Service.
Run the Problem Service.
Run the User Microservice.
Run the Leaderboard Service.
Run the RCE Engine.
Run Python Code separately.
Frontend Setup:
Ensure all Java code dependencies are correctly added.
Note: The frontend code is not fully developed and may have issues.
Conclusion
Alogmania-Judge is a work in progress with many features and improvements planned for the future. Your feedback and contributions are welcome to help make this platform better.

For any issues or contributions, please contact [Your Contact Information] or visit our GitHub Repository.
