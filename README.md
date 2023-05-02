# CryptMe

## Introduction

**CryptMe** is a secure messaging application that allows users to communicate with each other in a private and
confidential manner. The application provides end-to-end encryption using two different encryption algorithms: AES (
Advanced Encryption Standard) and DES (Data Encryption Standard). Both AES and DES are symmetric encryption algorithms,
which means that the same secret key is used for both encryption and decryption.

The application consists of two components: a server and a client. The server is responsible for managing user
connections and distributing messages between clients. The client is a graphical user interface that allows users to
connect to the server and communicate with each other.

When a user connects to the server, they are prompted to enter a username. Once the username is entered, the server
assigns it to the user and adds it to a list of connected users. The user is then able to send and receive messages to
and from other connected users.

To ensure that messages are secure, the application provides two different encryption methods: AES and DES. When a user
selects an encryption method, the application generates a secret key and an initialization vector for that method. The
key and initialization vector are then sent to the user, who can use them to encrypt and decrypt messages.

## Features

- AES encryption algorithm with 128-bit key size
- DES encryption algorithm with 56-bit key size
- Initialization vectors of 16 bytes for AES and 8 bytes for DES
- User registration with unique usernames
- Secure communication over an encrypted channel
- Log file to store generated keys and vectors
- Disconnect option for clients

## Installation and Usage

```
Clone the repository or download the zip file.
Compile with "javac *"
Rum the Server.java file to start the server.
Run the Client.java file to start the client.
```

![alt text](https://github.com/atikfirat/CryptMe/blob/main/demo/Server.png?raw=true)

## Demo

![alt text](https://github.com/atikfirat/CryptMe/blob/main/demo/demo.gif?raw=true)

