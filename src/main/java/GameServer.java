import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

class GameServer implements Runnable
{
	private ServerSocket ss = null;
	private Socket s = null;
	private int port;
	private Object msg;
	private Object incoming;
	private boolean incomingMove;
	private Engine engine;
	private Game game;
	private JTextArea output;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private ServerReadChannel src;
	private boolean running;
	private Thread runner;
	
	public GameServer(Game rhs, JTextArea rhs1)
	{
		engine = null;
		game = rhs;
		output = rhs1;
		port = game.getSetting().getPort();
		msg = null;
		incoming = null;
		incomingMove = false;
		runner = null;
		running = true;
		try 
		{
			ss = new ServerSocket(port);
			if(runner == null){
				runner = new Thread(this);
				runner.start();
			}
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,
					"Der Port: "+port+" wird schon benutzt,\nbitte waehlen Sie einen anderen Port als Host",
					"GameServer nicht gestartet",
					JOptionPane.WARNING_MESSAGE);
			engine.removeGame(engine.findGame(game));
			System.out.println("java.net.BindException der Port ist schon belegt");
			e.printStackTrace();
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
	public void run(){
		System.out.println("Warten auf Client");
		
		try 
		{
			s = ss.accept();
			System.out.println("Client-Verbindung hergestellt");
			System.out.println(s.getInetAddress());
			System.out.println(s.getInetAddress().getHostAddress());
			ois = new ObjectInputStream(s.getInputStream());
			src = new ServerReadChannel(this);
			oos = new ObjectOutputStream(s.getOutputStream());
		}
		catch (IOException e) 
		{
			System.out.println("IOException in GameServer create SocketConnection");
			e.printStackTrace();
		}

		while(running){
			try 
			{
				if(incoming != null){
					if(incoming.getClass().getName() == "java.lang.String"){
						output.append(game.getSetting().getName(1)+": "+incoming+"\n");
						incoming = null;
					}
					else if(incoming.getClass().getName() == "GameSetting"){
						String orgTitle = game.getSetting().getName(0)+" vs ";
						GameSetting gsclient = (GameSetting) incoming;
						game.getSetting().setName(1, gsclient.getName(0));
						String newTitle = game.getSetting().getName(0)+" vs "+game.getSetting().getName(1);
						engine.getControl().setTitleAt(orgTitle, newTitle);
						game.setStatus("Verbindung hergestellt - "+game.getSetting().getName(0)+" beginnt das Spiel");
						Control input = engine.getInput();
						JButton [] btn = input.getButtons(engine.findGame(game));
						for(int i=0; i<11; i++){
							btn[i].setEnabled(true);
						}
						msg = game.getSetting();
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
						output.append(game.getSetting().getName(0)+": "+msg+"\n");
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
			catch (IOException e) {
				System.out.println("FEHLER beim Server IOException konnte nicht senden");
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("GameServerThread is interrupted");
				e.printStackTrace();
			}
		}
		this.close();
	}
	public void setMSG(Object rhs){
		msg = rhs;
	}
	public void setICG(Object rhs){
		incoming = rhs;
	}
	public ObjectInputStream getObjectInputStream() {
		return ois;
	}
	public void shutdown(){
		if(this.src != null)
			this.src.setRunning(false);
		this.running = false;
	}
	public void close(){
		try {
			oos.close();
			ois.close();
			s.close();
			ss.close();
		} catch (IOException e) {
			System.out.println("shutdown not clean - GameServer");
			e.printStackTrace();
		}
	}
	public Engine getEngine(){
		return engine;
	}
	public Game getGame() {
		return game;
	}
}