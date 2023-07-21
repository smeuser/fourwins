
public class Start {
	private View output;
	private Control input;
	private Engine engine;
	
	public Start() {
		output = new View();
		engine = new Engine(output);
		input = new Control(engine);
		input.setView(output);
		engine.setInput(input);
	}
	public static void main(String[] args) {
		new Start();
	}
}
