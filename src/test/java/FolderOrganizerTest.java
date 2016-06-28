import java.io.File;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.hnk.app.FolderOrganizer;

public class FolderOrganizerTest {

	FolderOrganizer folderOrganizer;

	@Before
	public void before() {
		folderOrganizer = new FolderOrganizer();
	}

	@Test
	public void testGetFileTypeAfterDot() {
		File f = new File("test.txt.java");
		Assert.assertEquals(2, folderOrganizer.getFileTypeAfterDot(f));
	}

	@Test
	public void testGetFileTypes() {
		File[] files = { new File("test.txt.java"), new File("test.txt"), new File("test.exe"),
				new File("test.exe.exe.exe.log") };
		Assert.assertArrayEquals(Arrays.asList("java", "txt", "exe", "log").toArray(),
				folderOrganizer.getFileTypes(files).toArray());
	}

}
