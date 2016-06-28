package hu.hnk.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderOrganizer {
	public static final Logger logger = LoggerFactory.getLogger(FolderOrganizer.class);

	public void createDirectories(List<String> types) {
		for (String type : types) {
			File f = new File("_" + type);
			if (!f.exists()) {
				f.mkdir();
				logger.info("{} directory created", "_" + type);
			} else {
				logger.info("{} folder is already exixts", "_" + type);
			}
		}

	}

	public File[] getFilesFromCurrentFolder() {

		File f = new File(".");
		return f.listFiles();

	}

	public String getFileType(File f) {
		return f.getName().split("\\.")[getFileTypeAfterDot(f)];
	}

	public int getFileTypeAfterDot(File f) {
		return (int) (Arrays.asList(f.getName().split("\\.")).stream().count() - 1);
	}

	public List<String> getFileTypes(File[] files) {
		List<String> result = new ArrayList<>();
		for (File f : files) {
			if (f.isDirectory()) {
				logger.info("{} is directory", f);
			} else {
				logger.info("{} is file", f);
				String fileType = getFileType(f);
				logger.info("FileType {}", fileType);
				result.add(fileType);
			}
		}
		return result.stream().distinct().collect(Collectors.toList());
	}

	public void moveFilesToFolders(File[] files) {
		for (File f : files) {
			if (f.isFile()) {
				logger.info("moving {} to folder {} ", f, "_" + f);
				f.renameTo(new File("_" + getFileType(f) + "/" + f));
			}
		}

	}
}
