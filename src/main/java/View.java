import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class View {

	private Frame view;
	private JTabbedPane panes;
	
	public View() {
		super();
		view = new Frame();
		panes = new JTabbedPane();
		view.getContentPane().add(panes);
	}
	public void addPanel(JPanel rhs, String srhs, int type){
		if(panes.getTabCount() == 1){
			if(panes.getTitleAt(0) == "SplashScreen")
				panes.remove(0);
		}
		
		panes.addTab(srhs,rhs);
		String img = "";
		switch(type){
		case 0:	img = "src/main/resources/media/spiel.gif";
				break;
		case 1:	img = "src/main/resources/media/setup.gif";
				break;
		default:
		}
		panes.setIconAt(panes.getComponentCount()-1,new ImageIcon(img));
	}
	public Frame getFrame(){
		return view;
	}
	public void setMenuBar(JMenuBar rhs){
		view.setJMenuBar(rhs);
	}
	public JTabbedPane getPane(){
		return panes;
	}
	public int getFocus(){
		return panes.getSelectedIndex();
	}
	public void setFocus(int rhs){
		if(rhs < panes.getTabCount() && rhs >= 0)
			panes.setSelectedIndex(rhs);
		else
			System.out.println("Focus could not be set");
	}
}
