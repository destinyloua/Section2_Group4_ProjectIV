package back_end;

import java.nio.ByteBuffer;
import java.text.*;
import java.util.*;

import objects.*;

public class RequestHandler implements Runnable {
	private static int object;
	private static int action;
	
	public static Boolean Processing() {
		while(SocketHandler.CheckConnection()) {
			byte[] data = SocketHandler.ReceiveData();
			ByteBuffer read = ByteBuffer.wrap(data);
			object = read.getInt();
			if(object == -1) {
				System.out.println("Client disconnect");
				SocketHandler.Disconnect();
				break;
			}
			else {
				action = read.getInt();
				if(object == 1) {
					if(action == 1) {
						byte[] accountData = new byte[read.remaining()];
						read.get(accountData);
						Account a = new Account(accountData);
						object =0;
						action = 0;
						if(DatabaseHandler.InsertNewAccount(a)) {
							byte[] response = ByteBuffer.allocate(4).putInt(1).array();
							SocketHandler.SendData(response);
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy-HH:mm:ss");
					        String timeStamp = dateFormat.format(new Date());
					        String logMessage = timeStamp + ": New account created";
					        FileHandler.WriteToFile("Log.txt", logMessage);
						}
						else {
							//Failed
							byte[] response = ByteBuffer.allocate(4).putInt(0).array();
							SocketHandler.SendData(response);
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy-HH:mm:ss");
					        String timeStamp = dateFormat.format(new Date());
					        String logMessage = timeStamp + ": New account create (Failed)";
					        FileHandler.WriteToFile("Log.txt", logMessage);
						}
					}
					
					else {
						//Auth
					}
				}
			}
		}
		return true;
	}
	
	@Override
    public void run() {
		Processing();
    }
}
