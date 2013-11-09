package proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;
import java.util.TimerTask;

public class IsAliveListener implements Runnable {

	private ProxyData data;

	public IsAliveListener(ProxyData data) {
		this.data = data;
	}

	public ProxyData getData() {
		return this.data;
	}

	@Override
	public void run() {
		FileServerSave fss, temp = null;
		// TODO Auto-generated method stub
		try {
			while (!Thread.interrupted()) {
				DatagramPacket pack = new DatagramPacket(new byte[256], 256);
				this.data.getDsock().receive(pack);
				byte[] data = pack.getData();
				String a = new String(data, 0, pack.getLength());
				String port = a.trim().split("\\s+")[1];
				fss = new FileServerSave(pack.getAddress(), Integer.valueOf(port), 0, true);
				fss.setLast(new Date());
				for (FileServerSave f : this.data.getFservers()) {
					if (fss.equals(f)) {
						temp = f;
					}
				}
				if (temp != null) {
					temp.setLast(new Date());
					temp.setOnline(true);
				} else {
					fss.setLast(new Date());
					this.data.getFservers().add(fss);
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
