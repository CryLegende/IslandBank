package pw.retrixsolutions.islandbank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import net.milkbowl.vault.economy.Economy;
import pw.retrixsolutions.islandbank.commands.IslandBankCommand;
import pw.retrixsolutions.islandbank.commands.admin.Info;
import pw.retrixsolutions.islandbank.commands.admin.Reset;
import pw.retrixsolutions.islandbank.commands.admin.Version;
import pw.retrixsolutions.islandbank.commands.user.Deposit;
import pw.retrixsolutions.islandbank.commands.user.Modify;
import pw.retrixsolutions.islandbank.commands.user.View;
import pw.retrixsolutions.islandbank.commands.user.Withdraw;
import pw.retrixsolutions.islandbank.events.BankPermGUIEvents;
import pw.retrixsolutions.islandbank.events.IslandEvents;
import pw.retrixsolutions.islandbank.handlers.IslandBankManager;
import pw.retrixsolutions.islandbank.handlers.VaultHandler;
import pw.retrixsolutions.islandbank.objects.Bank;
import pw.retrixsolutions.islandbank.objects.BankCommand;
import pw.retrixsolutions.islandbank.objects.IslandBankData;
import pw.retrixsolutions.islandbank.placeholders.MVdWPlaceholderAPI;
import pw.retrixsolutions.islandbank.placeholders.PlaceholderAPI;

public class IslandBank extends JavaPlugin {
	
	public static Economy eco;
	
	public boolean thirteenPlus = false;
	
	private IslandBankData data = null;
	public IslandBankManager manager = null;
	public HashMap<BankCommand, List<String>> subCommands = new HashMap<BankCommand, List<String>>();

	private static IslandBank instance;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		instance=this;
		
		if (Bukkit.getPluginManager().getPlugin("ASkyBlock") == null || !Bukkit.getPluginManager().isPluginEnabled("ASkyBlock")) {
			Bukkit.getConsoleSender().sendMessage("[IslandBank] §cASkyBlock is required for this plugin to function!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		 if (!setupEconomy()) {
			 	Bukkit.getConsoleSender().sendMessage("[IslandBank] §cVault is required for this plugin to function!");
	            Bukkit.getPluginManager().disablePlugin(this);
	            return;
	        }
		
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			new MVdWPlaceholderAPI();
		}
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PlaceholderAPI();
		}
		
		manager = new IslandBankManager();
		data = new IslandBankData("islandbanks.yml");
		
		subCommands.put(new Withdraw(), Arrays.asList("withdraw", "w", "wd", "take", "remove"));
		subCommands.put(new Deposit(), Arrays.asList("deposit", "d", "dp", "give", "add", "put"));
		subCommands.put(new Modify(), Arrays.asList("modify", "edit", "change", "m", "bankperm", "bp", "perm", "permissions", "perms", "permission"));
		subCommands.put(new View(), Arrays.asList("see", "view", "look", "inspect", "show", "bal", "balance", "info", "b", "v"));
		subCommands.put(new Info(), Arrays.asList("info", "data", "inspect", "i"));
		subCommands.put(new Reset(), Arrays.asList("reset", "delete", "hard-reset", "remove"));
		subCommands.put(new Version(), Arrays.asList("version", "v"));
		
		getCommand("IB").setExecutor(new IslandBankCommand());
		
		new IslandBankAPI();
		
		String a = getServer().getClass().getPackage().getName();
		String version = a.substring(a.lastIndexOf('.') + 1);
		if (version.startsWith("v1.13")) {
			this.thirteenPlus=true;
		} else {
			this.thirteenPlus=false;
		}
		
		Bukkit.getPluginManager().registerEvents(new BankPermGUIEvents(), this);
		Bukkit.getPluginManager().registerEvents(new IslandEvents(), this);
	}
	
	public void onDisable() {
		try {
			if (!(manager.banks.size() == 0) && !manager.banks.isEmpty()) {
				for (int i = 0; i < manager.banks.size(); i++) {
					Bank b = manager.banks.get(0);
					manager.unloadBank(b);
					manager.banks.remove(0);
				}
			}
		} catch (IndexOutOfBoundsException ex) {
			Bukkit.getConsoleSender().sendMessage("[IslandBank] Unloaded all banks.");
		}
	}
	
	private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> economyProvider = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class<Economy>)Economy.class);
        if (economyProvider == null) {
            return false;
        }
        eco = economyProvider.getProvider();
        return eco != null;
    }
	
	public ASkyBlockAPI getIslandAPI() {
		return ASkyBlockAPI.getInstance();
	}
	
	public static IslandBank getInstance() {
		return instance;
	}
	
	public IslandBankData getIslandBankData() {
		return data;
	}
	
	public VaultHandler getVaultHandler() {
		return new VaultHandler();
	}
	
}