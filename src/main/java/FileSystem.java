import java.io.*;
import java.util.Vector;
public class FileSystem {
	private static Vector gameList;
	private static String GameListFilename = "gameList.dat";
	private static String SavedGamePath = "src/main/resources/savedGames/";

	public static void initGameList(){
		gameList = new Vector();
		FileSystem.loadGameList();
	}
	public static boolean saveGame(Game rhs){
		if(FileSystem.saveSavedGame(new SavedGame(rhs)))
			return true;
		else
			return false;
	}
	public static Game loadGame(int rhs){
		return new Game(FileSystem.loadSavedGame(rhs).getGame());
	}
	public static String[] getGameList(){
		String [] value = new String[gameList.size()];
		
		for(int i=0; i<gameList.size(); i++){
			value[i] = FileSystem.getGameInfo(FileSystem.loadSavedGame(i));
		}
		
		return value;
	}
	private static boolean saveSavedGame(SavedGame rhs){
		String fileName = SavedGamePath+"GameSave_Slot"+gameList.size();
		try {
			FileOutputStream fo = new FileOutputStream(fileName);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(rhs);
			oo.close();
			gameList.add(fileName);
			FileSystem.saveGameList(new GameListFile(gameList));
			System.out.println("Gamesave saved successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private static SavedGame loadSavedGame(int rhs){
		String fileName = SavedGamePath+"GameSave_Slot"+rhs;
		SavedGame value = null;
		try{
			FileInputStream fi = new FileInputStream(fileName);
			ObjectInputStream oi = new ObjectInputStream(fi);
			value = (SavedGame)oi.readObject();
			oi.close();
			System.out.println("Gamesave loaded successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ReadError in FileSystem.loadSavedGame");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	private static boolean loadGameList() {
		try{
			FileInputStream fi = new FileInputStream(SavedGamePath+GameListFilename);
			ObjectInputStream oi = new ObjectInputStream(fi);
			GameListFile value = new GameListFile();
			value = (GameListFile)oi.readObject();
			gameList = value.getContent();
			oi.close();
			System.out.println("GameListFile loaded successfully");
			return true;
		} catch (FileNotFoundException e) {
			GameListFile temp = new GameListFile();
			if(FileSystem.saveGameList(temp)){
				gameList = temp.getContent();
				System.out.println("GameListFile not found");
				System.out.println("created empty GameListFile");
				return true;
			}
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	private static boolean saveGameList(GameListFile rhs) {
		try {
			FileOutputStream fo = new FileOutputStream(SavedGamePath+GameListFilename);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(rhs);
			oo.close();
			System.out.println("GameListFile saved successfully");
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("GameListFile to save not found");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("IOException while saving GameListFile");
			e.printStackTrace();
			return false;
		}
	}
	public static void removeGame(int rhs){
		gameList.remove(rhs);
		FileSystem.saveGameList(new GameListFile(gameList));
	}
	private static String getGameInfo(SavedGame rhs){
		String value = "";
		if(rhs == null)
			return "empty Slot                            ";
		
		value += rhs.getDate().toString()+" - ";
		value += rhs.getGame().getSetting().getName(0)+" vs ";
		value += rhs.getGame().getSetting().getName(1)+" - ";
		value += rhs.getGame().getSetting().getScore(0)+" zu ";
		value += rhs.getGame().getSetting().getScore(1)+" von ";
		value += (rhs.getGame().getSetting().getWinCondition()+1)+" Spielen";
		
		return value;
	}
	public static int getGameListSize() {
		return gameList.size();
	}
}
