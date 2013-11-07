package server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.CharBuffer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import message.Response;
import message.request.DownloadFileRequest;
import message.request.InfoRequest;
import message.request.UploadRequest;
import message.request.VersionRequest;
import message.response.DownloadFileResponse;
import message.response.MessageResponse;

public class FileServer implements IFileServer {

	private ServerData data;

	public FileServer(ServerData data) {
		this.data = data;
	}

	@Override
	public Response download(DownloadFileRequest request) throws IOException {
		// TODO Auto-generated method stub
		File f = this.data.getFiles().get(request.getTicket().getFilename());
		FileReader r = new FileReader(f);
		CharBuffer cb = CharBuffer.allocate((int) f.length());
		r.read(cb);
		String content = new BASE64Encoder().encode(cb.toString().getBytes());
		Response resp = new DownloadFileResponse(request.getTicket(), content.getBytes());
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
		FileWriter fw = new FileWriter(this.data.getFdir()+"/"+request.getFilename());
		byte[] data = new BASE64Decoder().decodeBuffer(String.valueOf(request.getContent()));
		fw.write(String.valueOf(data));
		fw.close();
		return null;
	}

	@Override
	public Response version(VersionRequest request) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
