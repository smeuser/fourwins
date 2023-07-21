import java.io.Serializable;
import java.util.Vector;
import javax.swing.JPanel;

public class GameSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient JPanel ref;
	private int counter;
	private int move;
	private Vector moves;
	private int moveCounter;
	private int spmode;
	private int redScore;
	private int yellowScore;
	private String red;
	private String yellow;
	private int kilevel;
	private int kiColor;
	private int netmode;
	private transient String ownIP;
	private transient String hostIP;
	private transient int netPort;
	private int winCondition;
	private static transient int count = 0;
	
	public GameSetting() {
		super();
		counter = count;
		move = 0;
		moves = new Vector();
		moveCounter = -1;
		spmode = 1;
		redScore = 0;
		yellowScore = 0;
		red = "Spieler 1";
		yellow = "Spieler 2";
		kilevel = 0;
		kiColor = 1;
		netmode = 0;
		ownIP = "192.168.1.1";
		hostIP = "localhost";
		netPort = 6969;
		winCondition = 0;
		count++;
	}
	public GameSetting(GameSetting rhs) {
		super();
		counter = count;
		move = rhs.getMove();
		moves = rhs.getMoves();
		moveCounter = rhs.getMoveCounter();
		spmode = rhs.getSpMode();
		redScore = rhs.getScore(0);
		yellowScore = rhs.getScore(1);
		red = rhs.getName(0);
		yellow = rhs.getName(1);
		kilevel = rhs.getKiLevel();
		kiColor = rhs.getKiColor();
		netmode = 0;
		ownIP = "192.168.1.1";
		hostIP = "192.168.0.1";
		netPort = 6969;
		winCondition = rhs.getWinCondition();
		count++;
	}
	private Vector getMoves() {
		return moves;
	}
	public void turn(){
		if(move == 0){
			move = 1;
		}
		else
			move = 0;
	}
	public int getMove(){
		return move;
	}
	public Object getMoveAt(int rhs){
		return moves.elementAt(rhs);
	}
	public int getSpMode(){
		return spmode;
	}
	public int getScore(int player){
		switch(player){
		case 0:	return redScore;
		case 1: return yellowScore;
		default: return -1;
		}
	}
	public String getName(int player){
		switch(player){
		case 0:	return red;
		case 1: return yellow;
		default: return "";
		}
	}
	public int getKiLevel(){
		return kilevel;
	}
	public int getNetMode(){
		return netmode;
	}
	public String getIP(int from){
		switch(from){
		case 0:	return ownIP;
		case 1:	return hostIP;
		default:return "";
		}
	}
	public int getPort(){
		return netPort;
	}
	public void addMove(Point rhs, int pos){
		if(pos == moveCounter){
			moveCounter++;
			moves.add(rhs);
		}
		else{
			moves.add(++moveCounter, rhs);
			while(moveCounter+1 < moves.size()){
				moves.removeElementAt(moves.size()-1);
			}
		}
	}
	public void setSpMode(int rhs){
		spmode = rhs;
	}
	public void setScore(int player, int rhs){
		switch(player){
		case 0:	redScore = rhs;
				break;
		case 1:	yellowScore = rhs;
				break;
		default:
		}
	}
	public void setName(int player, String rhs){
		switch(player){
		case 0:	red = rhs;
				break;
		case 1:	yellow = rhs;
				break;
		default:
		}
	}
	public void setKiLevel(int rhs){
		kilevel = rhs;
	}
	public void setNetMode(int rhs){
		netmode = rhs;
	}
	public void setIP(int w, String rhs){
		switch(w){
		case 0:	ownIP = rhs;
				break;
		case 1:	hostIP = rhs;
				break;
		default:
		}
	}
	public void setPort(int rhs){
		netPort = rhs;
	}
	public int getCount() {
		return counter;
	}
	public void setRef(JPanel rhs){
		ref = rhs;
	}
	public JPanel getRef(){
		return ref;
	}
	public void setWinCondition(int selectedIndex) {
		winCondition = selectedIndex*2+1;		
	}
	public void setNetWinCondition(int rhs){
		winCondition = rhs;
	}
	public void decrementWinCondition(){
		winCondition--;
	}
	public void incrementScore(int player){
		switch(player){
		case 0:	redScore++;
				break;
		case 1:	yellowScore++;
				break;
		default:
		}
	}
	public int getWinCondition(){
		return winCondition;
	}
	public int swapMove() {
		switch(move){
		case 0:	move = 1;
				break;
		case 1:	move = 0;
				break;
		default:
		}
		
		return move;
	}
	public void swapPlayers(){
		String temp;
		temp = red;
		red = yellow;
		yellow = temp;
		int value;
		value = redScore;
		redScore = yellowScore;
		yellowScore = value;
	}
	public void swapKiColor(){
		switch(kiColor){
		case 0:	kiColor = 1;
				break;
		case 1:	kiColor = 0;
				break;
		default:
		}
	}
	public int getMoveCounter() {
		return moveCounter;
	}
	public void resetMoveCounter(){
		moveCounter = -1;
	}
	public void decrementMoveCounter() {
		moveCounter--;
	}
	public int getMoveCount() {
		return moves.size()-1;
	}
	public void incrementMoveCounter() {
		moveCounter++;
	}
	public int getKiColor() {
		return kiColor;
	}
	public void setMove(int rhs) {
		move = rhs;
	}
}
