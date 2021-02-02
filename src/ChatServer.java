package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    A server that manages a simple communication protocol
 */

public class ChatServer 
{
    public static void main(String[] args) throws IOException
    {
        
        final int PORT = 1234;
        final int PORT2 = 1111;
        ServerSocket server = new ServerSocket(PORT);
        // ServerSocket server2 = new ServerSocket(PORT2);
        Chatroom chatroom = new Chatroom();
        // Chatroom chatroom2 = new Chatroom();

        System.out.println("Server has been initiated\nWaiting for clients to connect\n");
            
        while(true)
        {
            
            Socket socket = server.accept();
            System.out.println("Client connected.");
            ChatService service = new ChatService(socket, chatroom);
            Thread thread = new Thread(service);
            thread.start();

        }


    }
}
