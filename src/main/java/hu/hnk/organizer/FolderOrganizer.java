package hu.hnk.organizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FolderOrganizer {
	public static final String LOGFILE_EXTENSION = ".orlog";

	public void createDirectories(List<String> types);

	public File[] getFilesFromCurrentFolder();

	public String getFileType(File f);

	public int getFileTypeAfterDot(File f);

	public List<String> getFileTypes(File[] files);

	public void moveFilesToFolders(File[] files) throws IOException;

}
