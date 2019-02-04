package pw.retrixsolutions.islandbank.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import pw.retrixsolutions.islandbank.objects.BankCommand;

public class Version extends BankCommand {

	@Override
	public boolean requiresPlayer() {
		return false;
	}

	@Override
	public Permission getPermissionRequired() {
		return new Permission("islandbank.admin");
	}

	@Override
	public void run(CommandSender sender, String[] args) {
		sender.sendMessage(getMessage("admin.version.version"));
		return;
	}

	@Override
	public int minimumArgs() {
		return 1;
	}

	@Override
	public int maximumArgs() {
		return 1;
	}

	@Override
	public String getUsage() {
		return getMessage("admin.version.usage");
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}