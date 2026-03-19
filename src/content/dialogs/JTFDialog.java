// Copyright (C) 2026 Remi Lemaire

package content.dialogs;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import content.cstm.Data;
import content.cstm.Language;

public class JTFDialog extends JDialog{
	private static final long serialVersionUID = 4L;
	private JTextField jtfPath;
	private JButton jbConfirm,jbShowJFC,jbCancel;
	private JLabel jLbl1;
	private FileDialog fd;
	private boolean acceptDir;
	private Font font;
	private Path path;
	private Language lang;
	
	private JTFDialog(JFrame parent, Path currentPath, Language l, Font f, boolean isDirOk){
		super(parent,l.jtfdialogTitle,ModalityType.APPLICATION_MODAL);
		acceptDir = isDirOk;
		path = currentPath;
		font = f;
		lang = l;
		
		getContentPane().setFocusable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		jLbl1 = new JLabel(lang.pathLabel);
		jLbl1.setFont(font);
		jLbl1.setBounds(20, 5, 280, 25);
		getContentPane().add(jLbl1);
		
		jtfPath = new JTextField();
		jtfPath.setText(path.toString());
		jtfPath.setFont(font.deriveFont(16f));
		jtfPath.setBounds(25, 35, 350, 35);
		jtfPath.addKeyListener(new JTFRefresh());
		getContentPane().add(jtfPath);
		
		jbConfirm = new JButton(lang.confirm);
		jbConfirm.setFont(font);
		jbConfirm.setToolTipText(lang.confirmPathTTT);
		jbConfirm.setFocusable(false);
		jbConfirm.setBounds(200, 125, 125, 25);
		jbConfirm.addActionListener(new BtnConfirm());
		getContentPane().add(jbConfirm);
		
		jbCancel = new JButton(lang.cancel);
		jbCancel.setFont(font);
		jbCancel.setToolTipText(lang.cancelPathTTT);
		jbCancel.setFocusable(false);
		jbCancel.setBounds(50,125,125,25);
		jbCancel.addActionListener(new BtnCancel());
		getContentPane().add(jbCancel);
		
		jbShowJFC = new JButton(lang.explore);
		jbShowJFC.setFont(font);
		jbShowJFC.setToolTipText(lang.exploreTTT);
		jbShowJFC.setFocusable(false);
		jbShowJFC.setBounds(120, 85, 125, 25);
		jbShowJFC.addActionListener(new BtnShowJFC());
		getContentPane().add(jbShowJFC);
		
		fd = new FileDialog(this);
		fd.setTitle(lang.jtfdialogTitle);
		fd.setAlwaysOnTop(true);
		fd.setModalityType(ModalityType.APPLICATION_MODAL);
		fd.setMultipleMode(false);
		fd.setMode(FileDialog.SAVE);
	}

	public static Path showJTFDialog(JFrame parent, Path currentPath, Language l, Font f, boolean isDirOk){
		JTFDialog jtfD = new JTFDialog(parent,currentPath, l, f, isDirOk);
		jtfD.setVisible(true);
		return jtfD.path;
	}
	
	
	//ActionListener
	
	class BtnCancel implements ActionListener{
		public void actionPerformed(ActionEvent e){
			path=null;
			setVisible(false);
		}
	}
	class BtnConfirm implements ActionListener{
		public void actionPerformed(ActionEvent e){
		new JTFRefresh().keyReleased(null);
		setVisible(false);
		}
	}
	class JTFRefresh implements KeyListener{
		public void keyReleased(KeyEvent e){
			String str;
			File f;
			
			str = jtfPath.getText();
			f = new File(str);
			if(f.exists()){
				path = Data.getRootFile(f).toPath();
				jbConfirm.setEnabled(true);
			}else
				jbConfirm.setEnabled(false);
		}
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
	}
	class BtnShowJFC implements ActionListener{
		public void actionPerformed(ActionEvent e){
			File fL[];
			Path newP;
			
			fd.setDirectory(path.toAbsolutePath().toString());
			fd.setVisible(true);
			fL=fd.getFiles();
			if(fL.length!=0){
				File file = fL[0];
				while(!file.exists() && (acceptDir || !file.isDirectory()) )
					file = file.getParentFile();
				
				newP = file.toPath();
				newP = (path.toAbsolutePath()).relativize(newP);
				
				try{
					if(newP.toFile().getCanonicalPath().equals(newP.toAbsolutePath().toString()))
						newP = path.resolve(newP);
					else
						newP = file.toPath().toAbsolutePath();
				}catch (IOException ex){
					System.err.println("Could not properly parse the new path" + ex.toString());
					newP = file.toPath().toAbsolutePath();
				}
				
				path = newP;
				jtfPath.setText(path.toString());
			}
		}
	}
}