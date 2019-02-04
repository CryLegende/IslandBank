package pw.retrixsolutions.islandbank.placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.Island;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.BankPerm;

public class PlaceholderAPI extends PlaceholderExpansion {
	
	/*
	 * islandbank_balance - Balance of the bank.
	 * islandbank_isowner - Boolean of ownership.
	 * islandbank_owner - Returns owners' name.
	 * islandbank_bankperm - Gets the linked bank-perm for the bank.
	 */
	
	public PlaceholderAPI() {
		this.register();
	}

	public String getIdentifier() {
		return "islandbank";
	}

	public String getPlugin() {
		return IslandBank.getInstance().getName();
	}

	public String getAuthor() {
		return "RetrixSolutions";
	}

	public String getVersion() {
		return "1.0";
	}

	public String onPlaceholderRequest(Player player, String identifier) {
		if (player == null || identifier == null) {
			return null;
		}
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(player.getUniqueId());
		switch (identifier.toLowerCase()) {
		case "balance":
			String bal = IslandBank.getInstance().getIslandBankData().getFormattedBalance(island);
			return bal == null ? "$0" : "$" + bal;
		case "isowner":
			if (player.getUniqueId().equals(island.getOwner())) {
				return "true";
			} else {
				return "false";
			}
		case "owner":
			return Bukkit.getOfflinePlayer(island.getOwner()).getName();
		case "bankperm":
			BankPerm perm = IslandBank.getInstance().getIslandBankData().getBankPerm(island);
			if (perm == null) {
				return "NONE";
			} else {
				return perm.getPermName();
			}
		default:
			return null;
		}
	}
}
