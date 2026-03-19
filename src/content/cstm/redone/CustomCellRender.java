// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import content.visuals.LotoLabel;

/**
 * Cell renderer for the output.
 * 
 * @author Remi Lemaire
 * 
 * @see OutputModel
 */
public class CustomCellRender extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 2L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		return (LotoLabel)value;
	}
}