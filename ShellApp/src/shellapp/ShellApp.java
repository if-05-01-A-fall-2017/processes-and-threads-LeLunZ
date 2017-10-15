/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shellapp;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */
public class ShellApp {

    /**
     * @param args the command line arguments
     */
    private static Thread t;
    public static void main(String[] args) {
        Scanner scan;
        String input;
        while(true){
            System.out.print("Text:");
            scan = new Scanner(System.in);
            input = scan.nextLine();
            String[] commands = input.split("&&");
            String output = "";
            for (String command : commands) {
                //output += UseCmd(Cmd[i]) ;
                if (command.contains("exit")) {
                    return;
                }
                CommandLine(command);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ShellApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private static void CommandLine(String arg){
        Runnable r = new RunnableProcess(arg);
        t = new Thread(r);
        t.start();
        
    }
}

class RunnableProcess implements Runnable{
    private final String commandLine;
    public RunnableProcess(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public void run() {
        StringBuilder output = new StringBuilder();
        try {
            Process p =  Runtime.getRuntime().exec(this.commandLine);
            p.waitFor();
            InputStream s = p.getInputStream();
            int b = s.read();
            while (b != -1) {
                System.out.print((char)b);
                b = s.read();
            }
        } catch (Exception e) {
        }
       
    }
    
}
