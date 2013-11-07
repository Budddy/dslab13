package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Response;
import message.request.BuyRequest;
import message.request.CreditsRequest;
import message.request.DownloadFileRequest;
import message.request.DownloadTicketRequest;
import message.request.InfoRequest;
import message.request.ListRequest;
import message.request.LoginRequest;
import message.request.LogoutRequest;
import message.request.UploadRequest;
import message.request.VersionRequest;
import message.response.MessageResponse;

public class ServeClientServer implements Runnable {

	private Socket s;
	private ServerData data;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private FileServer fs;

	public ServeClientServer(Socket s, ServerData data) {
		super();
		this.s = s;
		this.data = data;
		try {
			this.input = new ObjectInputStream(s.getInputStream());
			this.output = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fs = new FileServer(this.data, this.s);
	}

	public Socket getS() {
		return this.s;
	}

	@Override
	public void run() {
		while (s.isConnected()) {
			try {
				Object request = input.readObject();
				Response response;
				if (request instanceof DownloadFileRequest) {
					response = fs.download((DownloadFileRequest) request);
				} else if (request instanceof InfoRequest) {
					response = fs.info((InfoRequest) request);

				} else if (request instanceof ListRequest) {
					response = fs.list();

				} else if (request instanceof UploadRequest) {
					response = fs.upload((UploadRequest) request);

				} else if (request instanceof VersionRequest) {
					response = fs.version((VersionRequest) request);

				}else{
					response = new MessageResponse("Unknown command!");
				}
				output.writeObject(response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setS(Socket s) {
		this.s = s;
	}

}
