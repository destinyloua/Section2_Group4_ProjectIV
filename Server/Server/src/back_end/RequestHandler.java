package back_end;

import java.nio.ByteBuffer;
import java.text.*;
import java.util.*;

import objects.*;

public class RequestHandler implements Runnable {
	private static int object;
	private static int flag;
	
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
				flag = read.getInt();
				if(object == 1) {
					if(flag == 1) {
						byte[] accountData = new byte[read.remaining()];
						read.get(accountData);
						Account a = new Account(accountData);
						object =0;
						flag = 0;
						if(DatabaseHandler.InsertNewAccount(a)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy-HH:mm:ss");
					        String timeStamp = dateFormat.format(new Date());
					        String logMessage = timeStamp + ": New account created";
					        FileHandler.WriteToFile("Log.txt", logMessage);

							byte[] response = ByteBuffer.allocate(4).putInt(1).array();
							SocketHandler.SendData(response);
						}
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
