// Copyright (C) 2026 Remi Lemaire

package content.cstm;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * This enumeration regroups all shortcuts {@link KeyStroke} equivalents for an easy use allthrough the program
 * 
 * @author Remi Lemaire
 * 
 * @see GUI
 * @see ShortcutsDispatcher
 */

public enum Shortcuts{
	CMD_1(KeyEvent.VK_1),
	CMD_2(KeyEvent.VK_2),
	CMD_3(KeyEvent.VK_3),
	CMD_W(KeyEvent.VK_W),
	CMD_Q(KeyEvent.VK_Q),
	CMD_R(KeyEvent.VK_R),
	CMD_T(KeyEvent.VK_T),
	CMD_L(KeyEvent.VK_L),
	CMD_M(KeyEvent.VK_M),
	CMD_Z(KeyEvent.VK_Z),
	CMD_ALT_Z(KeyEvent.VK_Z, "alt"),
	CMD_S(KeyEvent.VK_S),
	CMD_SHIFT_S(KeyEvent.VK_S, "shift"),
	CMD_ALT_S(KeyEvent.VK_S, "alt"),
	CMD_SHIFT_SPACE(KeyEvent.VK_SPACE, "shift"),
	CMD_O(KeyEvent.VK_O);
	
	public final KeyStroke keys;
	private static final int shiftMask = java.awt.event.InputEvent.SHIFT_DOWN_MASK,
			altMask = java.awt.event.InputEvent.ALT_DOWN_MASK;
	
	/**
	 * Creates a new {@code Shortcut} item containing the system command mask ('Cmd' key for MacOS, 'Ctrl' key for Windows/Linux) and the given key.
	 * 
	 * @param key
	 * 
	 * @author Remi Lemaire
	 * 
	 * @see Shortcuts(int, String)
	 */
	Shortcuts(int key){
		this(key, "");
	}
	/**
	 * Creates a new {@code Shortcut} item containing the system command mask ('Cmd' key for MacOS, 'Ctrl' key for Windows/Linux) and the gien key.
	 * Depending on the {@code mask} argument, it can implements a 'shift' or 'alt' key in the shortcut. 
	 * 
	 * @param key
	 * @param mask
	 * Authorized values : "{@code alt}", "{@code shift}".
	 * Any other value will be the same as calling {@linkplain #Shortcuts(int)}
	 * 
	 * @author Remi Lemaire
	 * 
	 * @see #Shortcuts(int)
	 */
	Shortcuts(int key, String mask){
		int intMask;
		switch(mask){
		case "alt" :
			intMask = altMask;
			break;
		case "shift" :
			intMask = shiftMask;
			break;
		default :
			intMask = 0;
			break;
		}
		this.keys = KeyStroke.getKeyStroke(key, intMask | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
	}
}