package uk.co.hobnobian.sinewave.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Shower extends JPanel {
	private static final long serialVersionUID = 2561399939862496258L;
	private int i = 0;
	private ArrayList<Integer> freqs = new ArrayList<Integer>();
	
	public boolean cancel = false;
	
	public Shower() {
		this.setPreferredSize(new Dimension(1000,600));
	}
	
	public byte calculate(int f, int x) {
		double samplingInterval = (double) (Player.sample / f);
		double angle = (2.0 * Math.PI * x) / samplingInterval;
		byte toPlay = (byte) ((Math.sin(angle)*50));
		return toPlay;
	}
	
	public byte calculateAll(int x) {
		int total = 0;
		for (int f : freqs) {
			int sample = getWidth()*8;
			double samplingInterval = (double) (sample / f);
			double angle = (2.0 * Math.PI * x) / samplingInterval;
			byte toPlay = (byte) ((Math.sin(angle)*50));
			total += toPlay;
		}
		if (cancel) {
			total /= freqs.size();
		}
		return (byte) total;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		if (freqs.size() == 0) {
			g.drawLine(0, getHeight()/2, getWidth()-1, getHeight()/2);
			return;
		}
		g.setColor(Color.RED);
		g.drawLine(0, getHeight()/2, getWidth()-1, getHeight()/2);
		g.setColor(Color.BLACK);
		for (int px = 0; px < getWidth(); px++) {
			int x = px-(getWidth()/2)+i;
			int y = calculateAll(x);
			int nexty = calculateAll(x+1);
			g.drawLine(px, y+getHeight()/2, px, nexty+getHeight()/2);
		}
		
		i++;
		
	}
	
	public void addFreq(int f) {
		freqs.add(f);
	}
	
	public void removeFreq(int f) {
		if (freqs.contains(f)) {
			freqs.remove((Object)f);
		}
	}
	public void removeFreqIndex(int i) {
		if (freqs.size() > i) {
			freqs.remove(i);
		}
	}
	
	public void setFreqs(ArrayList<Integer> f) {
		freqs = f;
	}
}
