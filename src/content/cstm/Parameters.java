// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the packed object containing all the application parameters that could change from one execution to another
 * but must stay constant while the JVM is running (most are only read once at the initialization of the program).
 * 
 * @author Remi Lemaire
 * 
 * @see Data
 */

public class Parameters{
	public static final List<String> SAVE_DESC = Arrays.asList("font.size","output.default.size","output.min.size","control.size","main.width","main.height",
																"admin.width","admin.height","settings.width","settings.height","click.duration","font.name",
																"settings.file.name","splash.address","icon.address","standby.address","default.settings.address",
																"date.format","minutes.format","secondes.format","extension");
	public static final Types[] SAVE_TYPES = {Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,Types.Int,
											Types.String,Types.String,Types.String,Types.String,Types.String,Types.String,Types.String,Types.String,Types.String,Types.String,};
	
	public static final String PARAM_FILE_PATH="/parameters";
	
	//This font size is used in all the GUI
	public int fontSize, outputDefaultSize, outputMinSize, controlSize, mainWidth, mainHeight, adminWidth, adminHeight, settingsWidth, settingsHeight, clickTime;
	public String fontName, settingsFileName,splashAddress, iconAddress, standbyAddress, defaultSettingsAddress, dateFormat, minFormat, secFormat, extension;
	
	public Parameters(Object[] obj) throws IllegalArgumentException{
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
				case Double:				//Not used yet
					if(!(obj[i] instanceof Double))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (double) : "+obj[i]);
					break;
				case String:
					if(!(obj[i] instanceof String))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (String) : "+obj[i]);
					break;
				case Color:					//Not used yet
					if(!(obj[i] instanceof Color))
						throw new IllegalArgumentException("The element "+i+" of the array was not of the expected type (Color) : "+obj[i]);
					break;
				}
			fontSize =  (int)obj[0];
			outputDefaultSize =  (int)obj[1];
			outputMinSize =  (int)obj[2];
			controlSize =  (int)obj[3];
			mainWidth =  (int)obj[4];
			mainHeight =  (int)obj[5];
			adminWidth =  (int)obj[6];
			adminHeight =  (int)obj[7];
			settingsWidth =  (int)obj[8];
			settingsHeight =  (int)obj[9];
			clickTime = (int)obj[10];
			fontName = (String)obj[11];
			settingsFileName = (String)obj[12];
			splashAddress = (String)obj[13];
			iconAddress = (String)obj[14];
			standbyAddress = (String)obj[15];
			defaultSettingsAddress = (String)obj[16];
			dateFormat = (String)obj[17];
			minFormat = (String)obj[18];
			secFormat = (String)obj[19];
			extension = (String)obj[20];
		}else
			throw new IllegalArgumentException("The given array was either null or not of the correct size");
	}
}