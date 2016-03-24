/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messageserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan & Ribka
 */
public class ListenerThread implements Runnable{
    private int port;
    private Map<String, String> userList;
    private ServerSocket server = null;
    private boolean isRunning = true;
    private Socket socket;
    private BufferedReader input;
    
    public ListenerThread(int port, Map<String, String> userList){
        System.out.println("Starting Joining Listener");
        this.port = port;
        this.userList = userList;
    }

    @Override
    public void run() {
        System.out.println("Started litening for users");
        
        
        try {
            server = new ServerSocket(port);
            socket = server.accept();
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (isRunning != false)
        {
            try {
                String request = input.readLine();
                String message = parseRequest(request);
                System.out.println(message);
                
            } catch (IOException ex) {
                try {
                    socket = server.accept();
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException ex1) {
                    Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
    
    public String parseRequest(String request){
        String[] requestParts = request.split("::");
        if(requestParts[0].equals(MesssageType.JOIN)){
            userList.put(requestParts[1], requestParts[2]);
            return requestParts[1] + " added";
        } else if(requestParts[0].equals(MesssageType.BROADCAST)) {
            return "From " + requestParts[1] + ": [to all]" + requestParts[2];
        } else {
            return "Unknown Message Type";
        }
    }
    
    public void stop(){
        isRunning = false;
    }
    
}
