import java.io.Serializable;

public class Spielfeld implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Feld[][] set;
	private transient Point[] winStones;
	
	public Spielfeld() {
		super();
		set = new Feld[8][8];
		for(int i=0; i<8; i++){
			for(int o=0; o<8; o++)
				set[o][i] = new Feld();
		}
		winStones = new Point[4];
		for(int i=0; i<winStones.length; i++){
			winStones[i] = new Point();
		}
	}
	public Spielfeld(Spielfeld rhs){
		set = new Feld[8][8];
		for(int i=0; i<8; i++){
			for(int o=0; o<8; o++){
				set[o][i] = new Feld();
				set[o][i].set(rhs.getFeld(o,i).get());
			}
		}
		winStones = new Point[4];
		for(int i=0; i<winStones.length; i++){
			winStones[i] = new Point();
		}
	}
	public void clearSpielfeld(){
		for(int i=0; i<8; i++){
			for(int o=0; o<8; o++)
				set[o][i].set(-1);
		}
		viewFeld.reDrawAll();
	}
	public Feld getFeld(int vpointer, int hpointer){
		return set[vpointer][hpointer];
	}
	public void setFeld(int vpointer, int hpointer, int rhs){
		set[vpointer][hpointer].set(rhs);
	}
	public Point[] getWin(){
		return winStones;
	}
	public boolean check4Four(){
		if(checkColumns() || checkRows() || checkDiagLR() || checkDiagRL())
			return true;
		
		return false;
	}
	private boolean checkColumns(){
		int counter;
		int actual;
		int last;
		
		for(int i = 0; i<8; i++){
			last = -1;
			actual = -1;
			counter = 0;
			
			for(int o=7; o>-1; o--){
				actual = this.set[o][i].get();
				if(last != -1 && actual != -1){
					if(last == actual)
						counter++;
					else{
						counter = 0;
						last = -1;
					}
				}
				else
					counter = 0;
				
				if(counter >= 3){
					winStones[0].setX(i);
					winStones[1].setX(i);
					winStones[2].setX(i);
					winStones[3].setX(i);
					for(int x=0; x<4; x++){
						winStones[x].setY(o+x);
					}
					
					return true;
				}
				else{
					last = actual;
				}
			}
		}
		
		return false;	
	}
	private boolean checkRows(){
		int counter;
		int actual;
		int last;
		
		for(int i = 7; i>-1; i--){
			last = -1;
			actual = -1;
			counter = 0;
			
			for(int o=0; o<8; o++){
				actual = this.set[i][o].get();
				if(last != -1 && actual != -1){
					if(last == actual)
						counter++;
					else{
						counter = 0;
						last = -1;
					}
				}
				else
					counter = 0;
				
				if(counter >= 3){
					winStones[0].setY(i);
					winStones[1].setY(i);
					winStones[2].setY(i);
					winStones[3].setY(i);
					for(int x=0; x<4; x++){
						winStones[x].setX(o-x);
					}
					return true;
				}
				else{
					last = actual;
				}
			}
		}
		
		return false;	
	}
	private boolean checkDiagLR(){
		for(int i = 0; i<8; i++){
			for(int o=0; o<8; o++){
				if(i+3 <8 && o+3<8){
					if(	this.set[o][i].get() ==
						this.set[o+1][i+1].get() &&
						this.set[o+1][i+1].get() ==
						this.set[o+2][i+2].get() &&
						this.set[o+2][i+2].get() ==
						this.set[o+3][i+3].get() &&
						this.set[o][i].get() != -1){
						
						winStones[0].setY(o);		winStones[0].setX(i);
						winStones[1].setY(o+1);	winStones[1].setX(i+1);
						winStones[2].setY(o+2);	winStones[2].setX(i+2);
						winStones[3].setY(o+3);	winStones[3].setX(i+3);
						
						return true;
					}
				}
			}
		}
		
		return false;	
	}
	private boolean checkDiagRL(){
		for(int i = 0; i<8; i++){
			for(int o=0; o<8; o++){
				if(i+3 <8 && o-3>0){
					if(	this.set[o][i].get() ==
						this.set[o-1][i+1].get() &&
						this.set[o-1][i+1].get() ==
						this.set[o-2][i+2].get() &&
						this.set[o-2][i+2].get() ==
						this.set[o-3][i+3].get() &&
						this.set[o][i].get() != -1){
						
						winStones[0].setY(o);		winStones[0].setX(i);
						winStones[1].setY(o-1);	winStones[1].setX(i+1);
						winStones[2].setY(o-2);	winStones[2].setX(i+2);
						winStones[3].setY(o-3);	winStones[3].setX(i+3);
					
						return true;
					}
				}
			}
		}
		
		return false;	
	}
	public void setWinStones() {
		for(int i=0; i<4; i++){
			set[winStones[i].getY()][winStones[i].getX()].set(set[winStones[i].getY()][winStones[i].getX()].get()+2);
		}
	}
	public boolean check4Draw() {
		boolean value = true;
		for(int i=0; i<8; i++){
			for(int o=0; o<8; o++){
				if(set[i][o].get() == -1)
					value = false;
			}
		}
		return value;
	}
}
