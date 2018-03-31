package knp.MouShare.misc;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;

// Source:
//   https://stackoverflow.com/a/28138076

public class InputStreamReader implements Runnable {
	protected InputStream in;
	protected List<ChangeListener> listeners;
	protected Scanner scanner;

	public InputStreamReader(InputStream in) {
		this.in = in;
		this.listeners = new ArrayList<ChangeListener>();
	}

	public void addChangeListener(ChangeListener l) {
		this.listeners.add(l);
	}
	
	public void run() {
		byte[] bytes;
		byte[] current = new byte[1];
		int errorCount = 0;
		
		while (true) {
			if (errorCount == 10) {
				JOptionPane.showMessageDialog(null, "Error retrieving data from server.\nDisconnected.");
				break;
			}
			
			bytes = new byte[4];
			try {
				in.read(current);
				if (current[0] == -1) {
					errorCount = 0;
					in.read(bytes);
					
					if (bytes[3] != 0x7f) {
						errorCount++;
						continue;
					}
					
					ChangeEvent evt = new ChangeEvent(bytes);
					for (ChangeListener l : listeners) {
						l.stateChanged(evt);
					}
				} else errorCount++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
/*
	public void run() {
		String s = "";
		scanner = new Scanner(in);
		while (true) {
			try {
				s = scanner.nextLine();
				ChangeEvent evt = new ChangeEvent(s);
				for (ChangeListener l : listeners) {
					l.stateChanged(evt);
				}
				Thread.sleep(5);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Process interrupted.");
				return;
			} catch (NoSuchElementException | NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Error retrieving data from server.\nDisconnected.");
				return;
			}
		}
	}
*/
}