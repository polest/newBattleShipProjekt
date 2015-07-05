package Ships;


public class Submarine extends Ship{
	public static final int length = 2;
	public static final int shootArea = 1;
	public static final int loadTime = 1;

	public Submarine(){
		super(length,true,loadTime, shootArea);
	}
}
