package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class IsAliveSender implements Runnable {

	ServerData data;

	public IsAliveSender(ServerData data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(this.data.getFalive());
				String message = "!alive " + data.getTcpp();
				try {
					DatagramSocket socket = new DatagramSocket();
					byte[] buf = message.getBytes();
					DatagramPacket packet = new DatagramPacket(buf, buf.length, this.data.getPhost(),
							this.data.getPudpp());
					socket.send(packet);
					socket.close();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(message);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
