// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;

/**
 * {@link JSpinner} editor for the color JSpinner used to pick color.
 * 
 * @author Remi Lemaire
 * 
 * @see ColorCompModel
 */
public class ColorCompEditor extends DefaultEditor {
	private static final long serialVersionUID = 6L;
	private JFormattedTextField jtf;

	public ColorCompEditor(JSpinner spinner) {
        super(spinner);
        
        jtf = getTextField();
        jtf.setEditable(true);
        jtf.setHorizontalAlignment(JTextField.RIGHT);
        jtf.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){}
			
			public void focusGained(FocusEvent e){
				jtf.selectAll();
			}
        });
	}
}