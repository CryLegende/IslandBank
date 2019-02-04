package pw.retrixsolutions.islandbank.commands.admin;

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
import pw.retrixsolutions.islandbank.objects.BankPerm;

public class Reset extends BankCommand {

	@Override
	public boolean requiresPlayer() {
		return false;
	}

	@Override
	public Permission getPermissionRequired() {
		return new Permission("islandbank.admin");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(CommandSender sender, String[] args) {
		Player player = Bukkit.getPlayerExact(args[1]);
		OfflinePlayer of = Bukkit.getOfflinePlayer(args[1]);
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
				sender.sendMessage(getMessage("admin.reset.island-data-incorrect"));
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
			bank.setBankBalance(0);
			IslandBank.getInstance().getIslandBankData().setBankPerm(island, BankPerm.ALL);
			player.sendMessage(getMessage("admin.reset.reset-data").replaceAll("%owner%", n));
			return;
		}
	}

	@Override
	public int minimumArgs() {
		return 2;
	}

	@Override
	public int maximumArgs() {
		return 2;
	}

	@Override
	public String getUsage() {
		return getMessage("admin.reset.usage");
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}