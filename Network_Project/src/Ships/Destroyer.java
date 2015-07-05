package Ships;


public class Destroyer extends Ship{
	public static final int length = 5;
	public static final int shootArea = 3;
	public static final int loadTime = 3;


	public Destroyer(){
		super(length,true,loadTime, shootArea);
	}
}
