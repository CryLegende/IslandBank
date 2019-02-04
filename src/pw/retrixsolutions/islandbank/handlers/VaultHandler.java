package pw.retrixsolutions.islandbank.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;

import pw.retrixsolutions.islandbank.IslandBank;

public class VaultHandler {
	
	public double getBalance(UUID uuid) {
		return IslandBank.eco.getBalance(Bukkit.getOfflinePlayer(uuid));
	}

	public double round(double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		final long factor = (long) Math.pow(10.0, places);
		value *= factor;
		final long tmp = Math.round(value);
		return tmp / factor;
	}

	public void decreasePlayerBalance(UUID uuid, final double amount) {
		IslandBank.eco.withdrawPlayer((Bukkit.getOfflinePlayer(uuid)), amount);
	}
	
	public void increasePlayerBalance(UUID uuid, final double amount) {
		IslandBank.eco.depositPlayer((Bukkit.getOfflinePlayer(uuid)), amount);
	}

}
