package pw.retrixsolutions.islandbank.placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.wasteofplastic.askyblock.Island;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.BankPerm;

public class MVdWPlaceholderAPI {
	
	/*
	 * islandbank_balance - Balance of the bank.
	 * islandbank_isowner - Boolean of ownership.
	 * islandbank_owner - Returns owners' name.
	 * islandbank_bankperm - Gets the linked bank-perm for the bank.
	 */
	
	public MVdWPlaceholderAPI() {
		be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(IslandBank.getInstance(), "islandbank_balance", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				Island island = null;
				if (event.getPlayer() != null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());
				}
				if (event.getOfflinePlayer() != null && island == null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());					
				}
				if (island == null) {
					return "$0";
				}
				String bal = IslandBank.getInstance().getIslandBankData().getFormattedBalance(island);
				return bal == null ? "$0" : "$" + bal;
			}
			
		});
		be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(IslandBank.getInstance(), "islandbank_isowner", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				Island island = null;
				UUID uuid = null;
				if (event.getPlayer() != null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());
					uuid = event.getPlayer().getUniqueId();
				}
				if (event.getOfflinePlayer() != null && island == null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());	
					uuid = event.getOfflinePlayer().getUniqueId();
				}
				if (island == null || uuid == null) {
					return "false";
				} else {
					return island.getOwner().equals(uuid) ? "true" : "false";
				}
			}
			
		});
		be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(IslandBank.getInstance(), "islandbank_owner", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				Island island = null;
				if (event.getPlayer() != null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());
				}
				if (event.getOfflinePlayer() != null && island == null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());	
				}
				if (island == null) {
					return "Error";
				} else {
					return Bukkit.getOfflinePlayer(island.getOwner()).getName();
				}
			}
			
		});
		be.maximvdw.placeholderapi.PlaceholderAPI.registerPlaceholder(IslandBank.getInstance(), "islandbank_bankperm", new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				Island island = null;
				if (event.getPlayer() != null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());
				}
				if (event.getOfflinePlayer() != null && island == null) {
					island = IslandBank.getInstance().manager.getIslandForPlayer(event.getPlayer().getUniqueId());	
				}
				if (island == null) {
					return "NONE";
				} else {
					BankPerm perm = IslandBank.getInstance().getIslandBankData().getBankPerm(island);
					if (perm == null) {
						return "NONE";
					} else {
						return perm.getPermName();
					}
				}
			}
			
		});
	}

}
