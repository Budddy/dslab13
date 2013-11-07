package proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import message.Response;
import message.request.BuyRequest;
import message.request.CreditsRequest;
import message.request.DownloadTicketRequest;
import message.request.ListRequest;
import message.request.LoginRequest;
import message.request.LogoutRequest;
import message.request.UploadRequest;
import message.response.LoginResponse;
import message.response.MessageResponse;

public class ServeClientProxy implements Runnable {

	private Socket s;
	private ProxyData data;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private UserSave user = null;
	private Proxy p;

	public ServeClientProxy(Socket s, ProxyData data) {
		super();
		this.s = s;
		this.data = data;
		// System.out.println("client active1");
		try {
			// System.out.println("client 0");
			this.output = new ObjectOutputStream(s.getOutputStream());
			// System.out.println("client 1");
			this.input = new ObjectInputStream(s.getInputStream());
			// System.out.println("client 2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println("error");
			e.printStackTrace();
		}
		// System.out.println("client active2");
		this.p = new Proxy(data, this.user);
		// System.out.println("client active3");
	}

	public Socket getS() {
		return this.s;
	}

	@Override
	public void run() {
		// System.out.println("client run");
		try {
			while (this.s.isConnected()) {
				// System.out.println("client loop");

				Object request = this.input.readObject();
				Response response;
				// System.out.println(request);
				if (request instanceof BuyRequest) {
					if (this.user != null) {
						response = this.p.buy((BuyRequest) request);
					} else {
						response = new MessageResponse("Unknown command!");
					}

				} else if (request instanceof CreditsRequest) {
					if (this.user != null) {
						response = this.p.credits();
					} else {
						response = new MessageResponse("Unknown command!");
					}

				} else if (request instanceof DownloadTicketRequest) {
					if (this.user != null) {
						response = this.p.download((DownloadTicketRequest) request);
					} else {
						response = new MessageResponse("Unknown command!");
					}

				} else if (request instanceof ListRequest) {
					if (this.user != null) {
						response = this.p.list();
					} else {
						response = new MessageResponse("Unknown command!");
					}

				} else if (request instanceof LoginRequest) {
					LoginResponse l;
					l = this.p.login((LoginRequest) request);
					if (l.getType() == LoginResponse.Type.SUCCESS) {
						Map<String, UserSave> users = this.data.getUsers();
						UserSave u = users.get( ((LoginRequest) request).getUsername());
						this.p.setUs(u);
						this.user = u;
					}
					response = l;

				} else if (request instanceof LogoutRequest) {
					if (this.user != null) {
						response = this.p.logout();
						this.user.setOnline(false);
						this.s.close();
					} else {
						response = new MessageResponse("Unknown command!");
					}

				} else if (request instanceof UploadRequest) {
					if (this.user != null) {
						response = this.p.upload((UploadRequest) request);
					} else {
						response = new MessageResponse("Unknown command!");
					}
				} else {
					response = new MessageResponse("Unknown command!");
				}
				this.output.writeObject(response);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setS(Socket s) {
		this.s = s;
	}

}
