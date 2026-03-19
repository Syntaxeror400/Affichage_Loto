// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import javax.swing.table.AbstractTableModel;

import content.cstm.Grid;
import content.visuals.LotoLabel;

/**
 * {@link JTable} model for the output table.
 * 
 * @author Remi Lemaire
 */
public class OutputModel extends AbstractTableModel{
	private static final long serialVersionUID = 3L;
	private Grid data;
	
	public OutputModel(Grid grid){
		data = grid;
	}
	
	public int getRowCount() {
		return 9;
	}
	public int getColumnCount() {
		return 10;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col){
		return LotoLabel.class;
	}
	public LotoLabel getValueAt(int row, int col){
		return data.getLabel(row,col);
	}
}