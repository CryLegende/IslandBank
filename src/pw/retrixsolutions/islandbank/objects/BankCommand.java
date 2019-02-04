package pw.retrixsolutions.islandbank.objects;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import pw.retrixsolutions.islandbank.IslandBank;

public abstract class BankCommand {
	
	public abstract boolean requiresPlayer();
	public abstract Permission getPermissionRequired();	
	public abstract void run(CommandSender sender, String[] args);
	public abstract int minimumArgs();
	public abstract int maximumArgs();
	public abstract String getUsage();
	public abstract boolean isAdmin();
	
	public String getMessage(String location) {
		return ChatColor.translateAlternateColorCodes('&', IslandBank.getInstance().getConfig().getString("messages." + location));
	}

}
