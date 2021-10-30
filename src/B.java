
public class B extends Stroj implements StrojInterface {

	// podlozky + sroby
	
	public static final String TYP = "B";
	
	public final static int ENERGETICKA_NAROCNOST = 3;
	
	public final static int KAPACITA = 50;
	
	public B(int id) {
		super(id, ENERGETICKA_NAROCNOST, KAPACITA);
	}

	@Override
	public void vyrobSuciastku() {
	}
	
	@Override
	public String getTyp() {
		return B.TYP;
	}
}
