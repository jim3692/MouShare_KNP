package knp.MouShare;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import knp.MouShare.misc.GetAvailableAddresses;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;

import java.util.ArrayList;
import javax.swing.JButton;

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
		
		loadIPs(selfIPs);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetup = new JFrame();
		frmSetup.setTitle("Setup");
		frmSetup.setBounds(100, 100, 420, 267);
		frmSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSetup.getContentPane().setLayout(null);
		
		serverIP = new JTextField();
		serverIP.setBounds(160, 163, 114, 19);
		frmSetup.getContentPane().add(serverIP);
		serverIP.setColumns(10);
		
		JRadioButton masterMode = new JRadioButton("Run as Server");
		masterMode.setSelected(true);
		masterSlave.add(masterMode);
		masterMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadIPs(selfIPs);
			}
		});
		masterMode.setBounds(63, 53, 149, 23);
		frmSetup.getContentPane().add(masterMode);
		
		JRadioButton slaveMode = new JRadioButton("Run as Client");
		masterSlave.add(slaveMode);
		slaveMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		slaveMode.setBounds(63, 130, 149, 23);
		frmSetup.getContentPane().add(slaveMode);
		
		JLabel lblYourIp = new JLabel("Your IPs:");
		lblYourIp.setBounds(73, 86, 70, 15);
		frmSetup.getContentPane().add(lblYourIp);
		
		JLabel lblServerIp = new JLabel("Server IP:");
		lblServerIp.setBounds(73, 165, 70, 15);
		frmSetup.getContentPane().add(lblServerIp);
		
		selfIPs = new JComboBox<String>();
		selfIPs.setBounds(160, 81, 114, 24);
		frmSetup.getContentPane().add(selfIPs);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(287, 205, 117, 25);
		frmSetup.getContentPane().add(btnNext);
	}
	
	void loadIPs(JComboBox<String> box) {
		if (box.getComponents().length != 0) box.removeAllItems();
		ArrayList<String> list = GetAvailableAddresses.get();
		for (String string : list) {
			box.addItem(string);
		}
	}

	protected JComboBox<String> getSelfIPs() {
		return selfIPs;
	}
}
