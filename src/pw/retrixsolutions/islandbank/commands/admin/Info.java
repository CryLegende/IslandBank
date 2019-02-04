package pw.retrixsolutions.islandbank.commands.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.Bank;
import pw.retrixsolutions.islandbank.objects.BankCommand;

public class Info extends BankCommand {

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
				sender.sendMessage(getMessage("admin.info.island-data-incorrect"));
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
			List<String> format = getMessageList(IslandBank.getInstance().getConfig().getStringList("messages.admin.info.info-format"), n, bank, island);
			for (String str : format) {
				sender.sendMessage(str);
			}
			return;
		}
	}

	private List<String> getMessageList(List<String> list, String n, Bank bank, Island island) {
		List<String> data = new ArrayList<String>();
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		for (String d : list) {
			String format = d;
			format = format.replaceAll("%amount%", "\\$" + (bank.getBankBalance() == 0 ? "0.0" : formatter.format(bank.getBankBalance())));
			format = format.replaceAll("%id%", "island_" + island.getOwner().toString());
			format = format.replaceAll("%owner%", Bukkit.getOfflinePlayer(island.getOwner()).getName());
			format = ChatColor.translateAlternateColorCodes('&', format);
			data.add(format);
		}
		return data;
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
		return getMessage("admin.info.usage");
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
