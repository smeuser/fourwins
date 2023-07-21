import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

class GameClient implements Runnable

{
	private Socket s = null;
	private String adresse; // Adresse des Servers z.B. ServerName, IP
	private int port;
	private	ObjectOutputStream oos ;
	private	ObjectInputStream ois ;
	private Object msg;
	private Object incoming;
	private boolean incomingMove;
	private Engine engine;
	private Game game;
	private JTextArea output;
	private ClientReadChannel crc;
	private boolean running;
	private Thread runner;

	public GameClient(Game rhs, JTextArea rhs1)
	{
		super ();
		engine = null;
		game = rhs;
		adresse = game.getSetting().getIP(1);
		port = game.getSetting().getPort();
		output = rhs1;
		msg = game.getSetting();
		incoming = null;
		incomingMove = false;
		running = true;
		runner = null;
		this.activ();
		if(runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}
	public void setEngine(Engine rhs){
		engine = rhs;
	}
	public boolean isIncomingMove(){
		return incomingMove;
	}
	public void moveDone(){
		incomingMove = false;
	}
	public void activ()
	{
		try
		{
			s = new Socket(adresse, port);
			System.out.println(s.getInetAddress());
			System.out.println(s.getInetAddress().getHostName());
			System.out.println(s.getInetAddress().getHostAddress());

			//Kommunikationskanaele
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			crc = new ClientReadChannel(this);
			
		}
		catch (UnknownHostException e)
		{
			System.out.println("Der Host konnte nicht gefunden werden");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("IOException in GameClient create Socket: "+adresse+":"+port);
			e.printStackTrace();
		}
	}
	public void run() {
		while(running){
			try
			{
				if(incoming != null){
					if(incoming.getClass().getName() == "java.lang.String"){
						output.append(game.getSetting().getName(0)+": "+incoming+"\n");
						incoming = null;
					}
					else if(incoming.getClass().getName() == "GameSetting"){
						String orgTitle = game.getSetting().getName(0)+" vs ";
						GameSetting gshost = (GameSetting) incoming;
						game.importSetting(gshost);
						String newTitle = game.getSetting().getName(0)+" vs "+game.getSetting().getName(1);
						engine.getControl().setTitleAt(orgTitle, newTitle);
						game.setStatus("Verbindung hergestellt - "+game.getSetting().getName(0)+" beginnt das Spiel");
						System.out.println(gshost);
						incoming = null;
					}
					else if(incoming.getClass().getName() == "NetMove"){
						incomingMove = true;
						int spiel = engine.findGame(game);
						NetMove current = (NetMove)incoming;
						engine.insertStone(spiel, current.getMove());
						incoming = null;
					}
					else if(incoming.getClass().getName() == "ShutdownSignal"){
						this.shutdown();
					}
				}
				if(msg != null){
					if(msg.getClass().getName() == "java.lang.String"){
						oos.writeObject(msg);
						output.append(game.getSetting().getName(1)+": "+msg+"\n");
						msg = null;
					}
					else if(msg.getClass().getName() == "GameSetting"){
						oos.writeObject(msg);
						msg = null;
					}
					else if(msg.getClass().getName() == "NetMove"){
						oos.writeObject(msg);
						msg = null;
					}
					else if(msg.getClass().getName() == "ShutdownSignal"){
						oos.writeObject(msg);
						msg = null;
					}
				}	
			}
			catch (IOException ioe)
			{
				System.out.println("FEHLER beim Client IOException konnte nicht senden");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("GameClientThread is interrupted");
				e.printStackTrace();
			}
		}
		this.close();
	}

	public ObjectInputStream getObjectInputStream() {
		return ois;
	}
	public void setMSG(Object rhs){
		msg = rhs;
	}
	public void setICG(Object rhs) {
		incoming = rhs;
	}
	public void shutdown(){
		this.crc.setRunning(false);
		this.running = false;
	}
	public void close(){
		try {
			oos.close();
			ois.close();
			s.close();
		} catch (IOException e) {
			System.out.println("shutdown not clean - GameClient");
			e.printStackTrace();
		}
	}
	public Thread getRunner(){
		return runner;
	}
	public Engine getEngine(){
		return engine;
	}
	public Game getGame() {
		return game;
	}
}
