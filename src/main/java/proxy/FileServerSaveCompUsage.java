package proxy;

import java.util.Comparator;

public class FileServerSaveCompUsage implements Comparator<FileServerSave> {

	@Override
	public int compare(FileServerSave arg0, FileServerSave arg1) {
		if (!arg0.isOnline() && !arg1.isOnline()) { return 0; }
		if (!arg0.isOnline()) { return -1; }
		if (!arg1.isOnline()) { return 1; }
		if(arg0.getUsage()<arg1.getUsage()){
			return -1;
		}else if(arg0.getUsage()>arg1.getUsage()){
			return 0;
		}
		return 0;
	}

}
