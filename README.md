# TCP/IP Chat Application

A Java-based TCP/IP chat application built as a graduation project. It showcases the practical use of design patterns—**Proxy**, **Command**, and **State**—within a client-server chat system.

## Features

- **Client-Server Architecture** for real-time messaging
- Implements the following design patterns:
  - **Proxy** – Controls and secures access to the chat server
  - **Command** – Encapsulates chat operations as objects
  - **State** – Manages different states of the chat session dynamically

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Git (optional, for cloning the repo)

### Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Or-Jerbi/TCP-IP-Chat-Application.git
2. Navigate into the project directory:
   ```bash
   cd TCP-IP-Chat-Application

3. Compile the source files:

   ```bash
   javac -d bin src/il/ac/hit/chat/*.java
   
4. Run the server:
   ```bash
   java -cp bin il.ac.hit.chat.ChatServer
5. Run a client instance (in a new terminal or on a different machine):
   ```bash
   java -cp bin il.ac.hit.chat.ChatServer

## Project Structure
   TCP-IP-Chat-Application/
├── src/
│   └── il/
│       └── ac/
│           └── hit/
│               └── chat/
│                   ├── ChatClient.java
│                   ├── ChatServer.java
│                   ├── ProxyPattern.java
│                   ├── CommandPattern.java
│                   └── StatePattern.java
├── bin/
└── README.md


---

## Design Patterns

### Proxy Pattern
The Proxy Pattern is used to control access to the chat server. It acts as a gateway, ensuring that only authorized users or valid requests can interact with the server, providing an extra layer of security and abstraction.

### Command Pattern
The Command Pattern encapsulates actions such as sending messages or disconnecting clients as objects. This allows for more flexible handling of user actions, such as queuing requests, undoing actions, or adding new commands in the future.

### State Pattern
The State Pattern manages the different states of a client (e.g., connecting, connected, disconnected) and ensures that the application behaves differently depending on the current state, making the system more modular and easier to extend.

