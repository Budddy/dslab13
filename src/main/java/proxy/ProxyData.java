package proxy;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import cli.Shell;

public class ProxyData {

	private int tcpp;
	private int udpp;
	private int fstimeout;
	private int fscheckperiod;
	private Map<String,UserSave> users;
	private SortedMap<FileServerSave, FileServerSave> fservers;
	private List<Socket> slist;
	private ServerSocket ssock;
	private DatagramSocket dsock;
	private IsAliveListener alive;
	private ClientListenerProxy listen;
	private ProxyCli proxy;
	private Shell shell;
	private List<Thread> threads = new ArrayList<Thread>();
	private Set<String> filenames;

	
	public Set<String> getFilenames() {
		return filenames;
	}

	
	public void setFilenames(Set<String> filenames) {
		this.filenames = filenames;
	}

	
	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public ProxyData() {
	}

	public void addTread(Thread t) {
		this.threads.add(t);
	}

	public IsAliveListener getAlive() {
		return this.alive;
	}

	public DatagramSocket getDsock() {
		return this.dsock;
	}

	public int getFscheckperiod() {
		return this.fscheckperiod;
	}

	public SortedMap<FileServerSave, FileServerSave> getFservers() {
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
		for (FileServerSave fs : this.fservers.keySet()) {
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

	public List<Thread> getThreads() {
		return this.threads;
	}

	public int getUdpp() {
		return this.udpp;
	}

	public Map<String,UserSave> getUsers() {
		return this.users;
	}

	public synchronized void setAlive(IsAliveListener alive) {
		this.alive = alive;
	}

	public synchronized void setDsock(DatagramSocket dsock) {
		this.dsock = dsock;
	}

	public synchronized void setFscheckperiod(int fscheckperiod) {
		this.fscheckperiod = fscheckperiod;
	}

	public synchronized void setFservers(SortedMap<FileServerSave, FileServerSave> fservers) {
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

	public synchronized void setUdpp(int udpp) {
		this.udpp = udpp;
	}

	public synchronized void setUsers(Map<String,UserSave> users) {
		this.users = users;
	}
}