import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class viewFeld extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Feld feld;
	private ImageIcon pic;
	private static Vector instances = new Vector();
	
	viewFeld(){
		feld = new Feld();
		this.setIcon(setImage());
		instances.add(this);
	}
	viewFeld(Feld rhs){
		feld = rhs;
		this.setIcon(setImage());
		instances.add(this);
	}
	public boolean set(int rhs){
		feld.set(rhs);
		this.setIcon(setImage());
		return true;
	}
	public void clear(){
		feld = new Feld();
		this.setIcon(setImage());
	}
	private ImageIcon setImage(){
		switch(feld.get()){
		case -1:	pic = new ImageIcon("src/main/resources/media/feld.gif");
					break;
		case 0:		pic = new ImageIcon("src/main/resources/media/feld_rot.gif");
					break;
		case 1:		pic = new ImageIcon("src/main/resources/media/feld_gelb.gif");
					break;
		case 2:		pic = new ImageIcon("src/main/resources/media/stein_rot.gif");
					break;
		case 3:		pic = new ImageIcon("src/main/resources/media/stein_gelb.gif");
					break;
		default:	
		}
		
		return pic;
	}
	private void reDraw(){
		this.setIcon(setImage());
	}
	public static void reDrawAll(){
		viewFeld temp;
		for(int i=0; i<instances.size(); i++){
			temp = (viewFeld) instances.elementAt(i);
			temp.reDraw();
		}
	}
}
