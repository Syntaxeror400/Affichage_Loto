// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.awt.Color;
import java.awt.Taskbar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import content.visuals.GUI;

/**
 * The {@code Data} class contains all the methods needed to export the stats, load and save {@link Settings} file
 * and load all the internal ressources and the constants.
 * 
 * @author Remi Lemaire
 * 
 * @see GUI
 * @see Settings
 * @see Parameters
 */

public class Data{
	// Exception exit value
	private static final int PARAM_IOEX =1, PARAM_NULL=2;
	// This app parameters, loaded from the file inside the jar
	public static final Parameters params = loadParams();
	public static final boolean MAC_OS = System.getProperty("os.name").toLowerCase().startsWith("mac os x");
	
	private static final List<Character> validChars = Arrays.asList('0','1','2','3','4','5','6','7','8','9');
	private static final List<String> validImageExtentions = Arrays.asList("png", "jpg", "jpeg");
	
	private File file;
	private Path dirPath;
	private String name,fName,fContent;
	private SimpleDateFormat date,min,sec;
	
	private static File settingsFile;
	private static ImageIcon icon;
	private static FullImage standByImage;
	private static Settings defaultSettings;
	
	
	static{
		try {
		defaultSettings = loadSettings(new BufferedReader(new InputStreamReader(Data.class.getResourceAsStream(params.defaultSettingsAddress))));
		}catch(DataException e){e.printStackTrace();}
		settingsFile = new File(params.settingsFileName);
		
		icon = new ImageIcon(Data.class.getResource(params.iconAddress));
		if(Taskbar.isTaskbarSupported()){
			Taskbar.getTaskbar().setIconImage(icon.getImage());
		}
		
		try{
			standByImage = new FullImage(ImageIO.read(Data.class.getResourceAsStream(params.standbyAddress)));
		}catch(IOException e){e.printStackTrace();}
	}
	
	/**
	 * Load the default parameters that are packaged inside the jar.
	 * 
	 * @see Parameters
	 */
	private static Parameters loadParams(){
		Parameters param = null;
		BufferedReader br;
		int i=0;
		String line[] = new String[2];
		Object[] data = new Object[Parameters.SAVE_DESC.size()];
		
		try{
			br = new BufferedReader(new InputStreamReader(Data.class.getResourceAsStream(Parameters.PARAM_FILE_PATH)));
			for(int k=0; k++ < Parameters.SAVE_DESC.size();){
				line = splitFirst(br.readLine());
				i = Parameters.SAVE_DESC.indexOf(line[0]);
				if(i != -1)
					switch(Parameters.SAVE_TYPES[i]){		//No default case because those are enum items
					case Int:
						data[i] = Integer.valueOf(line[1]);
						break;
					case Boolean:
						data[i] = line[1].equals("true");
						break;
					case Double:							//Not used yet
						data[i] = Double.valueOf(line[1]);
						break;
					case String:
						data[i] = line[1];
						break;
					case Color:								//Not used yet
						String[] vals = line[1].split(",");
						data[i] = new Color(Integer.valueOf(vals[0]),Integer.valueOf(vals[1]),Integer.valueOf(vals[2]));
						break;
					}
				else{
					br.close();
					throw new IOException("Data - Setting name not recognized : "+line[0]);
				}
			}
			br.close();
			param = new Parameters(data);
		}catch(IOException e){e.printStackTrace();System.exit(PARAM_IOEX);}
		if(param == null){
			System.err.println("Data - Could not load parameters, exiting");
			System.exit(PARAM_NULL);
		}
		return param;
	}
	
	public Data(){
		name = "";
		date = new SimpleDateFormat(params.dateFormat);
		min = new SimpleDateFormat(params.minFormat);
		sec = new SimpleDateFormat(params.secFormat);
		dirPath = (new File("./")).toPath();
	}
	
	public void setName(String str){
		name = str;
	}
	public void setDirectory(Path path){
		setDirectory(path.toFile());
	}
	public void setDirectory(File file){
		dirPath = getRootFile(file).toPath();
	}
	public void resetDirectory(){
		dirPath = (new File("./")).toPath();
	}
	
	public String saveStats(ArrayList<Integer> serie){
		Date d = new Date();
		fName = name+" - "+date.format(d)+"h"+min.format(d)+" "+sec.format(d)+"s"+params.extension;
		return save(serie,fName);
	}
	public String saveStats(ArrayList<Integer> serie,String fileName){
		if(!fileName.endsWith(params.extension))
			fileName+=params.extension;
		return save(serie, fileName);
	}
	private String save(ArrayList<Integer> serie, String finalName){
		FileWriter fw;
		
		fContent = "";
		for(Iterator<Integer> it = serie.iterator();it.hasNext();){
			fContent+= it.next()+" - ";
		}
		if(!fContent.equals("")){
			fContent = fContent.substring(0,fContent.length()-3);
			try{
				file = new File(dirPath+"/"+finalName);
				file.createNewFile();
				fw = new FileWriter(file);
				fw.write(fContent);
				fw.close();
				return dirPath+"/\n"+finalName;
			}catch(IOException e){
				e.printStackTrace();
				return "-1";
		}}else return "";
	}

	public Path getPath(){
		return dirPath;
	}
	
	// Global static methods
	
	public static File getCanonOrAbs(File f){
		File temp = f;
		try{
			temp = temp.getCanonicalFile();
		}catch (IOException e){
			temp = temp.getAbsoluteFile();
		}
		return temp;
	}
	public static Path getCanonOrAbs(Path p) {
		return getCanonOrAbs(p.toFile()).toPath();
	}
	
	public static File getRootFile(Path p){
		return getRootFile(p.toFile());
	}
	public static File getRootFile(File file){
		while(!file.isDirectory())
			file = file.getParentFile();
		return file;
	}
	
	public static Path getFDRoot(String filePath){
		return getFDRoot(new File(filePath));
	}
	public static Path getFDRoot(Path p){
		return getFDRoot(p.toFile());
	}
	public static Path getFDRoot(File f){
		File root = f;
		if(f.exists()) {
			while(!root.isDirectory())
				root = root.getParentFile();
			return root.toPath();
		}
			return (new File(".")).toPath();
	}
	
	// Data static methods
	
	public static Settings getDefaultSettings(){
		return defaultSettings.clone();
	}
	public static Settings laodSettings() throws DataException{
		if(settingsFile.exists())
			try{
				return loadSettings(new BufferedReader(new FileReader(settingsFile)));
			}catch (FileNotFoundException e){e.printStackTrace();}
		return null;
	}
	private static Settings loadSettings(BufferedReader br) throws DataException{
		Settings set = null;
		int i=0;
		String line[] = new String[2];
		Object[] data = new Object[Settings.SAVE_DESC.size()];
		
		try{
			for(int k=0; k++ < Settings.SAVE_DESC.size();){
				line = splitFirst(br.readLine());
				if(line.length == 0){
					throw new DataException("Could not read the settings file, not enough arguments : "+(k-1)+" but "+Settings.SAVE_DESC.size()+" expected.");
				}
				
				i = Settings.SAVE_DESC.indexOf(line[0]);
				if(i != -1)
					switch(Settings.SAVE_TYPES[i]){		//No default case because those are enum items
					case Int:
						data[i] = Integer.valueOf(line[1]);
						break;
					case Boolean:
						data[i] = line[1].equals("true");
						break;
					case Double:
						data[i] = Double.valueOf(line[1]);
						break;
					case String:
						data[i] = line[1];
						break;
					case Color:
						String[] vals = line[1].split(",");
						data[i] = new Color(Integer.valueOf(vals[0]),Integer.valueOf(vals[1]),Integer.valueOf(vals[2]));
						break;
					}
				else{
					br.close();
					throw new IOException("Data - Setting name not recognized : "+line[0]);
				}
			}
			br.close();
			set = new Settings(data);
			return set;
		}catch(IOException e){e.printStackTrace();}
		return null;
	}
	public static boolean saveSettings(Settings set){
		BufferedWriter bw;
		try{
			bw = new BufferedWriter(new FileWriter(settingsFile));
			bw.write(set.toString());
			bw.close();
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static ImageIcon getIcon(){
		return icon;
	}
	
	public static FullImage getDefaultStandBy(){
		return standByImage;
	}
	
	public static List<String> searchForImages(String dirPath){
		ArrayList<String> ret = new ArrayList<String>();
		
		File imgDir = new File(dirPath);
		if(imgDir.exists() && imgDir.isDirectory())
			for(File file : imgDir.listFiles()){
				String temp[], ext;
				temp = file.getName().split("\\.");
					
				if(temp.length > 0)
					ext = temp[temp.length - 1].toLowerCase().trim();
				else
					ext = null;
				if(file.exists() && file.isFile() && validImageExtentions.contains(ext))
					ret.add(file.getAbsolutePath());
			}
			
		return ret;
	}
	public static FullImage loadImage(String path){
		File file = new File(path);
		if(file.exists())
			try{
				return new FullImage(ImageIO.read(file));
			}catch (IOException e){
				e.printStackTrace();
				return null;
			}
		else
			return null;
	}
	
	public static boolean settingsFound(){
		return settingsFile.exists();
	}
	public static boolean isCharValid(char c){
		return validChars.contains(c);
	}
	private static String[] splitFirst(String str){
		int i=-1;
		
		// Empty table to detect error
		if(str == null || str.equals(""))
			return new String[0];
		
		while(++i<str.length() && str.charAt(i) != ':');
		
		return new String[]{str.substring(0, i),str.substring(i+1,str.length())};
	}

	// Exception
	@SuppressWarnings("serial")
	public static class DataException extends Exception{
		public DataException(String string){
			super(string);
		}
	}
}