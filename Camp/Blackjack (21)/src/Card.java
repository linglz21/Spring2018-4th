import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Card {

	private static String[] cardNames = {"10clubs.GIF","10clubsS.GIF","10diamonds.GIF",
		"10diamondsS.GIF","10hearts.GIF","10heartsS.GIF","10spades.GIF","10spadesS.GIF",
		"2clubs.GIF","2clubsS.GIF","2diamonds.GIF","2diamondsS.GIF","2hearts.GIF",
		"2heartsS.GIF","2spades.GIF","2spadesS.GIF","3clubs.GIF","3clubsS.GIF",
		"3diamonds.GIF","3diamondsS.GIF","3hearts.GIF","3heartsS.GIF","3spades.GIF",
		"3spadesS.GIF","4clubs.GIF","4clubsS.GIF","4diamonds.GIF","4diamondsS.GIF",
		"4hearts.GIF","4heartsS.GIF","4spades.GIF","4spadesS.GIF","5clubs.GIF",
		"5clubsS.GIF","5diamonds.GIF","5diamondsS.GIF","5hearts.GIF","5heartsS.GIF",
		"5spades.GIF","5spadesS.GIF","6clubs.GIF","6clubsS.GIF","6diamonds.GIF",
		"6diamondsS.GIF","6hearts.GIF","6heartsS.GIF","6spades.GIF","6spadesS.GIF",
		"7clubs.GIF","7clubsS.GIF","7diamonds.GIF","7diamondsS.GIF","7hearts.GIF",
		"7heartsS.GIF","7spades.GIF","7spadesS.GIF","8clubs.GIF","8clubsS.GIF",
		"8diamonds.GIF","8diamondsS.GIF","8hearts.GIF","8heartsS.GIF","8spades.GIF",
		"8spadesS.GIF","9clubs.GIF","9clubsS.GIF","9diamonds.GIF","9diamondsS.GIF",
		"9hearts.GIF","9heartsS.GIF","9spades.GIF","9spadesS.GIF","aceclubs.GIF",
		"aceclubsS.GIF","acediamonds.GIF","acediamondsS.GIF","acehearts.GIF",
		"aceheartsS.GIF","acespades.GIF","acespadesS.GIF",
		"jackclubs.GIF","jackclubsS.GIF","jackdiamonds.GIF","jackdiamondsS.GIF",
		"jackhearts.GIF","jackheartsS.GIF","jackspades.GIF","jackspadesS.GIF",
		"kingclubs.GIF","kingclubsS.GIF","kingdiamonds.GIF",
		"kingdiamondsS.GIF","kinghearts.GIF","kingheartsS.GIF","kingspades.GIF",
		"kingspadesS.GIF","queenclubs.GIF","queenclubsS.GIF","queendiamonds.GIF",
		"queendiamondsS.GIF","queenhearts.GIF","queenheartsS.GIF","queenspades.GIF",
		"queenspadesS.GIF"};
	public static int[] values = {10,2,3,4,5,6,7,8,9,11,10,10,10};
	private static String[] suits = {"Clubs","Diamonds","Hearts","Spades"};
	private static int nameIndex = 0;
	private static BufferedImage back;
	private boolean isDown;
	
	private BufferedImage myImage;
	private int myValue;
	private String mySuit;
	
	public boolean isDown(){return isDown;}
	
	public Card(){
		isDown = false;
		myValue = values[nameIndex/8];
		try {
			myImage = ImageIO.read(MainClass.class
					.getResourceAsStream("/resources/cards/" + cardNames[nameIndex]));
			if(back==null){
				back = ImageIO.read(MainClass.class
						.getResourceAsStream("/resources/cards/back1.GIF"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mySuit = suits[(nameIndex/2) % 4];
		nameIndex += 2;
	}
	
	public void flip(){
		isDown = !isDown;
	}
	
	
	public void draw(Graphics g, int x, int y){
		if(isDown){
			g.drawImage(back, x, y, null);
		}else{
			g.drawImage(myImage, x, y, null);
		}
	}
	
	public int getValue(){ return myValue;}
	public String getSuit(){ return mySuit;}
	

}
