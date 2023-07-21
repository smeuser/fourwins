import java.io.Serializable;
import java.util.Date;

public class SavedGame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date now;
	private Game game;
	
	public SavedGame(Game rhs) {
		super();
		game = rhs;
		now = new Date();
	}
	public SavedGame(SavedGame rhs){
		game = rhs.getGame();
		now = rhs.getDate();
	}
	public Date getDate() {
		return this.now;
	}
	public Game getGame() {
		return this.game;
	}
}
