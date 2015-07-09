package Ships;


public class Corvette extends Ship{
	public static final int length = 3;
	public static final int shootArea = 1;
	public static final int loadTime = 2;

	public Corvette(){
		super(length,true,loadTime, shootArea);
	}
}
