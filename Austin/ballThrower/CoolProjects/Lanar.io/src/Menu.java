import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;


public class Menu implements ItemListener, ActionListener {

	private static JTextField maxPlayersTextField;
	private static JCheckBox privateGameCheckBox;
	private static JTextField gameNameTextField;
	private static JTextField newGamePort;
	private static JLabel gameSizeLabel;
	private static JCheckBox smallMapSize;
	private static JCheckBox medMapSize;
	private static JCheckBox largeMapSize;
	private static JCheckBox customMapSize;
	private static JTextField customWidth;
	private static JLabel x;
	private static JTextField customHeight;
	private static ArrayList<JCheckBox> mapSizes;
	private int selectedBoxes;
	private static JButton startGame;
	
	public static JList<String> games;
	private static JScrollPane gameScroll;
	public static DefaultListModel gamesModel;
	private static JButton refreshList;
	
	private static JTextField joinPrivateIP;
	private static JTextField joinPrivatePort;
	private static JButton joinGameButton;
	private static JButton joinPrivateGameButton;
	
	private static JTextField username;
	private static JTextField customSkinPath;
	private static JButton chooseSkin;
	public static JCheckBox antiAlias;
	public static JCheckBox fullScreen;
	
	private static HostListener hostListener;

	public Menu() {
		try {
			hostListener = new HostListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//New Game vars
		maxPlayersTextField = new JTextField("Max players");
		
		privateGameCheckBox = new JCheckBox("Private Game");
		privateGameCheckBox.setMnemonic('P');
		
		gameNameTextField = new JTextField("Game name");
		newGamePort = new JTextField("Custom port");
		gameSizeLabel = new JLabel("Map Size");
		
		smallMapSize = new JCheckBox("Small");
		smallMapSize.setMnemonic('S');
		smallMapSize.addItemListener(this);
		
		
		medMapSize = new JCheckBox("Medium");
		medMapSize.setMnemonic('M');
		medMapSize.addItemListener(this);
		
		largeMapSize = new JCheckBox("Large");
		largeMapSize.setMnemonic('L');
		largeMapSize.addItemListener(this);
		
		customMapSize = new JCheckBox("Custom");
		customMapSize.addItemListener(this);
		customMapSize.setMnemonic('C');
		customWidth = new JTextField("Width");
		x = new JLabel("x");
		customHeight = new JTextField("Height");
		customWidth.setEnabled(false);
		customHeight.setEnabled(false);
		
		mapSizes = new ArrayList<JCheckBox>();
		mapSizes.add(customMapSize);
		mapSizes.add(largeMapSize);
		mapSizes.add(medMapSize);
		mapSizes.add(smallMapSize);
		selectedBoxes = 0;
		
		startGame = new JButton("Start Game");
		startGame.setMnemonic('G');
		startGame.addActionListener(this);
		
		//Join game vars
		//String[] selections = {"Placeholder host 1 | 0/0 | 0ms", "Placeholder host 2 | 0/0 | 0ms"};
		gamesModel = new DefaultListModel();
		games = new JList<String>();
		games.setModel(gamesModel);
		games.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		games.setLayoutOrientation(JList.VERTICAL);
		games.setVisibleRowCount(-1);
		gameScroll = new JScrollPane(games);
		//gameScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		refreshList = new JButton("Refresh");
	
		joinGameButton = new JButton("Join Selected Game");
		joinGameButton.addActionListener(this);
		joinPrivateGameButton = new JButton("Join Game");
		joinPrivateGameButton.addActionListener(this);

		//Join private game vars
		joinPrivateIP = new JTextField("IP address");
		joinPrivatePort =  new JTextField("Port");
		
		//Options vars
		username = new JTextField("Username");
		customSkinPath = new JTextField("Path to skin");
		chooseSkin = new JButton("Choose skin...");
		antiAlias = new JCheckBox("Anti-Aliasing (High Quality)");
		antiAlias.setSelected(true);
		fullScreen = new JCheckBox("Fullscreen");
	}
	
	public static void addToList(String e) {
		gamesModel.addElement(e);
	}
	private static JFrame frame;
	public static void main(String[] args) {
		new Menu();
		frame = new JFrame("Lanor.io");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel newGamePanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints panelC = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0.1;
		c.weighty = 1;
		panelC.weighty = 1;
		panelC.weightx = 1;
		panelC.gridheight = 2;
		
		//Max players text field
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		newGamePanel.add(maxPlayersTextField, c);
		
		//Port number 
		c.gridy = 1;
		newGamePanel.add(newGamePort, c);
				
		//Game name label
		c.gridy = 2;
		newGamePanel.add(gameNameTextField, c);
		//Private game checkbox
		c.gridy = 3;
		newGamePanel.add(privateGameCheckBox, c);
		
		//Map Size label
		c.gridy = 4;
		newGamePanel.add(gameSizeLabel, c);
		
		//Small map size checkbox
		c.gridwidth = 1;
		c.gridy = 5;
		c.gridx = 0;
		newGamePanel.add(smallMapSize, c);
		
		//Med map size checkbox
		c.gridx = 1;
		newGamePanel.add(medMapSize, c);
		medMapSize.setSelected(true); //Default selection
		
		//Large map size checkbox
		c.gridx = 2;
		newGamePanel.add(largeMapSize, c);
		
		//Custom map size and button
		c.gridx = 0;
		c.gridy = 6;
		JPanel mapSizePanel = new JPanel(new FlowLayout());
		mapSizePanel.add(customMapSize, c);

		mapSizePanel.add(customWidth);
		mapSizePanel.add(x);
		mapSizePanel.add(customHeight);
		c.gridwidth = 3;
		c.gridx = 0;
		newGamePanel.add(mapSizePanel, c);
		
		//Start Game
		c.gridy = 7;
		c.gridwidth = 3;
		newGamePanel.add(startGame, c);
		
		
		//Add border
		TitledBorder gameStartTitle;
		gameStartTitle = BorderFactory.createTitledBorder("New Game");
		newGamePanel.setBorder(gameStartTitle);
		
		//Add new game components
		panelC.gridx = 0;
		panelC.gridy = 0;
		panelC.insets = new Insets(5, 5, 5, 5);
		panelC.fill = GridBagConstraints.BOTH;

		panel.add(newGamePanel, panelC);
		
		//Join game
		JPanel joinGamePanel = new JPanel(new GridBagLayout());
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		
		//List
		joinGamePanel.add(gameScroll, c);
		
		//Refresh list button
		c.gridy = 1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		joinGamePanel.add(refreshList, c);
		
		//Join game button 
		c.gridy = 2;
		c.weighty = 0.2;
		joinGamePanel.add(joinGameButton, c);
		//Join game border
		TitledBorder joinGameTitle;
		joinGameTitle = BorderFactory.createTitledBorder("Join Game");
		joinGamePanel.setBorder(joinGameTitle);
		
		
		
		//Add join game panel
		panelC.gridx = 1;
		panelC.gridheight = 1;
		panelC.weighty = 1;
		panel.add(joinGamePanel, panelC);

		
		//Join private game
		JPanel joinPrivateGame = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//IP textfield
		c.weightx = 0.7;
		joinPrivateGame.add(joinPrivateIP, c);
		
		//Colon
		c.gridx = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		joinPrivateGame.add(new JLabel(":"), c);
		
		//Port textfield
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		c.gridx = 2;
		joinPrivateGame.add(joinPrivatePort, c);
		
		//Join Game Button
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		joinPrivateGame.add(joinPrivateGameButton, c);
		//Border
		TitledBorder privateJoinGameTitle;
		privateJoinGameTitle = BorderFactory.createTitledBorder("Join Private Game");
		joinPrivateGame.setBorder(privateJoinGameTitle);
		
		//Add join private game panel
		panelC.gridy = 1;
		panelC.weighty = 0.2;
		panel.add(joinPrivateGame, panelC);
		
		//Options
		JPanel optionsPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(5,5,5,5);
		
		//Username
		optionsPanel.add(username, c);
		
		//Skin path+button
		c.gridy = 1;
		optionsPanel.add(customSkinPath, c);
		c.gridy = 2;
		optionsPanel.add(chooseSkin, c);
		
		//AA Checkbox
		c.gridy = 3;
		optionsPanel.add(antiAlias, c);
		
		//Fullscreen option
		c.gridy = 4;
		optionsPanel.add(fullScreen, c);
		
		//Border
		TitledBorder optionsTitle;
		optionsTitle = BorderFactory.createTitledBorder("Options");
		optionsPanel.setBorder(optionsTitle);
		
		//Add panel
		panelC.gridheight = 2;
		panelC.gridx = 2;
		panelC.gridy = 0;
		panel.add(optionsPanel, panelC);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);


	}
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		JCheckBox source = (JCheckBox) e.getSource();
		if (source.isSelected()){
			selectedBoxes++;
			if (selectedBoxes > 1) {
				for (JCheckBox box: mapSizes){
					if (box != source) box.setSelected(false);
				}
			}
			if (customMapSize.isSelected()) {
				customWidth.setEnabled(true);
				customHeight.setEnabled(true);
			}
		} else {
			selectedBoxes--;
			if (!customMapSize.isSelected()) {
				customWidth.setEnabled(false);
				customHeight.setEnabled(false);
			}
			if (selectedBoxes == 0) {
				source.setSelected(true);
			}
		}
		
	}
	
	public static Dimension getMapDimensions() {
		if (smallMapSize.isSelected()) return new Dimension(400, 300);
		else if (medMapSize.isSelected()) return new Dimension(800, 600);
		else if (largeMapSize.isSelected()) return new Dimension(1200, 800);
		else if (customMapSize.isSelected()) return null;
		else return null;
	}
	public static int getMapSize() {
		if (smallMapSize.isSelected()) return 400*300;
		else if (medMapSize.isSelected()) return 800*600;
		else if (largeMapSize.isSelected()) return 1200*800;
		else if (customMapSize.isSelected()) return 0;
		else return 0;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		if (source == startGame) {
			try { 
				new Server();
				Game game = null;
				if (smallMapSize.isSelected()) game = new Game(new Client(InetAddress.getLocalHost()), 400, 300);
				else if (medMapSize.isSelected()) game = new Game(new Client(InetAddress.getLocalHost()), 800, 600);
				else if (largeMapSize.isSelected()) game = new Game(new Client(InetAddress.getLocalHost()), 1200, 800);
				else if (customMapSize.isSelected()) game = new Game(new Client(InetAddress.getLocalHost()), 0, 0);
				new GameHelper(game);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (source == joinGameButton) {
			InetAddress host = hostListener.hosts.get(games.getSelectedIndex());
			if (host != null){
				try {
					Game game = null;
					if (smallMapSize.isSelected()) game = new Game(new Client(HostListener.hosts.get(games.getSelectedIndex())), 400, 300);
					else if (medMapSize.isSelected()) game = new Game(new Client(HostListener.hosts.get(games.getSelectedIndex())), 800, 600);
					else if (largeMapSize.isSelected()) game = new Game(new Client(HostListener.hosts.get(games.getSelectedIndex())), 1200, 800);
					else if (customMapSize.isSelected()) game = new Game(new Client(HostListener.hosts.get(games.getSelectedIndex())), 0, 0);
					new GameHelper(game);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (source == joinPrivateGameButton) {
			
		} else if (source == refreshList) {
			System.out.println("[Menu] Refreshing game list");
			gamesModel.clear();
			HostListener.hosts.clear();
		}
		
	}

	public static int getNewGamePort() {
		try {
			int ret = Integer.parseInt(newGamePort.getText());
			System.out.println("[Menu] got game port: "+ret);
			return ret;
		} catch (NumberFormatException e) {
			System.out.println("[Menu] Couldn't parse port number");
			return 0;
		}
	}
	public static int getMaxPlayers() {
		try {
			int ret = Integer.parseInt(maxPlayersTextField.getText());
			return ret;
		} catch (NumberFormatException e) {
			System.out.println("[Menu] Couldn't parse max players");
		}
		return 8; //Default
	}
	public static String getMaxPlayersString() {
		return maxPlayersTextField.getText();
	}
	public static String getGameName() {
		return gameNameTextField.getText();
	}
	public static String getUsername() {
		System.out.println("[Menu] Got username: "+username.getText());
		return username.getText();
	}
}
