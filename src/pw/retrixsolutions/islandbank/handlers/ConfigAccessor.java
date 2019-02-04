package pw.retrixsolutions.islandbank.handlers;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigAccessor {
	
	private File file = null;
	private FileConfiguration config = null;	
	private boolean exists = false;
	
	public ConfigAccessor(String fileName) {
		this.file = new File("plugins\\IslandBank\\" + fileName);
		this.exists = file.exists();
		int i = loadConfig(false);
		if (i != 0) {
			Bukkit.getConsoleSender().sendMessage("[IslandBank] §cThe file '" + fileName + "' couldn't be loaded! (Code: " + i + ")");
			file = null;
			config = null;
			exists = false;
		}
	}
	
	/*
	 * 0 = Made
	 * 1 = IOException
	 * 2 = NullPointerException
	 * 3 = Config already exists?
	 */
	
	private int loadConfig(boolean b) {
		if (b) {
			config = null;
		}
		if (file == null) {
			return 2;
		}
		if (!exists) {
			try {
				file.createNewFile();
				exists = true;
			} catch (IOException ex) {
				return 1;
			}
		}
		if (config != null) {
			return 3;
		} else {
			config = YamlConfiguration.loadConfiguration(file);
			return 0;
		}
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public boolean saveConfig() {
		try {
			config.save(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean reloadConfig() {
		return loadConfig(true) == 0 ? true : false;
	}

}
