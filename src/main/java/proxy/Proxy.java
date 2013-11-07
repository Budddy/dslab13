package proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Response;
import message.request.BuyRequest;
import message.request.DownloadTicketRequest;
import message.request.LoginRequest;
import message.request.UploadRequest;
import message.response.BuyResponse;
import message.response.CreditsResponse;
import message.response.ListResponse;
import message.response.LoginResponse;
import message.response.LoginResponse.Type;
import message.response.MessageResponse;
import model.DownloadTicket;

public class Proxy implements IProxy {

	UserSave us;
	ProxyData data;

	public Proxy(ProxyData data, UserSave us) {
		this.us = us;
		this.data = data;
	}

	@Override
	public Response buy(BuyRequest credits) throws IOException {
		// TODO Auto-generated method stub
		this.us.setCredits(this.us.getCredits() + credits.getCredits());
		return new BuyResponse(this.us.getCredits());
	}

	@Override
	public Response credits() throws IOException {
		// TODO Auto-generated method stub
		return new CreditsResponse(this.us.getCredits());
	}

	@Override
	public Response download(DownloadTicketRequest request) throws IOException {
		DownloadTicket ticket = new DownloadTicket();

		FileServerSave fs = this.data.getMinFileServer();
		ticket.setFilename(request.getFilename());
		ticket.setAddress(fs.getAddress());
		ticket.setPort(fs.getPort());
		ticket.setUsername(this.us.getName());
		return null;
	}

	public ProxyData getData() {
		return this.data;
	}

	public UserSave getUs() {
		return this.us;
	}

	@Override
	public Response list() throws IOException {
		return new ListResponse(this.data.getFilenames());
	}

	@Override
	public LoginResponse login(LoginRequest request) throws IOException {
		UserSave user = this.data.getUsers().get(request.getUsername());
		LoginResponse r;
		if (user != null) {
			if (user.getPassword().equals(request.getPassword())) {
				user.setOnline(true);
				r = new LoginResponse(Type.SUCCESS);

			} else {
				r = new LoginResponse(Type.WRONG_CREDENTIALS);
			}
		} else {
			r = new LoginResponse(Type.WRONG_CREDENTIALS);
		}
		// TODO Auto-generated method stub
		return r;
	}

	@Override
	public MessageResponse logout() throws IOException {
		// TODO Auto-generated method stub
		this.us.setOnline(false);
		return new MessageResponse("logout successfull");
	}

	public void setData(ProxyData data) {
		this.data = data;
	}

	public void setUs(UserSave us) {
		this.us = us;
	}

	@Override
	public MessageResponse upload(UploadRequest request) throws IOException {
		// TODO Auto-generated method stub
		this.us.setCredits(this.us.getCredits() + request.getContent().length);
		for (FileServerSave fss : this.data.getFservers().values()) {
			try {
				Socket s = new Socket(fss.getAddress(), fss.getPort());
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				oos.writeObject(request);
				ois.readObject();
				s.close();
				oos.close();
				ois.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new MessageResponse("success");
	}

}
