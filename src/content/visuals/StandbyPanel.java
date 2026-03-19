// Copyright (C) 2026 Remi Lemaire

package content.visuals;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import content.cstm.Data;
import content.cstm.FullImage;
import content.cstm.Settings;

/**
 * A class to make the standy mode able to display one or multiple images
 * with a defined wait time between images.
 * 
 * @author Remi Lemaire
 * 
 * @see Guis
 * 
 **/

public class StandbyPanel extends JPanel{
	private static final long serialVersionUID = 10L;
	protected static final int INTER_EX = 3;
	
	protected final Object workerLock = new Object();
	
	protected Runnable bgTask;
	protected Thread backgroundThread;
	private FullImage defaultImage = Data.getDefaultStandBy();
	protected ArrayList<FullImage> imageList = new ArrayList<FullImage>();
	
	protected JPanel basePanel;
	protected JLabel standbyLabel;
	
	protected int currentImage, nFrames, waitTime;
	protected double maxZoom;
	protected boolean isDefaultImage, returnToFirst;
	
	
	public StandbyPanel(Settings settings){
		super();
		
		basePanel = new JPanel();
		basePanel.setFocusable(false);
		basePanel.setLayout(new GridLayout(1,1));
		standbyLabel = new JLabel();
		
		setLayout(new GridBagLayout());
		basePanel.add(standbyLabel);
		add(basePanel);
		super.setVisible(false);
		
		updateImages(settings.standbyPath);
		waitTime = 1000 * settings.standbyWait;
		maxZoom = settings.standbyMaxZoom;
		isDefaultImage = settings.standbyDefault;
		returnToFirst = settings.standbyReturnOnWake;
		if(isDefaultImage)
			currentImage = -1;
		else
			currentImage = 0;
		
		initThread();
		backgroundThread.start();
	}
	
	public void setVisible(boolean visible){
		synchronized(workerLock){
			currentImage--;
			workerLock.notify();
		}
		super.setVisible(visible);
	}
	
	public void revalidate(){
		int size = Math.min(this.getHeight(), this.getWidth());
		if(size > 0)
			standbyLabel.setIcon(getCurrentIcon(size));
		
		super.revalidate();
	}
	
	public void setDefault(boolean flag){
		synchronized(workerLock){
			isDefaultImage = flag;
			workerLock.notify();
		}		
	}
	
	public void setReturnOnWake(boolean flag){
		synchronized(workerLock){
			returnToFirst = flag;
			workerLock.notify();
		}		
	}
	
	public void setWaitTime(int t){
		if(t > 0)
			synchronized(workerLock){
				waitTime = t;
			}
	}

	public void setMaxZoom(double zoom){
		if(zoom > 1.0)
			maxZoom = zoom;
	}
	
	public int updateImages(String imageDir){
		boolean wasDefault;
		int locNFrames = 0;
		
		synchronized(workerLock){
			wasDefault = isDefaultImage;
			isDefaultImage = true;
			workerLock.notify();
		}
		imageList.clear();
		
		for(String imagePath : Data.searchForImages(imageDir)){
			FullImage img = Data.loadImage(imagePath);
			if(img != null){
				++locNFrames;
				imageList.add(img);
			}
		}
		
		synchronized(workerLock){
			isDefaultImage = wasDefault;
			nFrames = locNFrames;
			workerLock.notify();
		}
		
		return locNFrames;
	}
	
	private ImageIcon getCurrentIcon(int size){
		FullImage img;
		double scale, zoom;
		int current;
		
		synchronized(workerLock){
			current = currentImage;
		}
		
		if(current == -1 || nFrames < 1)
			img = defaultImage;
		else
			img = imageList.get(current);
		scale = img.originalHeight > img.originalWidth ? -1 : 1;
		
		if(current != -1 && nFrames >= 1) {
			zoom = Math.max(((double)size)/img.originalWidth, ((double)size)/img.originalHeight);
			
			if(zoom > maxZoom)
				scale *= maxZoom/zoom;
		}
		
		return new ImageIcon(img.image.getScaledInstance((int)(scale*size), -(int)(scale*size), Image.SCALE_SMOOTH));
	}
	
	private void initThread(){
		bgTask = new Runnable(){			
			private boolean isDefault, visible;
			private int time, n;
			
			public void run(){
				try{
					while(true){
						
						synchronized(workerLock){
							visible = isVisible();
							time = waitTime;
							n = nFrames;
							isDefault = isDefaultImage || n==0;
						}
						
						if(visible){
							if(isDefault){
								synchronized(workerLock){
									currentImage = -1;
								}
								revalidate();
								synchronized(workerLock){
									workerLock.wait();
								}
							}else if(n > 1){
								synchronized(workerLock){
									currentImage++;
									if(currentImage >= n)
										currentImage=0;
								}
								revalidate();
								synchronized(workerLock){
									workerLock.wait(time);
								}
							}else {
								synchronized(workerLock){
									currentImage = 0;
								}
								revalidate();
								synchronized(workerLock){
									workerLock.wait();
								}
							}
						}else
							synchronized(workerLock){
								if(returnToFirst || currentImage < 0)
									currentImage = 0;
								
								workerLock.wait();
							}
					}
				}catch(InterruptedException e){e.printStackTrace();System.exit(INTER_EX);}
			}
		};
		
		backgroundThread = new Thread(bgTask);
	}
}