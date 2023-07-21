
public class HeadTreeNode {

	private TreeNode tree;
	
	public HeadTreeNode() {
		super();
		tree = new TreeNode();
	}
	public HeadTreeNode(Game rhs){
		tree = new TreeNode(rhs);
	}
	public int getBestMove(int player) {
		MoveNode best = tree.getBestMove(player);
		return best.getMove();
	}
}