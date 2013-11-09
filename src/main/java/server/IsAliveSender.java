package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TimerTask;

public class IsAliveSender extends TimerTask {

	private ServerData data;

	public IsAliveSender(ServerData data) {
		this.data = data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message = "!alive " + this.data.getTcpp();
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

	}

}
