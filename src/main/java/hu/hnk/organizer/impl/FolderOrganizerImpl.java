package hu.hnk.organizer.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import hu.hnk.organizer.FolderOrganizer;
@Component
public class FolderOrganizerImpl implements FolderOrganizer {

	public static final Logger logger = LoggerFactory.getLogger(FolderOrganizerImpl.class);

	private OutputStream outputStream;
	private OutputStreamWriter streamWriter;
	private File organizerLogFile;
	private List<String> blackList;

	public FolderOrganizerImpl() throws FileNotFoundException {
		blackList = new ArrayList<>();
		blackList.add("orlog");
		organizerLogFile = new File(getNewLogName());
		outputStream = new FileOutputStream(organizerLogFile);
		streamWriter = new OutputStreamWriter(outputStream);
	}

	private String getNewLogName() {
		return new String(
				LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("u-M-d-H-m").toFormatter())
						.toString() + LOGFILE_EXTENSION);
	}

	@Override
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

	@Override
	public File[] getFilesFromCurrentFolder() {
		File f = new File(".");
		return f.listFiles();
	}

	@Override
	public String getFileType(File f) {
		Validate.notNull(f);
		return f.getName().split("\\.")[getFileTypeAfterDot(f)];
	}

	@Override
	public int getFileTypeAfterDot(File f) {
		return (int) (Arrays.asList(f.getName().split("\\.")).stream().count() - 1);
	}

	@Override
	public List<String> getFileTypes(File[] files) {
		List<String> result = new ArrayList<>();
		for (File f : files) {
			if (f.isDirectory()) {
				logger.info("{} is directory", f);
			} else {
				logger.info("{} is file", f);
				String fileType = getFileType(f);
				logger.info("FileType: {}", fileType);
				if (!isFileTypBlackListed(fileType))
					result.add(fileType);
				else
					logger.info("FileType: {} is blacklisted.", fileType);
			}
		}
		return result.stream().distinct().collect(Collectors.toList());
	}

	private boolean isFileTypBlackListed(String fileType) {
		return blackList.contains(fileType);
	}

	@Override
	public void moveFilesToFolders(File[] files) throws IOException {
		for (File f : files) {
			if (f.isFile()) {
				streamWriter.append(f.getName() + " -> " + "_" + getFileType(f) + "/" + f.getName() + "\n");
				logger.info("moving {} to folder {} ", f.getName(), "_" + getFileType(f) + "/" + f.getName());
				f.renameTo(new File("_" + getFileType(f) + "/" + f));
			}
		}
		streamWriter.flush();
		streamWriter.close();

	}

	public File getOrganizerLogFile() {
		return organizerLogFile;
	}

	public void setOrganizerLogFile(File organizerLogFile) {
		this.organizerLogFile = organizerLogFile;
	}

	@Override
	public void addItemsToBlackList(Collection<String> items) {
		blackList.addAll(items);
	}

}
