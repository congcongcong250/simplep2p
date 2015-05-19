import java.net.*;
import java.util.*;

public class pingson implements Runnable{
	
	int sonpeer,peer;
	InetAddress sonadd;
	
	public void run(){
		
		TimerTask task = new TimerTask() {   
			public void run() {   
				try{
					System.out.println("pingson"+sonpeer);
					int port = sonpeer+50000;
					String s = "ping."+ peer;
					byte[] sendData = new byte[1024];
					sendData  = s.getBytes();
					DatagramPacket ping = new DatagramPacket(sendData,sendData.length,sonadd,port);
					DatagramSocket socket = new DatagramSocket();
					socket.send(ping);
					socket.close();
				} catch (Exception e){
					System.out.println("Ping fail");
				}
			}   
			};  
		Timer timer = new Timer();
		timer.schedule(task, 2000, 5000);
	}
	
	public pingson(int peer1,int sonpeer1,InetAddress sonadd1){
		peer = peer1;
		sonpeer = sonpeer1;
		sonadd = sonadd1;
	}
}
