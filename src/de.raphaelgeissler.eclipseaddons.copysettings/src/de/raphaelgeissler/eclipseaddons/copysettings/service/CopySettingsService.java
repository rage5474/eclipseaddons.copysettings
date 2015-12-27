package de.raphaelgeissler.eclipseaddons.copysettings.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CopySettingsService {

	static String absolutePathToSettingsFolder;

	public static void storeSettingsFolder(String absolutePathToSettingsFolder) {
		CopySettingsService.absolutePathToSettingsFolder = absolutePathToSettingsFolder;
	}

	public static void copySettingsFolder(List<String> absolutePathToPluginRootFolder) {

		for (String nextPluginRootFolderPath : absolutePathToPluginRootFolder) {
			File settingsFolder = new File(nextPluginRootFolderPath, "/.settings");
			try {
				if (settingsFolder.exists()) {
					FileUtils.copyDirectory(settingsFolder, new File(settingsFolder + ".backup"));
				}
			} catch (IOException e1) {
				// harmless, but not successfully
				// remove backup folder
				e1.printStackTrace();
			}

			try {
				if (settingsFolder.exists()) {
					FileUtils.deleteDirectory(settingsFolder);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				FileUtils.copyDirectory(new File(absolutePathToSettingsFolder), new File(nextPluginRootFolderPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				FileUtils.deleteDirectory(new File(settingsFolder + ".backup"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
