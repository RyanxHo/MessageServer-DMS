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
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ryan & Ribka
 */
public class MessageServer {
    
    private final int MESSAGE_PORT = 83;
    private final int JOIN_PORT = 84;
    private InetAddress hostAddress = null;
    private Map<String, String> userList =  new HashMap<String, String>();
    private ListenerThread joinThread;
    
    
    MessageServer(){
        System.out.println("Starting server ");
        joinThread = new ListenerThread(JOIN_PORT, userList);
        try {
            hostAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MessageServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   /* public void receiveMessage()
   {
        try {
            ServerSocket server = new ServerSocket(MESSAGE_PORT);
            
            Socket socket = server.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = input.readLine();
            System.out.println(request);
        } catch (IOException ex) {
            Logger.getLogger(MessageServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }*/
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MessageServer server = new MessageServer(); 
        System.out.println("Server started on address " + server.hostAddress.getHostAddress());
        Thread joinThread = new Thread(server.joinThread);
        joinThread.start();
    }
    
}
