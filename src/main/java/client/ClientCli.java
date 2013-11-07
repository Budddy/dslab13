package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Response;
import message.request.BuyRequest;
import message.request.CreditsRequest;
import message.request.DownloadFileRequest;
import message.request.DownloadTicketRequest;
import message.request.ListRequest;
import message.request.LoginRequest;
import message.request.LogoutRequest;
import message.request.UploadRequest;
import message.response.DownloadFileResponse;
import message.response.DownloadTicketResponse;
import message.response.LoginResponse;
import message.response.LoginResponse.Type;
import message.response.MessageResponse;
import model.UserInfo;
import util.ChecksumUtils;
import util.Config;
import cli.Command;
import cli.Shell;
import cli.TestInputStream;
import cli.TestOutputStream;

public class ClientCli implements IClientCli {

	public static void main(String[] args) throws IOException {
		Shell sh = new Shell("proxy", System.out, System.in);
		new ClientCli(new Config("client"), sh);
		sh.run();
	}

	private ClientData data;

	public ClientCli(Config conf, Shell shell) throws IOException {
		this.data = new ClientData();

		this.data.setDdir(conf.getString("download.dir"));
		this.data.setPhost(conf.getString("proxy.host"));
		this.data.setTcpp(conf.getInt("proxy.tcp.port"));

		Socket s = new Socket(this.data.getPhost(), this.data.getTcpp());
		this.data.setOis(new ObjectInputStream(s.getInputStream()));
		this.data.setOos(new ObjectOutputStream(s.getOutputStream()));
		this.data.setS(s);

		this.data.setClient(this);
		shell.register(this.data.getClient());

	}

	@Override
	@Command
	public Response buy(long credits) throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.getOos().writeObject(new BuyRequest(credits));
			return (Response) this.data.getOis().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Command
	public Response credits() throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.getOos().writeObject(new CreditsRequest());
			return (Response) this.data.getOis().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Command
	public Response download(String filename) throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.getOos().writeObject(new DownloadTicketRequest(filename));
			DownloadTicketResponse dtr = (DownloadTicketResponse) this.data.getOis().readObject();
			Socket down = new Socket(dtr.getTicket().getAddress(), dtr.getTicket().getPort());
			ObjectOutputStream oos = new ObjectOutputStream(down.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(down.getInputStream());
			oos.writeObject(new DownloadFileRequest(dtr.getTicket()));
			DownloadFileResponse dfr = (DownloadFileResponse) ois.readObject();
			ois.close();
			oos.close();
			down.close();
			return dfr;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Command
	public MessageResponse exit() throws IOException {
		if (this.data.isLog()) logout();
		this.data.getS().close();
		return new MessageResponse("Exit");
	}

	@Override
	@Command
	public Response list() throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.getOos().writeObject(new ListRequest());
			return (Response) this.data.getOis().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Command
	public LoginResponse login(String username, String password) throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.getOos().writeObject(new LoginRequest(username, password));
			LoginResponse lr = (LoginResponse) this.data.getOis().readObject();
			this.data.setLog(lr.getType() == Type.SUCCESS);
			this.data.setUser(new UserInfo(username,0,lr.getType() == Type.SUCCESS));
			return lr;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Command
	public MessageResponse logout() throws IOException {
		// TODO Auto-generated method stub
		try {
			this.data.setLog(false);
			this.data.getOos().writeObject(new LogoutRequest());
			return (MessageResponse) this.data.getOis().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// TODO
			return null;
		}
	}

	@Override
	@Command
	public MessageResponse upload(String filename) throws IOException {
		File f = new File(this.data.getDdir() + "/" + filename);
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		fis.close();
		this.data.getOos().writeObject(new UploadRequest(filename, 1, b));
		try {
			return (MessageResponse) this.data.getOis().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
