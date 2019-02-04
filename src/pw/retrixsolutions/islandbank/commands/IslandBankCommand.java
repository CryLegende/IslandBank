package pw.retrixsolutions.islandbank.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import net.md_5.bungee.api.ChatColor;
import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.BankCommand;

public class IslandBankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		boolean adminCommand = label.equalsIgnoreCase("IBA") || label.equalsIgnoreCase("IslandBankAdmin");
		boolean isPlayer = sender instanceof Player;
		if (!(args.length >= 1)) {
			help(sender, adminCommand);
			return false;
		}
		BankCommand subCmd = getSubCommand(args[0].toLowerCase(), adminCommand);
		if (subCmd == null) {
			help(sender, adminCommand);
			return false;
		}
		if (!(args.length >= subCmd.minimumArgs()) || args.length > subCmd.maximumArgs()) {
			sender.sendMessage(subCmd.getUsage());
			return false;
		}
		if (!isPlayer && subCmd.requiresPlayer()) {
			sender.sendMessage(subCmd.getMessage("generic.players-only"));
			return false;
		}
		if (adminCommand) {
			if (!sender.hasPermission(new Permission("islandbank.admin"))) {
				sender.sendMessage(subCmd.getMessage("generic.permission-denied"));
				return false;
			}
		} else {
			if (!sender.hasPermission(subCmd.getPermissionRequired())) {
				sender.sendMessage(subCmd.getMessage("generic.permission-denied"));
				return false;
			}
		}
		subCmd.run(sender, args);
		return true;
	}

	private void help(CommandSender sender, boolean adminCommand) {
		List<String> help;
		if (adminCommand) {
			help = IslandBank.getInstance().getConfig().getStringList("messages.admin.help");
		} else {
			help = IslandBank.getInstance().getConfig().getStringList("messages.generic.help");
		}
		for (String str : help) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
		}
	}

	public BankCommand getSubCommand(String argument, boolean admin) {
		for (BankCommand subCommand : IslandBank.getInstance().subCommands.keySet()) {
			if (admin) {
				if (subCommand.isAdmin()) {
					List<String> aliases = IslandBank.getInstance().subCommands.get(subCommand);
					for (String alias : aliases) {
						if (alias.equalsIgnoreCase(argument)) {
							return subCommand;
						}
					}
				}
				continue;
			} else {
				if (!subCommand.isAdmin()) {
					List<String> aliases = IslandBank.getInstance().subCommands.get(subCommand);
					for (String alias : aliases) {
						if (alias.equalsIgnoreCase(argument)) {
							return subCommand;
						}
					}
				}
				continue;
			}
		}
		return null;
	}

}
