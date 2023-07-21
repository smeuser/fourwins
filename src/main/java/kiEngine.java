public class kiEngine{

	/**
	 */
	private Feld[][] sfeld;
	private int moveQuality;
	private int redScore;
	private int yellowScore;
	private int move;
	private int iPlayer;
	private int depth;
	private TreeNode tree;

	public kiEngine() {
//		temporarly unused
		super();
		
		this.sfeld = new Feld[8][8];
		moveQuality = 0;
		redScore = 0;
		yellowScore = 0;
		move = -1;
		iPlayer = 0;
		//tree = new TreeNode();
	}
	public kiEngine(kiEngine rhs){
		this.sfeld = new Feld[8][8];
		for(int i = 0; i < 8; i++){
			for(int o = 0; o < 8; o++){
				this.sfeld[i][o] = new Feld();
				this.sfeld[i][o].set(rhs.getFeld(i,o).get());
			}
		}
		moveQuality = rhs.getMoveQuality();
		redScore = rhs.getRedScore();
		yellowScore = rhs.getYellowScore();
		move = rhs.getMove();
		iPlayer = rhs.getPlayer();
		tree = new TreeNode(this);
	}
	public kiEngine(Game rhs) {
		super();
		this.sfeld = new Feld[8][8];
		for(int i = 0; i < 8; i++){
			for(int o = 0; o < 8; o++){
				this.sfeld[i][o] = new Feld();
				this.sfeld[i][o].set(rhs.getSet().getFeld(i,o).get());
			}
		}
		moveQuality = 0;
		redScore = 0;
		yellowScore = 0;
		move = -1;
		iPlayer = rhs.getSetting().getMove();
		depth = rhs.getSetting().getKiLevel();
		this.swapPlayer();
		evalQuality();
		//	tree = new TreeNode(this);
	}
	public void evalQuality(){
		int hpointer = 0;
		int vpointer = 7;
		
		checkFunc.setFeld(sfeld);
		
		while(vpointer >= 0){
			if(sfeld[vpointer][hpointer].get() != -1){
				switch(sfeld[vpointer][hpointer].get()){
					case 0:		redScore+=checkFunc.evalStone(vpointer,hpointer,0);
								break;
					case 1:		yellowScore+=checkFunc.evalStone(vpointer,hpointer,1);
								break;
					default:	break;
				}
			}
			hpointer++;
			if(hpointer > 7){
				hpointer = 0;
				vpointer--;
			}
		}
		moveQuality = evalMoveQuality();
	}
	public kiEngine setVirtualStone(int hpointer, int intdepth){
		int vpointer = 7;
		
		while(sfeld[vpointer][hpointer].get() != -1){
			vpointer--;
			if(vpointer<0){
				return null;
			}
		}
		swapPlayer();
		sfeld[vpointer][hpointer].set(iPlayer);
		evalQuality();
		this.move = hpointer;
		this.depth = intdepth;
		
		return this;
	}
	private int evalMoveQuality(){
		return redScore-yellowScore;
	}
	public int getMoveQuality(){
		return this.moveQuality;
	}
	public boolean setEngine(Spielfeld rhs) {
		for(int i = 0; i < 8; i++){
			for(int o = 0; o < 8; o++){
				this.sfeld[i][o].set(rhs.getFeld(i,o).get());
			}
		}
		return true;
	}
	private Feld getFeld(int v, int h){
		return sfeld[v][h];
	}
	public int getMove(){
		return this.move;
	}
	private int getRedScore(){
		return this.redScore;
	}
	private int getYellowScore(){
		return this.yellowScore;
	}
	private int getPlayer(){
		return this.iPlayer;
	}
	private void swapPlayer(){
		if(iPlayer==0){
			iPlayer = 1;
		}
		else{
			iPlayer = 0;
		}
	}
	public void setDepth(int rhs){
		depth = rhs;
	}
	public int getDepth(){
		return depth;
	}
	public void clearScore(){
		redScore = 0;
		yellowScore = 0;
		moveQuality = 0;
	}
	public TreeNode getTreeNode(){
		return tree;
	}
}
