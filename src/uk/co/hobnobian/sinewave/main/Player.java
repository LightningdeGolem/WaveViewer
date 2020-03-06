package uk.co.hobnobian.sinewave.main;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player {
	private boolean noiseDown = true;
	private ArrayList<Integer> freqs = new ArrayList<Integer>();
	public static final int sample = 8000;
	private final AudioFormat audio = new AudioFormat(sample, 16, 1, true, true);
	private SourceDataLine line;
	
	private int x = 0;
	public Player() {
		try {
			line = AudioSystem.getSourceDataLine(audio);
			line.open();
			line.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void next() {
		float total = 0f;
		for (int f : freqs) {
			double samplingInterval = (double) (sample / f);
			double angle = (2.0 * Math.PI * x) / samplingInterval;
			byte toPlay = (byte) ((Math.sin(angle)*127));
			total += toPlay;
		}
		if (noiseDown) {
			total /= freqs.size();
		}
		
		line.write(new byte[] {(byte) total, (byte) total},0,2);
		x++;
	}
	
	public void add(int freq) {
		freqs.add(freq);
	}
	public void remove(int freq) {
		if (freqs.contains(freq)) {
			freqs.remove((Object)freq);
		}
	}
	
	public boolean isReduceNoiseOn() {
		return noiseDown;
	}

	public void setReduceNoise(boolean noiseDown) {
		this.noiseDown = noiseDown;
	}
	
}
