// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * A class containing an image and the original sizes for scaling.
 * 
 * @author Remi Lemaire
 */
public class FullImage{
	public Image image;
	
	public int originalHeight, originalWidth;
	
	public FullImage(BufferedImage buffer){
		originalHeight = buffer.getHeight();
		originalWidth = buffer.getWidth();
		image = new ImageIcon(buffer).getImage();
	}
}
