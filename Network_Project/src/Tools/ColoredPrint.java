package Tools;

import java.io.Serializable;

public class ColoredPrint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8766246344042568115L;

	public enum EPrintColor{
		BLACK,
		RED,
		GREEN,
		YELLOW,
		BLUE,
		PURPLE,
		CYAN,
		WHITE,
	}

	private final String ANSI_RESET = (char)27+"[0m";
	private final String ANSI_BLACK = (char)27+"[90m";
	private final String ANSI_RED = (char)27+"[91m";
	private final String ANSI_GREEN = (char)27+"[5;92m";
	private final String ANSI_YELLOW = (char)27+"[93m";
	private final String ANSI_BLUE = (char)27+"[94m";
	private final String ANSI_PURPLE = (char)27+"[95m";
	private final String ANSI_CYAN = (char)27+"[12;96m";
	private final String ANSI_WHITE = (char)27+"[97m";

	public void println(EPrintColor color, String text){

		System.setProperty("jansi.passthrough", "true");

		switch(color){
		case BLACK:
			System.out.println(ANSI_BLACK + text + ANSI_RESET);
			break;
		case BLUE:
			System.out.println(ANSI_BLUE + text + ANSI_RESET);
			break;
		case CYAN:	
			System.out.println(ANSI_CYAN + text + ANSI_RESET);
			break;
		case GREEN:
			System.out.println(ANSI_GREEN + text + ANSI_RESET);
			break;
		case PURPLE:
			System.out.println(ANSI_PURPLE + text + ANSI_RESET);
			break;
		case RED:
			System.out.println(ANSI_RED + text + ANSI_RESET);
			break;
		case WHITE:
			System.out.println(ANSI_WHITE + text + ANSI_RESET);
			break;
		case YELLOW:
			System.out.println(ANSI_YELLOW + text + ANSI_RESET);
			break;
		default:
			System.out.println(text);
			break;
		}

	}

	public void print(EPrintColor color, String text){
		System.setProperty("jansi.passthrough", "true");

		switch(color){
		case BLACK:
			System.out.print(ANSI_BLACK + text + ANSI_RESET);
			break;
		case BLUE:
			System.out.print(ANSI_BLUE + text + ANSI_RESET);
			break;
		case CYAN:	
			System.out.print(ANSI_CYAN + text + ANSI_RESET);
			break;
		case GREEN:
			System.out.print(ANSI_GREEN + text + ANSI_RESET);
			break;
		case PURPLE:
			System.out.print(ANSI_PURPLE + text + ANSI_RESET);
			break;
		case RED:
			System.out.print(ANSI_RED + text + ANSI_RESET);
			break;
		case WHITE:
			System.out.print(ANSI_WHITE + text + ANSI_RESET);
			break;
		case YELLOW:
			System.out.print(ANSI_YELLOW + text + ANSI_RESET);
			break;
		default:
			System.out.print(text);
			break;
		}

	}

	public void format(EPrintColor color, String format, String text){
		System.setProperty("jansi.passthrough", "true");

		switch(color){
		case BLACK:
			System.out.format(ANSI_BLACK + format, text + ANSI_RESET);
			break;
		case BLUE:
			System.out.format(ANSI_BLUE + format, text + ANSI_RESET);
			break;
		case CYAN:	
			System.out.format(ANSI_CYAN + format, text + ANSI_RESET);
			break;
		case GREEN:
			System.out.format(ANSI_GREEN + format, text + ANSI_RESET);
			break;
		case PURPLE:
			System.out.format(ANSI_PURPLE + format, text + ANSI_RESET);
			break;
		case RED:
			System.out.format(ANSI_RED + format, text + ANSI_RESET);
			break;
		case WHITE:
			System.out.format(ANSI_WHITE + format, text + ANSI_RESET);
			break;
		case YELLOW:
			System.out.format(ANSI_YELLOW + format, text + ANSI_RESET);
			break;
		default:
			System.out.format(text);
			break;
		}

	}

	public void format(EPrintColor color, String text){
		System.setProperty("jansi.passthrough", "true");

		switch(color){
		case BLACK:
			System.out.format(ANSI_BLACK + text + ANSI_RESET);
			break;
		case BLUE:
			System.out.format(ANSI_BLUE + text + ANSI_RESET);
			break;
		case CYAN:	
			System.out.format(ANSI_CYAN + text + ANSI_RESET);
			break;
		case GREEN:
			System.out.format(ANSI_GREEN + text + ANSI_RESET);
			break;
		case PURPLE:
			System.out.format(ANSI_PURPLE + text + ANSI_RESET);
			break;
		case RED:
			System.out.format(ANSI_RED + text + ANSI_RESET);
			break;
		case WHITE:
			System.out.format(ANSI_WHITE + text + ANSI_RESET);
			break;
		case YELLOW:
			System.out.format(ANSI_YELLOW  + text + ANSI_RESET);
			break;
		default:
			System.out.format(text);
			break;
		}

	}

}
