package pw.retrixsolutions.islandbank.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI { 
	
	private Inventory inv;

	public GUI(String name, int size) {
		this.inv = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', name));
	}
	
	public void addItem(ItemStack... stack) {
		inv.addItem(stack);
	}
	
	public void fillInventory(ItemStack stack) {
		for (int i = 0; i < inv.getSize(); i++) {
			setItem(stack, i);
		}
	}
	
	public void setItem(ItemStack stack, int slot) {
		inv.setItem(slot, stack);
	}
	
	public Inventory create() {
		return inv;
	}

}
