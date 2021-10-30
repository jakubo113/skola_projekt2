import java.io.Serializable;

public interface StrojInterface extends Serializable {

	public void vyrobSuciastku();
	
	public int getKapacita();
	
	public int getId();
	
	public String getTyp();
	
	public boolean isPokazeny();

	public void setPokazeny(boolean pokazeny);
}
