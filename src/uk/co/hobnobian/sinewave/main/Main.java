package uk.co.hobnobian.sinewave.main;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class Main {
	static JList<String> list;
	static JFormattedTextField toAdd;
	static ArrayList<Integer> current = new ArrayList<Integer>();
	
	static JCheckBox noiseCancel;
	
	static Shower s;
	static Player p;
	
	static ActionListener onupdate = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			update();
			
		}
		
	};
	
	private static void update() {
		String[] data = new String[current.size()];
		int i = 0;
		for (int d : current) {
			data[i] = Integer.toString(d)+"hz @ 50";
			i++;
		}
		s.setFreqs(current);
		list.setListData(data);
		s.cancel = noiseCancel.isSelected();
		s.repaint();
	}

	
	static ActionListener addPress = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Integer i = (Integer) toAdd.getValue();
			current.add(i);
			update();
		}
		
	};
	static ActionListener removePress = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int s = list.getSelectedIndex();
			if (s < 0) {
				return;
			}
			current.remove(s);
			update();
		}
	};

	public static void main(String[] args) {
		JFrame jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s = new Shower();
		
		jframe.setLayout(new FlowLayout());
		
		p = new Player();
		
		JPanel buttons = new JPanel();
		list = new JList<String>();
		list.setListData(new String[0]);
		buttons.setLayout(new GridLayout(3,2));
		
		
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		
		toAdd = new JFormattedTextField(formatter);
		
		JButton done = new JButton("Add");
		done.addActionListener(addPress);
		JButton delete = new JButton("Remove");
		delete.addActionListener(removePress);
		
		noiseCancel = new JCheckBox("Noise Cancel");
		noiseCancel.addActionListener(onupdate);
		
		buttons.add(toAdd);
		buttons.add(done);
		buttons.add(list);
		buttons.add(delete);
		buttons.add(noiseCancel);
		buttons.add(new JLabel());
		
		
		
		jframe.add(s);
		jframe.add(buttons);
		
		jframe.pack();
		jframe.setVisible(true);
		
		
//		while (true) {
//			
//		p.next();
//		}
	}

}
