package pw.retrixsolutions.islandbank.objects;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.handlers.StringUtils;
import pw.retrixsolutions.islandbank.handlers.XMaterial;

public class BankPermGUI extends GUI {

	public BankPermGUI(String name, int size) {
		super(name, size);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getGlass() {
		ItemStack s;
		if (IslandBank.getInstance().thirteenPlus) {
			s=new ItemStack(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.parseMaterial(), 1);
		} else {
			s=new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), 1, (short)7);
		}
		ItemMeta meta = s.getItemMeta();
		meta.setDisplayName("§c");
		s.setItemMeta(meta);
		return s;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getOwner() {
		ItemStack s;
		if (IslandBank.getInstance().thirteenPlus) {
			s=new ItemStack(XMaterial.RED_WOOL.parseMaterial(), 1);
		} else {
			s=new ItemStack(XMaterial.RED_WOOL.parseMaterial(), 1, (short)14);
		}
		ItemMeta meta = s.getItemMeta();
		meta.setDisplayName("§cCurrent BankPerm§7: §fOWNER");
		meta.setLore(Arrays.asList(StringUtils.encodeString("OWNER"), "§7Click to change."));
		s.setItemMeta(meta);
		return s;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getAll() {
		ItemStack s;
		if (IslandBank.getInstance().thirteenPlus) {
			s=new ItemStack(XMaterial.GREEN_WOOL.parseMaterial(), 1);
		} else {
			s=new ItemStack(XMaterial.GREEN_WOOL.parseMaterial(), 1, (short)5);
		}
		ItemMeta meta = s.getItemMeta();
		meta.setDisplayName("§cCurrent BankPerm§7: §fALL");
		meta.setLore(Arrays.asList(StringUtils.encodeString("ALL"), "§7Click to change."));
		s.setItemMeta(meta);
		return s;
	}

}
