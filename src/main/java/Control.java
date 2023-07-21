import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EtchedBorder;
import com.sun.java.swing.plaf.motif.*;
import com.sun.java.swing.plaf.windows.*;

public class Control implements ActionListener {

	private Engine engine;
	private View view;
	private Vector game;
	JMenuItem f0;
	JMenuItem f1;
	JMenuItem f2;
	JMenuItem f3;
	JMenuItem v0;
	JMenuItem v1;
	JMenuItem v2;
	JMenuItem v3;
	JMenuItem [] ansicht;
	int [] available;
	
	public Control(Engine rhs) {
		super();
		this.engine = rhs;
		this.game = new Vector();
		FileSystem.initGameList();
	}
	public void setView(View rhs){
		this.view = rhs;
		final JMenuItem f0 = new JMenuItem("Neues Spiel");
		final JMenuItem f1 = new JMenuItem("Spiel laden");
		final JMenuItem f2 = new JMenuItem("Spiel speichern");
		final JMenuItem f3 = new JMenuItem("Beenden");
		
		LookAndFeelInfo [] laf = UIManager.getInstalledLookAndFeels();
		for(int i = 0; i< laf.length; i++){
			System.out.println("name: "+laf[i].getName());
		}
		
		final JMenuItem v0 = new JMenuItem("Metal");
		final JMenuItem v1 = new JMenuItem("CDE/Motif");
		final JMenuItem v2 = new JMenuItem("Windows");
		final JMenuItem v3 = new JMenuItem("Windows Classic");
		
		f0.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			engine.newGame();
		}});
		f1.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			new LoadGameMenuFrame(engine);
			System.out.println("Spiel laden");
		}});
		f2.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			if(view.getPane().getTabCount() > 0){
				if(view.getPane().getTitleAt(view.getFocus()) == "SplashScreen"){
					System.out.println("Es gibt kein Spiel zum speichern: SplashScreen");
					return;
				}
				else if(view.getPane().getTitleAt(view.getFocus()) == "Setup"){
					System.out.println("Es gibt kein Spiel zum speichern: Setup");
					return;
				}
				if(FileSystem.saveGame(engine.getGame(view.getFocus()))){
					engine.removeGame(view.getFocus());
					System.out.println("Spiel gespeichert");
					return;
				}
			}
			System.out.println("Es gibt kein Spiel zum speichern: Setup");			
		}});
		f3.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			view.getFrame().dispose();
		}});
		v0.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			LookAndFeelInfo [] laf = UIManager.getInstalledLookAndFeels();
			boolean LAFexists = false;
			for(int i = 0; i < laf.length; i++){
				if(laf[i].getName() == "Metal")
					LAFexists = true;
			}
			if(LAFexists){
				try{
					UIManager.setLookAndFeel(
							UIManager.getCrossPlatformLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(view.getFrame());
					System.out.println("Look and Fell setting succesful -- "+UIManager.getCrossPlatformLookAndFeelClassName().toString());
				} catch(Exception exc){
					System.err.println("Error -- Look and Fell setting failed");
					exc.printStackTrace();
				}
			}
		}});
		v1.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			LookAndFeelInfo [] laf = UIManager.getInstalledLookAndFeels();
			boolean LAFexists = false;
			for(int i = 0; i < laf.length; i++){
				if(laf[i].getName() == "CDE/Motif")
					LAFexists = true;
			}
			if(LAFexists){
				try{
					UIManager.setLookAndFeel(
							new MotifLookAndFeel());
					SwingUtilities.updateComponentTreeUI(view.getFrame());
					System.out.println("Look and Fell setting succesful -- "+UIManager.getCrossPlatformLookAndFeelClassName().toString());
				} catch(Exception exc){
					System.err.println("Error -- Look and Fell setting failed");
					exc.printStackTrace();
				}
			}
		}});
		v2.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			LookAndFeelInfo [] laf = UIManager.getInstalledLookAndFeels();
			boolean LAFexists = false;
			for(int i = 0; i < laf.length; i++){
				if(laf[i].getName() == "Windows")
					LAFexists = true;
			}
			if(LAFexists){
				try{
					UIManager.setLookAndFeel(
							new WindowsLookAndFeel());
					SwingUtilities.updateComponentTreeUI(view.getFrame());
					System.out.println("Look and Fell setting succesful");
				} catch(Exception exc){
					System.err.println("Error -- Look and Fell setting failed");
					exc.printStackTrace();
				}
			}
		}});
		v3.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			LookAndFeelInfo [] laf = UIManager.getInstalledLookAndFeels();
			boolean LAFexists = false;
			for(int i = 0; i < laf.length; i++){
				if(laf[i].getName() == "Windows Classic")
					LAFexists = true;
			}
			if(LAFexists){
				try{
					UIManager.setLookAndFeel(
							new WindowsClassicLookAndFeel());
					SwingUtilities.updateComponentTreeUI(view.getFrame());
					System.out.println("Look and Fell setting succesful");
				} catch(Exception exc){
					System.err.println("Error -- Look and Fell setting failed");
					exc.printStackTrace();
				}
			}
		}});
		
		JMenu file = new JMenu("Datei");
		file.add(f0);
		file.addSeparator();
		file.add(f1);
		file.add(f2);
		file.addSeparator();
		file.add(f3);
		
		JMenu mview = new JMenu("Ansicht");
		mview.add(v0);
		mview.add(v1);
		mview.add(v2);
		mview.add(v3);
		
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		menu.add(mview);
		
		view.setMenuBar(menu);
	}
	public void addGame(final Game rhs){
		final JPanel Game = new JPanel();
		JPanel Navi = new JPanel();
		JPanel Command = new JPanel();
		JPanel Spielfeld = new JPanel();
		JPanel InOutPut = new JPanel();
		JPanel GamePanel = new JPanel();
		
		Navi.setLayout(new GridLayout(0,8,0,0));
		JButton[] insert = new JButton[11];

		for(int i=0; i<8; i++){
			insert[i] = new JButton(new ImageIcon("src/main/resources/media/redDown.gif"));
			insert[i].setRolloverIcon(new ImageIcon("src/main/resources/media/redDownAnim.gif"));
			insert[i].addActionListener(this);
			Navi.add(insert[i]);
		}
		
		Command.setLayout(new GridLayout(1,3,0,0));
		insert[8] = new JButton(new ImageIcon("src/main/resources/media/backward.gif"));
		insert[8].addActionListener(this);
		Command.add(insert[8]);
		insert[9] = new JButton(new ImageIcon("src/main/resources/media/forward.gif"));
		insert[9].addActionListener(this);
		Command.add(insert[9]);
		insert[10] = new JButton(new ImageIcon("src/main/resources/media/hint.gif"));
		insert[10].addActionListener(this);
		Command.add(insert[10]);
		
		if(rhs.getSetting().getSpMode() == 2 && rhs.getSetting().getNetMode() == 0){
			for(int i=0; i<11; i++){
				insert[i].setEnabled(false);
			}
		}
		
		Spielfeld.setLayout(new GridLayout(8,8,0,0));
		for(int i=0; i<8; i++){
			for(int o=0; o<8; o++){
				Spielfeld.add(new viewFeld(rhs.getSet().getFeld(i,o)));
			}
		}
		
		GridBagLayout gridbag = new GridBagLayout();
		gridbag.setConstraints(rhs.getStatus(),buildConstraints(2,3,0,0,1,1,100,100));
		gridbag.setConstraints(rhs.getInput(),buildConstraints(2,3,0,1,1,1,100,100));
		gridbag.setConstraints(rhs.getOutput(),buildConstraints(2,3,0,2,1,3,100,100));
		gridbag.setConstraints(rhs.getClose(),buildConstraints(0,1,1,4,1,1,100,100));
		rhs.getStatus().setText(rhs.getSetting().getName(0)+" beginnt das Spiel");
		InOutPut.setLayout(gridbag);
		InOutPut.add(rhs.getStatus());
		if(rhs.getSetting().getSpMode() == 2){
			InOutPut.add(rhs.getInput());
			InOutPut.add(rhs.getOutput());
		}
		InOutPut.add(rhs.getClose());
		
		GamePanel.setLayout(new BorderLayout());
		GamePanel.add(Navi, BorderLayout.NORTH);
		if(rhs.getSetting().getSpMode() == 0){
			GamePanel.add(Command, BorderLayout.CENTER);
		}
		GamePanel.add(Spielfeld, BorderLayout.SOUTH);
		Game.setLayout(new BorderLayout());
		Game.setPreferredSize(new Dimension(64*8,64*8+10+50+20));
		Game.add(GamePanel, BorderLayout.CENTER);
		//	Game.add(Spielfeld, BorderLayout.CENTER);
		//	Game.add(Navi, BorderLayout.NORTH);
		Game.add(InOutPut, BorderLayout.SOUTH);
		game.add(insert);
		
		if(rhs.getSetting().getSpMode() == 2){
			rhs.getStatus().setText("Warten auf Client");
			if(rhs.getSetting().getNetMode() == 0){
				rhs.getInput().addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						rhs.getService().setMSG(rhs.getInput().getText().trim());
						System.out.println("Nachricht wird gesendet zum Client");
					}
				});
				rhs.initServer(engine);
			}
			else if(rhs.getSetting().getNetMode() == 1){
				rhs.getInput().addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						rhs.getClient().setMSG(rhs.getInput().getText().trim());
						System.out.println("Nachricht wird gesendet zum Server");
					}
				});
				engine.setButtonIcons(game.size()-1);
				rhs.initClient(engine);
			}
		}
		String title = rhs.getSetting().getName(0)+" vs "+rhs.getSetting().getName(1);
		view.addPanel(Game, title, 0);
		view.setFocus(view.getPane().getTabCount()-1);
		rhs.getClose().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				engine.removeGame(view.getPane().indexOfComponent(Game));
			}
		});
	}
	public void addGameSetup(final GameSetting rhs){
		JPanel Setup = new JPanel();
		JPanel setup = new JPanel();
		JPanel help = new JPanel();
		JPanel question = new JPanel();
		
		rhs.setRef(Setup);
		
		JLabel lspMode = new JLabel("Spielmodus");
		//	lspMode.setFont(a);
		
		JLabel []empty = new JLabel[10];
		for(int i = 0; i < 10; i++){
			empty[i] = new JLabel("");
		}
		final JLabel lred = new JLabel("Spielername Rot");
		final JLabel lyellow = new JLabel("Spielername Gelb");
		JLabel lwin = new JLabel("Siegbedingungen");
		final JLabel lki = new JLabel("KI Stufe");
		final JLabel lnetMode = new JLabel("Netzmodus");
		final JLabel lownIP = new JLabel("Eigene IP");
		final JLabel lhostIP = new JLabel("Host IP");
		final JLabel lnetPort = new JLabel("Port");
		final JComboBox spMode = new JComboBox();
		spMode.addItem("KI Spiel");
		spMode.addItem("Hotseat");
		spMode.addItem("Netzwerk");
		spMode.setSelectedIndex(1);
		final JTextField red = new JTextField(20);
		red.setText(rhs.getName(0));
		final JTextField yellow = new JTextField(20);
		yellow.setText(rhs.getName(1));
		JComboBox win = new JComboBox();
		win.addItem("Best of 1");
		win.addItem("Best of 3");
		win.addItem("Best of 5");
		win.addItem("Best of 7");
		win.setSelectedIndex(0);
		final JComboBox ki = new JComboBox();
		ki.addItem("einfach");
		ki.addItem("mittel");
		ki.addItem("schwer");
		ki.setSelectedIndex(0);
		ki.setVisible(false);
		final JComboBox netMode = new JComboBox();
		netMode.addItem("Host");
		netMode.addItem("Client");
		netMode.setSelectedIndex(0);
		netMode.setVisible(false);
		final JTextField ownIP = new JTextField(15);
		ownIP.setVisible(false);
		ownIP.setEditable(false);
		ownIP.setText(rhs.getIP(0));
		final JTextField hostIP = new JTextField(15);
		hostIP.setText(rhs.getIP(1)+"");
		hostIP.setVisible(false);
		final JTextField netPort = new JTextField(5);
		netPort.setText(rhs.getPort()+"");
		netPort.setVisible(false);
		lki.setVisible(false);
		lnetMode.setVisible(false);
		lhostIP.setVisible(false);
		lnetPort.setVisible(false);
		lownIP.setVisible(false);
		
		ki.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox obj = (JComboBox)e.getSource();
				rhs.setKiLevel(obj.getSelectedIndex());
				yellow.setText("KI Stufe "+rhs.getKiLevel());
								
				System.out.println("KiLevel: "+rhs.getKiLevel());
			}
		});
		spMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox obj = (JComboBox)e.getSource();
				rhs.setSpMode(obj.getSelectedIndex());
				
				switch(rhs.getSpMode()){
				case 0:	ki.setVisible(true);
						lki.setVisible(true);
						lred.setText("Spielername");
						lyellow.setText("");
						yellow.setEditable(false);
						yellow.setText("KI Stufe "+ki.getSelectedIndex());
						netMode.setVisible(false);
						lnetMode.setVisible(false);
						hostIP.setVisible(false);
						lhostIP.setVisible(false);
						netPort.setVisible(false);
						lnetPort.setVisible(false);
						ownIP.setVisible(false);
						lownIP.setVisible(false);
						break;
				case 1:	ki.setVisible(false);
						lki.setVisible(false);
						lred.setText("Spielername Rot");
						lyellow.setText("Spielername Gelb");
						yellow.setEditable(true);
						yellow.setText("");
						netMode.setVisible(false);
						lnetMode.setVisible(false);
						hostIP.setVisible(false);
						lhostIP.setVisible(false);
						netPort.setVisible(false);
						lnetPort.setVisible(false);
						ownIP.setVisible(false);
						lownIP.setVisible(false);
						break;
				case 2:	ki.setVisible(false);
						lki.setVisible(false);
						lred.setText("Spielername");
						lyellow.setText("");
						yellow.setEditable(false);
						yellow.setText("");
						netMode.setVisible(true);
						lnetMode.setVisible(true);
						netMode.setSelectedIndex(0);
						hostIP.setVisible(false);
						lhostIP.setVisible(false);
						netPort.setVisible(true);
						lnetPort.setVisible(true);
						ownIP.setVisible(true);
						lownIP.setVisible(true);
						break;
				default:
				}
								
				System.out.println("spMode: "+rhs.getSpMode());
			}
		});
		win.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox obj = (JComboBox)e.getSource();
				rhs.setWinCondition(obj.getSelectedIndex());
								
				System.out.println("spMode: "+rhs.getSpMode());
			}
		});
		netMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox obj = (JComboBox)e.getSource();
				rhs.setNetMode(obj.getSelectedIndex());
				
				switch(rhs.getNetMode()){
				case 0:	hostIP.setVisible(false);
						lhostIP.setVisible(false);
//						lred.setText("Spielername");
//						lyellow.setText("");
//						yellow.setEditable(false);
//						yellow.setText("");
//						red.setEditable(true);
//						red.setText("Spieler Rot");
						break;
				case 1:	hostIP.setVisible(true);
						lhostIP.setVisible(true);
//						lred.setText("");
//						lyellow.setText("Spielername");
//						red.setEditable(false);
//						red.setText("");
//						yellow.setEditable(true);
//						yellow.setText("Spieler Gelb");
						break;
				default:
				}
				
				System.out.println("spMode: "+rhs.getNetMode());
			}
		});
		
		final JTextArea taHelp = new JTextArea(27,15);
		taHelp.setEditable(false);
		taHelp.setBackground(new Color(238,238,238));
		taHelp.setBorder(new EtchedBorder(1));
		taHelp.setLineWrap(true);
		taHelp.setWrapStyleWord(true);
		help.add(taHelp);
		
		spMode.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.spMode());
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		red.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.red(rhs.getSpMode()));
				red.selectAll();
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		yellow.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.yellow());
				yellow.selectAll();
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		win.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.winCondition());
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		ki.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.kiLevel());
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		netMode.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.netMode());
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		hostIP.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.hostIP());
				hostIP.selectAll();
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		netPort.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent arg0) {
				taHelp.setText(setupHelpText.netPort());
				netPort.selectAll();
			}

			public void focusLost(FocusEvent arg0) {
				taHelp.setText("");
			}
		});
		
		setup.setLayout(new GridLayout(14,2,5,5));
		
		setup.add(lspMode);
		setup.add(spMode);
		setup.add(empty[0]);
		setup.add(empty[1]);
		setup.add(lred);
		setup.add(red);
		setup.add(lyellow);
		setup.add(yellow);
		setup.add(empty[2]);
		setup.add(empty[3]);
		setup.add(lwin);
		setup.add(win);
		setup.add(empty[4]);
		setup.add(empty[5]);
		setup.add(lki);
		setup.add(ki);
		setup.add(empty[6]);
		setup.add(empty[7]);
		setup.add(lnetMode);
		setup.add(netMode);
		setup.add(empty[8]);
		setup.add(empty[9]);
		setup.add(lownIP);
		setup.add(ownIP);
		setup.add(lhostIP);
		setup.add(hostIP);
		setup.add(lnetPort);
		setup.add(netPort);
		
		JButton confirm = new JButton(new ImageIcon("src/main/resources/media/ok.gif"));
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("confirm");
				rhs.setName(0,red.getText().trim());
				rhs.setName(1,yellow.getText().trim());
				rhs.setIP(0,ownIP.getText().trim());
				rhs.setIP(1,hostIP.getText().trim());
				rhs.setKiLevel((int)(Math.pow(2,(double)ki.getSelectedIndex())));
				rhs.setSpMode(spMode.getSelectedIndex());
				rhs.setNetMode(netMode.getSelectedIndex());
				System.out.println("KiTiefe: "+rhs.getKiLevel());
				try{
					rhs.setPort(Integer.parseInt(netPort.getText().trim()));
				}
				catch(NumberFormatException nfe){
					
				}
				view.getPane().remove(rhs.getRef());
				engine.newGame(rhs);
			}
		});
		JButton decline = new JButton(new ImageIcon("src/main/resources/media/no.gif"));
		decline.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				view.getPane().remove(rhs.getRef());
				engine.cancelGame(rhs);
				System.out.println("decline");
			}
		});
		
		question.setLayout(new FlowLayout());
		question.add(confirm, BorderLayout.WEST);
		question.add(decline, BorderLayout.WEST);
		
		/*
		gridbag.setConstraints(lspMode,buildConstraints(1,1,0,0,1,1,20,100));
		gridbag.setConstraints(lred,buildConstraints(1,1,0,2,1,1,20,100));
		gridbag.setConstraints(lyellow,buildConstraints(1,1,0,3,1,1,20,100));
		gridbag.setConstraints(lwin,buildConstraints(1,1,0,5,1,1,20,100));
		gridbag.setConstraints(lki,buildConstraints(1,1,0,7,1,1,20,100));
		gridbag.setConstraints(lnetMode,buildConstraints(1,1,0,9,1,1,20,100));
		gridbag.setConstraints(lownIP,buildConstraints(1,1,0,11,1,1,20,100));
		gridbag.setConstraints(lhostIP,buildConstraints(1,1,0,12,1,1,20,100));
		gridbag.setConstraints(lnetPort,buildConstraints(1,1,0,13,1,1,20,100));
		gridbag.setConstraints(spMode,buildConstraints(1,3,1,0,1,1,80,100));
		gridbag.setConstraints(red,buildConstraints(1,3,1,2,1,1,80,100));
		gridbag.setConstraints(yellow,buildConstraints(1,3,1,3,1,1,80,100));
		gridbag.setConstraints(win,buildConstraints(1,3,1,5,1,1,80,100));
		gridbag.setConstraints(ki,buildConstraints(1,3,1,7,1,1,80,100));
		gridbag.setConstraints(netMode,buildConstraints(1,3,1,9,1,1,80,100));
		gridbag.setConstraints(ownIP,buildConstraints(1,3,1,11,1,1,80,100));
		gridbag.setConstraints(hostIP,buildConstraints(1,3,1,12,1,1,80,100));
		gridbag.setConstraints(netPort,buildConstraints(1,3,1,13,1,1,80,100));
		
		setup.add(lspMode);
		setup.add(lred);
		setup.add(lyellow);
		setup.add(lwin);
		setup.add(lki);
		setup.add(lnetMode);
		setup.add(lownIP);
		setup.add(lhostIP);
		setup.add(lnetPort);
		setup.add(spMode);
		setup.add(red);
		setup.add(yellow);
		setup.add(win);
		setup.add(ki);
		setup.add(netMode);
		setup.add(ownIP);
		setup.add(hostIP);
		setup.add(netPort);*/
		
		Setup.setLayout(new BorderLayout());
		Setup.setPreferredSize(new Dimension(64*8,64*8+10+50+20));
		Setup.add(question, BorderLayout.SOUTH);
		Setup.add(help, BorderLayout.EAST);
		Setup.add(setup, BorderLayout.CENTER);
		
		view.addPanel(Setup,"Setup", 1);
		view.setFocus(view.getPane().getTabCount()-1);
	}
	public void actionPerformed(ActionEvent arg0) {
		JButton temp = (JButton)arg0.getSource();
		JButton []array = new JButton[11];
		
		for(int i=0; i<game.size(); i++){
			array = (JButton[])game.get(i);
			for(int o=0; o<8; o++){
				if(temp == array[o]){
					engine.insertStone(i,o);
					viewFeld.reDrawAll();
					return;
				}
			}
			if(temp == array[8]){
				engine.takeBackMove(i);
			}
			if(temp == array[9]){
				engine.doMove(i);
			}
			if(temp == array[10]){
				engine.giveHint(i);
			}
		}
	}
	private GridBagConstraints buildConstraints(int fill, int anchor, int gx, int gy, int gw, int gh, int wx, int wy){
		GridBagConstraints gbc = new GridBagConstraints();
		
		switch(fill){
		case 0:	gbc.fill = GridBagConstraints.NONE;
				break;
		case 1:	gbc.fill = GridBagConstraints.BOTH;
				break;
		case 2:	gbc.fill = GridBagConstraints.HORIZONTAL;
				break;
		case 3:	gbc.fill = GridBagConstraints.VERTICAL;
				break;
		default:
		}
		
		switch(anchor){
		case 0:	gbc.anchor = GridBagConstraints.NORTH;
				break;
		case 1:	gbc.anchor = GridBagConstraints.EAST;
				break;
		case 2:	gbc.anchor = GridBagConstraints.SOUTH;
				break;
		case 3:	gbc.anchor = GridBagConstraints.WEST;
				break;
		case 4:	gbc.anchor = GridBagConstraints.NORTHEAST;
				break;
		case 5:	gbc.anchor = GridBagConstraints.NORTHWEST;
				break;
		case 6:	gbc.anchor = GridBagConstraints.SOUTHEAST;
				break;
		case 7:	gbc.anchor = GridBagConstraints.SOUTHWEST;
				break;
		default:
		}
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
		return gbc;
	}
	public void removeButtons(int rhs) {
		game.remove(rhs);
	}
	public JButton[] getButtons(int rhs) {
		return (JButton[])game.elementAt(rhs);
	}
	public void setTitleAt(String rhs, String rhs1){
		int i;
		for(i = 0; i < view.getPane().getTabCount(); i++){
			if(view.getPane().getTitleAt(i).equals(rhs))
				break;
		}
		view.getPane().setTitleAt(i, rhs1);
	}
}
