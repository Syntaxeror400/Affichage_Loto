// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import content.cstm.Grid;
import content.visuals.LotoLabel;

/**
 * {@link OutputModel} redone for the control panel.
 * 
 * @author Remi Lemaire
 * 
 * @see OutputModel
 */
public class ControlModel extends OutputModel{
	private static final long serialVersionUID = 9L;

	public ControlModel(Grid grid) {
		super(grid);
	}
	
	public LotoLabel getValueAt(int row, int col){
		return super.getValueAt(row, col).getSmall();
	}
}