package pw.retrixsolutions.islandbank.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.BankCommand;
import pw.retrixsolutions.islandbank.objects.BankPerm;
import pw.retrixsolutions.islandbank.objects.BankPermGUI;

public class Modify extends BankCommand {
	
	@Override
	public void run(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(player.getUniqueId());
		if (island == null) {
			player.sendMessage(getMessage("modify.island-data-incorrect"));
			return;
		} 
		if (!player.getUniqueId().equals(island.getOwner())) {
			player.sendMessage(getMessage("generic.owner-required"));
			return;
		}
		BankPerm current = IslandBank.getInstance().getIslandBankData().getBankPerm(island);
		if (current == null) {
			player.sendMessage(getMessage("modify.bankperm-data-incorrect"));
			return;
		}
		if (args.length == 2) {
			try {
				BankPerm nBP = BankPerm.valueOf(args[1].toUpperCase());
				if (current == nBP) {
					player.sendMessage(getMessage("modify.already-set"));
					return;
				}
				IslandBank.getInstance().getIslandBankData().setBankPerm(island, nBP);
				player.sendMessage(getMessage("modify.modified"));
				return;
			} catch (IllegalArgumentException ex) {
				player.sendMessage(getMessage("modify.invalid-bankperm"));
				return;
			}
		} else {
			BankPermGUI gui = new BankPermGUI("&d&lIslandBank", 27);
			gui.fillInventory(gui.getGlass());
			if (current.equals(BankPerm.ALL)) {
				gui.setItem(gui.getAll(), 13);
			} else {
				gui.setItem(gui.getOwner(), 13);
			}
			player.openInventory(gui.create());
			return;
		}
	}

	@Override
	public boolean requiresPlayer() {
		return true;
	}

	@Override
	public Permission getPermissionRequired() {
		return new Permission("islandbank.modify");
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
		return getMessage("modify.usage");
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
