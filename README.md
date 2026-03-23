# Overview
A messaging application built in Java that allows users to communicate via a locally hosted REST API.

# How to run
1. Launch the Server
2. Start the client
3. If client connected to Server start messaging

# Features
- Server-Client communication including a Database
- Sending and receiving messages
- JSON based data exchange

# Technologies
- Java
- Spring Boot
- JavaFX
- Jackson

# Architecture
The application consists of a locally hosted Server to which the Client connects to:
- The Server is built with Spring Boot and handles all incoming messages via a REST API.
- The client uses JavaFX to provide a graphical user interface which allows the player to write messages and send them.
