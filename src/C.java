
public class C extends Stroj implements StrojInterface {

	// podlozky
	// moze byt iba jeden!!!
	
	public static final String TYP = "C";
	
	public final static int ENERGETICKA_NAROCNOST = 2;
	
	public final static int KAPACITA = 150;
	
	public C(int id) {
		super(id, ENERGETICKA_NAROCNOST, KAPACITA);
	}

	@Override
	public void vyrobSuciastku() {
	}
	
	@Override
	public String getTyp() {
		return C.TYP;
	}
}
