
public class setupHelpText {
		
	public static String spMode(){
		String lhs = "KI Spiel:\nspielen Sie eine Partie gegen den Computer, waehlen sie aus 3 verschiedenen SchwierigkeitsStufen\n";
		lhs += "\nHotseat:\nspielen Sie eine Partie gegen einen menschlichen Spieler am gleichen Computer.\n";
		lhs += "\nNetzwerk:\nspielen Sie eine Partie gegen einen menschlichen Spieler ueber das Netzwerk.\n";
		return lhs;
	}
	public static String red(int rhs){
		String lhs = "Spielername Rot:\ntragen Sie hier den Spielernamen des beginnenden Spielers ein.\n";
		String lhs1 = "Spielername:\ntragen Sie hier Ihren Namen ein.\n";
		
		if(rhs == 0 || rhs == 2)
			lhs = lhs1;
		
		return lhs;
	}
	public static String yellow(){
		String lhs = "Spielername Gelb:\ntragen Sie hier den Spielernamen des 2.Spielers ein.\n";
		return lhs;
	}
	public static String winCondition(){
		String lhs = "Siegbedingungen:\nlegen Sie die Bedingungen fest um eine Partie zu entscheiden.";
		lhs += "\nEs wird nach dem Best of n System gespielt, das heisst wer mehr Spiele von der mit n angegebenen Zahl an Spielen gewinnt, entscheidet die komplette Partie fuer sich.\n";
		return lhs;
	}
	public static String kiLevel(){
		String lhs = "KI Stufe:\nwaehlen Sie den Schwierigkeitsgrad des Computerspielers.\n";
		return lhs;
	}
	public static String netMode(){
		String lhs = "Netzmodus:\n\n";
		lhs += "Host -> Sie leiten das Spiel, geben Sie ihrem Mitspieler ihre IP-Adresse, damit er sich verbinden kann.\n";
		lhs += "\nClient -> Sie schliessen sich einem Spiel an, erfragen Sie die IP-Adresse des Spielleiters und tragen sie als Host-IP ein.\n";
		return lhs;
	}
	public static String hostIP(){
		String lhs = "Host-IP:\ntragen Sie hier die IP-Adresse des Spielleiters ein mit dem Sie sich verbinden m�chten.\n";
		return lhs;
	}
	public static String netPort(){
		String lhs = "Port:\nals Host bestimmen sie hier ueber welchen Port das Netzwerkspiel geleitet wird. Host und Client muessen den gleichen Port angeben. Gueltige Werte sind eine Zahl von 1025 - 65500.\n";
		return lhs;
	}
}
