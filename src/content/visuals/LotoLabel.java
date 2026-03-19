// Copyright (C) 2026 Remi Lemaire

package content.visuals;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import content.cstm.Data;
import content.cstm.Settings;

/**
 * The {@code LotoLabel} class extends the {@link JLabel} class so that it is more apropriate with the loto number display requirements. 
 * It has a {@link String} value witch is the text that the LotoLabel will display if in the apropriate state. A set of default color is given, 
 * but all colors can be changed after the constructor call.
 * <p>
 * It has 4 states, all used to quickly change the visual of a LotoLabel :
 * <p><blockquote>
 * 0 : The label is not showing any text, the background color is normalBg
 * <p>
 * 1 : The value is shown in the normalFont color and the background color is darkBg
 * <p>
 * 2 : The value is shown in the normalFont color and the background color is normalBg
 * <p>
 * 3 : The labels is now in "check mode" witch means that it will either show the value in valid or invalid mode, depending on the {@code shown} value.
 * </blockquote><p>
 *  The invalid mode paints the value in the invalidFont color with the background color lightBg.
 *  The valid mode paints either paints the value in validFont color and keeps the previous background color of the label,
 *  or with the value in the normalFont color and the background in the validFont color, depending on the {@code vBgReversed} value;
 * 
 * @author Remi Lemaire
 * @see GUI
 */

public class LotoLabel extends JLabel{
	private static final long serialVersionUID = 6L;
	private String value;
	private int state;
	public static Color normalBg, lightBg, darkBg ,normalFont, invalidFont, validFont;
	private boolean shown,nBg,defaultFont;
	private static boolean vBgReversed;
	private static Font font;
	
	static{
		Settings set = Data.getDefaultSettings();
		font = new Font(set.fontName, set.bold ? Font.BOLD:Font.PLAIN, set.fontSize);
	}
	
	public LotoLabel(String str){
		super("",JLabel.CENTER);
		value = str;
		state = 0;
		shown = false;
		nBg=true;
		defaultFont=true;
		setFont(font);
		setOpaque(true);
	}

	public void setState(int i){
		if(i>=0 && i<=3)
			state = i;
		repaint();
	}
	public void setValue(String nValue){
		value=nValue;
	}
	
	public static void setFSize(float size){
		font = font.deriveFont(size);
	}
	public static void setFont(String fontName){
		font = new Font(fontName, font.isBold() ? Font.BOLD : Font.PLAIN, font.getSize());
	}
	public static void setBold(boolean bold){
		if(bold)
			font = font.deriveFont(Font.BOLD);
		else
			font = font.deriveFont(Font.PLAIN);
	}
	public static void setVBgReversed(boolean b){
		vBgReversed=b;
	}
	public static int getFSize(){
		return font.getSize();
	}
	
	public int getState(){
		return state;
	}
	public boolean isShown(){
		return shown;
	}
	
	public LotoLabel getSmall(){
		LotoLabel ret = this.clone();
		ret.defaultFont=false;
		ret.setFont(getFont().deriveFont(14f));
		
		return ret;
	}
	
	public void repaint(){
		if(defaultFont)
			setFont(font);
		switch(state){
			case 0:
				setText("");
				setBackground(normalBg);
				nBg=true;
				shown = false;
				break;
			case 1:
				setText(value);
				setBackground(darkBg);
				nBg=false;
				setForeground(normalFont);
				shown = true;
				break;
			case 2:
				setText(value);
				setBackground(normalBg);
				nBg=true;
				setForeground(normalFont);
				shown = true;
				break;
			case 3:
				setText(value);
				if(shown){
					if(!vBgReversed){
						if(nBg)
							setBackground(normalBg);
						else
							setBackground(darkBg);
						setForeground(validFont);
					}else{
						setForeground(normalFont);
						setBackground(validFont);
					}
				}else{
					setBackground(lightBg);
					setForeground(invalidFont);
				}
				break;
			default:
				setText("ERR");
				setBackground(Color.red);
				setForeground(Color.white);
				break;
		}
		super.repaint();
	}
	public String toString(){
		return "[LotoLabel:"+value+", State:"+state+", Shown:"+shown+"]";
	}
	public LotoLabel clone(){
		LotoLabel ret = new LotoLabel(value);
		ret.setState(state);
		ret.shown = this.shown;
		return ret;
	}
}