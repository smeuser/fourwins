import java.io.Serializable;
import java.util.Vector;

public class GameListFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector gameList;
	
	public GameListFile() {
		gameList = new Vector();
	}
	public GameListFile(Vector rhs){
		gameList = new Vector();
		gameList = rhs;
	}
	public int addGame(String rhs){
		gameList.add(rhs);
		return gameList.size();
	}
	public Vector getContent() {
		return this.gameList;
	}
}
