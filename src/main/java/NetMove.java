import java.io.Serializable;

public class NetMove implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int move;
	public NetMove(int rhs) {
		super();
		move = rhs;
	}
	public int getMove(){
		return this.move;
	}
	public void setMove(int rhs){
		this.move = rhs;
	}
}
