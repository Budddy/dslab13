package proxy;

import java.net.InetAddress;
import java.util.Date;
import model.FileServerInfo;

public class FileServerSave implements Comparable<FileServerSave> {

	private InetAddress address;
	private int port;
	private long usage;
	private boolean online;
	private Date last;

	public FileServerSave() {
		// TODO Auto-generated constructor stub
	}

	public FileServerSave(InetAddress address, int port, long usage, boolean online) {
		super();
		this.address = address;
		this.port = port;
		this.usage = usage;
		this.online = online;
	}

	@Override
	public int compareTo(FileServerSave arg0) {
		FileServerSave other = arg0;
		return this.last.compareTo(other.last);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		FileServerSave other = (FileServerSave) obj;
		if (this.address == null) {
			if (other.address != null) { return false; }
		} else if (!this.address.equals(other.address)) { return false; }
		if (this.port != other.port) { return false; }
		return true;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	public FileServerInfo getInfo() {
		return new FileServerInfo(this.address, this.port, this.usage, this.online);
	}

	public Date getLast() {
		return this.last;
	}

	public int getPort() {
		return this.port;
	}

	public long getUsage() {
		return this.usage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ( (this.address == null) ? 0 : this.address.hashCode());
		result = (prime * result) + this.port;
		return result;
	}

	public boolean isOnline() {
		return this.online;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public void setLast(Date last) {
		this.last = last;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsage(long usage) {
		this.usage = usage;
	}

	@Override
	public String toString() {
		return "FileServer [address=" + this.address + ", port=" + this.port + ", usage=" + this.usage
				+ ", online=" + this.online + "]";
	}

}
