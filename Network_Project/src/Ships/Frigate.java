package Ships;


public class Frigate extends Ship{
	public static final int length = 4;
	public static final int shootArea = 2;
	public static final int loadTime = 2;

	
	public Frigate(){
		super(length,true,loadTime, shootArea);
	}
	
}
