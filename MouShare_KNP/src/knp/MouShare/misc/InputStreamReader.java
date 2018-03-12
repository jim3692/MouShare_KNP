package knp.MouShare.misc;

import java.io.*;
import java.util.*;

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
		String s = "";
		scanner = new Scanner(in);
		while (true) {
			s = scanner.nextLine();
			ChangeEvent evt = new ChangeEvent(s);
			for (ChangeListener l : listeners) {
				l.stateChanged(evt);
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				continue;
			}
		}
	}
}