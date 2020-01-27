package eecs2030.lab1;

/**
 * A class that represents a windowed view of Hounsfield units. A Hounsfield
 * window is defined by two values: (1) the window level, and (2) the window
 * width. The window level is the Hounsfield unit value that the window is
 * centered on. The window width is the range of Hounsfield unit values that the
 * window is focused on.
 * 
 * <p>
 * A window has a lower and upper bound. The lower bound is defined as the
 * window level minus half the window width:
 * 
 * <p>
 * lo = level - (width / 2)
 * 
 * <p>
 * The upper bound is defined as the window level plus half the window width:
 * 
 * <p>
 * hi = level + (width / 2)
 * 
 * <p>
 * Hounsfield units are mapped by the window to a real number in the range of
 * {@code 0} to {@code 1}. A Hounsfield unit with a value less than lo is mapped
 * to the value {@code 0}. A Hounsfield unit with a value greater than hi is
 * mapped to the value {@code 1}. A Hounsfield unit with a value v between lo
 * and hi is mapped to the value:
 * 
 * <p>
 * (v - lo) / width
 * 
 *
 */
public class HounsfieldWindow {

	public static final int MIN_VALUE = -1024;	
	public static final int MAX_VALUE = 3071;
	
	private int level;
	private int width;
	private int value;
	
	
	public HounsfieldWindow() {
		this.level = 0;
		this.width = 400;
		this.value = 0;
	}
	
	public HounsfieldWindow (int level, int width) {
		this.level = level;
		this.width = width;
		
		if (this.level < Hounsfield.MIN_VALUE || this.level > Hounsfield.MAX_VALUE || this.width < 1) {
			throw new IllegalArgumentException();
		}
	}
	
	public int getLevel() {
		return this.level; 
	}
	
	public int getWidth() {
		return  this.width;	
	}
	
	public int setLevel(int level) {
		if (level < Hounsfield.MIN_VALUE || level > Hounsfield.MAX_VALUE) {
			throw new IllegalArgumentException();
		}
		int oldValue = this.level;
		this.level = level;
		
		return oldValue;
	}
	
	public int setWidth(int width) {
		if (width < 1) {
			throw new IllegalArgumentException();
		}
		int oldValue = this.width;
		this.width = width;
		
		return oldValue;
	}
	
	public int get () {
		return this.value;
	}
	public double map (Hounsfield h) {
	/*	lo = level - (width / 2)
		hi = level + (width / 2)
		v < lo = 0
		v > hi = 1
		(v - lo) / width
	*/ 
		double low = this.level - (this.width / 2.0);
		double high = this.level + (this.width / 2.0);
		double maps = 5.0;
		this.value = h.get();
	
		if (this.value < low) {
			maps = 0;
		}
		else if (this.value > high) {
			maps = 1;
		}
		else {
			if (low < 0) {
				this.value = this.value + (this.level - (this.width / 2));
				low = low + (this.level - (this.width / 2));
				high = high + (this.level - (this.width / 2));
			}
			else {
				this.value = this.value - (this.level - (this.width / 2));
				low = low - (this.level - (this.width / 2));
				high = high - (this.level - (this.width / 2));
			}
			maps = (this.value - low) / this.width;
			
		}
		System.out.println(maps);
		return maps; 
		}
}