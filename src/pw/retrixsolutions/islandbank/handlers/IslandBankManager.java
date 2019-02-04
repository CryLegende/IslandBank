package pw.retrixsolutions.islandbank.handlers;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.objects.Bank;

public class IslandBankManager {

	public ArrayList<Bank> banks = new ArrayList<Bank>();
	private ASkyBlockAPI api;

	public IslandBankManager() {
		this.api = IslandBank.getInstance().getIslandAPI();
	}
	
	public void unloadBank(Island island) {
		if (!hasBank(island)) {
			return;
		}
		unloadBank(getBank(island));
		return;
	}
	
	public void unloadBank(Bank bank) {
		banks.remove(bank);
		double balance = bank.getBankBalance();
		Island island = bank.getIsland();
		IslandBank.getInstance().getIslandBankData().setBankBalance(island, balance);
		return;
	}

	public void loadBank(Island island) {
		try {
			double balance = IslandBank.getInstance().getIslandBankData().getBankBalance(island);
			Bank bank = new Bank(island, balance);
			banks.add(bank);
		} catch (NumberFormatException ex) {
			Bukkit.getConsoleSender().sendMessage("[IslandBank] §cFailed to load the bank for 'island_" + island.getOwner().toString() + "'!");
			return;
		}
	}
	
	public Bank getBank(Island island) {
		for (Bank bank : banks) {
			if (bank.getIsland().equals(island)) {
				return bank;
			}
		}
		loadBank(island);
		return getBank(island);
	}
	
	public boolean hasBank(Island island) {
		for (Bank bank : banks) {
			if (bank.getIsland().equals(island)) {
				return true;
			}
		}
		return false;
	}

	public Island getIslandForPlayer(UUID uuid) {
		if (ASkyBlockAPI.getInstance().getOwnedIslands().size() == 0) {
			return null;
		}
		for (Map.Entry<UUID, Island> island : api.getOwnedIslands().entrySet()) {
			Island entry = island.getValue();
			if (entry.getOwner().toString().equals(uuid.toString())) {
				return entry;
			}
			if (entry.getMembers().contains(uuid)) {
				return entry;
			}
		}
		return null;
	}

}
