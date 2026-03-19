// Copyright (C) 2026 Remi Lemaire

package content.cstm.redone;

import javax.swing.AbstractSpinnerModel;

/**
 * {@link JSpinner} model for the color JSpinner used to pick color.
 * 
 * @author Remi Lemaire
 * 
 * @see ColorCompEditor
 */
public class ColorCompModel extends AbstractSpinnerModel{
	private static final long serialVersionUID = 5L;
	
	private int val;
	private static int max = 255, min = 0;
	
	public ColorCompModel(){
		this(min);
	}
	public ColorCompModel(int value){
		super();
		val = cropVal(value);
	}
	
	public Object getValue() {
		return val;
	}
	
	public void setValue(Object value) {
		if(value instanceof Integer){
			val = cropVal( (int)value );
            fireStateChanged();
		}else
			throw new IllegalArgumentException("value must be int");
	}

	public Object getNextValue(){
		if(++val > max)
			val = max;
		return val;
	}

	public Object getPreviousValue() {
		if(--val < min)
			val = min;
		return val;
	}
	
	private int cropVal(int i){
		if(i<min)
			return min;
		if(i>max)
			return max;
		return i;
	}
}