package src.client;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
This program acts as a client connecting to the Chatroom
Is this necessar? 
Maybe replaced by Telnet
*/

public class ChatClient 
{
    public static void main(String[] args) throws IOException 
    {
        final int PORT = 1234;
        Socket socket = new Socket("localhost",PORT);
        InputStream instream = socket.getInputStream();
        OutputStream outstream = socket.getOutputStream();
        Scanner in = new Scanner(instream);
        PrintWriter out = new PrintWriter(outstream);

        while

    }
}
