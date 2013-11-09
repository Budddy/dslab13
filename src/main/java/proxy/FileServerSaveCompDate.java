package proxy;

import java.util.Comparator;

public class FileServerSaveCompDate implements Comparator<FileServerSave> {

	@Override
	public int compare(FileServerSave arg0, FileServerSave arg1) {
		return arg0.getLast().compareTo(arg1.getLast());
	}

}
