package hu.hnk.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.organizer.impl.FolderOrganizerImpl;

public class MainApp {

	public static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {

		logger.info("Reading files and folders in actual directory");

		FolderOrganizerImpl folderOrganizer = null;
		try {
			folderOrganizer = new FolderOrganizerImpl();
		} catch (FileNotFoundException e1) {
			logger.warn("File can not been found.");
			System.exit(-1);
		}
		List<String> types = new ArrayList<>();

		try {
			moveFilesToFolders(folderOrganizer);
		} catch (IOException e) {
			logger.warn("Something wrong happened:", e);
		}
		moveFilesToOriginalFolders(folderOrganizer);

	}

	private static void moveFilesToOriginalFolders(FolderOrganizerImpl folderOrganizer) {
		// TODO Auto-generated method stub

	}

	private static void moveFilesToFolders(FolderOrganizerImpl folderOrganizer) throws IOException {
		List<String> types;
		File[] files = folderOrganizer.getFilesFromCurrentFolder();
		types = folderOrganizer.getFileTypes(files);
		folderOrganizer.createDirectories(types);
		folderOrganizer.moveFilesToFolders(files);
	}

}
