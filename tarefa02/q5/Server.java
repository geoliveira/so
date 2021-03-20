/**
 * Implementation of the RemoteFunc interface
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server extends UnicastRemoteObject implements RemoteFunc {
    public String mailbox[][] = new String[11][11];

    public Server() throws RemoteException {  }
    
    public Date getDate() throws RemoteException {
        return new Date();
    }

    public String sendMsg(String exp[]) throws RemoteException {
        String ack = new String();
        String parts[] = exp[0].split(":");
        Integer fromID, toID;

        if (parts[0].equals("0")) {
            toID = Integer.parseInt(parts[1]);

            for (int i = 0; i < mailbox.length; i++)
                if (mailbox[i][toID] != null)
                    ack = ack.concat("From "+i+": "+mailbox[i][toID]);

        } else if (parts[0].equals("1")) {
            fromID = Integer.parseInt(parts[1]);
            toID = Integer.parseInt(parts[2]);

            mailbox[fromID][toID] = parts[3].concat("\n");
            ack = "Registered email from "+fromID+" to "+toID;
        }
        
        return ack;
    }

    public static void main(String[] args)  {
        try {
            /** 
             * we only need to install this to distribute classes
             * 	System.setSecurityManager(new RMISecurityManager());
             */
            RemoteFunc dateServer = new Server();

            // Bind this object instance to the name "DateServer"
            Naming.rebind("DateServer", dateServer);
            //Naming.rebind("//localhost:1099/DateServer", dateServer);

            System.out.println("DateServer bound in registry");
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}                                                                                                                                                                                                                                                                                                                               