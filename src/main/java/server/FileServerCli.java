package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import message.response.MessageResponse;
import util.Config;
import cli.Command;
import cli.Shell;
import cli.TestInputStream;
import cli.TestOutputStream;

public class FileServerCli implements IFileServerCli {

	public static void main(String[] args) throws IOException {
		Shell sh = new Shell("proxy", new TestOutputStream(), new TestInputStream());
		new FileServerCli(new Config("fs1"), sh);
		sh.run();
	}

	private ServerData data = new ServerData();

	public FileServerCli(Config conf, Shell sh) throws IOException {
		this.data.setFalive(conf.getInt("fileserver.alive"));
		// System.out.println(this.data.getFalive());

		this.data.setFdir(conf.getString("fileserver.dir"));
		// System.out.println(this.data.getFdir());

		this.data.setTcpp(conf.getInt("tcp.port"));
		// System.out.println(this.data.getTcpp());

		this.data.setPhost(InetAddress.getByName(conf.getString("proxy.host")));
		// System.out.println(this.data.getPhost());

		this.data.setPudpp(conf.getInt("proxy.udp.port"));
		// System.out.println(this.data.getPudpp());

		this.data.setThreads(Executors.newCachedThreadPool());
		this.data.setSh(sh);
		this.data.setFServer(this);
		sh.register(this.data.getFServer());

		new Thread(new IsAliveSender(this.data)).start();
	}

	@Override
	@Command
	public MessageResponse exit() throws IOException {
		this.data.getThreads().shutdownNow();
		this.data.getSh().close();
		// TODO Auto-generated method stub
		return null;
	}

}
