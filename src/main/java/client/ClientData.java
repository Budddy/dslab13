package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import cli.Shell;
import model.UserInfo;

public class ClientData {

	private String ddir;
	private String phost;
	private int tcpp;
	private ObjectInputStream ois;
	private Socket s;
	private ClientCli client;
	private boolean log = false;
	private UserInfo user;
	private Shell sh;

	
	public Shell getSh() {
		return sh;
	}

	
	public void setSh(Shell sh) {
		this.sh = sh;
	}

	private ObjectOutputStream oos;

	public ClientCli getClient() {
		return this.client;
	}

	public String getDdir() {
		return this.ddir;
	}

	public ObjectInputStream getOis() {
		return this.ois;
	}

	public ObjectOutputStream getOos() {
		return this.oos;
	}

	public String getPhost() {
		return this.phost;
	}

	public Socket getS() {
		return this.s;
	}

	public int getTcpp() {
		return this.tcpp;
	}

	public UserInfo getUser() {
		return this.user;
	}

	public boolean isLog() {
		return this.log;
	}

	public void setClient(ClientCli client) {
		this.client = client;
	}

	public void setDdir(String ddir) {
		this.ddir = ddir;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public void setPhost(String phost) {
		this.phost = phost;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public void setTcpp(int tcpp) {
		this.tcpp = tcpp;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
