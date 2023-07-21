import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

public class ServerReadChannel implements Runnable{
	
	private ObjectInputStream ois;
	private GameServer service;
	private Thread runner;
	private boolean running;
		
	public ServerReadChannel(GameServer rhs) {
		super();
		service = rhs;
		ois = rhs.getObjectInputStream();
		running = true;
		runner = null;
		if(runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}

	public void run() {
		while(running){
			try {
				Object temp = ois.readObject();
				service.setICG(temp);
			} catch (IOException e) {
				System.out.println("Verbindung zum Client ging verloren");
				JOptionPane.showMessageDialog(null,
						"Die Verbindung zum Client-Rechner ging verloren,\ndas Spiel wird abgebrochen",
						"Verbindungsfehler",
						JOptionPane.QUESTION_MESSAGE);
				service.getEngine().removeGame(service.getEngine().findGame(service.getGame()));
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Thread getRunner() {
		return runner;
	}

	public void setRunner(Thread runner) {
		this.runner = runner;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
