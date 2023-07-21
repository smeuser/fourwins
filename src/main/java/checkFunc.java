
public class checkFunc {

	protected static Feld [][] sfeld;
	
	public checkFunc() {
		super();
		sfeld = null;
	}
	public static void setFeld(Feld[][] rhs){
		sfeld = rhs;
	}
	public static int evalStone(int v, int h, int p){
		int value = 0;
		
		value += checkLeftRow(v,h,p);
		value += checkRightRow(v,h,p);
		value += checkAscColumn(v,h,p);
		//	value += checkDesColumn(v,h,p);
		value += checkLBRTDiag(v,h,p);
		value += checkRBLTDiag(v,h,p);
		//	value += checkLTRBDiag(v,h,p);
		//	value += checkRTLBDiag(v,h,p);
		
		return value;
	}
	private static int checkLeftRow(int v, int h, int p){
		int counter = 0;
		
		if(h < 3)
			return 0;
		
		for(int i = h; i > h-4; i--){
			if(sfeld[v][i].get() == swap(p))
				return 0;
			if(sfeld[v][i].get() == p)
				counter++;
			if(sfeld[v][i].get() == -1)
				continue;
		}
		if(counter == 2)
			counter *= 2;
		else if(counter == 3)
			counter *= 4;
		else if(counter == 4)
			counter *= 8;
		return counter*25;
	}
	private static int checkRightRow(int v, int h, int p){
		int counter = 0;
		
		if(h > 4)
			return 0;
		
		for(int i = h; i < h+4; i++){
			if(sfeld[v][i].get() == swap(p))
				return 0;
			if(sfeld[v][i].get() == p)
				counter++;
			if(sfeld[v][i].get() == -1)
				continue;
		}
		if(counter == 2)
			counter *= 2;
		else if(counter == 3)
			counter *= 4;
		else if(counter == 4)
			counter *= 8;
		return counter*25;
	}
	private static int checkAscColumn(int v, int h, int p){
		int counter = 0;
		
		if(v < 3)
			return 0;
		
		for(int i = v; i > v-4; i--){
			if(sfeld[i][h].get() == swap(p))
				return 0;
			if(sfeld[i][h].get() == p)
				counter++;
			if(sfeld[v][i].get() == -1)
				continue;
		}
		if(counter == 2)
			counter *= 2;
		else if(counter == 3)
			counter *= 4;
		else if(counter == 4)
			counter *= 8;
		return counter*25;
	}
	private static int checkLBRTDiag(int v, int h, int p){
		int counter = 0;
		
		if(v < 3 || h > 4)
			return 0;
		
		for(int i = v, o = h; i > v-4 && o < h+4; i--, o++){
			if(sfeld[i][o].get() == swap(p))
				return 0;
			if(sfeld[i][o].get() == p)
				counter++;
			if(sfeld[v][i].get() == -1)
				continue;
		}
		if(counter == 2)
			counter *= 2;
		else if(counter == 3)
			counter *= 4;
		else if(counter == 4)
			counter *= 8;
		return counter*25;
	}
	private static int checkRBLTDiag(int v, int h, int p){
		int counter = 0;
		
		if(v < 3 || h < 3)
			return 0;
		
		for(int i = v, o = h; i > v-4 && o > h-4; i--, o--){
			if(sfeld[i][o].get() == swap(p))
				return 0;
			if(sfeld[i][o].get() == p)
				counter++;
			if(sfeld[v][i].get() == -1)
				continue;
		}
		if(counter == 2)
			counter *= 2;
		else if(counter == 3)
			counter *= 4;
		else if(counter == 4)
			counter *= 8;
		return counter*25;
	}
	private static int swap(int rhs){
		if(rhs == 0)
			return 1;
		if(rhs == 1)
			return 0;
		
		return -1;
	}
}
