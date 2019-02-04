package pw.retrixsolutions.islandbank.objects;

import com.wasteofplastic.askyblock.Island;

import pw.retrixsolutions.islandbank.IslandBank;

public class Bank {
	
	private Island i;
	private double b;

	public Bank(Island island, double balance) {
		this.i = island;
		setBankBalance(balance);
	}
	
	public Island getIsland() {
		check();
		return i;
	}
	
	public double getBankBalance() {
		check();
		return b;
	}
	
	public void setBankBalance(double amount) {
		this.b = amount;
		IslandBank.getInstance().getIslandBankData().setBankBalance(i, b);
		check();
	}
	
	private void check() {
		if (!IslandBank.getInstance().getIslandBankData().hasIslandData(i)) {
			IslandBank.getInstance().getIslandBankData().createIslandData(i, b);
		}
	}

	public void increaseBankBalance(double amount) {
		this.b += amount;
		IslandBank.getInstance().getIslandBankData().setBankBalance(i, b);
		check();
	}
	
	public void decreaseBankBalance(double amount) {
		this.b -= amount;
		IslandBank.getInstance().getIslandBankData().setBankBalance(i, b);
		check();
	}

}
