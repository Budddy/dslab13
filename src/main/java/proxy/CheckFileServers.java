package proxy;

import java.util.Date;
import java.util.TimerTask;

public class CheckFileServers extends TimerTask {

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
		Date from;
		from = new Date();
		from = new Date(from.getTime() - this.data.getFstimeout());
		for (FileServerSave fss : this.data.getFservers()) {
			fss.setOnline( (fss.getLast().getTime() - from.getTime()) > 0);
		}

	}

	public void setData(ProxyData data) {
		this.data = data;
	}

}
