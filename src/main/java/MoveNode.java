
public class MoveNode {
	private int move;
	private int quality;
	private int depth;
	
	public MoveNode() {
		super();
	}
	public MoveNode(int rhsmove, int rhsquality, int rhsdepth){
		this.move = rhsmove;
		this.quality = rhsquality;
		this.depth = rhsdepth;
	}
	public MoveNode(kiEngine rhs, int rhs1) {
		quality = rhs.getMoveQuality();
		move = rhs1;
		rhs.getDepth();
	}
	public void setMove(int rhs){
		move = rhs;
	}
	public int getMove(){
		return move;
	}
	public void setQuality(int rhs){
		quality = rhs;
	}
	public int getQuality(){
		return quality;
	}
	public int getDepth(){
		return depth;
	}
}
