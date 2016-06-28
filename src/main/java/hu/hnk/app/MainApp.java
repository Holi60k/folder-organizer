package hu.hnk.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp {

	public static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
		logger.info("Reading files and folders in actual directory");
		FolderOrganizer folderOrganizer = new FolderOrganizer();
		List<String> types = new ArrayList<>();
		File[] files = folderOrganizer.getFilesFromCurrentFolder();
		types = folderOrganizer.getFileTypes(files);
		folderOrganizer.createDirectories(types);
		folderOrganizer.moveFilesToFolders(files);
	}

}
