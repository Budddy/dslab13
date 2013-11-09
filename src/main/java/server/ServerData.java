package server;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import cli.Shell;

public class ServerData {

	private int falive;
	private String fdir;
	private int tcpp;
	private InetAddress phost;
	private int pudpp;
	private Shell sh;
	private FileServerCli fServer;
	private Map<String, File> files;
	private ServerSocket ssock;
	private ExecutorService threads;
	private Timer time;

	public ServerData() {
	}

	public int getFalive() {
		return this.falive;
	}

	public String getFdir() {
		return this.fdir;
	}

	public Map<String, File> getFiles() {
		return this.files;
	}

	public FileServerCli getfServer() {
		return this.fServer;
	}

	public FileServerCli getFServer() {
		// TODO Auto-generated method stub
		return this.fServer;
	}

	public InetAddress getPhost() {
		return this.phost;
	}

	public int getPudpp() {
		return this.pudpp;
	}

	public Shell getSh() {
		return this.sh;
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

	public void setFalive(int falive) {
		this.falive = falive;
	}

	public void setFdir(String fdir) {
		this.fdir = fdir;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}

	public void setfServer(FileServerCli fServer) {
		this.fServer = fServer;
	}

	public void setFServer(FileServerCli fileServerCli) {
		this.fServer = fileServerCli;

	}

	public void setPhost(InetAddress phost) {
		this.phost = phost;
	}

	public void setPudpp(int pudpp) {
		this.pudpp = pudpp;
	}

	public void setSh(Shell sh) {
		this.sh = sh;
	}

	public void setSsock(ServerSocket ssock) {
		this.ssock = ssock;
	}

	public void setTcpp(int tcpp) {
		this.tcpp = tcpp;
	}

	public void setThreads(ExecutorService threads) {
		this.threads = threads;
	}

	public void setTime(Timer time) {
		this.time = time;
	}
}