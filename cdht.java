
import java.net.*;
import java.util.*;

public class cdht {
	
	static int peer,port,sonpeer,grandsonpeer,fatherpeer,grandfatherpeer;
	
	public static void main(String[] args)throws Exception {
		if(args.length != 3){
			throw new Exception("Incorrect Arguments");
		}
		//Set the goal ip address
		String Name = "localhost";
		InetAddress IPAddress = InetAddress.getByName(Name);
		
		//Set the server port and client port 
		peer = Integer.parseInt(args[0]);
		sonpeer = Integer.parseInt(args[1]);
		grandsonpeer = Integer.parseInt(args[2]);
		
		pingson son = new pingson(peer,sonpeer, IPAddress);
		Thread Tson = new Thread(son);
		
		pinggrandson grandson = new pinggrandson(peer,grandsonpeer, IPAddress);
		Thread Tgrandson = new Thread(grandson);
		
		Tson.start();
		Tgrandson.start();
		
		//Server will return fatherpeer and grandfatherpeer
		pingserver udp = new pingserver(peer);
		Thread Tudp = new Thread(udp);
		Tudp.start();
		
		Scanner s = new Scanner(System.in);
		String operation = new String();
		while(s.hasNext()){
			operation = s.nextLine();
			if(operation.equals("quit")){
				fatherpeer = udp.p1;
				grandfatherpeer = udp.p2;
				
				//Stop the thread
				(new Thread(new tcpsend(peer,fatherpeer,grandfatherpeer, sonpeer,grandsonpeer,IPAddress))).start();
				
			}else{
				String[] op = operation.split(" ");
				(new Thread(new tcpsend(peer,op[1],IPAddress))).start();
			}
		}
		s.close();
		
	}
	
}

