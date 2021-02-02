package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatService implements Runnable {
    private Socket socket;
    private Chatroom chatroom;
    private BufferedReader instream;
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

    public ChatService(Socket socket, Chatroom chatroom) {
        this.socket = socket;
        this.chatroom = chatroom;
    }

    public void run() {
        try {
            try {
                instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outstream = new PrintWriter(socket.getOutputStream());
                signinUser();
                doService();
            } finally {
                socket.close();
            }
        } catch (Exception exeption) {
            exeption.printStackTrace();
        }
    }

    public void doService() {

        String line;
        
        try {
            while ((line = instream.readLine()) != null) 
            {

                if (line.equals("LOGOUT")) {
                    logoutUser();
                    break;
                } else {
                    chatroomLock.lock();
                    try {
                        broadcast(line);
                    } finally {
                        chatroomLock.unlock();
                    }
                }

            }
        } catch (IOException exception) 
        {
            exception.printStackTrace();
        }
    }

    
    public void signinUser()
    {
        boolean flag = false;
        do {
            try {
                flag = false;
                write("Please enter your username:\n");
                username = instream.readLine();
                ArrayList<String> existingUsers;
                chatroomLock.lock();
                try {
                    existingUsers = chatroom.getUserList();
                    for (String existingUser : existingUsers) {
                        if (username.equals(existingUser)) {
                            write("user with the name " + username + " Already exists\n");
                            flag = true;
                        }
                    }
                    if(flag)
                    {
                        continue;
                    }
                    chatroom.addUser(this,username);
                    write("Signed in as " + username + "\n\n");

                    for(String client : existingUsers)
                    {
                        if(username.equals(client))
                        {
                            continue;
                        }
                        chatroom.getService(client).write(username + " has joined the session\nNow there are " + existingUsers.size() + " users in the chatroom\n\n" );
                    }
                } finally {
                    chatroomLock.unlock();
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
             
        }while(flag);
    }

    
    public void logoutUser() 
    {
        chatroomLock.lock();
        try
        {
            chatroom.removeUser(this, username);
            write("Successfully signed off\n");
            for(String client : chatroom.getUserList())
            {
                if(username.equals(client))
                {
                    continue;
                }
                chatroom.getService(client).write(username + " has left the session\nNow there are " + chatroom.getUserList().size() + " users in the chatroom\n\n" );
            }
        }
        finally
        {
            chatroomLock.unlock();
        }
    }


    public void broadcast(String message)
    {
        ArrayList<String> clients = chatroom.getUserList();     
        
        for(String client : clients)
        {
            if(username.equals(client))
            {
            }
            else
            {            
            chatroom.getService(client).receiveMessage(username, message);      
            }
        }
    }

    public void write (String message)
    {
        outstream.write(message);
        outstream.flush();
    }

    public void receiveMessage(String username, String message)
    {
        write(username + "> " + message + "\n");
    }
}