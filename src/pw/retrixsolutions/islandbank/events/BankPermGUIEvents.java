package pw.retrixsolutions.islandbank.events;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;
import pw.retrixsolutions.islandbank.handlers.StringUtils;
import pw.retrixsolutions.islandbank.handlers.XMaterial;
import pw.retrixsolutions.islandbank.objects.BankPerm;

public class BankPermGUIEvents implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		if (!event.getInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&d&lIslandBank"))) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem() == null) {
			return;
		}
		ItemStack stack = event.getCurrentItem();
		if (!stack.hasItemMeta()) {
			return;
		}
		if (!stack.getItemMeta().hasLore()) {
			return;
		}
		List<String> lore = stack.getItemMeta().getLore();
		if (!StringUtils.hasHiddenString(lore.get(0))) {
			return;
		}
		String decoded = StringUtils.extractHiddenString(lore.get(0));
		if (decoded == null) {
			return;
		}
		Island island = IslandBank.getInstance().manager.getIslandForPlayer(player.getUniqueId());
		if (island == null) {
			return;
		}
		BankPerm current = IslandBank.getInstance().getIslandBankData().getBankPerm(island);
		BankPerm nBP;
		try {
			nBP = BankPerm.valueOf(decoded.toUpperCase()).getOpposite();
		} catch (IllegalArgumentException ex) {
			return;
		}
		if (current == null || nBP == null) {
			return;
		}
		if (current == nBP) {
			player.sendMessage(getMessage("modify.already-set"));
			return;
		} else {
			IslandBank.getInstance().getIslandBankData().setBankPerm(island, nBP);
			player.sendMessage(getMessage("modify.modified"));
			
			if (nBP.equals(BankPerm.OWNER)) {
				event.getInventory().setItem(13, getOwner());
			} else {
				event.getInventory().setItem(13, getAll());
			}
			return;
		}
	}
	
	private String getMessage(String location) {
		return ChatColor.translateAlternateColorCodes('&', IslandBank.getInstance().getConfig().getString("messages." + location));
	}

	@SuppressWarnings("deprecation")
	private ItemStack getOwner() {
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
	private ItemStack getAll() {
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
