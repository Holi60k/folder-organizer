package hu.hnk.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import hu.hnk.organizer.FolderOrganizer;

@Component
public class MainApp {

	public static final Logger logger = LoggerFactory.getLogger(MainApp.class);
	public static final String ORGANIZE = "organize";
	public static final String DEORGANIZE = "deorganize";
	public static final Set<String> ACTIONS = new HashSet<>(Arrays.asList(ORGANIZE, DEORGANIZE));

	@Autowired
	private FolderOrganizer folderOrganizer;

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-service.xml");
		MainApp app = context.getBean(MainApp.class);

		String action = null;
		if (args.length > 0) {
			action = ACTIONS.contains(args[0]) ? args[0] : ORGANIZE;
		} else {
			action = ORGANIZE;
		}

		logger.info("Reading files and folders in the actual directory");
		logger.info("Action: {}", action);

		List<String> types = new ArrayList<>();

		try {
			if (ORGANIZE.equals(action))
				moveFilesToFolders(app.folderOrganizer);
			else if (DEORGANIZE.equals(action))
				moveFilesToOriginalFolders(app.folderOrganizer);
		} catch (IOException e) {
			logger.warn("Something wrong happened:", e);
		}

	}

	private static void moveFilesToOriginalFolders(FolderOrganizer folderOrganizer) {

	}

	private static void moveFilesToFolders(FolderOrganizer folderOrganizer) throws IOException {
		Validate.notNull(folderOrganizer);
		List<String> types;
		File[] files = folderOrganizer.getFilesFromCurrentFolder();
		types = folderOrganizer.getFileTypes(files);
		folderOrganizer.createDirectories(types);
		folderOrganizer.moveFilesToFolders(files);
	}

}
