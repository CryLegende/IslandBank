package pw.retrixsolutions.islandbank.commands.user;

import java.text.DecimalFormat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.Bank;
import pw.retrixsolutions.islandbank.objects.BankCommand;
import pw.retrixsolutions.islandbank.objects.BankPerm;

public class Deposit extends BankCommand {

	@Override
	public void run(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(player.getUniqueId());
		if (island == null) {
			player.sendMessage(getMessage("deposit.island-data-incorrect"));
			return;
		} 
		BankPerm perm = IslandBank.getInstance().getIslandBankData().getBankPerm(island);
		if (perm == null) {
			player.sendMessage(getMessage("deposit.bankperm-data-incorrect"));
			return;
		}
		if (perm == BankPerm.OWNER && !player.getUniqueId().equals(island.getOwner())) {
			player.sendMessage(getMessage("generic.owner-required"));
			return;
		}
		Bank bank = IslandBank.getInstance().manager.getBank(island);
		if (bank == null) {
			IslandBank.getInstance().manager.loadBank(island);
			bank = IslandBank.getInstance().manager.getBank(island);
		}
		double playerBalance = IslandBank.getInstance().getVaultHandler().getBalance(player.getUniqueId());
		double amount;
		if (args[1].equalsIgnoreCase("all")) {
			amount = playerBalance;
		} else {
			try {
				amount = IslandBank.getInstance().getVaultHandler().round(Double.parseDouble(args[1]), 2);
			} catch (NumberFormatException ex) {
				player.sendMessage(getMessage("deposit.enter-valid-amount"));
				return;
			}
		}
		if (amount > playerBalance) {
			player.sendMessage(getMessage("deposit.invalid-balance"));
			return;
		}
		bank.increaseBankBalance(amount);
		IslandBank.getInstance().getVaultHandler().decreasePlayerBalance(player.getUniqueId(), amount);
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		player.sendMessage(getMessage("deposit.deposited").replaceAll("%amount%", "\\$" + formatter.format(amount)));
		return;
	}

	@Override
	public boolean requiresPlayer() {
		return true;
	}

	@Override
	public Permission getPermissionRequired() {
		return new Permission("islandbank.deposit");
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
		return getMessage("deposit.usage");
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}
