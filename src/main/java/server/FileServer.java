package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Set;
import message.Response;
import message.request.DownloadFileRequest;
import message.request.InfoRequest;
import message.request.UploadRequest;
import message.request.VersionRequest;
import message.response.DownloadFileResponse;
import message.response.InfoResponse;
import message.response.ListResponse;
import message.response.MessageResponse;
import message.response.VersionResponse;

public class FileServer implements IFileServer {

	private ServerData data;

	public FileServer(ServerData data) {
		this.data = data;
	}

	@Override
	public Response download(DownloadFileRequest request) throws IOException {
		// TODO Auto-generated method stub
		File f = this.data.getFiles().get(request.getTicket().getFilename());
		FileInputStream r = new FileInputStream(f);
		byte[] b = new byte[r.available()];
		r.read(b);
		Response resp = new DownloadFileResponse(request.getTicket(),b);
		r.close();
		return resp;
	}

	@Override
	public Response info(InfoRequest request) throws IOException {
		// TODO Auto-generated method stub
		File f = new File(this.data.getFdir() + "/" + request.getFilename());
		return new InfoResponse(request.getFilename(), f.length());
	}

	@Override
	public Response list() throws IOException {
		// TODO Auto-generated method stub
		File f = new File(this.data.getFdir());
		Set<String> names = new HashSet<String>();
		for (String name : f.list()) {
			names.add(name);
		}
		return new ListResponse(names);
	}

	@Override
	public MessageResponse upload(UploadRequest request) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fw = new FileOutputStream(this.data.getFdir() + "/" + request.getFilename());
		byte[] data = request.getContent();
		fw.write(data);
		fw.close();
		return null;
	}

	@Override
	public Response version(VersionRequest request) throws IOException {
		// TODO Auto-generated method stub
		return new VersionResponse(request.getFilename(), 1);
	}

}
