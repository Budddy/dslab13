package proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;
import java.util.TimerTask;

public class IsAliveListener extends TimerTask {

	private ProxyData data;

	public IsAliveListener(ProxyData data) {
		super();
		this.data = data;
	}

	public ProxyData getData() {
		return this.data;
	}

	@Override
	public void run() {
		DatagramPacket pack = new DatagramPacket(new byte[10], 10);
		FileServerSave fss, temp;
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				// System.out.println("test");
				this.data.getDsock().receive(pack);
				// System.out.println(pack);
				fss = new FileServerSave(pack.getAddress(), pack.getPort(), 0, true);
				fss.setLast(new Date());
				temp = this.data.getFservers().get(fss);
				if (temp != null) {
					temp.setLast(new Date());
					temp.setOnline(true);
				} else {
					fss.setLast(new Date());
					this.data.getFservers().put(fss, fss);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void setData(ProxyData data) {
		this.data = data;
	}

}
