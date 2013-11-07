package server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import message.Response;
import message.request.DownloadFileRequest;
import message.request.InfoRequest;
import message.request.UploadRequest;
import message.request.VersionRequest;
import message.response.DownloadFileResponse;
import message.response.MessageResponse;

public class FileServer implements IFileServer {
	
	private ServerData data;
	private Socket s;
	
	public FileServer(ServerData data,Socket s){
		this.data=data;
		this.s=s;
	}
	@Override
	public Response download(DownloadFileRequest request) throws IOException {
		// TODO Auto-generated method stub
		File f = data.getFiles().get(request.getTicket().getFilename());
		FileReader r = new FileReader(f);
		StringBuilder sb= new StringBuilder();
		char[] c = new char[10];
		while(r.read(c)!=-1){
			sb.append(c);
		}
		byte[] content = sb.toString().getBytes();
		Response resp = new DownloadFileResponse(request.getTicket(),content);
		r.close();
		return resp;
	}

	@Override
	public Response info(InfoRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response list() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageResponse upload(UploadRequest request) throws IOException {
		// TODO Auto-generated method stub
		FileWriter fw = new FileWriter(this.data.getFdir());
		fw.write(String.valueOf(request.getContent()));
		
		return null;
	}

	@Override
	public Response version(VersionRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
