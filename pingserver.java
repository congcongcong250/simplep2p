import java.io.*;
import java.net.*;



public class pingserver implements Runnable{
	
	int peer,p1,p2,f=1;
	
	public void run(){
		try{
			int port = peer+50000;
			DatagramSocket server = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			String send = new String();
			while(true){
				
				//Get receive packet
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				server.receive(receivePacket);
				
				/*To get the string correct 
				 * It is a tricky one since UTF-8 encoding and some kind of buffer thing
				 */
				String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength(), "UTF-8");
				
				//Split the msg into two parts one is operation and another one is senders peer
				String[] msg = sentence.split("\\.");
				System.out.println(sentence); 
				//System.out.println("UDPs server established at port:" + port +" Get: "+sentence + " "+"\""+msg[0]+"+"+msg[1]+"\"");
				
				//if it is a ping msg send back ask
				if(msg[0].equals("ping")){
					int p = Integer.parseInt(msg[1]);
					
					//Save peer number
					if(f == 1){
						if(p != p1 && p != p2){
							p1 = p;
						}
					}else{
						if(p != p1 && p != p2){
							p2 = p;
						}
					}
					f = f*(-1);
					
					//Receive info
					System.out.println("PING A ping request message was received from Peer " + p +".");
					//Send back ACK
					InetAddress IPAddress = receivePacket.getAddress();
					int report = 50000+p;
					send = "ack."+peer;
					sendData = send.getBytes();
					//System.out.println("GET AND SEND "+report+" "+send);
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, report);
					DatagramSocket s = new DatagramSocket();
					s.send(sendPacket);
					s.close();
				}else if(msg[0].equals("ack")){
					int p  = Integer.parseInt(msg[1]);
					System.out.println("ACK A ping response message was received from Peer " + p +".");
				}
			}
		} catch (IOException eio){
			System.out.println("IO"+eio.getMessage());
		} 
	}
	
	public pingserver(int peer1){
		peer = peer1;
	}
	
}
