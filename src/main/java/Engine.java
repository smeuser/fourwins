import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Engine {

	private View output;
	private Control input;
	private Vector game;
	
	public Engine() {
		super();
	}
	public Engine(View rhs){
		super();
		this.output = rhs;
		this.game = new Vector();
	}
	public void setInput(Control rhs){
		this.input = rhs;
		output.getPane().addTab("SplashScreen", new JPanel().add(new JLabel(new ImageIcon("src/main/resources/media/cover.jpg"))));
	}
	public void insertStone(int spiel, int row) {
		Game temp = (Game)game.get(spiel);
		int value = temp.setStone(row);
		
		switch(value){
		case -1:	setButtonIcons(spiel);
					return;
		case 0:		temp.clearSpielfeld();
					temp.setStatus(temp.getSetting().getName(temp.getSetting().getMove())+" beginnt das Spiel");
					if(temp.getSetting().getSpMode() == 0 && temp.getSetting().getKiColor() == temp.getSetting().getMove())
						temp.kiStart();
					setButtonIcons(spiel);
					break;
		case 1:		this.removeGame(spiel);
					break;
		default:
		}
	}
	public void newGame(){
		game.add(new Game());
		Game temp = (Game)game.lastElement();
		input.addGameSetup(temp.getSetting());
	}
	public void newGame(GameSetting rhs) {
		Game temp = (Game)game.lastElement();
		input.addGame(temp);		
	}
	public boolean newGame(Game rhs) {
		//	Constructor for loaded games
		if(rhs == null)
			return false;
		game.add(rhs);
		Game temp = (Game)game.lastElement();
		input.addGame(temp);
		
		switch(temp.getSetting().getWinCondition() % 2){
		case 1:	temp.getStatus().setText(temp.getSetting().getName(swap(temp.getSetting().getMove()))+" ist am Zug");
				break;
		case 0:	temp.getStatus().setText(temp.getSetting().getName(temp.getSetting().getMove())+" ist am Zug");
				break;
		default:
		}
		setButtonIcons(game.size()-1);
		
		return true;
	}
	private int swap(int rhs) {
		int value = 0;
		if(rhs == 0)
			value = 1;
		else if(rhs == 1)
			value = 0;
		return value;
	}
	public void cancelGame(GameSetting rhs){
		Game temp;
		for(int i = 0; i< game.size(); i++){
			temp = (Game)game.elementAt(i);
			if(rhs == temp.getSetting()){
				game.remove(i);
				break;
			}
		}
		
		if(output.getPane().getTabCount() == 0)
			output.getPane().addTab("SplashScreen", new JPanel().add(new JLabel(new ImageIcon("src/main/resources/media/cover.jpg"))));	
		
		System.out.println("gameVectorSize: "+game.size());
	}
	public void takeBackMove(int spiel){
		Game temp = (Game)game.get(spiel);
		temp.takeBackMove();
	}
	public void doMove(int spiel) {
		Game temp = (Game)game.get(spiel);
		temp.doMove();
		System.out.println("takeBack rueckgaengig machen");
	}
	public void giveHint(int i) {
		System.out.println("Zugvorschlag Spalte: "+((Game)this.game.elementAt(i)).getHint());
	}
	public void removeGame(int rhs){
		Game temp = ((Game)game.get(rhs));
		if(temp.getSetting().getSpMode() == 2){
			if(temp.getSetting().getNetMode() == 0){
				temp.getService().shutdown();
			}
			if(temp.getSetting().getNetMode() == 1){
				temp.getClient().shutdown();
			}
		}
		output.getPane().remove(rhs);
		input.removeButtons(rhs);
		game.remove(rhs);
		
		if(output.getPane().getTabCount() == 0)
			output.getPane().addTab("SplashScreen", new JPanel().add(new JLabel(new ImageIcon("src/main/resources/media/cover.jpg"))));	
	}
	public void setButtonIcons(int rhs){
		JButton [] temp = input.getButtons(rhs);
		String icon = "";
		String rollIcon = "";
		int move =	((Game)game.elementAt(rhs)).getSetting().getMove();
		if(((Game)game.elementAt(rhs)).getSetting().getSpMode() == 2){
			if(((Game)game.elementAt(rhs)).getSetting().getNetMode() != move)
				move = 3;
		}
			
		switch(move){
		case 0:	icon = "src/main/resources/media/redDown.gif";
				rollIcon = "src/main/resources/media/redDownAnim.gif";
				break;
		case 1:	icon = "src/main/resources/media/yellowDown.gif";
				rollIcon = "src/main/resources/media/yellowDownAnim.gif";
				break;
		case 3:	icon = "src/main/resources/media/disabled.gif";
				rollIcon = "src/main/resources/media/disabled.gif";
				break;
		default:
		}
		
		for(int i = 0; i < 8; i++){
			temp[i].setIcon(new ImageIcon(icon));
			temp[i].setRolloverIcon(new ImageIcon(rollIcon));
		}
	}
	public Game getGame(int spiel) {
		return (Game)game.elementAt(spiel);
	}
	public int findGame(Game rhs){
		return game.indexOf(rhs);
	}
	public Control getControl(){
		return input;
	}
	public View getView() {
		return output;
	}
	public Control getInput() {
		return input;
	}
}
