package pw.retrixsolutions.islandbank.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.wasteofplastic.askyblock.events.IslandChangeOwnerEvent;
import com.wasteofplastic.askyblock.events.IslandNewEvent;
import com.wasteofplastic.askyblock.events.IslandPreDeleteEvent;

import pw.retrixsolutions.islandbank.IslandBank;

public class IslandEvents implements Listener {
	
	/*
	 * IslandNewEvent.java - new island
	 */
	
	@EventHandler
	public void onChangeOwner(IslandChangeOwnerEvent event) {
		String id = "island_" + event.getOldOwner().toString();
		IslandBank.getInstance().getIslandBankData().changeIslandID(id, "island_" + event.getNewOwner().toString());
		return;
	}
	
	@EventHandler
	public void onDelete(IslandPreDeleteEvent event) {
		IslandBank.getInstance().getIslandBankData().removeBank(event.getIsland());
		return;
	}
	
	@EventHandler
	public void onNew(IslandNewEvent event) {
		IslandBank.getInstance().getIslandBankData().createIslandData(IslandBank.getInstance().getIslandAPI().getIslandAt(event.getIslandLocation()), 0.0);
		return;
	}

}
