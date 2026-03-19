// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import content.cstm.Shortcuts;

/**
 * This class is used to allow for application wide keyboard shortcuts.
 * 
 * @author Remi Lemaire
 * 
 * @see GUI
 * @see Shortcuts
 */

public class ShortcutsDispatcher implements KeyEventDispatcher{
	private HashMap<KeyStroke, AbstractButton> map;
	private int clickTime;
	
	public ShortcutsDispatcher(int clickTime){
		map = new HashMap<KeyStroke,AbstractButton>();
		this.clickTime = clickTime;
	}
	
	public boolean addShortcut(Shortcuts shortcut, AbstractButton but){
		if(but == null)
			return false;
		return map.put(shortcut.keys, but) == null;
	}
	public boolean replaceShortcut(Shortcuts shortcut, AbstractButton but){
		if(but == null)
			return false;
		return map.replace(shortcut.keys, but) != null;
	}
	public boolean removeShortcut(Shortcuts shortcut){
		return map.remove(shortcut.keys) != null;
	}
	public void clearShortcuts(){
		map.clear();
	}
	
	public boolean dispatchKeyEvent(KeyEvent e){
		KeyStroke shortcut = KeyStroke.getKeyStrokeForEvent(e);
		if(map.containsKey(shortcut)){
			final AbstractButton but = map.get(shortcut);
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					but.doClick(clickTime);
				}
			});
			return true;
		}
		return false;
	}
}