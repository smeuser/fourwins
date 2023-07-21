import java.io.Serializable;

public class Feld implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stone;
	
	Feld(){
		stone = -1;
	}
	public boolean set(int rhs){
		stone = rhs;
		return true;
	}
	public void clear(){
		stone = -1;
	}
	public int get(){
		return stone;
	}
}
