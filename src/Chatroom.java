package src;

import java.net.Socket;
import java.util.ArrayList;

/*
A chat consisting of  multiple chat users
@param size the number of users
*/ 

public class Chatroom
{
    private ArrayList<String> users;
    private String message;

    public Chatroom()
    {
        users = new ArrayList<>(); 
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

    public ArrayList getUserList()
    {
        return users;
    }

    public void write(String message, Socket socket)
    {
        
    }
    
}
