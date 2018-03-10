package knp.MouShare;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JComboBox;

public class Setup {

	private JFrame frmSetup;
	private JTextField serverKey;
	private final ButtonGroup masterSlave = new ButtonGroup();

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetup = new JFrame();
		frmSetup.setTitle("Setup");
		frmSetup.setBounds(100, 100, 450, 300);
		frmSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSetup.getContentPane().setLayout(null);
		
		serverKey = new JTextField();
		serverKey.setBounds(160, 187, 114, 19);
		frmSetup.getContentPane().add(serverKey);
		serverKey.setColumns(10);
		
		JRadioButton masterMode = new JRadioButton("Run as Server");
		masterMode.setSelected(true);
		masterSlave.add(masterMode);
		masterMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		slaveMode.setBounds(63, 154, 149, 23);
		frmSetup.getContentPane().add(slaveMode);
		
		JLabel lblYourIp = new JLabel("Your IPs:");
		lblYourIp.setBounds(73, 86, 70, 15);
		frmSetup.getContentPane().add(lblYourIp);
		
		JLabel lblServerIp = new JLabel("Server IP:");
		lblServerIp.setBounds(73, 189, 70, 15);
		frmSetup.getContentPane().add(lblServerIp);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(160, 81, 114, 24);
		frmSetup.getContentPane().add(comboBox);
	}
}
