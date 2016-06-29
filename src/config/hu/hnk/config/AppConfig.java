package hu.hnk.config;

import java.io.FileNotFoundException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hu.hnk.organizer.FolderOrganizer;
import hu.hnk.organizer.impl.FolderOrganizerImpl;

@Configuration
@ComponentScan(basePackages = { "hu.hnk.organizer" })
public class AppConfig {

	@Bean
	public FolderOrganizer folderOrganizer() throws FileNotFoundException {
		return new FolderOrganizerImpl();
	}
}
