/**
 * The remote interface
 */

import java.util.Date;
import java.rmi.*;

public interface RemoteFunc extends Remote {
	public abstract Date getDate() throws RemoteException;

	public abstract String sendMsg(String exp[]) throws RemoteException;
}
