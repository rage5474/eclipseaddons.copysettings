package de.raphaelgeissler.eclipseaddons.copysettings.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.raphaelgeissler.eclipseaddons.copysettings.service.CopySettingsService;

public class CopySettingsServiceTest {
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();
	private File pluginFolder1;
	private File pluginFolder2;
	private File pluginFolder3;
	
	@Before
	public void setup() throws IOException
	{
		pluginFolder1 = createFolderInTmpFolder("/firstplugin");
		pluginFolder2 = createFolderInTmpFolder("/sndplugin");
		pluginFolder3 = createFolderInTmpFolder("/trdplugin");
		
		File settingsFolderPlugin1 = new File(pluginFolder1 + "/.settings");
		settingsFolderPlugin1.mkdirs();
		
		File settingsFolderPlugin2 = new File(pluginFolder2 + "/.settings");
		settingsFolderPlugin2.mkdirs();
		
		File settingsFile = new File(settingsFolderPlugin1, "/dummySettingsFile.txt");
		settingsFile.createNewFile();
		
	}

	private File createFolderInTmpFolder(String folderName) {
		File settingsFolder = new File(tmpFolder.getRoot().getAbsolutePath(), folderName);
		settingsFolder.mkdirs();
		return settingsFolder;
	}
	
	@Test
	public void storeCopySettingsTest() throws Exception {
		CopySettingsService.storeSettingsFolder(pluginFolder1.getAbsolutePath());
		assertEquals(pluginFolder1.getAbsolutePath(), CopySettingsService.absolutePathToSettingsFolder);
	}
	
	@Test
	public void applySettingsTest() throws Exception {
		CopySettingsService.storeSettingsFolder(pluginFolder1.getAbsolutePath());
		CopySettingsService.copySettingsFolder(Arrays.asList(pluginFolder2.getAbsolutePath(), pluginFolder3.getAbsolutePath()));
		
		File settingsFolderPlugin2 = new File(pluginFolder2, "/.settings");
		File settingsFilePlugin2 = new File(settingsFolderPlugin2, "/dummySettingsFile.txt");
		File settingsFolderBackupPlugin2 = new File(pluginFolder2, "/.settings.backup");
		
		File settingsFolderPlugin3 = new File(pluginFolder3, "/.settings");
		File settingsFilePlugin3 = new File(settingsFolderPlugin3, "/dummySettingsFile.txt");
		
		assertTrue(settingsFolderPlugin2.exists());
		assertTrue(settingsFilePlugin2.exists());
		assertTrue(settingsFolderPlugin3.exists());
		assertTrue(settingsFilePlugin3.exists());
		assertFalse(settingsFolderBackupPlugin2.exists());
	}
}
