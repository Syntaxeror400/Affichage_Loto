// Copyright (C) 2026 Remi Lemaire

package content.dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import content.cstm.Language;

public class SliderDialog extends JDialog{
	private static final long serialVersionUID = 5L;
	private JSlider slider;
	private JButton jbConfirm;
	private JLabel jLbl1;
	private JTextField jtfValue;
	private Language lang;
	
	private SliderDialog(JFrame parent,int size, Language l){
		super(parent,l.sliderdialogTitle,ModalityType.APPLICATION_MODAL);
		getContentPane().setFocusable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		lang = l;
		
		slider = new JSlider();
		slider.setToolTipText(lang.sliderTTT);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setMaximum(size);
		slider.setValue(1);
		slider.setMinimum(1);
		slider.setFocusable(false);
		slider.addChangeListener(new SliderChanged());
		slider.setBounds(25, 70, 350, 35);
		getContentPane().add(slider);
		
		jbConfirm = new JButton(lang.confirm);
		jbConfirm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jbConfirm.setBounds(130, 125, 120, 25);
		jbConfirm.setFocusable(false);
		jbConfirm.addActionListener(new BtnConfirm());
		getContentPane().add(jbConfirm);
		
		jLbl1 = new JLabel(lang.orderLabel);
		jLbl1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLbl1.setBounds(30, 20, 175, 16);
		getContentPane().add(jLbl1);
		
		jtfValue = new JTextField("1",JLabel.LEFT);
		jtfValue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jtfValue.setBounds(205, 20, 60, 20);
		jtfValue.setFocusable(true);
		getContentPane().add(jtfValue);
	}
	
	public static int showSliderDialog(JFrame parent,int size, Language l){
		if(size>0){
			SliderDialog sd = new SliderDialog(parent,size+1,l);
			sd.setVisible(true);
			return sd.slider.getValue()-1;
		}else
			return 0;
	}
	
	
	//ActionListener
	
	class SliderChanged implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			jtfValue.setText(String.valueOf(slider.getValue()));
		}
	}
	class BtnConfirm implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
		}
	}
	class ValueListener implements ActionListener,KeyListener,MouseListener{
		public void mouseClicked(MouseEvent e){
			jtfValue.selectAll();
		}
		public void actionPerformed(ActionEvent e){
			(new ValueListener()).keyReleased(null);
			setVisible(false);
		}
		public void keyReleased(KeyEvent e){
			String temp;
			char[] temp2,temp3;
			int k=0,fLenght=0,temp4;
			
			temp = jtfValue.getText();
			temp2 = temp.toCharArray();
			for(int i=0;i<temp2.length;i++)
				if(temp2[i]=='0' || temp2[i]=='1' || temp2[i]=='2' || temp2[i]=='3' || temp2[i]=='4' || temp2[i]=='5' || temp2[i]=='6' || temp2[i]=='7' || temp2[i]=='8' || temp2[i]=='9')
					fLenght++;
			
			if(fLenght>0){
				temp3 = new char[fLenght];
				for(int i=0;i<fLenght;i++)
					if(temp2[i]=='0' || temp2[i]=='1' || temp2[i]=='2' || temp2[i]=='3' || temp2[i]=='4' || temp2[i]=='5' || temp2[i]=='6' || temp2[i]=='7' || temp2[i]=='8' || temp2[i]=='9'){
					temp3[k] = temp2[i];
					k++;
				}
				temp = String.valueOf(temp3);
				if(temp.length()>2)temp = temp.substring(0, 2);
				temp4 = Integer.valueOf(temp);
				if(temp4>slider.getMaximum() || temp4<1)
					jtfValue.setText("1");
				else{jtfValue.setText(temp);}
			}else
				jtfValue.setText("1");
			slider.setValue(Integer.valueOf(jtfValue.getText()));
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void keyPressed(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
	}
}