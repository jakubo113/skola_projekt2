
public class Stroj {
	
	private int id;
	
	private int energetickaNarocnost;
	
	private int kapacita;
	
	private int produkcia;
	
	private boolean pokazeny;
	
	public Stroj() {}
	
	public Stroj (int id, int energetickaNarocnost, int kapacita) {
		this.id = id;
		this.energetickaNarocnost = energetickaNarocnost;
		this.kapacita = kapacita;
		this.produkcia = 0;
		this.setPokazeny(false);
	}
	
	public int getKapacita() {
		return kapacita;
	}
	
	public int getId() {
		return id;
	}
	
	public int getProdukcia() {
		return produkcia;
	}
	
	public void addProdukcia(int produkcia) {
		this.produkcia += produkcia;
	}

	public boolean isPokazeny() {
		return pokazeny;
	}

	public void setPokazeny(boolean pokazeny) {
		this.pokazeny = pokazeny;
	}
}
