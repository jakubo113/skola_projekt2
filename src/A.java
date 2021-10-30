
public class A extends Stroj implements StrojInterface {

	// podlozky
	
	public static final String TYP = "A";
	
	public final static int ENERGETICKA_NAROCNOST = 4;
	
	public final static int KAPACITA = 70;
	
	public A(int id) {
		super(id, ENERGETICKA_NAROCNOST, KAPACITA);
	}

	@Override
	public void vyrobSuciastku() {
	}
	
	@Override
	public String getTyp() {
		return A.TYP;
	}
}
