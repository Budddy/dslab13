package proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientListenerProxy implements Runnable {

	private ServerSocket ssock;
	private List<Socket> slist;
	private ProxyData data;

	public ClientListenerProxy(ProxyData data) {
		super();
		this.ssock = data.getSsock();
		this.slist = new ArrayList<Socket>();
		this.data = data;
	}

	public ServerSocket getSsock() {
		return this.ssock;
	}

	@Override
	public void run() {
		Socket s;
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				s = this.data.getSsock().accept();
				this.slist.add(s);
				this.data.getThreads().execute(new ServeClientProxy(s, this.data));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void setSsock(ServerSocket ssock) {
		this.ssock = ssock;
	}

}
