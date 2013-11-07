package proxy;

import model.UserInfo;

public class UserSave implements Comparable<UserSave> {

	private String name;
	private long credits;
	private boolean online;
	private String password;

	public UserSave(String name, long credits, boolean online, String password) {
		super();
		this.name = name;
		this.credits = credits;
		this.online = online;
		this.password = password;
	}

	@Override
	public int compareTo(UserSave arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		UserSave other = (UserSave) obj;
		if (this.name == null) {
			if (other.name != null) { return false; }
		} else if (!this.name.equals(other.name)) { return false; }
		return true;
	}

	public long getCredits() {
		return this.credits;
	}

	public UserInfo getInfo() {
		return new UserInfo(this.name, this.credits, this.online);
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ( (this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public boolean isOnline() {
		return this.online;
	}

	public void setCredits(long credits) {
		this.credits = credits;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [name=" + this.name + ", credits=" + this.credits + ", online=" + this.online
				+ ", password=" + this.password + "]";
	}

}
