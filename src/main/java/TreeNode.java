import java.util.Vector;

public class TreeNode {

	private Vector appTree;
	private kiEngine act;
	private int depth;
	
	public TreeNode() {
		//	temporarly unused
		super();
		appTree = new Vector(8);
		act = new kiEngine();
		//appTree.add(new kiEngine());
	}
	public TreeNode(Game rhs){
		appTree = new Vector(8);
		act = new kiEngine(rhs);
		depth = act.getDepth();
		if(depth > 0){			
			for(int i = 0; i < 8; i++){
				act.clearScore();
				addNode(new kiEngine(act).setVirtualStone(i,depth-1));
			}
		}
	}
	public TreeNode(kiEngine rhs){
		appTree = new Vector();
		if(rhs != null)
			act = rhs;
		else{
			act = null;
			return;
		}
		depth = rhs.getDepth();
		if(depth > 0){			
			for(int i = 0; i < 8; i++){
				act.clearScore();
				addNode(new kiEngine(act).setVirtualStone(i,depth-1));
			}
		}
	}
	public TreeNode getNode(int index){
		if(appTree.size()-1 < index){
			return null;
		}
		else
			return (TreeNode)appTree.elementAt(index);
	}	
	public int addNode(kiEngine rhs){
		if(appTree.add(new TreeNode(rhs))){
			return appTree.size();
		}
		else
			return -1;
	}
	private int sumMoves(int [] moves){
		int move = 0;
		for(int i=0; i<moves.length; i++){
			move += moves[i];
		}
		return move;
	}
	private boolean isTreeEmpty(){
		boolean value = true;
		for(int i = 0; i < appTree.size(); i++){
			if(appTree.elementAt(i) != null){
				value = false;
				break;
			}
		}
		return value;
	}
	public MoveNode getBestMove(int player) {
		if(this.isTreeEmpty()){
			if(act == null)
				return new MoveNode(-1,0,-1);
			return new MoveNode(act.getMove(), act.getMoveQuality(), act.getDepth());
		}
		else{
			MoveNode[] values = new MoveNode[8];
			for(int i = 0; i < 8; i++){
				values[i] = ((TreeNode)appTree.get(i)).getBestMove(player);
			}
			
			int [] nodeMoves = new int[8];
			for(int i = 0; i < 8; i++){
				nodeMoves[i] = values[i].getQuality();
			}
			
			nodeMoves = this.fillEmptyNodes(nodeMoves);
			int bestNodeMove = getValue(player, nodeMoves);
			
			return new MoveNode(bestNodeMove, sumMoves(nodeMoves),act.getDepth());
		}
	}
	private int [] fillEmptyNodes(int [] rhs){
		int count = 0;
		for(int i = 0; i < 8; i++){
			if(rhs[i] == 0)
				count++;
		}
		
		int value = sumMoves(rhs)/(8-count);
		
		for(int i = 0; i < 8; i++){
			if(rhs[i] == 0)
				rhs[i] = value;
		}
		
		return rhs;
	}
	private int getBestValue(int [] rhs){
		int returnValue = 0;
		for(int i=1; i<rhs.length; i++){
			if(rhs[i] > rhs[returnValue])
				returnValue = i;
		}
		
		return returnValue;
	}
	private int getWorstValue(int [] rhs){
		int returnValue = 0;
		for(int i=1; i<rhs.length; i++){
			if(rhs[i] < rhs[returnValue])
				returnValue = i;
		}
		
		return returnValue;
	}
	private int getValue(int player, int [] rhs){
		int returnValue = -1;
		switch(player){
		case 0:	returnValue = getBestValue(rhs);
				break;
		case 1:	returnValue = getWorstValue(rhs);
				break;
		default:
		}
		return returnValue;
	}
}
