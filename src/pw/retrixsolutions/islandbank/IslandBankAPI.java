package pw.retrixsolutions.islandbank;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.exceptions.IslandNotFound;

public class IslandBankAPI {
	
	private static IslandBankAPI api;
	
	public IslandBankAPI() {
		api=this;
	}
	
	public static IslandBankAPI getAPI() {
		return api;
	}
	
	public void decreaseIslandBalance(Player player, double amount) throws IslandNotFound {
		decreaseIslandBalance(player.getUniqueId(), amount);
		return;
	}
	
	public void decreaseIslandBalance(Island island, double amount) {
		decreaseIslandBalance("island_" + island.getOwner().toString(), amount);
		return;
	}
	
	public void decreaseIslandBalance(String id, double amount) {
		double current = IslandBank.getInstance().getIslandBankData().getBankBalance(id);
		double balance = current - amount;
		IslandBank.getInstance().getIslandBankData().setBankBalance(id, balance);
		return;
	}
	
	public void decreaseIslandBalance(UUID uuid, double amount) throws IslandNotFound {
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(uuid);
		if (island == null) {
			throw new IslandNotFound("The island for '" + uuid.toString() + "' couldn't be found!");
		} else {
			decreaseIslandBalance(island, amount);
			return;
		}
	}
	
	public void increaseIslandBalance(Player player, double amount) throws IslandNotFound {
		increaseIslandBalance(player.getUniqueId(), amount);
		return;
	}
	
	public void increaseIslandBalance(Island island, double amount) {
		increaseIslandBalance("island_" + island.getOwner().toString(), amount);
		return;
	}
	
	public void increaseIslandBalance(String id, double amount) {
		double current = IslandBank.getInstance().getIslandBankData().getBankBalance(id);
		double balance = current + amount;
		IslandBank.getInstance().getIslandBankData().setBankBalance(id, balance);
		return;
	}
	
	public void increaseIslandBalance(UUID uuid, double amount) throws IslandNotFound {
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(uuid);
		if (island == null) {
			throw new IslandNotFound("The island for '" + uuid.toString() + "' couldn't be found!");
		} else {
			increaseIslandBalance(island, amount);
			return;
		}
	}
	
	public double getIslandBalance(Player player) throws IslandNotFound {
		return getIslandBalance(player.getUniqueId());
	}
	
	public double getIslandBalance(Island island) {
		return getIslandBalance("island_" + island.getOwner().toString());
	}
	
	public double getIslandBalance(String id) {
		return IslandBank.getInstance().getIslandBankData().getBankBalance(id);
	}
	
	public double getIslandBalance(UUID uuid) throws IslandNotFound {
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(uuid);
		if (island == null) {
			throw new IslandNotFound("The island for '" + uuid.toString() + "' couldn't be found!");
		} else {
			return getIslandBalance(island);
		}
	}

}
