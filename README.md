# Chat Server

## COP 4338
Assignment 1
Cristian Bencomo


## Description
a client will send 3 types of messages to the other clients

> Resquest: BROADCAST username message:     

> Response: calls the write function of each specific client service other than the sender

> Meaning: the message sent by 1 client will be displayed for every other client


> Request: SIGNIN username 

> Response: Either accepts the request or denies it

> Meaning: The program will confirm that there is no other user with the same name, and will then assign the username to the socket


> Request: LOGOUT username

> Response: Goodbye message and remove username

> Meaning: The program will delete the username from the users Arraylist and close its corresponding thread and socket


## Contents
The src folder contains 3 java files
*   ChatServer.java contains the main function and creates the server
*   ChatService.java defines each individual client thread
*   Chatroom.java stores the user Arraylist and their respective chatservice instance


## How to use
*   Run the main ChatServer 
*   Check the terminal running the server to confirm that the server is awaiting connections
*   Connect to the server from different terminals by using telnet, connect to localhost and the port 1234
*   Chat!