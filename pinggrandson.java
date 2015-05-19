import java.net.*;
import java.util.*;

public class pinggrandson implements Runnable{
	
	int grandsonpeer,peer;
	InetAddress grandsonadd;
	
	public void run(){
		
		TimerTask task = new TimerTask() {   
			public void run() {   
				try{
					System.out.println("pinggrandson"+grandsonpeer);
					int port = grandsonpeer+50000;
					String s = "ping."+ peer;
					byte[] sendData = new byte[1024];
					sendData  = s.getBytes();
					DatagramPacket ping = new DatagramPacket(sendData,sendData.length,grandsonadd,port);
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
	
	public pinggrandson(int p,int gsp,InetAddress gsa){
		peer = p;
		grandsonpeer = gsp;
		grandsonadd = gsa;
	}
}
