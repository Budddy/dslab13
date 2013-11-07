package proxy;

import static util.TestUtils.readLines;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import message.Response;
import message.response.FileServerInfoResponse;
import message.response.MessageResponse;
import message.response.UserInfoResponse;
import model.FileServerInfo;
import model.UserInfo;
import util.Config;
import cli.Command;
import cli.Shell;
import cli.TestOutputStream;

public class ProxyCli implements IProxyCli {

	public static void main(String[] args) throws IOException {
		Shell sh = new Shell("proxy", new TestOutputStream(System.out), System.in);
		new ProxyCli(new Config("proxy"), sh);
	}

	private ProxyData data = new ProxyData();

	public ProxyCli(Config conf, Shell sh) throws IOException {
		this.data.setUsers(new HashMap<String, UserSave>());
		this.data.setFservers(new TreeMap<FileServerSave, FileServerSave>());
		getUsers();

		this.data.setTcpp(conf.getInt("tcp.port"));

		// System.out.println(this.data.getTcpp());

		this.data.setUdpp(conf.getInt("udp.port"));
		// System.out.println(this.data.getUdpp());

		this.data.setFstimeout(conf.getInt("fileserver.timeout"));
		// System.out.println(this.data.getFstimeout());

		this.data.setFscheckperiod(conf.getInt("fileserver.checkPeriod"));
		// System.out.println(this.data.getFscheckperiod());

		this.data.setSsock(new ServerSocket(this.data.getTcpp()));
		this.data.setDsock(new DatagramSocket(this.data.getUdpp()));

		this.data.setAlive(new IsAliveListener(this.data));
		this.data.setListen(new ClientListenerProxy(this.data));

		this.data.setThreads(Executors.newCachedThreadPool());
		this.data.getThreads().execute(this.data.getListen());
		Timer tim = new Timer();
		this.data.setTime(tim);
		this.data.getTime().scheduleAtFixedRate(this.data.getAlive(), 0, this.data.getFscheckperiod());

		this.data.getThreads().execute(new Thread(this.data.getListen()));

		this.data.setShell(sh);
		this.data.setProxy(this);
		sh.register(this.data.getProxy());
		new Thread(sh).start();
	}

	@Override
	@Command
	public MessageResponse exit() throws IOException {
		// TODO Auto-generated method stub
		this.data.getThreads().shutdownNow();
		this.data.getTime().cancel();
		this.data.getDsock().close();
		this.data.getSsock().close();
		this.data.getShell().close();
		return new MessageResponse("That's it folks!");
	}

	@Override
	@Command
	public Response fileservers() throws IOException {
		List<FileServerInfo> lfsi = new ArrayList<FileServerInfo>();
		for (FileServerSave fss : this.data.getFservers().values()) {
			lfsi.add(fss.getInfo());
		}
		return new FileServerInfoResponse(lfsi);
	}

	private void getUsers() throws IOException {
		/***************************************************************/
		/** TODO find way to use actual file **/
		/***************************************************************/

		ArrayList<String> names = new ArrayList<String>();
		Config userconf = new Config("user");

		File file = new File("src/main/resources/user.properties");

		String fileName = file.getName();

		URL url = getClass().getClassLoader().getResource(fileName);
		if (url == null) { throw new IllegalArgumentException(String.format("Resource %s not found.",
				fileName)); }

		List<String> lines = readLines(url.openStream(), Charset.defaultCharset());
		for (String line : lines) {
			if (line.contains("credits")) {
				// System.out.println(line.split("\\p{Punct}")[0]);
				names.add(line.split("\\p{Punct}")[0]);
			}
		}
		for (String name : names) {
			UserSave u = new UserSave(name, userconf.getInt(name + ".credits"), false,
					userconf.getString(name + ".password"));
			// System.out.println(u);
			this.data.getUsers().put(u.getName(), u);
		}

		/***************************************************************/

	}

	@Override
	@Command
	public Response users() throws IOException {
		List<UserInfo> lui = new ArrayList<UserInfo>();

		for (UserSave u : this.data.getUsers().values()) {
			lui.add(u.getInfo());
			// System.out.println("test");
		}
		return new UserInfoResponse(lui);
	}

}
