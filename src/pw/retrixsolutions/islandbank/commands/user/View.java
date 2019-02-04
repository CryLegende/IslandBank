package pw.retrixsolutions.islandbank.commands.user;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.Bank;
import pw.retrixsolutions.islandbank.objects.BankCommand;

public class View extends BankCommand {

	@Override
	public boolean requiresPlayer() {
		return false;
	}

	@Override
	public Permission getPermissionRequired() {
		return new Permission("IslandBank.View");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(CommandSender sender, String[] args) {
		Player player;
		OfflinePlayer of = null;
		boolean b;
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(getMessage("generic.players-only"));
				return;
			} else {
				player = (Player) sender;
				b=true;
			}
		} else {
			player = Bukkit.getPlayerExact(args[1]);
			of = Bukkit.getOfflinePlayer(args[1]);
			b=false;
		}
		if (player == null && of == null) {
			sender.sendMessage(getMessage("generic.invalid-player"));
			return;
		} else {
			UUID uuid;
			String n;
			if (player == null) {
				uuid = of.getUniqueId();
				n = of.getName();
			} else {
				uuid = player.getUniqueId();
				n = player.getName();
			}
			Island island = IslandBank.getInstance().manager.getIslandForPlayer(uuid);
			if (island == null) {
				sender.sendMessage(getMessage("view.island-data-incorrect"));
				return;
			}
			Bank bank = IslandBank.getInstance().manager.getBank(island);
			if (bank == null) {
				IslandBank.getInstance().manager.loadBank(island);
				bank = IslandBank.getInstance().manager.getBank(island);
			}
			if (bank.getBankBalance() == Double.MAX_VALUE) {
				bank.setBankBalance(0);
			}
			String format = getMessage("view.view-format");
			DecimalFormat formatter = new DecimalFormat("#,###.00");
			format = format.replaceAll("%amount%", "\\$" + (bank.getBankBalance() == 0 ? "0.0" : formatter.format(bank.getBankBalance())));
			format = format.replaceAll("%player%", b ? "Your " : n + (n.charAt(n.length()-1)=='s' || (n.charAt(n.length()-1)=='S') ? "' " : "s' "));
			sender.sendMessage(format);
			return;
		}
	}

	@Override
	public int minimumArgs() {
		return 1;
	}

	@Override
	public int maximumArgs() {
		return 2;
	}

	@Override
	public String getUsage() {
		return getMessage("view.usage");
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
