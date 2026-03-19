// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code Settings} class contains all the application parameters that can change while the
 * application is running.
 * 
 * The default settings are stored in a file packed in the jar, the name and location of
 * this file is named in {@code default.settings.address} the {@link Parameters} class.
 * If for any reason the default settings are to be changed, it is this file that needs to be altered.
 * 
 * @author Remi Lemaire
 *
 * @see Data
 */

public class Settings{
	//This font size is only used in the output table
	public static final List<String> SAVE_DESC = Arrays.asList("font.size","bold.numbers","autosave.stats","reverse.validated.colors","save.path",
														"font.name","normal.background","dark.background","light.background","normal.font.color",
														"invalid.font.color","valid.font.color","language.name","standby.default","standby.return",
														"standby.wait", "standby.zoom","standby.path");
	public static final Types[] SAVE_TYPES = {Types.Int,
			Types.Boolean,Types.Boolean, Types.Boolean,
			Types.String,Types.String,
			Types.Color,Types.Color,Types.Color,Types.Color,Types.Color,Types.Color,
			Types.String,
			Types.Boolean, Types.Boolean, Types.Int, Types.Double, Types.String};
	
	public int fontSize, standbyWait;
	public double standbyMaxZoom;
	public boolean bold, autosave, reverseValid, standbyDefault, standbyReturnOnWake;
	public String savePath, fontName, standbyPath;
	public Color normalBg,darkBg,lightBg,normalF,invalidF,validF;
	public Language lang;
	
	
	public Settings(int fontSize, boolean bold, boolean autosave, boolean reverseValid, String savePath, String fontName, Color normalBg, Color darkBg,
					Color lightBg, Color normalF, Color invalidF, Color validF, Language language, boolean standbyDefault, boolean standbyReturnOnWake,
					int standbyWait, double standbyMaxZoom, String standbyPath){
		this.fontSize=fontSize;
		this.bold=bold;
		this.autosave=autosave;
		this.reverseValid=reverseValid;
		this.savePath=savePath;
		this.fontName = fontName;
		this.normalBg=normalBg;
		this.darkBg=darkBg;
		this.lightBg=lightBg;
		this.normalF=normalF;
		this.invalidF=invalidF;
		this.validF=validF;
		this.lang = language;
		
		this.standbyDefault = standbyDefault;
		this.standbyReturnOnWake = standbyReturnOnWake;
		this.standbyWait = standbyWait;
		this.standbyMaxZoom = standbyMaxZoom;
		this.standbyPath = standbyPath;
	}
	public Settings(Object[] obj) throws IllegalArgumentException{
		if(obj != null && obj.length == SAVE_DESC.size()){
			for(int i=0; i<SAVE_DESC.size(); i++)
				switch(SAVE_TYPES[i]){		//No default case because those are enum items
				case Int:
					if(!(obj[i] instanceof Integer))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (int) : "+obj[i]);
					break;
				case Boolean:
					if(!(obj[i] instanceof Boolean))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (boolean) : "+obj[i]);
					break;
				case Double:
					if(!(obj[i] instanceof Double))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (double) : "+obj[i]);
					break;
				case String:
					if(!(obj[i] instanceof String))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (String) : "+obj[i]);
					break;
				case Color:
					if(!(obj[i] instanceof Color))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (Color) : "+obj[i]);
					break;
				}
			
			fontSize=(int)obj[0];
			bold=(boolean)obj[1];
			autosave=(boolean)obj[2];
			reverseValid=(boolean)obj[3];
			savePath=(String)obj[4];
			fontName = (String)obj[5];
			normalBg=(Color)obj[6];
			darkBg=(Color)obj[7];
			lightBg=(Color)obj[8];
			normalF=(Color)obj[9];
			invalidF=(Color)obj[10];
			validF=(Color)obj[11];
			lang = Language.getLanguage((String)obj[12]);
			standbyDefault =(boolean) obj[13];
			standbyReturnOnWake = (boolean) obj[14];
			standbyWait =(int) obj[15];
			standbyMaxZoom =(double) obj[16];
			standbyPath =(String) obj[17];
			
		}else
			throw new IllegalArgumentException("The given array was either null or not of the correct size");
	}
	
	public String toString(){
		String str="";
		str += SAVE_DESC.get(0)+':'+fontSize+"\n";
		str += SAVE_DESC.get(1)+':'+bold+"\n";
		str += SAVE_DESC.get(2)+':'+autosave+"\n";
		str += SAVE_DESC.get(3)+':'+reverseValid+"\n";
		str += SAVE_DESC.get(4)+':'+savePath+"\n";
		str += SAVE_DESC.get(5)+':'+fontName+"\n";
		str += SAVE_DESC.get(6)+':'+normalBg.getRed()+','+normalBg.getGreen()+','+normalBg.getBlue()+"\n";
		str += SAVE_DESC.get(7)+':'+darkBg.getRed()+','+darkBg.getGreen()+','+darkBg.getBlue()+"\n";
		str += SAVE_DESC.get(8)+':'+lightBg.getRed()+','+lightBg.getGreen()+','+lightBg.getBlue()+"\n";
		str += SAVE_DESC.get(9)+':'+normalF.getRed()+','+normalF.getGreen()+','+normalF.getBlue()+"\n";
		str += SAVE_DESC.get(10)+':'+invalidF.getRed()+','+invalidF.getGreen()+','+invalidF.getBlue()+"\n";
		str += SAVE_DESC.get(11)+':'+validF.getRed()+','+validF.getGreen()+','+validF.getBlue()+"\n";
		str += SAVE_DESC.get(12)+':'+lang.toString()+"\n";
		str += SAVE_DESC.get(13)+':'+standbyDefault+"\n";
		str += SAVE_DESC.get(14)+':'+standbyReturnOnWake+"\n";
		str += SAVE_DESC.get(15)+':'+standbyWait+"\n";
		str += SAVE_DESC.get(16)+':'+standbyMaxZoom+"\n";
		str += SAVE_DESC.get(17)+':'+standbyPath+"\n";
		return str;
	}
	public Settings clone(){
		return new Settings(fontSize, bold, autosave, reverseValid, savePath, fontName, normalBg, darkBg, lightBg, normalF, invalidF,
							validF, lang, standbyDefault, standbyReturnOnWake, standbyWait, standbyMaxZoom, standbyPath);
	}
}