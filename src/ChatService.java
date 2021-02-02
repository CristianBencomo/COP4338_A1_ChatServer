package src;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatService implements Runnable 
{
    private Socket socket;
    private Chatroom chatroom;
    private Scanner instream;
    private PrintWriter outstream;
    private String username;

    private Lock chatroomLock = new ReentrantLock();

    /*
     * Constructs a service object that processes commands from a socket for a chat
     * 
     * @param socket for the socket
     * 
     * @param chatroom for the chatroom
     */

    public ChatService(Socket socket, Chatroom chatroom) 
    {
        this.socket = socket;
        this.chatroom = chatroom;
    }

    
    public void run()
    {
        try 
        {
            try 
            {
                instream = new Scanner(socket.getInputStream());
                outstream = new PrintWriter(socket.getOutputStream());
                signinUser();
                doService();
            }
            finally
            {
                socket.close();
            }
        } 
        catch (Exception exeption) 
        {
            exeption.printStackTrace();
        }
    }

    
    public void doService() 
    {

        String message;

        while (true) 
        {   
            if (!instream.hasNext()) 
            {
                continue;
            } else {
                message = instream.nextLine();
            }
            if (message.equals("LOGOUT")) {
                logoutUser();
            } else {
                chatroomLock.lock();
                try
                {
                chatroom.broadcast(username,message);
                }
                finally
                {
                    chatroomLock.unlock();
                }
            }

        }
    }

    
    public void signinUser()
    {
        boolean flag = false;
        do
        {
            outstream.println("Please enter your username:\n");
            username = instream.nextLine();
            ArrayList<String> existingUsers;
            chatroomLock.lock();
            try
            {
                existingUsers = chatroom.getUserList();
                for(String existingUser : existingUsers )
                {
                    if(username.equals(existingUser))
                    {
                        outstream.println("user with the name " + username + " Already exists");
                        continue;
                    }
                }
                chatroom.addUser(username);
                flag = true;
                outstream.println("Signed in as " + username);
            }
            finally
            {
                chatroomLock.unlock();
            }   
        }while(!flag);
    }

    
    public void logoutUser() 
    {
        chatroomLock.lock();
        try
        {
            chatroom.removeUser(username);
            outstream.println("Successfully signed off");
        }
        finally
        {
            chatroomLock.unlock();
        }
    }
}