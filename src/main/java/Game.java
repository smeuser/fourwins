import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static transient final long serialVersionUID = 1L;
	private Spielfeld set;
	private transient JLabel status;
	private GameSetting act;
	private transient JTextField input;
	private transient JTextArea output;
	private transient JScrollPane scroll;
	private transient JButton close;
	private transient GameServer service;
	private transient GameClient client;
	
	public Game() {
		super();
		set = new Spielfeld();
		act = new GameSetting();
		status = new JLabel("status");
		status.setBorder(new EtchedBorder(1));
		input = new JTextField(30);
		input.setBorder(new EtchedBorder(1));
		output = new JTextArea(5,30);
		output.setBorder(new EtchedBorder(1));
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		close = new JButton(new ImageIcon("src/main/resources/media/decline.gif"));
		scroll = new JScrollPane(output,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		service = null;
		client = null;
	}
	public Game(Game rhs) {
		//	Constructor for the FileSystem
		//	in case a game should be loaded
		super();
		set = new Spielfeld(rhs.getSet());
		act = new GameSetting(rhs.getSetting());
		status = new JLabel(act.getName(act.getMove())+" ist am Zug");
		status.setBorder(new EtchedBorder(1));
		input = new JTextField(30);
		input.setBorder(new EtchedBorder(1));
		output = new JTextArea(5,30);
		output.setBorder(new EtchedBorder(1));
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		close = new JButton(new ImageIcon("src/main/resources/media/decline.gif"));
		scroll = new JScrollPane(output,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		service = null;
		client = null;
	}
	public Game(GameSetting rhs){
		set = new Spielfeld();
		act = rhs;
		status = new JLabel(act.getName(0)+" beginnt das Spiel");
		status.setBorder(new EtchedBorder(1));
		input = new JTextField(30);
		input.setBorder(new EtchedBorder(1));
		output = new JTextArea(5,30);
		output.setBorder(new EtchedBorder(1));
		output.setWrapStyleWord(true);
		output.setLineWrap(true);
		close = new JButton(new ImageIcon("src/main/resources/media/decline.gif"));
		scroll = new JScrollPane(output,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if(act.getSpMode() == 2){
			if(act.getNetMode() == 0){
				service = new GameServer(this, output);
			}
			else if(act.getNetMode() == 1){
				client = new GameClient(this, output);
			}
		}
	}
	//	SpMode Entry
	public int setStone(int hpointer){
		int value = -1;
		if(act.getSpMode() == 0 && act.getKiColor() == act.getMove() &&
				act.getMoveCount() != act.getMoveCounter())
			return value;
		if(act.getSpMode() == 1 || act.getSpMode() == 0){
			int vpointer = 7;
			
			while(set.getFeld(vpointer,hpointer).get() != -1){
				vpointer--;
				if(vpointer<0)
					return -1;
			}
			
			set.getFeld(vpointer,hpointer).set(act.getMove());
			act.addMove(new Point(vpointer,hpointer), act.getMoveCount());
			status.setText(this.swapMove());
			value= this.check4Win();
			if(0 > value && act.getSpMode() == 0 && act.getKiColor() == act.getMove()){
				value = this.kiMove();
			}
		}
		else if(act.getSpMode() == 2){
			boolean incomingMove = false;
			switch(act.getNetMode()){
			case 0:	incomingMove = this.getService().isIncomingMove();
					break;
			case 1:	incomingMove = this.getClient().isIncomingMove();
					break;
			default:
			}
			if(act.getMove() == act.getNetMode() ||
					(act.getMove() != act.getNetMode() && incomingMove)){
				int vpointer = 7;
				
				while(set.getFeld(vpointer,hpointer).get() != -1){
					vpointer--;
					if(vpointer<0)
						return -1;
				}
				
				if(!incomingMove){
					switch(act.getNetMode()){
					case 0:	this.service.setMSG(new NetMove(hpointer));
							break;
					case 1:	this.client.setMSG(new NetMove(hpointer));
							break;
					default:
					}
				}
				
				set.getFeld(vpointer,hpointer).set(act.getMove());
				act.addMove(new Point(vpointer,hpointer), act.getMoveCount());
				status.setText(this.swapMove());
				if(incomingMove){
					switch(act.getNetMode()){
					case 0:	this.getService().moveDone();
							break;
					case 1:	this.getClient().moveDone();
							break;
					default:
					}
				}
				value= this.check4Win();
			}
			else
				return -1;
		}
		viewFeld.reDrawAll();
		return value;
	}
	public void kiStart(){
		this.kiMove();
	}
	private int kiMove(){
		HeadTreeNode head = new HeadTreeNode(this);
		return this.setStone(head.getBestMove(act.getKiColor()));
	}
	public int getHint(){
		HeadTreeNode head = new HeadTreeNode(this);
		return head.getBestMove(swap(act.getKiColor()));
	}
	private String swapMove(){
		return act.getName(act.swapMove())+" ist am Zug";
	}
	private int check4Win(){
		if(set.check4Four()){
			status.setText("Vier gewinnt!!!");
			act.decrementWinCondition();
			act.incrementScore(set.getFeld(set.getWin()[0].getY(),set.getWin()[0].getX()).get());
			set.setWinStones();
			viewFeld.reDrawAll();
			return this.check4WinCondition(0);
		}
		else if(set.check4Draw()){
			status.setText("Unentschieden!!!");
			act.decrementWinCondition();
			return this.check4WinCondition(1);
		}
		return -1;
	}
	public Spielfeld getSet(){
		return set;
	}
	public JLabel getStatus() {
		return status;
	}
	public void setStatus(String rhs) {
		status.setText(rhs);		
	}
	public GameSetting getSetting() {
		return act;
	}
	public JTextField getInput() {
		return input;
	}
	public JScrollPane getOutput(){
		return scroll;
	}
	public JButton getClose(){
		return close;
	}
	public void takeBackMove() {
		if(act.getMoveCounter() >= 0){
			Point temp = (Point)act.getMoveAt(act.getMoveCounter());
			set.getFeld(temp.getY(),temp.getX()).set(-1);
			act.swapMove();
			viewFeld.reDrawAll();
			act.decrementMoveCounter();
		}
		else
			System.out.println("Zug zurueck nicht moeglich");
	}
	public void doMove() {
		if(act.getMoveCounter() < act.getMoveCount()){
			act.incrementMoveCounter();
			Point temp = (Point)act.getMoveAt(act.getMoveCounter());
			set.getFeld(temp.getY(),temp.getX()).set(act.getMove());
			act.swapMove();
			viewFeld.reDrawAll();
		}
		else
			System.out.println("Weiter Vorgehen nicht moeglich");
	}
	private int check4WinCondition(int win){
		int returnvalue = -1;
		if(act.getWinCondition() > 0){
			if(win == 0){
				JOptionPane.showMessageDialog(null,
						"Vier Gewinnt!!!\nSpielstand "+act.getName(0)+": "+act.getScore(0)+" Spielstand "+act.getName(1)+": "+act.getScore(1)+"\nnoch auszutragende Spiele: "+act.getWinCondition(),
						"Spielstand",
						JOptionPane.QUESTION_MESSAGE);
				returnvalue = 0;
			}
			else if(win == 1){
				JOptionPane.showMessageDialog(null,
						"Unentschieden!!!\nSpielstand "+act.getName(0)+": "+act.getScore(0)+" Spielstand "+act.getName(1)+": "+act.getScore(1)+"\nnoch auszutragende Spiele: "+act.getWinCondition(),
						"Spielstand",
						JOptionPane.QUESTION_MESSAGE);
				returnvalue = 0;
			}
		}
		else{
			int winner = act.getScore(0)-act.getScore(1);
			String output;
			if(winner < 0)
				output = "Vier Gewinnt!!!\nSpielstand "+act.getName(0)+": "+act.getScore(0)+" Spielstand "+act.getName(1)+": "+act.getScore(1)+"\nSpieler: "+act.getName(1)+" hat die Partie fuer sich entschieden";
			else if(winner > 0)
				output = "Vier Gewinnt!!!\nSpielstand "+act.getName(0)+": "+act.getScore(0)+" Spielstand "+act.getName(1)+": "+act.getScore(1)+"\nSpieler: "+act.getName(0)+" hat die Partie fuer sich entschieden";
			else
				output = "Unentschieden!!!\nSpielstand "+act.getName(0)+": "+act.getScore(0)+" Spielstand "+act.getName(1)+": "+act.getScore(1)+"\nkeiner der Spieler konnte eine Entscheidung erringen";
			JOptionPane.showMessageDialog(null,
					output,
					"Spielstand",
					JOptionPane.QUESTION_MESSAGE);
			
			returnvalue = 1;
		}
		
		return returnvalue;
	}
	public void clearSpielfeld() {
		set.clearSpielfeld();
		act.setMove((act.getWinCondition()+1) % 2);
	}
	private int swap(int rhs){
		int value = 0;
		switch(rhs){
		case 0:	value = 1;
				break;
		case 1:	value = 0;
				break;
		default:
		}
		return value;
	}
	public GameServer getService(){
		return service;
	}
	public GameClient getClient(){
		return client;
	}
	public void initServer(Engine rhs){
		service = new GameServer(this, this.output);
		service.setEngine(rhs);
	}
	public void initClient(Engine rhs){
		client = new GameClient(this, this.output);
		client.setEngine(rhs);
	}
	public void importSetting(GameSetting rhs) {
		this.getSetting().setNetWinCondition(rhs.getWinCondition());
		this.getSetting().setName(0,rhs.getName(0));
		this.getSetting().setName(1,rhs.getName(1));
	}
}
