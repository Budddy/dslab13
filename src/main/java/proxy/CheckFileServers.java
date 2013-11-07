package proxy;

import java.util.Date;

public class CheckFileServers implements Runnable {

	private ProxyData data;

	public CheckFileServers(ProxyData data) {
		super();
		this.data = data;
	}

	public ProxyData getData() {
		return this.data;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		FileServerSave actual = new FileServerSave();
		Date from;
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(this.data.getFscheckperiod());
				from = new Date();
				from = new Date(from.getTime() - this.data.getFstimeout());
				actual.setLast(from);
				this.data.setFservers(this.data.getFservers().headMap(actual));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public void setData(ProxyData data) {
		this.data = data;
	}

}
