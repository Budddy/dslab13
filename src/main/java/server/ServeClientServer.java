package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Response;
import message.request.DownloadFileRequest;
import message.request.InfoRequest;
import message.request.ListRequest;
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
			this.output = new ObjectOutputStream(s.getOutputStream());
			this.input = new ObjectInputStream(s.getInputStream());
			this.fs = new FileServer(this.data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Socket getS() {
		return this.s;
	}

	@Override
	public void run() {
		try {
			while (this.s.isConnected()) {
				Object request = this.input.readObject();
				Response response;
				if (request instanceof DownloadFileRequest) {
					response = this.fs.download((DownloadFileRequest) request);
				} else if (request instanceof InfoRequest) {
					response = this.fs.info((InfoRequest) request);

				} else if (request instanceof ListRequest) {
					response = this.fs.list();

				} else if (request instanceof UploadRequest) {
					response = this.fs.upload((UploadRequest) request);

				} else if (request instanceof VersionRequest) {
					response = this.fs.version((VersionRequest) request);

				} else {
					response = new MessageResponse("Unknown command!");
				}
				this.output.writeObject(response);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void setS(Socket s) {
		this.s = s;
	}

}
