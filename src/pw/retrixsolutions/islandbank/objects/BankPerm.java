package pw.retrixsolutions.islandbank.objects;

public enum BankPerm {
	
	ALL("ALL", "OWNER"),
	OWNER("OWNER", "ALL");
	
	private String n;
	private String o;

	private BankPerm(String name, String o) {
		this.n = name;
		this.o = o;
	}
	
	public String getPermName() {
		return n;
	}
	
	public BankPerm getOpposite() {
		return BankPerm.valueOf(o);
	}

}
