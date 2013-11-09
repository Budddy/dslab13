package proxy;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import cli.Shell;

public class ProxyData {

	private int tcpp;
	private int udpp;
	private int fstimeout;
	private int fscheckperiod;
	private Map<String, UserSave> users;
	private Set<FileServerSave> fservers =new HashSet<FileServerSave>();
	private List<Socket> slist = new ArrayList<Socket>();
	private ServerSocket ssock;
	private DatagramSocket dsock;
	private IsAliveListener alive;
	private ClientListenerProxy listen;
	private ProxyCli proxy;
	private Shell shell;
	private ExecutorService threads;
	private Set<String> filenames=new HashSet<String>();
	private Timer time;

	public ProxyData() {
	}

	public IsAliveListener getAlive() {
		return this.alive;
	}

	public DatagramSocket getDsock() {
		return this.dsock;
	}

	public Set<String> getFilenames() {
		return this.filenames;
	}

	public int getFscheckperiod() {
		return this.fscheckperiod;
	}

	public Set<FileServerSave> getFservers() {
		return this.fservers;
	}

	public int getFstimeout() {
		return this.fstimeout;
	}

	public ClientListenerProxy getListen() {
		return this.listen;
	}

	public FileServerSave getMinFileServer() {
		FileServerSave min = null;
		for (FileServerSave fs : this.fservers) {
			if ( (min == null) && fs.isOnline()) {
				min = fs;
			} else if ( (min.getUsage() > fs.getUsage()) && fs.isOnline()) {
				min = fs;
			}

		}
		return min;
	}

	public ProxyCli getProxy() {
		return this.proxy;
	}

	public Shell getShell() {
		return this.shell;
	}

	public List<Socket> getSlist() {
		return this.slist;
	}

	public ServerSocket getSsock() {
		return this.ssock;
	}

	public int getTcpp() {
		return this.tcpp;
	}

	public ExecutorService getThreads() {
		return this.threads;
	}

	public Timer getTime() {
		return this.time;
	}

	public int getUdpp() {
		return this.udpp;
	}

	public Map<String, UserSave> getUsers() {
		return this.users;
	}

	public synchronized void setAlive(IsAliveListener alive) {
		this.alive = alive;
	}

	public synchronized void setDsock(DatagramSocket dsock) {
		this.dsock = dsock;
	}

	public void setFilenames(Set<String> filenames) {
		this.filenames = filenames;
	}

	public synchronized void setFscheckperiod(int fscheckperiod) {
		this.fscheckperiod = fscheckperiod;
	}

	public synchronized void setFservers(Set<FileServerSave> fservers) {
		this.fservers = fservers;
	}

	public synchronized void setFstimeout(int fstimeout) {
		this.fstimeout = fstimeout;
	}

	public synchronized void setListen(ClientListenerProxy listen) {
		this.listen = listen;
	}

	public synchronized void setProxy(ProxyCli proxy) {
		this.proxy = proxy;
	}

	public synchronized void setShell(Shell shell) {
		this.shell = shell;
	}

	public synchronized void setSlist(List<Socket> slist) {
		this.slist = slist;
	}

	public synchronized void setSsock(ServerSocket ssock) {
		this.ssock = ssock;
	}

	public synchronized void setTcpp(int tcpp) {
		this.tcpp = tcpp;
	}

	public void setThreads(ExecutorService threads) {
		this.threads = threads;
	}

	public void setTime(Timer time) {
		this.time = time;
	}

	public synchronized void setUdpp(int udpp) {
		this.udpp = udpp;
	}

	public synchronized void setUsers(Map<String, UserSave> users) {
		this.users = users;
	}
}