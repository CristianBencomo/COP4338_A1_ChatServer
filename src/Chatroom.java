package src;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/*
A chat consisting of  multiple chat users
@param size the number of users
*/

public class Chatroom 
{
    private ArrayList<String> users;
    public Socket socket;
    private PrintWriter outstream; 

    public Chatroom(Socket socket) 
    {
        users = new ArrayList<>();
        this.socket = socket;
        try
        {
        outstream = new PrintWriter(socket.getOutputStream());
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void addUser(String username) 
    {
        users.add(username);
    }

    public void removeUser(String username) 
    {
        users.remove(username);
    }

    public int numberOfUsers() 
    {
        return users.size();
    }

    public ArrayList<String> getUserList() 
    {
        return users;
    }

    public void broadcast(String username, String message)
    {
            
        for(String client : users)
        {
            if(username.equals(client))
            {
                continue;
            }
            
            outstream.write(message);
            outstream.flush();
            
        }
    }
}
