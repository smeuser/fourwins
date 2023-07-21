import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class LoadGameMenuFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton load;
	private JButton cancel;
	private JButton remove;
	private JScrollPane scroll;
	private JPanel panCenter = new JPanel();
	private JPanel panSouth = new JPanel();
	private JList list;
	private Engine engine;

	public LoadGameMenuFrame(Engine rhs) {
		super("Spiel laden");
		setSize(600,200);
		setLocation(200,200);
		
		engine = rhs;
		
		load = new JButton("laden");
		cancel = new JButton("abbrechen");
		remove = new JButton("loeschen");
		
		load.addActionListener(this);
		cancel.addActionListener(this);
		remove.addActionListener(this);
		
		String[] data = new String[FileSystem.getGameListSize()];
		data = FileSystem.getGameList();
		list = new JList();
		list.setListData(data);
		list.setSize(300, 160);
		list.setBorder(new EtchedBorder(1));
		list.setBackground(Color.LIGHT_GRAY);
		scroll = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panCenter.add(scroll);
		panSouth.setLayout(new GridLayout(3,1,0,0));
		panSouth.add(load);
		panSouth.add(remove);
		panSouth.add(cancel);
		
		this.getContentPane().add(panCenter, BorderLayout.WEST);
		this.getContentPane().add(panSouth, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		JButton src = (JButton)arg0.getSource();
		if(src == load){
			if(engine.newGame(FileSystem.loadGame(list.getSelectedIndex())))
				this.dispose();
		}
		if(src == cancel){
			this.dispose();
		}
		if(src == remove){
			FileSystem.removeGame(list.getSelectedIndex());
			new LoadGameMenuFrame(engine).setLocation(this.getLocation());
			this.dispose();
		}
		
	}

}