package src;

import java.util.ArrayList;

/*
 * A chatroom consisting of multiple users and their respective service
*/

public class Chatroom 
{
    private ArrayList<String> users;
    private ArrayList<ChatService> chatservices;

    public Chatroom() 
    {
        users = new ArrayList<>();
        chatservices = new ArrayList<>();
    }

    public void addUser(ChatService chatservice, String username) 
    {
        users.add(username);
        chatservices.add(chatservice);
    }

    public void removeUser(ChatService chatservice, String username) 
    {
        users.remove(username);
        chatservices.remove(chatservice);
    }

    public int numberOfUsers() 
    {
        return users.size();
    }

    public ArrayList<String> getUserList() 
    {
        return users;
    }

    public ChatService getService(String username)
    {
        int index;
        index = users.indexOf(username);

        return chatservices.get(index);
    }
}
