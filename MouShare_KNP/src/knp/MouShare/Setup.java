package knp.MouShare;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import knp.MouShare.misc.GetAvailableAddresses;
import knp.MouShare.net.Client;
import knp.MouShare.net.Server;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

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
		
		JPanel panel = new JPanel();
		parentPanel.add(panel, "name_44307525046091");
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				server.getPrintStream().println(String.format("%d;%d", e.getX(), e.getY()));
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
			//System.out.println(server.getScanner().next());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Client client;
	void setupClient() {
		try {
			client = new Client(InetAddress.getByName(serverIP.getText()), 8520);
			//client.getPrintStream().println("hello");
			//client.close();
			while (true)
				System.out.println(client.getScanner().next());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected JComboBox<String> getSelfIPs() {
		return selfIPs;
	}
}
