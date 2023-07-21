import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

public class ClientReadChannel implements Runnable {

	private ObjectInputStream ois;
	private GameClient client;
	private Thread runner;
	private boolean running;
		
	public ClientReadChannel(GameClient rhs) {
		super();
		client = rhs;
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
				client.setICG(temp);
			} catch (IOException e) {
				System.out.println("Verbindung zum Host ging verloren");
				JOptionPane.showMessageDialog(null,
						"Die Verbindung zum Host-Rechner ging verloren,\ndas Spiel wird abgebrochen",
						"Verbindungsfehler",
						JOptionPane.QUESTION_MESSAGE);
				client.getEngine().removeGame(client.getEngine().findGame(client.getGame()));
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
