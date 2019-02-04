package pw.retrixsolutions.islandbank.objects;

import java.util.Set;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.handlers.ConfigAccessor;

public class IslandBankData extends ConfigAccessor {

	public IslandBankData(String fileName) {
		super(fileName);
	}
	
	public void changeIslandID(Island island, String newID) {
		changeIslandID("island_" + island.getOwner().toString(), newID);
		return;
	}
	
	public void changeIslandID(String oldID, String newID) {
		double balance = getBankBalance(oldID);
		if (balance == Double.MAX_VALUE) {
			balance = 0.0;
		}
		BankPerm perm = getBankPerm(oldID);
		if (perm == null) {
			perm = BankPerm.ALL;
		}
		removeBank(oldID);
		createIslandData(newID, balance);
		setBankPerm(newID, perm);
		return;
	}

	public boolean removeBank(Island island) {
		return removeBank("island_" + island.getOwner().toString());
	}
	
	public boolean removeBank(String id) {
		if (!hasIslandData(id)) {
			return false;
		}
		getConfig().set("islands." + id, null);
		return saveConfig();
	}
	
	public String getFormattedBalance(Island island) throws NumberFormatException {
		return getFormattedBalance("island_" + island.getOwner().toString());
	}
	
	public String getFormattedBalance(String id) {
		if (!hasIslandData(id)) {
			return null;
		}
		return formatFully(Double.parseDouble(getConfig().getString("islands." + id + ".balance")));
	}

	private String formatFully(double n) {
		   if (n < 1000) return "" + n;
		    int exp = (int) (Math.log(n) / Math.log(1000));
		    return String.format("%.1f %c", n / Math.pow(1000, exp), "kMBTQE".charAt(exp-1));
	}

	public double getBankBalance(Island island) throws NumberFormatException {
		return getBankBalance("island_" + island.getOwner().toString());
	}
	
	public double getBankBalance(String id) throws NumberFormatException {
		if (!hasIslandData(id)) {
			return Double.MAX_VALUE;
		}
		return Double.parseDouble(getConfig().getString("islands." + id + ".balance"));
	}
	
	public boolean setBankBalance(String id, double amount) {
		if (!hasIslandData(id)) {
			return false;
		}
		getConfig().set("islands." + id +".balance", amount);
		return saveConfig();
	}
	
	public boolean setBankBalance(Island island, double amount) {
		return setBankBalance("island_" + island.getOwner().toString(), amount);
	}
	
	public boolean setBankPerm(Island island, BankPerm perm) {
		return setBankPerm("island_" + island.getOwner().toString(), perm);
	}
	
	public boolean setBankPerm(String id, BankPerm perm) {
		if (!hasIslandData(id)) {
			return false;
		}
		getConfig().set("islands." + id +".withdraw-deposit", perm.getPermName());
		return saveConfig();
	}
	
	
	public BankPerm getBankPerm(Island island) {
		return getBankPerm("island_" + island.getOwner().toString());
	}
	
	public BankPerm getBankPerm(String id) {
		if (!hasIslandData(id)) {
			return null;
		}
		BankPerm perm = BankPerm.valueOf(getConfig().getString("islands." + id + ".withdraw-deposit").toUpperCase());
		return perm;
	}
	
	public boolean createIslandData(String id, double amount) {
		getConfig().set("islands." + id + ".balance", amount);
		getConfig().set("islands." + id +".withdraw-deposit", BankPerm.ALL.getPermName());
		return saveConfig();
	}
	
	public boolean createIslandData(Island island, double amount) {
		String id = "island_" + island.getOwner().toString();
		return createIslandData(id, amount);
	}
	
	public boolean hasIslandData(Island island) {
		String id = "island_" + island.getOwner().toString();
		return hasIslandData(id);
	}
	
	public boolean hasIslandData(String id) {
		if (!getConfig().contains("islands")) {
			return false;
		}
		Set<String> islandData = getConfig().getConfigurationSection("islands").getKeys(false);
		return islandData.contains(id);
	}

}
