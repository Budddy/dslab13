package server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.Executors;
import message.response.MessageResponse;
import util.Config;
import cli.Command;
import cli.Shell;
import cli.TestInputStream;
import cli.TestOutputStream;

public class FileServerCli implements IFileServerCli {

	public static void main(String[] args) throws IOException {
		Shell sh = new Shell("server", new TestOutputStream(System.out),System.in);
		sh.register(new FileServerCli(new Config("fs1"), sh));
		new Thread(sh).start();

	}

	private ServerData data = new ServerData();

	public FileServerCli(Config conf, Shell sh) throws IOException {
		this.data.setFalive(conf.getInt("fileserver.alive"));
		this.data.setFdir(conf.getString("fileserver.dir"));
		this.data.setTcpp(conf.getInt("tcp.port"));
		this.data.setPhost(InetAddress.getByName(conf.getString("proxy.host")));
		this.data.setPudpp(conf.getInt("proxy.udp.port"));
		this.data.setFiles(new HashMap<String, File>());

		this.data.setThreads(Executors.newCachedThreadPool());
		this.data.setSh(sh);
		this.data.setFServer(this);
		this.data.setSsock(new ServerSocket(this.data.getTcpp()));
		Timer tim = new Timer();
		this.data.setTime(tim);
		this.data.getTime().scheduleAtFixedRate(new IsAliveSender(this.data), 0, this.data.getFalive());
		this.data.getThreads().execute(new ClientListenerServer(this.data));
		File files = new File(this.data.getFdir());
		for (File f : files.listFiles()) {
			this.data.getFiles().put(f.getName(), f);
		}
	}

	@Override
	@Command
	public MessageResponse exit() throws IOException {
		this.data.getThreads().shutdownNow();
		//this.data.getSh().close();
		this.data.getTime().cancel();
		this.data.getSsock().close();
		//System.in.close();
		// TODO Auto-generated method stub
		return null;
	}

}
