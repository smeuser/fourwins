import java.io.Serializable;

public class Point implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	public Point() {
		super();
		this.x = -1;
		this.y = -1;
	}
	public Point(int vpointer, int hpointer) {
		this.y = vpointer;
		this.x = hpointer;
	}
	public void setX(int rhs){
		this.x = rhs;
	}
	public void setY(int rhs){
		this.y = rhs;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
}
