package knp.MouShare;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.net.*;
import java.util.*;
import java.io.*;

import knp.MouShare.misc.*;
import knp.MouShare.misc.InputStreamReader;
import knp.MouShare.net.*;

public class Setup {

	private JFrame frmSetup;
	private JTextField serverIP;
	private final ButtonGroup masterSlave = new ButtonGroup();
	private JComboBox<String> selfIPs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Setup window = new Setup();
					window.afterInit();
					window.frmSetup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Setup() {
		initialize();
		frmSetup.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel parentPanel = new JPanel();
		frmSetup.getContentPane().add(parentPanel, "name_44277902296572");
		parentPanel.setLayout(new CardLayout(0, 0));
		
		JPanel setupPanel = new JPanel();
		parentPanel.add(setupPanel, "name_44302910620072");
		setupPanel.setLayout(null);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(275, 193, 117, 25);
		setupPanel.add(btnNext);
		
		serverIP = new JTextField();
		serverIP.setBounds(160, 134, 114, 19);
		setupPanel.add(serverIP);
		serverIP.setColumns(10);
		
		JLabel lblServerIp = new JLabel("Server IP:");
		lblServerIp.setBounds(72, 137, 70, 15);
		setupPanel.add(lblServerIp);
		
		JRadioButton slaveMode = new JRadioButton("Run as Client");
		slaveMode.setBounds(44, 103, 149, 23);
		setupPanel.add(slaveMode);
		masterSlave.add(slaveMode);
		
		JRadioButton masterMode = new JRadioButton("Run as Server");
		masterMode.setBounds(44, 40, 149, 23);
		setupPanel.add(masterMode);
		masterMode.setSelected(true);
		masterSlave.add(masterMode);
		
		selfIPs = new JComboBox<String>();
		selfIPs.setBounds(160, 71, 114, 24);
		setupPanel.add(selfIPs);
		
		JLabel lblYourIp = new JLabel("Your IPs:");
		lblYourIp.setBounds(72, 71, 70, 15);
		setupPanel.add(lblYourIp);
		
		JPanel serverPanel = new JPanel();
		parentPanel.add(serverPanel, "name_7018014209702");
		serverPanel.setLayout(null);
		
		JPanel mousePanel = new JPanel();
		mousePanel.setBounds(12, 12, 392, 218);
		mousePanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		serverPanel.add(mousePanel);
		mousePanel.setLayout(null);
		mousePanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				float relX = (float)e.getX() / (float)(mousePanel.getWidth() - 1) * 100f;
				float relY = (float)e.getY() / (float)(mousePanel.getHeight() - 1) * 100f;
				
				byte[] bytes = { (byte)-1, (byte)relX, (byte)relY, 0, 0x7f };
				
				try {
					server.getPrintStream().write(bytes);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		masterMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadIPs(selfIPs);
			}
		});
		slaveMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (masterMode.isSelected()) {
					setupServer();
					((CardLayout)(parentPanel.getLayout())).next(parentPanel);
					frmSetup.setResizable(true);
				}
				else setupClient();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetup = new JFrame();
		frmSetup.setResizable(false);
		frmSetup.setTitle("Setup");
		frmSetup.setBounds(100, 100, 420, 267);
		frmSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void afterInit() {
		loadIPs(selfIPs);
	}
	
	void loadIPs(JComboBox<String> box) {
		if (box.getComponents().length != 0) box.removeAllItems();
		ArrayList<String> list = GetAvailableAddresses.get();
		for (String string : list) {
			box.addItem(string);
		}
	}
	
	Server server;
	void setupServer() {
		try {
			server = new Server(8520);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Client client;
	Robot robot;
	void setupClient() {
		try {
			client = new Client(InetAddress.getByName(serverIP.getText()), 8520);
			robot = new Robot();
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			float width = (float)screenSize.getWidth();
			float height = (float)screenSize.getHeight();
			
			InputStreamReader iReader = new InputStreamReader(client.getSocket().getInputStream());
			iReader.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					byte[] in = (byte[])e.getSource();
					System.out.println(in[0] + ";" + in[1] + ";" + in[2]);
					
					float nextX = width * (float)in[0] / 100f;
					float nextY = height * (float)in[1] / 100f;
					
					robot.mouseMove((int)nextX, (int)nextY);
					
				}
			});
			
			Thread t = new Thread( iReader );
			t.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	protected JComboBox<String> getSelfIPs() {
		return selfIPs;
	}
}
