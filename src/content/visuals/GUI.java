// Copyright (C) 2026 Remi Lemaire

package content.visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.desktop.QuitStrategy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Path;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import content.cstm.Data;
import content.cstm.Data.DataException;
import content.cstm.Grid;
import content.cstm.Language;
import content.cstm.Settings;
import content.cstm.Shortcuts;
import content.cstm.redone.ColorCompEditor;
import content.cstm.redone.ColorCompModel;
import content.cstm.redone.ControlModel;
import content.cstm.redone.CustomCellRender;
import content.cstm.redone.OutputModel;
import content.cstm.redone.ShortcutsDispatcher;
import content.dialogs.JTFDialog;
import content.dialogs.SliderDialog;

/**
 * The {@code GUI} class contains all the frames and component needed for the graphics
 * of the loto application and deals with all the interaction between those components.
 * <p>
 * No public method except {@code setVisible()} is available because once a instance of GUI is running, 
 * it deals with all the user inputs and executes a {@code System.exit(0)} if the main frame or the output frame are closed.
 * 
 * @author Remi Lemaire
 * 
 * @see LotoLabel
 * @see Data
 * @see Grid
 **/

public class GUI{
	
	//GUI variables
	
	private JFrame mainFrame,outputFrame,adminFrame,controlFrame,settingsFrame;
	private JPanel jpBaseMain,jpHigh,jpLow,jpBaseOut,jpBaseAdmin,jpBaseControl,jpSetG,jpSetBg,jpSetFC, jpSetStdby, jpSetNBg,jpSetVF,jpSetIF,jpSetNF,
					jpSetLBg,jpSetDBg,jpNBgShow,jpDBgShow,jpLBgShow,jpNFShow,jpIFShow,jpVFShow,jpSetTop,jpSetMid,jpSetBot,jpSetStdbyDef,jpSetStdbyPath,
					jpSetStdbyWait, jpSetStdbyNImgs;
	private StandbyPanel jpStandby;
	private JTabbedPane jpBaseSettigns;
	private JTable jtOutput,jtControl;
	private JMenuBar mainMBar;
	private JMenu jmFile,jmEdit,jmSerie,jmWindows,jmView,subResPos;
	private JMenuItem miClose,miQuit,miUndo,miReset,miEmptyCheck,miChooseDir,miSaveStats,miSaveAs,miResTabSize,miResTabPos,miResTabAll,miResMainPos,
						miResAdminPos,miResAllPos,miAdmin,miControl,miSettings,miStandBy;
	private JRadioButton jrBold,jrPlain,jrASaveF,jrASaveT,jrStdbyDefT,jrStdbyDefF;
	private ButtonGroup series,frames,style,autoS,standbyAnim;
	private JRadioButtonMenuItem s1,s2,s3,w1,w2,w3,w4,w5;
	private JButton jbClear,jbShowAll,jbTake,jbUndo,jbRedo,jbClearCheck,jbCheckAll,jbCheck,jbUndoCheck,jbRedoCheck,
					jbAdminDel,jbAdminDelCheck,jbAdminAdd,jbAdminCancel,jbAdminConfirm,
					jbResetNBg,jbConfirmNBg,jbCancelNBg, jbResetDBg,jbConfirmDBg,jbCancelDBg, jbResetLBg,jbConfirmLBg,jbCancelLBg,
					jbResetNF,jbConfirmNF,jbCancelNF, jbResetIF,jbConfirmIF,jbCancelIF, jbResetVF,jbConfirmVF,jbCancelVF,
					jbConfirmSize,jbCancelSize,jbResetFont, jbResetSet,jbSetSaveFolder,jbLoadSet,jbSaveSettings,jbConfirmFName,jbCancelFName,
					jbSetImgFolder;
	private JSpinner spinNBgR,spinNBgG,spinNBgB,spinDBgR,spinDBgG,spinDBgB,spinLBgR,spinLBgG,spinLBgB,spinNFR,spinNFG,spinNFB,spinIFR,spinIFG,spinIFB,
					spinVFR,spinVFG,spinVFB,spinSize,spinWait;
	private JCheckBox jcbVBgRev;
	private JLabel jLblNumber,jLblSerieA,jLblNbCheck,jLblRequired,jLblValid,jLblInvalid,jLblAdminNb,jLblNBgR,jLblNBgG,jLblNBgB,jLblDBgR,jLblDBgG,jLblDBgB,
					jLblLBgR,jLblLBgG,jLblLBgB,jLblNFR,jLblNFG,jLblNFB,jLblIFR,jLblIFG,jLblIFB,jLblVFR,jLblVFG,jLblVFB,jLblSize,jLblAutoSave,
					jLblCurrentSave, jLblFontName,jLblDefault,jLblWait, JLblWaitUnit,jLblStdbyPath, jLblStdbyTxtImgs, jLblStdbyNImgs;
	private JTextField jtfTake,jtfCheck,jtfAdmin,jtfSaveFolder,jtfFontName,jtfStandbyImages;
	private FileDialog fd;
	private Font font;
	private ShortcutsDispatcher dispatcher;
	public static Language lang;
	
	//Program variables
	
	private Settings settings;
	private int lastAct,lastCheckAct,nbrCheck,nbrErr;
	private boolean switchable,clearMode,clearCheckMode, standBy, buttonMemory[];
	private String nSerie;
	private Data data;
	private Grid grid;
	
	
	//Public methods
	
	public GUI(){
		if(Data.MAC_OS){
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			Desktop.getDesktop().setQuitStrategy(QuitStrategy.NORMAL_EXIT);
		}
		settings = Data.getDefaultSettings();
		
		lang = settings.lang;
		dispatcher = new ShortcutsDispatcher(Data.params.clickTime);
		
		grid = new Grid();
		font = new Font(Data.params.fontName, Font.PLAIN, Data.params.fontSize);
		lastAct=0;
		nbrCheck=0;
		nbrErr=0;
		clearMode=true;
		clearCheckMode=true;
		switchable=false;
		standBy=false;
		buttonMemory = new boolean[6];
		data = new Data();
		
		initializeMain();
		initializeOutput();
		initializeAdmin();
		initializeControl();
		initializeFD();
		initializeSettings();
		initializeStandBy();
		initializeMenuBar();
		
		if(Data.MAC_OS)
			Desktop.getDesktop().setDefaultMenuBar(mainMBar);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
	}
	
	public void setVisible(){
		outputFrame.setVisible(true);
		mainFrame.setVisible(true);
		mainFrame.setJMenuBar(mainMBar);
		
		int x=jpBaseMain.getWidth(), y=jpBaseMain.getHeight(), h=Data.params.mainHeight, w=Data.params.mainWidth;
		if(x!=w || y!=h)
			mainFrame.setSize(2*w-x, 2*h-y);
		x=jpBaseOut.getWidth();
		y=jpBaseOut.getHeight();
		w = Data.params.outputDefaultSize;
		if(x!=w || y!=w)
			outputFrame.setSize(2*w-x,2*w-y);
		
		preloadSettings(false);
	}
	
	
	//Private methods
	
	private void setClear(){
		clearMode=true;
		jbClear.setText(lang.clearAll);
		jbClear.setToolTipText(lang.clearAllTTT);
		jbClear.repaint();
	}
	private void setReset(){
		jbClear.setText(lang.reset);
		jbClear.setToolTipText(lang.resetTTT);
		clearMode=false;
		jbClear.repaint();
	}
	private void setCheckClear(){
		clearCheckMode=true;
		jbClearCheck.setText(lang.clearAll);
		jbClearCheck.setToolTipText(lang.emptyCheckTTT);
		jbClearCheck.repaint();
	}
	private void setCheckReset(){
		clearCheckMode=false;
		jbClearCheck.setText(lang.reset);
		jbClearCheck.setToolTipText(lang.resetCheckTTT);
		jbClearCheck.repaint();
	}
	
	private void deactivateButtons(){
		buttonMemory[0] = jbUndo.isEnabled();
		buttonMemory[1] = jbRedo.isEnabled();
		buttonMemory[2] = jbClear.isEnabled();
		buttonMemory[3] = jbUndoCheck.isEnabled();
		buttonMemory[4] = jbRedoCheck.isEnabled();
		buttonMemory[5] = jbClearCheck.isEnabled();
		
		jtfTake.setEnabled(false);
		jbTake.setEnabled(false);
		jbUndo.setEnabled(false);
		jbRedo.setEnabled(false);
		jbClear.setEnabled(false);
		jbShowAll.setEnabled(false);
		jtfCheck.setEnabled(false);
		jbCheck.setEnabled(false);
		jbUndoCheck.setEnabled(false);
		jbRedoCheck.setEnabled(false);
		jbClearCheck.setEnabled(false);
		jbCheckAll.setEnabled(false);
		
		miAdmin.setEnabled(false);
	}
	private void restoreButtons(){
		jtfTake.setEnabled(true);
		jbTake.setEnabled(true);
		jbUndo.setEnabled(buttonMemory[0]);
		jbRedo.setEnabled(buttonMemory[1]);
		jbClear.setEnabled(buttonMemory[2]);
		jbShowAll.setEnabled(true);
		jtfCheck.setEnabled(true);
		jbCheck.setEnabled(true);
		jbUndoCheck.setEnabled(buttonMemory[3]);
		jbRedoCheck.setEnabled(buttonMemory[4]);
		jbClearCheck.setEnabled(buttonMemory[5]);
		jbCheckAll.setEnabled(true);
		
		miAdmin.setEnabled(true);
	}
	
	private void updateUndoRedo(){
		if(lastAct==1){
			jbUndo.setEnabled(grid.numberSize()!=0);
			miUndo.setEnabled(grid.numberSize()!=0);
			jbRedo.setEnabled(grid.undoneSize()!=0);
		}else{
			boolean b= lastAct!=0 && (lastAct==2 || lastAct==3);
			jbUndo.setEnabled(b);
			miUndo.setEnabled(b);
			jbRedo.setEnabled(!b);
		}
	}
	private void updateUndoRedoCheck(){
		if(lastCheckAct==1){
			jbUndoCheck.setEnabled(grid.checkedSize()!=0);
			miEmptyCheck.setEnabled(grid.checkedSize()!=0);
			jbRedoCheck.setEnabled(grid.uncheckedSize()!=0);
		}else{
			boolean b= lastCheckAct!=0 && (lastCheckAct==2 || lastCheckAct==3);
			jbUndoCheck.setEnabled(b);
			miEmptyCheck.setEnabled(b);
			jbRedoCheck.setEnabled(!b);
		}
	}
	private void updateLabels(){
		if(nbrCheck>1)
			jLblValid.setText(nbrCheck+lang.validNbrs);
		else
			jLblValid.setText(nbrCheck+lang.validNbr);			
		if(nbrErr>1)
			jLblInvalid.setText(nbrErr+lang.wrongNbrs);
		else
			jLblInvalid.setText(nbrErr+lang.wrongNbr);
		jLblValid.repaint();
		jLblInvalid.repaint();
	}
	private void repaintOut(){
		grid.repaint();
		jtOutput.repaint();
		jtControl.repaint();
		jpStandby.repaint();
	}
	
	private void initializeMain(){
		mainFrame = new JFrame();
		mainFrame.setTitle(lang.mainTitle);
		mainFrame.setSize(Data.params.mainWidth,Data.params.mainHeight);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setFocusable(true);
		mainFrame.addWindowListener(new FrameListener());
		mainFrame.addKeyListener(new ShortcutListener());
		mainFrame.setName("main");
				
		jpBaseMain = new JPanel();
		mainFrame.setContentPane(jpBaseMain);
		jpBaseMain.setLayout(new GridLayout(2, 1, 0, 0));
		jpBaseMain.setFocusable(false);
		
		jpHigh = new JPanel();
		jpHigh.setFocusable(false);
		jpHigh.setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), lang.numberBorder, TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		jpBaseMain.add(jpHigh);
		
		jLblNumber = new JLabel(lang.numberLabel);
		jLblNumber.setFont(font);
		
		jtfTake = new JTextField();
		jtfTake.setDragEnabled(false);
		jtfTake.setName("taker");
		jtfTake.addMouseListener(new JTFListener());
		jtfTake.addActionListener(new JTFListener());
		jtfTake.addKeyListener(new JTFListener());
		jtfTake.addKeyListener(new ShortcutListener());
		jtfTake.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jtfTake.setColumns(10);
		
		jbClear = new JButton(lang.clearAll);
		jbClear.setPreferredSize(new Dimension(100, 30));
		jbClear.setName("clear");
		jbClear.setToolTipText(lang.clearAllTTT);
		jbClear.addActionListener(new MainBtnListener());
		jbClear.setFont(font);
		jbClear.setFocusable(false);
		jbClear.setEnabled(false);

		jbShowAll = new JButton(lang.showAll);
		jbShowAll.setPreferredSize(new Dimension(100, 30));
		jbShowAll.setName("showAll");
		jbShowAll.setToolTipText(lang.showAllTTT);
		jbShowAll.addActionListener(new MainBtnListener());
		jbShowAll.setFont(font);
		jbShowAll.setFocusable(false);
		
		jLblSerieA = new JLabel(lang.seriesTypeA,JLabel.CENTER);
		jLblSerieA.setFont(font);
		
		jbTake = new JButton(lang.confirm);
		jbTake.setName("taker");
		jbTake.addActionListener(new JTFListener());
		jbTake.setToolTipText(lang.confirmAddTTT);
		jbTake.setFont(font);
		jbTake.setFocusable(false);
		
		jbUndo = new JButton(lang.undo);
		jbUndo.setPreferredSize(new Dimension(100, 30));
		jbUndo.setName("undo");
		jbUndo.addActionListener(new MainBtnListener());
		jbUndo.setToolTipText(lang.undoTTT);
		jbUndo.setFont(font);
		jbUndo.setFocusable(false);
		jbUndo.setEnabled(false);

		jbRedo = new JButton(lang.redo);
		jbRedo.setPreferredSize(new Dimension(100, 30));
		jbRedo.setName("redo");
		jbRedo.addActionListener(new MainBtnListener());
		jbRedo.setToolTipText(lang.redoTTT);
		jbRedo.setFont(font);
		jbRedo.setFocusable(false);
		jbRedo.setEnabled(false);
		
		GroupLayout glHigh = new GroupLayout(jpHigh);
		//> jpHigh layout and components 
		glHigh.setHorizontalGroup(
			glHigh.createParallelGroup(Alignment.LEADING)
				.addGroup(glHigh.createSequentialGroup()
					.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
						.addGroup(glHigh.createSequentialGroup()
							.addGap(35)
							.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
								.addGroup(glHigh.createSequentialGroup()
									.addGap(20)
									.addComponent(jLblNumber, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
								.addGroup(glHigh.createSequentialGroup()
									.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
										.addGroup(glHigh.createSequentialGroup()
											.addComponent(jbTake, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addGap(130)
											.addComponent(jbRedo, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
										.addGroup(glHigh.createSequentialGroup()
											.addGap(30)
											.addComponent(jtfTake, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
											.addGap(160)
											.addComponent(jbUndo, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
									.addGap(40)
									.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
										.addComponent(jbClear, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
										.addComponent(jbShowAll, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(glHigh.createSequentialGroup()
							.addGap(98)
							.addComponent(jLblSerieA, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		glHigh.setVerticalGroup(
			glHigh.createParallelGroup(Alignment.LEADING)
				.addGroup(glHigh.createSequentialGroup()
					.addGap(14)
					.addGroup(glHigh.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(glHigh.createSequentialGroup()
							.addComponent(jLblNumber, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(jtfTake, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(glHigh.createSequentialGroup()
							.addGroup(glHigh.createParallelGroup(Alignment.BASELINE)
								.addComponent(jbUndo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(jbClear, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addGap(26)))
					.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
						.addGroup(glHigh.createParallelGroup(Alignment.LEADING)
							.addGroup(glHigh.createSequentialGroup()
								.addGap(5)
								.addComponent(jbTake, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addComponent(jbShowAll, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(glHigh.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbRedo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(jLblSerieA, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		glHigh.linkSize(SwingConstants.VERTICAL, new Component[] {jbClear, jbShowAll, jbUndo, jbRedo});
		glHigh.linkSize(SwingConstants.HORIZONTAL, new Component[] {jbClear, jbShowAll, jbUndo, jbRedo});
		//<>
		jpHigh.setLayout(glHigh);

		jpLow = new JPanel();
		jpLow.setFocusable(false);
		jpLow.setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), lang.checkBorder, TitledBorder.LEFT, TitledBorder.TOP, null, Color.black));
		jpBaseMain.add(jpLow);
		
		jLblNbCheck = new JLabel(lang.numberLabel);
		jLblNbCheck.setFont(font);
		
		jtfCheck = new JTextField();
		jtfCheck.setName("checker");
		jtfCheck.addActionListener(new JTFListener());
		jtfCheck.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jtfCheck.setColumns(10);
		jtfCheck.addMouseListener(new JTFListener());
		jtfCheck.addKeyListener(new JTFListener());
		jtfCheck.addKeyListener(new ShortcutListener());
		
		jbClearCheck = new JButton(lang.clearAll);
		jbClearCheck.setName("clearCheck");
		jbClearCheck.addActionListener(new MainBtnListener());
		jbClearCheck.setToolTipText(lang.emptyCheckTTT);
		jbClearCheck.setFont(font);
		jbClearCheck.setFocusable(false);
		jbClearCheck.setEnabled(false);
		
		jbCheckAll = new JButton(lang.showAll);
		jbCheckAll.setName("checkAll");
		jbCheckAll.setToolTipText(lang.checkAllTTT);
		jbCheckAll.setFont(font);
		jbCheckAll.addActionListener(new MainBtnListener());
		jbCheckAll.setFocusable(false);
		
		jbCheck = new JButton(lang.check);
		jbCheck.setToolTipText(lang.checkTTT);
		jbCheck.setFont(font);
		jbCheck.setName("checker");
		jbCheck.addActionListener(new JTFListener());
		jbCheck.setFocusable(false);
		
		jbUndoCheck = new JButton(lang.undo);
		jbUndoCheck.setName("undoCheck");
		jbUndoCheck.addActionListener(new MainBtnListener());
		jbUndoCheck.setToolTipText(lang.undoTTT);
		jbUndoCheck.setFont(font);
		jbUndoCheck.setFocusable(false);
		jbUndoCheck.setEnabled(false);

		jbRedoCheck = new JButton(lang.redo);
		jbRedoCheck.setName("redoCheck");
		jbRedoCheck.addActionListener(new MainBtnListener());
		jbRedoCheck.setToolTipText(lang.redoTTT);
		jbRedoCheck.setFont(font);
		jbRedoCheck.setFocusable(false);
		jbRedoCheck.setEnabled(false);

		jLblRequired = new JLabel(lang.requiredA);
		jLblRequired.setFont(font);
		
		jLblValid = new JLabel(0+lang.validNbr);
		jLblValid.setFont(font);
		
		jLblInvalid = new JLabel(0+lang.wrongNbr);
		jLblInvalid.setFont(font);
		
		GroupLayout glLow = new GroupLayout(jpLow);
		//>jpLow layout and components 
		glLow.setHorizontalGroup(
			glLow.createParallelGroup(Alignment.LEADING)
				.addGroup(glLow.createSequentialGroup()
					.addGap(25)
					.addComponent(jLblNbCheck, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(55)
					.addComponent(jLblRequired, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
				.addGroup(glLow.createSequentialGroup()
					.addGap(40)
					.addComponent(jtfCheck, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addGroup(glLow.createParallelGroup(Alignment.LEADING)
						.addGroup(glLow.createSequentialGroup()
							.addGap(155)
							.addComponent(jbUndoCheck, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addComponent(jLblValid, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addComponent(jbClearCheck, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
				.addGroup(glLow.createSequentialGroup()
					.addGap(10)
					.addComponent(jbCheck, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addGroup(glLow.createParallelGroup(Alignment.LEADING)
						.addGroup(glLow.createSequentialGroup()
							.addGap(155)
							.addComponent(jbRedoCheck, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addComponent(jLblInvalid, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addComponent(jbCheckAll, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
		);
		glLow.setVerticalGroup(
			glLow.createParallelGroup(Alignment.LEADING)
				.addGroup(glLow.createSequentialGroup()
					.addGap(12)
					.addGroup(glLow.createParallelGroup(Alignment.LEADING)
						.addGroup(glLow.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblNbCheck, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addComponent(jLblRequired, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGroup(glLow.createParallelGroup(Alignment.LEADING)
						.addGroup(glLow.createSequentialGroup()
							.addGap(10)
							.addComponent(jtfCheck, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(glLow.createSequentialGroup()
							.addGap(25)
							.addGroup(glLow.createParallelGroup(Alignment.LEADING)
								.addComponent(jbUndoCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLblValid, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addComponent(jbClearCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGroup(glLow.createParallelGroup(Alignment.LEADING)
						.addGroup(glLow.createSequentialGroup()
							.addGap(10)
							.addComponent(jbCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(glLow.createSequentialGroup()
							.addGap(20)
							.addGroup(glLow.createParallelGroup(Alignment.LEADING)
								.addGroup(glLow.createSequentialGroup()
									.addGap(5)
									.addComponent(jbRedoCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addComponent(jLblInvalid, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addComponent(jbCheckAll, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
		);
		glLow.linkSize(SwingConstants.VERTICAL, new Component[] {jbClearCheck, jbCheckAll, jbUndoCheck, jbRedoCheck});
		glLow.linkSize(SwingConstants.HORIZONTAL, new Component[] {jbClearCheck, jbCheckAll, jbUndoCheck, jbRedoCheck});
		//<>
		jpLow.setLayout(glLow);
	}
	private void initializeMenuBar(){
		mainMBar = new JMenuBar();
		jmFile = new JMenu(lang.fileMenu);
		jmEdit = new JMenu(lang.editMenu);	
		jmSerie = new JMenu(lang.seriesMenu);
		jmView = new JMenu(lang.viewMenu);
		jmWindows = new JMenu(lang.windowMenu);
		subResPos = new JMenu(lang.resetPosMenu);
		
		miClose = new JMenuItem(lang.closeMItem);
		if(!Data.MAC_OS)
			miQuit = new JMenuItem(lang.quitMItem);
		miUndo = new JMenuItem(lang.undo);
		miReset = new JMenuItem(lang.reset);
		miEmptyCheck = new JMenuItem(lang.emptyCheckMItem);
		miChooseDir = new JMenuItem(lang.pathMItem);
		miSaveStats = new JMenuItem(lang.saveMItem);
		miSaveAs = new JMenuItem(lang.saveAsMItem);
		miStandBy = new JMenuItem(lang.standByMenuItem);
		miResTabSize = new JMenuItem(lang.resetTabSizeMItem);
		miResTabPos = new JMenuItem(lang.resetTabPosMItem);
		miResMainPos = new JMenuItem(lang.resetMainPosMItem);
		miResAdminPos = new JMenuItem(lang.resetAdminPosMItem);
		miResAllPos = new JMenuItem(lang.resetAllPosMItem);
		miResTabAll = new JMenuItem(lang.resetTabAllMItem);
		miAdmin = new JMenuItem(lang.adminMItem);
		miControl = new JMenuItem(lang.controlMItem);
		miSettings = new JMenuItem(lang.settingsMItem);
		
		jmWindows.addMenuListener(new FrameMenuState());
		s1 = new JRadioButtonMenuItem(lang.seriesTypeA);
		s2 = new JRadioButtonMenuItem(lang.seriesTypeB);
		s3 = new JRadioButtonMenuItem(lang.seriesTypeC);
		w1 = new JRadioButtonMenuItem(lang.mainTitle);
		w2 = new JRadioButtonMenuItem(lang.outputTitle);
		w3 = new JRadioButtonMenuItem(lang.adminTitle);
		w4 = new JRadioButtonMenuItem(lang.controlTitle);
		w5 = new JRadioButtonMenuItem(lang.settingsTitle);
		s1.addItemListener(new RadioSerie());
		s1.setAccelerator(Shortcuts.CMD_1.keys);
		dispatcher.addShortcut(Shortcuts.CMD_1, s1);
		s2.addItemListener(new RadioSerie());
		s2.setAccelerator(Shortcuts.CMD_2.keys);
		dispatcher.addShortcut(Shortcuts.CMD_2, s2);
		s3.addItemListener(new RadioSerie());
		s3.setAccelerator(Shortcuts.CMD_3.keys);
		dispatcher.addShortcut(Shortcuts.CMD_3, s3);
		w1.addItemListener(new RadioWindows());
		w1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		w2.addItemListener(new RadioWindows());
		w2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
		w3.addItemListener(new RadioWindows());
		w3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		w4.addItemListener(new RadioWindows());
		w4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,0));
		w5.addItemListener(new RadioWindows());
		w5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		series = new ButtonGroup();
		series.add(s1);
		series.add(s2);
		series.add(s3);
		frames = new ButtonGroup();
		frames.add(w1);
		frames.add(w2);
		frames.add(w3);
		frames.add(w4);
		frames.add(w5);
		s1.setSelected(true);
		w1.setSelected(true);
		miClose.setName("miClose");
		miClose.addActionListener(new MiListener());
		miClose.setAccelerator(Shortcuts.CMD_W.keys);
		dispatcher.addShortcut(Shortcuts.CMD_W, miClose);
		miClose.setEnabled(false);
		if(!Data.MAC_OS){
			miQuit.setName("miQuit");
			miQuit.addActionListener(new MiListener());
			miQuit.setAccelerator(Shortcuts.CMD_Q.keys);
			dispatcher.addShortcut(Shortcuts.CMD_Q, miQuit);
		}
		miUndo.setName("undo");
		miUndo.addActionListener(new MainBtnListener());
		miUndo.setAccelerator(Shortcuts.CMD_Z.keys);
		dispatcher.addShortcut(Shortcuts.CMD_Z, miUndo);
		miReset.setName("miReset");
		miReset.addActionListener(new MiListener());
		miReset.setAccelerator(Shortcuts.CMD_R.keys);
		dispatcher.addShortcut(Shortcuts.CMD_R, miReset);
		miEmptyCheck.setName("clearCheck");
		miEmptyCheck.addActionListener(new MainBtnListener());
		miEmptyCheck.setAccelerator(Shortcuts.CMD_ALT_Z.keys);
		dispatcher.addShortcut(Shortcuts.CMD_ALT_Z, miEmptyCheck);
		miChooseDir.setName("miChooseDir");
		miChooseDir.addActionListener(new MiListener());
		miChooseDir.setAccelerator(Shortcuts.CMD_ALT_S.keys);
		dispatcher.addShortcut(Shortcuts.CMD_ALT_S, miChooseDir);
		miSaveStats.setName("miSaveStats");
		miSaveStats.addActionListener(new MiListener());
		miSaveStats.setAccelerator(Shortcuts.CMD_S.keys);
		dispatcher.addShortcut(Shortcuts.CMD_S, miSaveStats);
		miSaveAs.setName("miSaveAs");
		miSaveAs.addActionListener(new MiListener());
		miSaveAs.setAccelerator(Shortcuts.CMD_SHIFT_S.keys);
		dispatcher.addShortcut(Shortcuts.CMD_SHIFT_S, miSaveAs);
		miStandBy.setName("miStandBy");
		miStandBy.addActionListener(new MiListener());
		miStandBy.setAccelerator(Shortcuts.CMD_SHIFT_SPACE.keys);
		dispatcher.addShortcut(Shortcuts.CMD_SHIFT_SPACE, miStandBy);
		miResTabSize.setName("miResTabSize");
		miResTabSize.addActionListener(new MiListener());
		miResTabPos.setName("miResTabPos");
		miResTabPos.addActionListener(new MiListener());
		miResMainPos.setName("miResMainPos");
		miResMainPos.addActionListener(new MiListener());
		miResAdminPos.setName("miResAdminPos");
		miResAdminPos.addActionListener(new MiListener());
		miResAllPos.setName("miResAllPos");
		miResAllPos.addActionListener(new MiListener());
		miResTabAll.setName("miResTabAll");
		miResTabAll.addActionListener(new MiListener());
		miResTabAll.setAccelerator(Shortcuts.CMD_T.keys);
		dispatcher.addShortcut(Shortcuts.CMD_T, miResTabAll);
		miAdmin.setName("miAdmin");
		miAdmin.addActionListener(new MiListener());
		miAdmin.setAccelerator(Shortcuts.CMD_L.keys);
		dispatcher.addShortcut(Shortcuts.CMD_L, miAdmin);
		miControl.setName("miControl");
		miControl.addActionListener(new MiListener());
		miControl.setAccelerator(Shortcuts.CMD_M.keys);
		dispatcher.addShortcut(Shortcuts.CMD_M, miControl);
		miSettings.setName("miSettings");
		miSettings.addActionListener(new MiListener());
		miSettings.setAccelerator(Shortcuts.CMD_O.keys);
		dispatcher.addShortcut(Shortcuts.CMD_O, miSettings);
		subResPos.add(miResTabPos);
		subResPos.add(miResMainPos);
		subResPos.addSeparator();
		subResPos.add(miResAllPos);
		
		jmSerie.add(s1);
		jmSerie.add(s2);
		jmSerie.add(s3);
		if(!Data.MAC_OS)
			jmFile.add(miQuit);
		jmFile.add(miClose);
		jmFile.addSeparator();
		jmFile.add(miSaveStats);
		jmFile.add(miSaveAs);
		jmFile.add(miChooseDir);
		jmEdit.add(miUndo);
		miUndo.setEnabled(false);
		jmEdit.addSeparator();
		jmEdit.add(miReset);
		jmEdit.add(miEmptyCheck);
		miEmptyCheck.setEnabled(false);
		jmView.add(miStandBy);
		jmView.add(miResTabSize);
		jmView.add(subResPos);
		jmView.addSeparator();
		jmView.add(miResTabAll);
		jmWindows.add(w1);
		jmWindows.add(w2);
		jmWindows.add(w3);
		w3.setEnabled(false);
		w3.setVisible(false);
		jmWindows.add(w4);
		w4.setEnabled(false);
		w4.setVisible(false);
		jmWindows.add(w5);
		w5.setEnabled(false);
		w5.setVisible(false);
		jmWindows.addSeparator();
		jmWindows.add(miAdmin);
		jmWindows.add(miControl);
		jmWindows.add(miSettings);
		
		mainMBar.add(jmFile);
		mainMBar.add(jmEdit);
		mainMBar.add(jmSerie);
		mainMBar.add(jmView);
		mainMBar.add(jmWindows);		
	}
	private void initializeOutput(){
		outputFrame = new JFrame();
		outputFrame.setTitle(lang.outputTitle);
		outputFrame.setMinimumSize(new Dimension(Data.params.outputMinSize,Data.params.outputMinSize));
		outputFrame.setSize(Data.params.outputDefaultSize,Data.params.outputDefaultSize);
		outputFrame.setLocationRelativeTo(null);
		outputFrame.setResizable(true);
		outputFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		outputFrame.setFocusable(true);
		outputFrame.addWindowListener(new FrameListener());
		outputFrame.addKeyListener(new ShortcutListener());
		outputFrame.setName("output");
		
		jpBaseOut = new JPanel();
		jpBaseOut.setFocusable(false);
		jpBaseOut.setLayout(new GridLayout(1,1));
		jpBaseOut.setName("jpOut");
		jpBaseOut.addComponentListener(new FrameResized());
		outputFrame.setContentPane(jpBaseOut);
		jpBaseOut.setBackground(Color.black);
		
		jtOutput = new JTable(new OutputModel(grid));
		jtOutput.setFocusable(false);
		jtOutput.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtOutput.setBorder(new LineBorder(Color.BLACK));
		jtOutput.setDefaultRenderer(LotoLabel.class, new CustomCellRender());
		jpBaseOut.add(jtOutput);
		
		LotoLabel.setFSize(settings.fontSize);
		LotoLabel.normalBg = settings.normalBg;
		LotoLabel.darkBg = settings.darkBg;
		LotoLabel.lightBg = settings.lightBg;
		LotoLabel.normalFont = settings.normalF;
		LotoLabel.invalidFont = settings.invalidF;
		LotoLabel.validFont = settings.validF;
		LotoLabel.setBold(settings.bold);
		LotoLabel.setFont(settings.fontName);
	}
	private void initializeAdmin(){
		adminFrame = new JFrame();
		adminFrame.setTitle(lang.adminTitle);
		adminFrame.setAlwaysOnTop(true);
		adminFrame.setSize(Data.params.adminWidth, Data.params.adminHeight);
		adminFrame.setLocationRelativeTo(null);
		adminFrame.setResizable(false);
		adminFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		adminFrame.setFocusable(true);
		adminFrame.addWindowListener(new FrameListener());
		adminFrame.addKeyListener(new ShortcutListener());
		adminFrame.setName("admin");
		
		jpBaseAdmin = new JPanel();
		adminFrame.setContentPane(jpBaseAdmin);
		jpBaseAdmin.setFocusable(false);
		
		jLblAdminNb = new JLabel(lang.adminNbrLabel);
		jLblAdminNb.setHorizontalAlignment(SwingConstants.CENTER);
		jLblAdminNb.setFont(font);
		
		jtfAdmin = new JTextField();
		jtfAdmin.addMouseListener(new JTFListener());
		jtfAdmin.addKeyListener(new JTFListener());
		jtfAdmin.addKeyListener(new ShortcutListener());
		jtfAdmin.setFont(new Font("Tahoma", Font.PLAIN, 22));
		
		jbAdminCancel = new JButton(lang.cancel);
		jbAdminCancel.setName("cancel");
		jbAdminCancel.setToolTipText(lang.adminCancelTTT);
		jbAdminCancel.setFont(font);
		jbAdminCancel.setFocusable(false);
		jbAdminCancel.addActionListener(new AdminBtnListener());

		jbAdminConfirm = new JButton(lang.confirm);
		jbAdminConfirm.setName("confirm");
		jbAdminConfirm.setToolTipText(lang.adminConfirmTTT);
		jbAdminConfirm.setFont(font);
		jbAdminConfirm.setFocusable(false);
		jbAdminConfirm.addActionListener(new AdminBtnListener());
		
		jbAdminDel = new JButton(lang.delete);
		jbAdminDel.setName("del");
		jbAdminDel.setToolTipText(lang.deleteTTT);
		jbAdminDel.setFont(font);
		jbAdminDel.setFocusable(false);
		jbAdminDel.addActionListener(new AdminBtnListener());
		
		jbAdminDelCheck = new JButton(lang.deleteCheck);
		jbAdminDelCheck.setName("delCheck");
		jbAdminDelCheck.setToolTipText(lang.deleteCheck);
		jbAdminDelCheck.setFont(font);
		jbAdminDelCheck.setFocusable(false);
		jbAdminDelCheck.addActionListener(new AdminBtnListener());

		jbAdminAdd = new JButton(lang.add);
		jbAdminAdd.setName("add");
		jbAdminAdd.setToolTipText(lang.confirmAddTTT);
		jbAdminAdd.setFont(font);
		jbAdminAdd.setFocusable(false);
		jbAdminAdd.addActionListener(new AdminBtnListener());
		
		GroupLayout glBaseAdmin = new GroupLayout(jpBaseAdmin);
		//> jpBaseAdmin layout and components 
		glBaseAdmin.setHorizontalGroup(
			glBaseAdmin.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, glBaseAdmin.createSequentialGroup()
					.addGap(38)
					.addGroup(glBaseAdmin.createParallelGroup(Alignment.LEADING)
						.addGroup(glBaseAdmin.createParallelGroup(Alignment.LEADING)
							.addGroup(glBaseAdmin.createSequentialGroup()
								.addGap(40)
								.addComponent(jtfAdmin, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addGroup(glBaseAdmin.createSequentialGroup()
								.addGap(12)
								.addComponent(jbAdminCancel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addComponent(jLblAdminNb, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(glBaseAdmin.createSequentialGroup()
							.addGap(12)
							.addComponent(jbAdminConfirm, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 13, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
					.addGroup(glBaseAdmin.createParallelGroup(Alignment.LEADING)
						.addComponent(jbAdminDel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbAdminDelCheck, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbAdminAdd, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		glBaseAdmin.setVerticalGroup(
			glBaseAdmin.createParallelGroup(Alignment.LEADING)
				.addGroup(glBaseAdmin.createSequentialGroup()
					.addGroup(glBaseAdmin.createParallelGroup(Alignment.LEADING)
						.addGroup(glBaseAdmin.createSequentialGroup()
							.addGap(37)
							.addGroup(glBaseAdmin.createSequentialGroup()
								.addComponent(jbAdminDel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGap(15)
								.addComponent(jbAdminDelCheck, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGap(5))
							.addGroup(glBaseAdmin.createSequentialGroup()
								.addGap(10)
								.addComponent(jbAdminAdd, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addGroup(glBaseAdmin.createSequentialGroup()
							.addGap(21)
							.addComponent(jLblAdminNb, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jtfAdmin, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(jbAdminCancel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbAdminConfirm, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		glBaseAdmin.linkSize(SwingConstants.VERTICAL, new Component[] {jbAdminDel, jbAdminDelCheck, jbAdminAdd});
		glBaseAdmin.linkSize(SwingConstants.HORIZONTAL, new Component[] {jbAdminDel, jbAdminDelCheck, jbAdminAdd});
		//<>
		jpBaseAdmin.setLayout(glBaseAdmin);
	}
	private void initializeControl(){
		controlFrame = new JFrame();
		controlFrame.setTitle(lang.controlTitle);
		controlFrame.setSize(Data.params.controlSize,Data.params.controlSize);
		controlFrame.setMinimumSize(new Dimension(Data.params.controlSize,Data.params.controlSize));
		controlFrame.setLocationRelativeTo(null);
		controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		controlFrame.setFocusable(true);
		controlFrame.addWindowListener(new FrameListener());
		controlFrame.addKeyListener(new ShortcutListener());
		controlFrame.setName("control");
		
		jpBaseControl = new JPanel();
		jpBaseControl.setFocusable(false);
		jpBaseControl.setLayout(new GridLayout(1,1));
		jpBaseControl.setName("jpControl");
		jpBaseControl.addComponentListener(new FrameResized());
		controlFrame.getContentPane().add(jpBaseControl,BorderLayout.CENTER);
		
		jtControl = new JTable(new ControlModel(grid));
		jtControl.setFocusable(false);
		jtControl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtControl.setBorder(new LineBorder(Color.black));
		jtControl.setDefaultRenderer(LotoLabel.class, new CustomCellRender());
		jpBaseControl.add(jtControl);
	}
	private void initializeFD(){
		fd = new FileDialog(mainFrame);
		fd.setDirectory(new File("").getParent());
		fd.setMode(FileDialog.SAVE);
		fd.setTitle(lang.filedialogTitle);
		fd.setMultipleMode(false);
	}
	private void initializeSettings(){
		settingsFrame = new JFrame();
		settingsFrame.setResizable(false);
		settingsFrame.setTitle(lang.settingsTitle);
		settingsFrame.setSize(Data.params.settingsWidth,Data.params.settingsHeight);
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		settingsFrame.setFocusable(true);
		settingsFrame.addWindowListener(new FrameListener());
		settingsFrame.addKeyListener(new ShortcutListener());
		settingsFrame.setName("settings");
		
		jpBaseSettigns = new JTabbedPane();
		settingsFrame.getContentPane().add(jpBaseSettigns, BorderLayout.CENTER);
		jpBaseSettigns.setFocusable(false);
		
		jpSetG= new JPanel();
		jpSetG.setFocusable(false);
		
		jpSetBg = new JPanel();
		jpSetBg.setFocusable(false);
		jpSetBg.setLayout(new GridLayout(1, 3, 0, 0));
		
		jpSetFC = new JPanel();
		jpSetFC.setFocusable(false);
		jpSetFC.setLayout(new GridLayout(1, 3, 0, 0));
		
		jpSetStdby = new JPanel();
		jpSetStdby.setFocusable(false);
		
		jpSetNBg = new JPanel();
		jpSetNBg.setBorder(new TitledBorder(null, lang.normalBgBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetDBg = new JPanel();
		jpSetDBg.setBorder(new TitledBorder(null, lang.darkBgBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetLBg = new JPanel();
		jpSetLBg.setBorder(new TitledBorder(null, lang.lightBgBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetNF = new JPanel();
		jpSetNF.setBorder(new TitledBorder(null, lang.normalFBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetVF = new JPanel();
		jpSetVF.setBorder(new TitledBorder(null, lang.validFBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetIF = new JPanel();
		jpSetIF.setBorder(new TitledBorder(null, lang.invalidFBorder, TitledBorder.LEADING, TitledBorder.TOP, font));
		
		jpSetFC.add(jpSetNF);
		jpSetFC.add(jpSetVF);
		jpSetFC.add(jpSetIF);
		jpSetBg.add(jpSetNBg);
		jpSetBg.add(jpSetDBg);
		jpSetBg.add(jpSetLBg);
		jpBaseSettigns.addTab(lang.generalTab, jpSetG);
		jpBaseSettigns.addTab(lang.backgroundTab, jpSetBg);
		jpBaseSettigns.addTab(lang.fontTab, jpSetFC);
		jpBaseSettigns.addTab(lang.standbyTab, jpSetStdby);
		style = new ButtonGroup();
		
		autoS = new ButtonGroup();
		jpSetG.setLayout(new GridLayout(3, 0, 0, 0));
		
		jpSetTop = new JPanel();
		jpSetTop.setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), lang.fontBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jpSetG.add(jpSetTop);
		
		jLblSize = new JLabel(lang.fontSizeLabel);
		jLblSize.setBounds(30, 23, 40, 17);
		jLblSize.setFont(font);
		
		spinSize = new JSpinner(new SpinnerNumberModel(settings.fontSize,0,1000,1));
		spinSize.setBounds(75, 18, 110, 30);
		spinSize.setFont(font);
		spinSize.setFocusable(false);
		
		jbConfirmSize = new JButton(lang.confirm);
		jbConfirmSize.setBounds(240, 18, 100, 30);
		jbConfirmSize.setFont(font);
		jbConfirmSize.setFocusable(false);
		jbConfirmSize.setName("confirmSize");
		jbConfirmSize.addActionListener(new SettingsBtnListener());
		jrPlain = new JRadioButton(lang.plain);
		jrPlain.setBounds(600, 20, 88, 23);
		jrPlain.setFont(font);
		jrPlain.setFocusable(false);
		jrPlain.setSelected(!settings.bold);
		jrPlain.addItemListener(new RadioPlain());
		style.add(jrPlain);
		
		jrBold = new JRadioButton(lang.bold);
		jrBold.setBounds(600, 56, 59, 23);
		jrBold.setFont(font);
		jrBold.setFocusable(false);
		jrBold.setSelected(settings.bold);
		jrBold.addItemListener(new RadioPlain());
		style.add(jrBold);
		
		jbCancelSize = new JButton(lang.cancel);
		jbCancelSize.setLocation(350, 18);
		jbCancelSize.setSize(new Dimension(100, 30));
		jbCancelSize.setPreferredSize(new Dimension(100, 30));
		jbCancelSize.setMinimumSize(new Dimension(100, 30));
		jbCancelSize.setMaximumSize(new Dimension(100, 30));
		jbCancelSize.setFont(font);
		jbCancelSize.setFocusable(false);
		jbCancelSize.setName("cancelSize");
		jbCancelSize.addActionListener(new SettingsBtnListener());
		
		jbResetFont = new JButton(lang.reset);
		jbResetFont.setBounds(475, 35, 110, 30);
		jbResetFont.setMinimumSize(new Dimension(100, 30));
		jbResetFont.setMaximumSize(new Dimension(100, 30));
		jbResetFont.setPreferredSize(new Dimension(100, 30));
		jbResetFont.setFont(font);
		jbResetFont.setFocusable(false);
		jbResetFont.setName("resetFont");
		jbResetFont.addActionListener(new SettingsBtnListener());
		jpSetTop.setLayout(null);
		jpSetTop.add(jLblSize);
		jpSetTop.add(spinSize);
		jpSetTop.add(jbConfirmSize);
		jpSetTop.add(jbCancelSize);
		jpSetTop.add(jbResetFont);
		jpSetTop.add(jrPlain);
		jpSetTop.add(jrBold);
		
		jLblFontName = new JLabel(lang.fontLabel);
		jLblFontName.setBounds(30, 61, 50, 17);
		jpSetTop.add(jLblFontName);
		
		jtfFontName = new JTextField();
		jtfFontName.setText(settings.fontName);
		jtfFontName.setBounds(80, 56, 130, 26);
		jpSetTop.add(jtfFontName);
		jtfFontName.setColumns(10);
		
		jbConfirmFName = new JButton(lang.confirm);
		jbConfirmFName.setBounds(240, 52, 100, 30);
		jbConfirmFName.setName("confirmFName");
		jbConfirmFName.setFocusable(false);
		jbConfirmFName.addActionListener(new SettingsBtnListener());
		jpSetTop.add(jbConfirmFName);
		
		jbCancelFName = new JButton(lang.cancel);
		jbCancelFName.setBounds(350, 51, 100, 30);
		jbCancelFName.setName("cancelFName");
		jbCancelFName.setFocusable(false);
		jbCancelFName.addActionListener(new SettingsBtnListener());
		jpSetTop.add(jbCancelFName);
		
		jpSetMid = new JPanel();
		jpSetMid.setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), lang.saveSettingsBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jpSetG.add(jpSetMid);
		
		jLblAutoSave = new JLabel(lang.autoSaveLabel);
		jLblAutoSave.setToolTipText(lang.autosaveTTT);
		jLblAutoSave.setFont(font);
		
		jrASaveT = new JRadioButton(lang.yes);
		jrASaveT.setFocusable(false);
		jrASaveT.setFont(font);
		jrASaveT.setSelected(false);
		jrASaveT.addItemListener(new RadioAutosave());
		autoS.add(jrASaveT);
		
		jrASaveF = new JRadioButton(lang.no);
		jrASaveF.setFocusable(false);
		jrASaveF.setFont(font);
		jrASaveF.setSelected(true);
		autoS.add(jrASaveF);
		
		jLblCurrentSave = new JLabel(lang.currentFolderLabel);
		jLblCurrentSave.setToolTipText(lang.currentFolderTTT);
		jLblCurrentSave.setFont(font);
		
		jtfSaveFolder = new JTextField();
		jtfSaveFolder.setEditable(false);
		jtfSaveFolder.setToolTipText(lang.currentFolderTTT);
		jtfSaveFolder.setText(settings.savePath);
		
		jbSetSaveFolder = new JButton(lang.change);
		jbSetSaveFolder.setFocusable(false);
		jbSetSaveFolder.setName("miChooseDir");
		jbSetSaveFolder.addActionListener(new MiListener());
		
		GroupLayout glSetMid = new GroupLayout(jpSetMid);
		//> jpSetMid layout and components 
		glSetMid.setHorizontalGroup(
			glSetMid.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetMid.createSequentialGroup()
					.addContainerGap()
					.addGroup(glSetMid.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetMid.createSequentialGroup()
							.addComponent(jLblAutoSave)
							.addGap(18)
							.addComponent(jrASaveT)
							.addGap(18)
							.addComponent(jrASaveF))
						.addGroup(glSetMid.createSequentialGroup()
							.addComponent(jLblCurrentSave, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jtfSaveFolder, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbSetSaveFolder)))
					.addContainerGap())
		);
		glSetMid.setVerticalGroup(
			glSetMid.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetMid.createSequentialGroup()
					.addContainerGap()
					.addGroup(glSetMid.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLblAutoSave)
						.addComponent(jrASaveT)
						.addComponent(jrASaveF))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(glSetMid.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLblCurrentSave)
						.addComponent(jtfSaveFolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbSetSaveFolder))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		//<>
		jpSetMid.setLayout(glSetMid);
		
		jpSetBot = new JPanel();
		jpSetBot.setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), lang.otherBorder, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jpSetG.add(jpSetBot);
		
		jbResetSet = new JButton(lang.resetAllSettings);
		jbResetSet.setName("resetSettings");
		jbResetSet.setFocusable(false);
		jbResetSet.addActionListener(new SettingsBtnListener());

		jbLoadSet = new JButton(lang.loadSettings);
		jbLoadSet.setName("loadSettings");
		jbLoadSet.setFocusable(false);
		jbLoadSet.setToolTipText(lang.loadSettingsTTT);
		jbLoadSet.addActionListener(new SettingsBtnListener());
		
		jbSaveSettings = new JButton(lang.saveSettings);
		jbSaveSettings.setName("saveSettings");
		jbSaveSettings.setFocusable(false);
		jbSaveSettings.addActionListener(new SettingsBtnListener());
		
		
		GroupLayout glSetBot = new GroupLayout(jpSetBot);
		//> jpSetBot layout and components 
		glSetBot.setHorizontalGroup(
			glSetBot.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetBot.createSequentialGroup()
					.addContainerGap()
					.addComponent(jbLoadSet, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbSaveSettings, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jbResetSet, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		glSetBot.setVerticalGroup(
			glSetBot.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetBot.createSequentialGroup()
					.addGap(15)
					.addGroup(glSetBot.createParallelGroup(Alignment.BASELINE)
						.addComponent(jbLoadSet, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbSaveSettings, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(jbResetSet, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		//<>
		jpSetBot.setLayout(glSetBot);
		
		jLblNBgR = new JLabel(lang.redLabel);
		jLblNBgG = new JLabel(lang.greenLabel);
		jLblNBgB = new JLabel(lang.blueLabel);
		
		spinNBgR = new JSpinner(new ColorCompModel());
		spinNBgR.setEditor(new ColorCompEditor(spinNBgR));
		spinNBgR.setName("normal");
		spinNBgR.setFocusable(false);
		spinNBgR.setValue(settings.normalBg.getRed());
		spinNBgR.addChangeListener(new SpinBg());
		
		spinNBgG = new JSpinner(new ColorCompModel());
		spinNBgG.setEditor(new ColorCompEditor(spinNBgG));
		spinNBgG.setName("normal");
		spinNBgG.setFocusable(false);
		spinNBgG.setValue(settings.normalBg.getGreen());
		spinNBgG.addChangeListener(new SpinBg());
		
		spinNBgB = new JSpinner(new ColorCompModel());
		spinNBgB.setEditor(new ColorCompEditor(spinNBgB));
		spinNBgB.setName("normal");
		spinNBgB.setFocusable(false);
		spinNBgB.setValue(settings.normalBg.getBlue());
		spinNBgB.addChangeListener(new SpinBg());
		
		jpNBgShow = new JPanel();
		jpNBgShow.setBorder(new LineBorder(Color.black, 2));
		jpNBgShow.setBackground(settings.normalBg);
		
		jbResetNBg = new JButton(lang.reset);
		jbResetNBg.setFont(font);
		jbResetNBg.setFocusable(false);
		jbResetNBg.setName("resetN");
		jbResetNBg.addActionListener(new SettingsBgListener());
		
		jbConfirmNBg = new JButton(lang.confirm);
		jbConfirmNBg.setFont(font);
		jbConfirmNBg.setName("confirmN");
		jbConfirmNBg.setFocusable(false);
		jbConfirmNBg.addActionListener(new SettingsBgListener());
		
		jbCancelNBg = new JButton(lang.cancel);
		jbCancelNBg.setFont(font);
		jbCancelNBg.setFocusable(false);
		jbCancelNBg.setName("cancelN");
		jbCancelNBg.addActionListener(new SettingsBgListener());
		
		GroupLayout glSetNBg = new GroupLayout(jpSetNBg);
		//> jpSetNBg layout and components 
		glSetNBg.setHorizontalGroup(
			glSetNBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetNBg.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetNBg.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblNBgR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblNBgG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblNBgB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetNBg.createParallelGroup(Alignment.LEADING)
						.addComponent(spinNBgR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinNBgG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinNBgB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpNBgShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetNBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetNBg.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmNBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetNBg.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelNBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		glSetNBg.setVerticalGroup(
			glSetNBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetNBg.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetNBg.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetNBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblNBgR)
							.addGap(12)
							.addComponent(jLblNBgG)
							.addGap(12)
							.addComponent(jLblNBgB))
						.addGroup(glSetNBg.createSequentialGroup()
							.addComponent(spinNBgR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinNBgG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinNBgB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetNBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jpNBgShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetNBg.createParallelGroup(Alignment.LEADING)
						.addComponent(jbResetNBg)
						.addGroup(glSetNBg.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmNBg))
						.addComponent(jbCancelNBg)))
		);
		//<>
		jpSetNBg.setLayout(glSetNBg);
		
		jLblDBgR = new JLabel(lang.redLabel);
		
		jLblDBgG = new JLabel(lang.greenLabel);
		
		jLblDBgB = new JLabel(lang.blueLabel);
		
		spinDBgR = new JSpinner(new ColorCompModel());
		spinDBgR.setEditor(new ColorCompEditor(spinDBgR));
		spinDBgR.setName("dark");
		spinDBgR.setFocusable(false);
		spinDBgR.setValue(settings.darkBg.getRed());
		spinDBgR.addChangeListener(new SpinBg());
		
		spinDBgG = new JSpinner(new ColorCompModel());
		spinDBgG.setEditor(new ColorCompEditor(spinDBgG));
		spinDBgG.setName("dark");
		spinDBgG.setFocusable(false);
		spinDBgG.setValue(settings.darkBg.getGreen());
		spinDBgG.addChangeListener(new SpinBg());
		
		spinDBgB = new JSpinner(new ColorCompModel());
		spinDBgB.setEditor(new ColorCompEditor(spinDBgB));
		spinDBgB.setName("dark");
		spinDBgB.setFocusable(false);
		spinDBgB.setValue(settings.darkBg.getBlue());
		spinDBgB.addChangeListener(new SpinBg());
		
		jpDBgShow = new JPanel();
		jpDBgShow.setBorder(new LineBorder(Color.black, 2));
		jpDBgShow.setBackground(settings.darkBg);
		
		jbResetDBg = new JButton(lang.reset);
		jbResetDBg.setFont(font);
		jbResetDBg.setFocusable(false);
		jbResetDBg.setName("resetD");
		jbResetDBg.addActionListener(new SettingsBgListener());
		
		jbConfirmDBg = new JButton(lang.confirm);
		jbConfirmDBg.setFont(font);
		jbConfirmDBg.setFocusable(false);
		jbConfirmDBg.setName("confirmD");
		jbConfirmDBg.addActionListener(new SettingsBgListener());
		
		jbCancelDBg = new JButton(lang.cancel);
		jbCancelDBg.setFont(font);
		jbCancelDBg.setFocusable(false);
		jbCancelDBg.setName("cancelD");
		jbCancelDBg.addActionListener(new SettingsBgListener());
		
		GroupLayout glSetDBg = new GroupLayout(jpSetDBg);
		//> jpSetDBg layout and components
		glSetDBg.setHorizontalGroup(
			glSetDBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetDBg.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetDBg.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblDBgR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblDBgG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblDBgB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetDBg.createParallelGroup(Alignment.LEADING)
						.addComponent(spinDBgR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinDBgG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinDBgB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpDBgShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetDBg.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmDBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetDBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetDBg.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelDBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		glSetDBg.setVerticalGroup(
			glSetDBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetDBg.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetDBg.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetDBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblDBgR)
							.addGap(12)
							.addComponent(jLblDBgG)
							.addGap(12)
							.addComponent(jLblDBgB))
						.addGroup(glSetDBg.createSequentialGroup()
							.addComponent(spinDBgR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinDBgG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinDBgB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetDBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jpDBgShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetDBg.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetDBg.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmDBg))
						.addComponent(jbResetDBg)
						.addComponent(jbCancelDBg)))
		);
		//<>
		jpSetDBg.setLayout(glSetDBg);

		jLblLBgR = new JLabel(lang.redLabel);
		
		jLblLBgG = new JLabel(lang.greenLabel);
		
		jLblLBgB = new JLabel(lang.blueLabel);
		
		spinLBgR = new JSpinner(new ColorCompModel());
		spinLBgR.setEditor(new ColorCompEditor(spinLBgR));
		spinLBgR.setName("light");
		spinLBgR.setFocusable(false);
		spinLBgR.setValue(settings.lightBg.getRed());
		spinLBgR.addChangeListener(new SpinBg());
		
		spinLBgG = new JSpinner(new ColorCompModel());
		spinLBgG.setEditor(new ColorCompEditor(spinLBgG));
		spinLBgG.setName("light");
		spinLBgG.setFocusable(false);
		spinLBgG.setValue(settings.lightBg.getGreen());
		spinLBgG.addChangeListener(new SpinBg());
		
		spinLBgB = new JSpinner(new ColorCompModel());
		spinLBgB.setEditor(new ColorCompEditor(spinLBgB));
		spinLBgB.setName("light");
		spinLBgB.setFocusable(false);
		spinLBgB.setValue(settings.lightBg.getBlue());
		spinLBgB.addChangeListener(new SpinBg());
		
		jpLBgShow = new JPanel();
		jpLBgShow.setBorder(new LineBorder(Color.black, 2));
		jpLBgShow.setBackground(settings.lightBg);
		
		jbResetLBg = new JButton(lang.reset);
		jbResetLBg.setFont(font);
		jbResetLBg.setFocusable(false);
		jbResetLBg.setName("resetL");
		jbResetLBg.addActionListener(new SettingsBgListener());
		
		jbConfirmLBg = new JButton(lang.confirm);
		jbConfirmLBg.setFont(font);
		jbConfirmLBg.setFocusable(false);
		jbConfirmLBg.setName("confirmL");
		jbConfirmLBg.addActionListener(new SettingsBgListener());
		
		jbCancelLBg = new JButton(lang.cancel);
		jbCancelLBg.setFont(font);
		jbCancelLBg.setFocusable(false);
		jbCancelLBg.setName("cancelL");
		jbCancelLBg.addActionListener(new SettingsBgListener());
		
		GroupLayout glSetLBg = new GroupLayout(jpSetLBg);
		//> jpSetLBg layout and components 
		glSetLBg.setHorizontalGroup(
			glSetLBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetLBg.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetLBg.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblLBgR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblLBgG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblLBgB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetLBg.createParallelGroup(Alignment.LEADING)
						.addComponent(spinLBgR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinLBgG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinLBgB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpLBgShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetLBg.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmLBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetLBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetLBg.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelLBg, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		glSetLBg.setVerticalGroup(
			glSetLBg.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetLBg.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetLBg.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetLBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblLBgR)
							.addGap(12)
							.addComponent(jLblLBgG)
							.addGap(12)
							.addComponent(jLblLBgB))
						.addGroup(glSetLBg.createSequentialGroup()
							.addComponent(spinLBgR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinLBgG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinLBgB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetLBg.createSequentialGroup()
							.addGap(5)
							.addComponent(jpLBgShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetLBg.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetLBg.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmLBg))
						.addComponent(jbResetLBg)
						.addComponent(jbCancelLBg)))
		);
		//<>
		jpSetLBg.setLayout(glSetLBg);

		jLblNFR = new JLabel(lang.redLabel);
		
		jLblNFG = new JLabel(lang.greenLabel);
		
		jLblNFB = new JLabel(lang.blueLabel);
		
		spinNFR = new JSpinner(new ColorCompModel());
		spinNFR.setEditor(new ColorCompEditor(spinNFR));
		spinNFR.setName("normal");
		spinNFR.setFocusable(false);
		spinNFR.setValue(settings.normalF.getRed());
		spinNFR.addChangeListener(new SpinF());
		
		spinNFG = new JSpinner(new ColorCompModel());
		spinNFG.setEditor(new ColorCompEditor(spinNFG));
		spinNFG.setName("normal");
		spinNFG.setFocusable(false);
		spinNFG.setValue(settings.normalF.getGreen());
		spinNFG.addChangeListener(new SpinF());
		
		spinNFB = new JSpinner(new ColorCompModel());
		spinNFB.setEditor(new ColorCompEditor(spinNFB));
		spinNFB.setName("normal");
		spinNFB.setFocusable(false);
		spinNFB.setValue(settings.normalF.getBlue());
		spinNFB.addChangeListener(new SpinF());
		
		jpNFShow = new JPanel();
		jpNFShow.setBorder(new LineBorder(Color.black, 2, true));
		jpNFShow.setBackground(settings.normalF);
		
		jbResetNF = new JButton(lang.reset);
		jbResetNF.setFont(font);
		jbResetNF.setFocusable(false);
		jbResetNF.setName("resetN");
		jbResetNF.addActionListener(new SettingsFListener());
		
		jbConfirmNF = new JButton(lang.confirm);
		jbConfirmNF.setFont(font);
		jbConfirmNF.setFocusable(false);
		jbConfirmNF.setName("confirmN");
		jbConfirmNF.addActionListener(new SettingsFListener());
		
		jbCancelNF = new JButton(lang.cancel);
		jbCancelNF.setFont(font);
		jbCancelNF.setFocusable(false);
		jbCancelNF.setName("cancelN");
		jbCancelNF.addActionListener(new SettingsFListener());
		
		GroupLayout glSetNF = new GroupLayout(jpSetNF);
		//> jpSetNF layout and components 
		glSetNF.setHorizontalGroup(
			glSetNF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetNF.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetNF.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblNFR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblNFG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblNFB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetNF.createParallelGroup(Alignment.LEADING)
						.addComponent(spinNFR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinNFG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinNFB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpNFShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetNF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetNF.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmNF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetNF.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelNF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		glSetNF.setVerticalGroup(
			glSetNF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetNF.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetNF.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetNF.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblNFR)
							.addGap(12)
							.addComponent(jLblNFG)
							.addGap(12)
							.addComponent(jLblNFB))
						.addGroup(glSetNF.createSequentialGroup()
							.addComponent(spinNFR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinNFG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinNFB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetNF.createSequentialGroup()
							.addGap(5)
							.addComponent(jpNFShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetNF.createParallelGroup(Alignment.LEADING)
						.addComponent(jbResetNF)
						.addGroup(glSetNF.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmNF))
						.addComponent(jbCancelNF)))
		);
		//<>
		jpSetNF.setLayout(glSetNF);

		jLblIFR = new JLabel(lang.redLabel);
		
		jLblIFG = new JLabel(lang.greenLabel);
		
		jLblIFB = new JLabel(lang.blueLabel);
		
		spinIFR = new JSpinner(new ColorCompModel());
		spinIFR.setEditor(new ColorCompEditor(spinIFR));
		spinIFR.setName("invalid");
		spinIFR.setFocusable(false);
		spinIFR.setValue(settings.invalidF.getRed());
		spinIFR.addChangeListener(new SpinF());
		
		spinIFG = new JSpinner(new ColorCompModel());
		spinIFG.setEditor(new ColorCompEditor(spinIFG));
		spinIFG.setName("invalid");
		spinIFG.setFocusable(false);
		spinIFG.setValue(settings.invalidF.getGreen());
		spinIFG.addChangeListener(new SpinF());
		
		spinIFB = new JSpinner(new ColorCompModel());
		spinIFB.setEditor(new ColorCompEditor(spinIFB));
		spinIFB.setName("invalid");
		spinIFB.setFocusable(false);
		spinIFB.setValue(settings.invalidF.getBlue());
		spinIFB.addChangeListener(new SpinF());
		
		jpIFShow = new JPanel();
		jpIFShow.setBorder(new LineBorder(Color.black, 2, true));
		jpIFShow.setBackground(settings.invalidF);
		
		jbResetIF = new JButton(lang.reset);
		jbResetIF.setFont(font);
		jbResetIF.setFocusable(false);
		jbResetIF.setName("resetI");
		jbResetIF.addActionListener(new SettingsFListener());
		
		jbConfirmIF = new JButton(lang.confirm);
		jbConfirmIF.setFont(font);
		jbConfirmIF.setFocusable(false);
		jbConfirmIF.setName("confirmI");
		jbConfirmIF.addActionListener(new SettingsFListener());
		
		jbCancelIF = new JButton(lang.cancel);
		jbCancelIF.setFont(font);
		jbCancelIF.setFocusable(false);
		jbCancelIF.setName("cancelI");
		jbCancelIF.addActionListener(new SettingsFListener());
		
		GroupLayout glSetIF = new GroupLayout(jpSetIF);
		//> jpSetIF layout and components 
		glSetIF.setHorizontalGroup(
			glSetIF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetIF.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetIF.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblIFR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblIFG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblIFB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetIF.createParallelGroup(Alignment.LEADING)
						.addComponent(spinIFR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinIFG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinIFB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpIFShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetIF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetIF.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelIF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetIF.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmIF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
		);
		glSetIF.setVerticalGroup(
			glSetIF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetIF.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetIF.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetIF.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblIFR)
							.addGap(12)
							.addComponent(jLblIFG)
							.addGap(12)
							.addComponent(jLblIFB))
						.addGroup(glSetIF.createSequentialGroup()
							.addComponent(spinIFR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinIFG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinIFB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetIF.createSequentialGroup()
							.addGap(5)
							.addComponent(jpIFShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetIF.createParallelGroup(Alignment.LEADING)
						.addComponent(jbResetIF)
						.addComponent(jbCancelIF)
						.addGroup(glSetIF.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmIF))))
		);
		//<>
		jpSetIF.setLayout(glSetIF);

		jLblVFR = new JLabel(lang.redLabel);
		
		jLblVFG = new JLabel(lang.greenLabel);
		
		jLblVFB = new JLabel(lang.blueLabel);
		
		spinVFR = new JSpinner(new ColorCompModel());
		spinVFR.setEditor(new ColorCompEditor(spinVFR));
		spinVFR.setName("valid");
		spinVFR.setFocusable(false);
		spinVFR.setValue(settings.validF.getRed());
		spinVFR.addChangeListener(new SpinF());
		
		spinVFG = new JSpinner(new ColorCompModel());
		spinVFG.setEditor(new ColorCompEditor(spinVFG));
		spinVFG.setName("valid");
		spinVFG.setFocusable(false);
		spinVFG.setValue(settings.validF.getGreen());
		spinVFG.addChangeListener(new SpinF());
		
		spinVFB = new JSpinner(new ColorCompModel());
		spinVFB.setEditor(new ColorCompEditor(spinVFB));
		spinVFB.setName("valid");
		spinVFB.setFocusable(false);
		spinVFB.setValue(settings.validF.getBlue());
		spinVFB.addChangeListener(new SpinF());
		
		jpVFShow = new JPanel();
		jpVFShow.setBorder(new LineBorder(Color.black, 2, true));
		jpVFShow.setBackground(settings.validF);
		
		jbResetVF = new JButton(lang.reset);
		jbResetVF.setName("resetV");
		jbResetVF.setFont(font);
		jbResetVF.setFocusable(false);
		jbResetVF.addActionListener(new SettingsFListener());
		
		jbConfirmVF = new JButton(lang.confirm);
		jbConfirmVF.setFont(font);
		jbConfirmVF.setFocusable(false);
		jbConfirmVF.setName("confirmV");
		jbConfirmVF.addActionListener(new SettingsFListener());
		
		jbCancelVF = new JButton(lang.cancel);
		jbCancelVF.setName("cancelV");
		jbCancelVF.setFont(font);
		jbCancelVF.setFocusable(false);
		jbCancelVF.addActionListener(new SettingsFListener());
		
		jcbVBgRev = new JCheckBox(lang.reversedBackground);
		jcbVBgRev.setFont(font);
		jcbVBgRev.setFocusable(false);
		jcbVBgRev.addChangeListener(new BgTypeListener());
		
		GroupLayout glSetVF = new GroupLayout(jpSetVF);
		//> jpSetVF layout and components 
		glSetVF.setHorizontalGroup(
			glSetVF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetVF.createSequentialGroup()
					.addGap(12)
					.addGroup(glSetVF.createParallelGroup(Alignment.LEADING)
						.addComponent(jLblVFR, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblVFG, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLblVFB, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(glSetVF.createParallelGroup(Alignment.LEADING)
						.addComponent(spinVFR, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinVFG, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinVFB, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(jpVFShow, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
				.addComponent(jbResetVF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
				.addGroup(glSetVF.createSequentialGroup()
					.addGap(56)
					.addComponent(jbConfirmVF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetVF.createSequentialGroup()
					.addGap(114)
					.addComponent(jbCancelVF, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
				.addGroup(glSetVF.createSequentialGroup()
					.addGap(59)
					.addComponent(jcbVBgRev, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
		);
		glSetVF.setVerticalGroup(
			glSetVF.createParallelGroup(Alignment.LEADING)
				.addGroup(glSetVF.createSequentialGroup()
					.addGap(14)
					.addGroup(glSetVF.createParallelGroup(Alignment.LEADING)
						.addGroup(glSetVF.createSequentialGroup()
							.addGap(5)
							.addComponent(jLblVFR)
							.addGap(12)
							.addComponent(jLblVFG)
							.addGap(12)
							.addComponent(jLblVFB))
						.addGroup(glSetVF.createSequentialGroup()
							.addComponent(spinVFR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinVFG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(spinVFB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(glSetVF.createSequentialGroup()
							.addGap(5)
							.addComponent(jpVFShow, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)))
					.addGap(21)
					.addGroup(glSetVF.createParallelGroup(Alignment.LEADING)
						.addComponent(jbResetVF)
						.addGroup(glSetVF.createSequentialGroup()
							.addGap(27)
							.addComponent(jbConfirmVF))
						.addComponent(jbCancelVF))
					.addGap(18)
					.addComponent(jcbVBgRev, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
		);
		//<>
		jpSetVF.setLayout(glSetVF);
		
		jpSetStdbyDef = new JPanel();
		jpSetStdbyDef.setFocusable(false);
		
		jLblDefault = new JLabel(lang.settingsStdbyDefault);
		jLblDefault.setFont(font);
		jLblDefault.setFocusable(false);
		jpSetStdbyDef.add(jLblDefault);
		
		standbyAnim = new ButtonGroup();
		
		jrStdbyDefT = new JRadioButton(lang.yes);
		jrStdbyDefT.setFont(font);
		jrStdbyDefT.setFocusable(false);
		jrStdbyDefT.setSelected(settings.standbyDefault);
		jrStdbyDefT.setEnabled(!settings.standbyDefault);
		jrStdbyDefT.addItemListener(new RadioStdby());
		standbyAnim.add(jrStdbyDefT);
		jpSetStdbyDef.add(jrStdbyDefT);
		
		jrStdbyDefF = new JRadioButton(lang.no);
		jrStdbyDefF.setFont(font);
		jrStdbyDefF.setFocusable(false);
		jrStdbyDefF.setSelected(!settings.standbyDefault);
		jrStdbyDefF.setEnabled(!settings.standbyDefault);
		standbyAnim.add(jrStdbyDefF);
		jpSetStdbyDef.add(jrStdbyDefF);
		

		jpSetStdbyWait = new JPanel();
		jpSetStdbyWait.setFocusable(false);
		
		jLblWait = new JLabel(lang.settingsWaitTime);
		jLblWait.setFont(font);
		jLblWait.setFocusable(false);
		jpSetStdbyWait.add(jLblWait);
		
		spinWait = new JSpinner(new SpinnerNumberModel(15, 1, Integer.MAX_VALUE, 1));
		spinWait.setPreferredSize(new Dimension(100,25));
		spinWait.setFont(font);
		spinWait.setValue(settings.standbyWait);
		spinWait.addChangeListener(new SpinWait());
		jpSetStdbyWait.add(spinWait);
		
		JLblWaitUnit = new JLabel(lang.settingsWaitUnit);
		JLblWaitUnit.setFont(font);
		JLblWaitUnit.setFocusable(false);
		jpSetStdbyWait.add(JLblWaitUnit);
		
		jpSetStdbyPath = new JPanel();
		jpSetStdbyPath.setFocusable(false);

		jLblStdbyPath = new JLabel(lang.settingsStdbyPath);
		jLblStdbyPath.setFont(font);
		jLblStdbyPath.setFocusable(false);
		jpSetStdbyPath.add(jLblStdbyPath);
		
		jtfStandbyImages = new JTextField();
		jtfStandbyImages.setPreferredSize(new Dimension(300,25));
		jtfStandbyImages.setFont(font);
		jtfStandbyImages.setText(settings.standbyPath);
		jtfStandbyImages.setEditable(false);
		jpSetStdbyPath.add(jtfStandbyImages);
		
		jbSetImgFolder = new JButton(lang.change);
		jbSetImgFolder.setFocusable(false);
		jbSetImgFolder.setName("stdbyImages");
		jbSetImgFolder.addActionListener(new SettingsBtnListener());
		jpSetStdbyPath.add(jbSetImgFolder);

		jpSetStdbyNImgs = new JPanel();
		jpSetStdbyNImgs.setFocusable(false);
		
		jLblStdbyTxtImgs = new JLabel(lang.nbStandbyLabel);
		jLblStdbyTxtImgs.setFont(font);
		jLblStdbyTxtImgs.setFocusable(false);
		jpSetStdbyNImgs.add(jLblStdbyTxtImgs);

		jLblStdbyNImgs = new JLabel(" -");
		jLblStdbyNImgs.setFont(font);
		jLblStdbyNImgs.setFocusable(false);
		jpSetStdbyNImgs.add(jLblStdbyNImgs);
		
		jpSetStdby.setLayout(new GridLayout(5,1));
		jpSetStdby.add(new JPanel());
		jpSetStdby.add(jpSetStdbyDef);
		jpSetStdby.add(jpSetStdbyWait);
		jpSetStdby.add(jpSetStdbyPath);
		jpSetStdby.add(jpSetStdbyNImgs);
	}
	private void initializeStandBy(){
		jpStandby = new StandbyPanel(settings);
		jpStandby.setBackground(settings.normalBg);
		jpStandby.setName("standbyPanel");
		jpStandby.addComponentListener(new FrameResized());
	}
	
	private void exit(){
		System.exit(0);
	}
	private void preloadSettings(boolean reset){
		Settings set;
		boolean ok=false;
		
		if(reset){
			set = Data.getDefaultSettings();
			ok = (JOptionPane.showOptionDialog(null, lang.settingsResetMsg, lang.warning, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					new Object[]{lang.no,lang.yes}, lang.no)==1);
		}else{
			set = null;
			try {
			set = Data.laodSettings();
			}catch(DataException e){
				JOptionPane.showMessageDialog(null, lang.loadSettingsErrorMessage, lang.loadSettingsErrorTitle, JOptionPane.PLAIN_MESSAGE);
			}
			if(set != null)
				ok = (JOptionPane.showOptionDialog(null, lang.loadSettingsMsg, lang.loadSettingsTitle, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
						new Object[]{lang.no,lang.yes}, lang.no)==1);
		}
		if(ok)
			loadSettings(set);
	}
	private void loadSettings(Settings set){
		if(set != null){
			settings = set.clone();
			
			spinNBgR.setValue(set.normalBg.getRed());
			spinNBgG.setValue(set.normalBg.getGreen());
			spinNBgB.setValue(set.normalBg.getBlue());
			spinDBgR.setValue(set.darkBg.getRed());
			spinDBgG.setValue(set.darkBg.getGreen());
			spinDBgB.setValue(set.darkBg.getBlue());
			spinLBgR.setValue(set.lightBg.getRed());
			spinLBgG.setValue(set.lightBg.getGreen());
			spinLBgB.setValue(set.lightBg.getBlue());
			spinNFR.setValue(set.normalF.getRed());
			spinNFG.setValue(set.normalF.getGreen());
			spinNFB.setValue(set.normalF.getBlue());
			spinIFR.setValue(set.invalidF.getRed());
			spinIFG.setValue(set.invalidF.getGreen());
			spinIFB.setValue(set.invalidF.getBlue());
			spinVFR.setValue(set.validF.getRed());
			spinVFG.setValue(set.validF.getGreen());
			spinVFB.setValue(set.validF.getBlue());
			
			jcbVBgRev.setSelected(set.reverseValid);
			spinSize.setValue(set.fontSize);
			jrPlain.setSelected(!set.bold);
			jrBold.setSelected(set.bold);
			jrASaveT.setSelected(set.autosave);
			
			data.setDirectory((new File(set.savePath)).toPath());
			settings.savePath = data.getPath().toString();
			jtfSaveFolder.setText(settings.savePath);
			
			LotoLabel.setFSize(set.fontSize);
			LotoLabel.normalBg = set.normalBg;
			LotoLabel.darkBg = set.darkBg;
			LotoLabel.lightBg = set.lightBg;
			LotoLabel.normalFont = set.normalF;
			LotoLabel.invalidFont = set.invalidF;
			LotoLabel.validFont = set.validF;
			LotoLabel.setBold(set.bold);
			LotoLabel.setFont(set.fontName);
			
			settingsFrame.repaint();
			repaintOut();
		}
	}
	
	
	//Listeners
	
	private class JTFListener implements MouseListener,KeyListener,ActionListener{
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void keyPressed(KeyEvent e){}
		public void keyTyped(KeyEvent e){}

		public void mouseClicked(MouseEvent e){
			((JTextField)e.getSource()).selectAll();
		}
		public void keyReleased(KeyEvent e){
			JTextField source = (JTextField)e.getSource();
			String temp;
			char[] temp2,temp3;
			int k=0,fLenght=0,temp4;
			
			temp = source.getText();
			temp2 = temp.toCharArray();
			for(int i=0;i<temp2.length;i++)
				if(Data.isCharValid(temp2[i]))
					fLenght++;
			if(fLenght>0){
				temp3 = new char[fLenght];
				for(int i=0;i<fLenght;i++)
					if(Data.isCharValid(temp2[i])){
						temp3[k] = temp2[i];
						k++;
				}
				temp = String.valueOf(temp3);
				if(temp.length()>2)
					temp = temp.substring(0, 2);
				temp4 = Integer.valueOf(temp);
				if(temp4>90 || temp4==0)
					source.setText("");
				else
					source.setText(temp);
			}else
				source.setText("");
		}
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "taker":
				takerUsed();
				break;
			case "checker":
				checkerUsed();
				break;
			default:
				System.err.println("-JTFListener- Component not listed in switch :"+((JComponent)e.getSource()).getName());
				break;
			}
		}
		
		public void takerUsed(){
			String temp;
			int takerNbr;
			(new JTFListener()).keyReleased((new KeyEvent(jtfTake,KeyEvent.KEY_RELEASED,0,0,0,'0')));
			temp = jtfTake.getText();
			
			if(temp != null && !temp.equals("")){
				takerNbr = Integer.valueOf(temp);
				if(grid.add(takerNbr)){
					lastAct=1;
					setCheckReset();
					jbClearCheck.doClick(Data.params.clickTime);
					setClear();
					updateUndoRedo();
					jbClear.setEnabled(true);
					
					repaintOut();
				}
				jtfTake.setText("");
			}
		}
		public void checkerUsed(){
			String temp;
			int cNbr;
			(new JTFListener()).keyReleased((new KeyEvent(jtfCheck,KeyEvent.KEY_RELEASED,0,0,0,'0')));
			temp = jtfCheck.getText();
			
			if(temp != null && !temp.equals("")){
				cNbr = Integer.valueOf(temp);
				if(!grid.isAlreadyChecked(cNbr)){
					if(grid.check(cNbr))
						nbrCheck++;
					else
						nbrErr++;
					
					lastCheckAct=1;
					setCheckClear();
					updateLabels();
					updateUndoRedoCheck();
					jbClearCheck.setEnabled(true);
					
					repaintOut();
				}
				jtfCheck.setText("");
			}
		}
	}
	
	private class MainBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "clear":
				if(clearMode){
					setCheckReset();
					jbClearCheck.doClick(Data.params.clickTime);
					lastAct=3;
					grid.undoAll();
					setReset();
					updateUndoRedo();
				}else{
					if(settings.autosave){
						if(lastAct == 3)
							grid.redoAll();
						miSaveStats.doClick(Data.params.clickTime);
					}
					grid.resetAll();
					lastAct=nbrCheck=nbrErr=0;
					switchable=false;
					setClear();
					setCheckClear();
					jbClear.setEnabled(false);
					jbUndo.setEnabled(false);
					jbRedo.setEnabled(false);
					jbClearCheck.setEnabled(false);
					jbUndoCheck.setEnabled(false);
					jbRedoCheck.setEnabled(false);
				}
				repaintOut();
				break;
			case "undo":
				setCheckReset();
				jbClearCheck.doClick(Data.params.clickTime);
				switch(lastAct){
				case 1:
					grid.undo();
					if(grid.numberSize()==0)
						setReset();
					break;
				case 2:
					lastAct=4;
					grid.redoAll();
					setReset();
					break;
				case 3:
					lastAct=5;
					grid.redoAll();
					setClear();
					break;
				default:
					System.err.println("Undo - lastAct out of bounds : "+lastAct);
					break;
				}
				updateUndoRedo();
				repaintOut();
				break;
			case "redo":
				setCheckReset();
				jbClearCheck.doClick(Data.params.clickTime);
				switch(lastAct){
				case 1:
					grid.redo();
					if(grid.numberSize()!=0)
						setClear();
					break;
				case 4:
					jbShowAll.doClick(Data.params.clickTime);
					break;
				case 5:
					setClear();
					jbClear.doClick(Data.params.clickTime);
					break;
				default:
					System.err.println("Redo - lasAct out of bounds : "+lastAct);
					break;
				}
				updateUndoRedo();
				repaintOut();
				break;
			case "showAll":
				setCheckReset();
				jbClearCheck.doClick(Data.params.clickTime);
				if(lastAct==2){
					jbUndo.setEnabled(false);
					jbRedo.setEnabled(false);
				}else{
					lastAct=2;
					grid.showAll();
					setClear();
					updateUndoRedo();
					jbClear.setEnabled(true);
				}
				repaintOut();
				break;
			case "clearCheck":
				nbrErr = nbrCheck = 0;
				if(clearCheckMode){
					lastCheckAct=3;
					grid.uncheckAll();
					setCheckReset();
					updateUndoRedoCheck();
				}else{
					lastCheckAct=0;
					grid.resetCheck();
					jbUndoCheck.setEnabled(false);
					jbRedoCheck.setEnabled(false);
					jbClearCheck.setEnabled(false);
					setCheckClear();
				}
				updateLabels();
				repaintOut();
				break;
			case "checkAll":
				if(lastCheckAct==2){
					jbUndoCheck.setEnabled(false);
					jbRedoCheck.setEnabled(false);
				}else{
					lastCheckAct=2;
					nbrCheck = grid.checkAll();
					nbrErr = 90 - nbrCheck;
					jbClearCheck.setEnabled(true);
					setCheckClear();
					updateLabels();
					updateUndoRedoCheck();
				}
				repaintOut();
				break;
			case "undoCheck":
				switch(lastCheckAct){
				case 1:
					if(grid.uncheck())
						nbrCheck--;
					else
						nbrErr--;
					if(grid.checkedSize()==0){
						nbrCheck=nbrErr=0;
						setCheckReset();
						}
					else
						setCheckClear();
					break;
				case 2:
					lastCheckAct=4;
					nbrCheck=grid.recheckAll();
					nbrErr=grid.checkedSize()-nbrCheck;
					setCheckReset();
					break;
				case 3:
					lastCheckAct=5;
					nbrCheck=grid.recheckAll();
					nbrErr=grid.checkedSize()-nbrCheck;
					setCheckClear();
					break;
				default:
					System.err.println("UndoCheck - lastCheckAct out of bounds : "+lastCheckAct);
					break;
				}
				updateLabels();
				updateUndoRedoCheck();
				repaintOut();
				break;
			case "redoCheck":
				switch(lastCheckAct){
				case 1:
					if(grid.recheck())
						nbrCheck++;
					else
						nbrErr++;
					setCheckClear();
					jbClearCheck.setEnabled(true);
					updateLabels();
					break;
				case 4:
					jbCheckAll.doClick(Data.params.clickTime);
					break;
				case 5:
					setCheckClear();
					jbClearCheck.doClick(Data.params.clickTime);
					break;
				default:
					System.err.println("RedoCheck - lasCheckAct out of bounds : "+lastCheckAct);
					break;
				}
				updateUndoRedoCheck();
				repaintOut();
				break;
			default:
				System.err.println("-MainBtnListener- Component not listed in switch :"+((JComponent)e.getSource()).getName());
				break;
			}
		}
	}
	private class AdminBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "cancel":
				miClose.doClick(Data.params.clickTime);
				repaintOut();
				break;
			case "confirm":
				miClose.doClick(Data.params.clickTime);
				if(lastAct==2 || lastAct==3){
					lastAct=1;
					grid.clearUndo();
				}
				if(lastCheckAct==2 || lastCheckAct==3){
					lastCheckAct=1;
					grid.clearUndoCheck();
				}
				grid.confirmAdmin();

				if(grid.numberSize()==0)
					setReset();
				else
					setClear();
				if(grid.checkedSize()==0)
					setCheckReset();
				else
					setCheckClear();
				
				jbClear.setEnabled(true);
				jbClearCheck.setEnabled(true);
				updateUndoRedo();
				updateUndoRedoCheck();
				repaintOut();
				break;
			case "del":
				String temp;
				int num;
				(new JTFListener()).keyReleased((new KeyEvent(jtfAdmin,KeyEvent.KEY_RELEASED,0,0,0,'0')));
				temp = jtfAdmin.getText();

				if(temp!=null && !temp.equals("")){
					num = Integer.valueOf(temp);
					grid.adminDel(num);
				}
				jtfAdmin.setText("");
				break;
			case "delCheck":
				String temp2;
				int tNum;
				(new JTFListener()).keyReleased((new KeyEvent(jtfAdmin,KeyEvent.KEY_RELEASED,0,0,0,'0')));
				temp2 = jtfAdmin.getText();
				
				if(temp2!=null && !temp2.equals("")){
					tNum = Integer.valueOf(temp2);
					grid.adminDelCheck(tNum);
				}
				jtfAdmin.setText("");
				break;
			case "add":
				String temp3;
				int tNum2,index;
				(new JTFListener()).keyReleased((new KeyEvent(jtfAdmin,KeyEvent.KEY_RELEASED,0,0,0,'0')));
				temp3 = jtfAdmin.getText();

				if(temp3!=null && !temp3.equals("")){
					tNum2 = Integer.valueOf(temp3);
					index = SliderDialog.showSliderDialog(adminFrame, grid.adminSize(),lang);
					grid.adminAdd(tNum2, index);
				}
				jtfAdmin.setText("");
				break;
			default:
				System.err.println("-AdminBtnListener- Component not listed ins switch :"+((JComponent)e.getSource()).getName());
				break;
			}
		}
	}
	private class SettingsBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
				case "confirmSize":
					settings.fontSize = (int)spinSize.getValue();
					LotoLabel.setFSize(settings.fontSize);
					repaintOut();
					break;
				case "cancelSize":
					spinSize.setValue(settings.fontSize);
					break;
				case "confirmFName":
					settings.fontName = jtfFontName.getText();
					LotoLabel.setFont(settings.fontName);
					repaintOut();
					break;
				case "cancelFName":
					jtfFontName.setText(settings.fontName);
					break;
				case "resetFont":
					settings.fontSize = Data.getDefaultSettings().fontSize;
					settings.fontName = Data.getDefaultSettings().fontName;
					spinSize.setValue(settings.fontSize);
					jtfFontName.setText(settings.fontName);
					LotoLabel.setFont(settings.fontName);
					LotoLabel.setFSize(settings.fontSize);
					settings.bold = Data.getDefaultSettings().bold;
					if(settings.bold)
						jrBold.setSelected(true);
					else
						jrPlain.setSelected(true);
					
					repaintOut();
					break;
				case "resetSettings":
					preloadSettings(true);
					break;
				case "loadSettings":
					if(Data.settingsFound())
						preloadSettings(false);
					else
						JOptionPane.showMessageDialog(settingsFrame, lang.noSettingsMsg, lang.warning, JOptionPane.PLAIN_MESSAGE);
					break;
				case "saveSettings":
					if(Data.saveSettings(settings))
						JOptionPane.showMessageDialog(settingsFrame, lang.settingsSavedMsg, lang.saved, JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(settingsFrame, lang.saveFailedMsg, lang.saveFailed, JOptionPane.PLAIN_MESSAGE);
					break;
				case "stdbyImages":
					Path p = JTFDialog.showJTFDialog(null,Data.getFDRoot(settings.standbyPath),lang, font, true);
					if(p!=null){
						settings.standbyPath = Data.getRootFile(p).getPath();
						if(settingsFrame.isVisible()){
							jtfStandbyImages.setText(settings.standbyPath);
							settingsFrame.repaint();
						}
					}
					int n = jpStandby.updateImages(settings.standbyPath);
					if(n > 0){
						jrStdbyDefT.setEnabled(true);
						jrStdbyDefF.setEnabled(true);
						jLblStdbyNImgs.setText(" " + n);
					}else {
						jrStdbyDefT.setEnabled(false);
						jrStdbyDefF.setEnabled(false);
						jrStdbyDefT.setSelected(false);
						jrStdbyDefF.setSelected(true);
						
						jLblStdbyNImgs.setText(" -");
					}					
					break;
				default:
					System.err.println("-SettingsBtnListener- Component not listed in switch : "+((JComponent)e.getSource()).getName());
					break;
			}
		}
	}
	
	private class SettingsBgListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "confirmN":
				try {
					spinNBgR.commitEdit();
					spinNBgG.commitEdit();
					spinNBgB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsBgListener - Could not parse normal color");
				}
				
				LotoLabel.normalBg = settings.normalBg;
				break;
			case "confirmD":
				try {
					spinDBgR.commitEdit();
					spinDBgG.commitEdit();
					spinDBgB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsBgListener - Could not parse dark color");
				}
				
				LotoLabel.darkBg = settings.darkBg;
				break;
			case "confirmL":
				try {
					spinLBgR.commitEdit();
					spinLBgG.commitEdit();
					spinLBgB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsBgListener - Could not parse light color");
				}
				
				LotoLabel.lightBg = settings.lightBg;
				break;
			case "cancelN":
				settings.normalBg = LotoLabel.normalBg;
				spinNBgR.setValue(settings.normalBg.getRed());
				spinNBgG.setValue(settings.normalBg.getGreen());
				spinNBgB.setValue(settings.normalBg.getBlue());
				jpNBgShow.setBackground(settings.normalBg);
				break;
			case "cancelD":
				settings.darkBg = LotoLabel.darkBg;
				spinDBgR.setValue(settings.darkBg.getRed());
				spinDBgG.setValue(settings.darkBg.getGreen());
				spinDBgB.setValue(settings.darkBg.getBlue());
				jpDBgShow.setBackground(settings.darkBg);
				break;
			case "cancelL":
				settings.lightBg = LotoLabel.lightBg;
				spinLBgR.setValue(settings.lightBg.getRed());
				spinLBgG.setValue(settings.lightBg.getGreen());
				spinLBgB.setValue(settings.lightBg.getBlue());
				jpLBgShow.setBackground(settings.lightBg);
				break;
			case "resetN":
				settings.normalBg = Data.getDefaultSettings().normalBg;
				spinNBgR.setValue(settings.normalBg.getRed());
				spinNBgG.setValue(settings.normalBg.getGreen());
				spinNBgB.setValue(settings.normalBg.getBlue());
				jpNBgShow.setBackground(settings.normalBg);
				break;
			case "resetD":
				settings.darkBg = Data.getDefaultSettings().darkBg;
				spinDBgR.setValue(settings.darkBg.getRed());
				spinDBgG.setValue(settings.darkBg.getGreen());
				spinDBgB.setValue(settings.darkBg.getBlue());
				jpDBgShow.setBackground(settings.darkBg);
				break;
			case "resetL":
				settings.lightBg = Data.getDefaultSettings().lightBg;
				spinLBgR.setValue(settings.lightBg.getRed());
				spinLBgG.setValue(settings.lightBg.getGreen());
				spinLBgB.setValue(settings.lightBg.getBlue());
				jpLBgShow.setBackground(settings.lightBg);
				break;
			default:
				System.err.println("-SettingsBgListener- Component not listed in switch : "+((JComponent)e.getSource()).getName());
				break;
			}
			settingsFrame.repaint();
			repaintOut();
		}
	}
	private class SettingsFListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "confirmN":
				try {
					spinNFR.commitEdit();
					spinNFG.commitEdit();
					spinNFB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsFListener - Could not parse normal color");
				}
				
				LotoLabel.normalFont = settings.normalF;
				break;
			case "confirmI":
				try {
					spinIFR.commitEdit();
					spinIFG.commitEdit();
					spinIFB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsFListener - Could not parse invalid color");
				}
				
				LotoLabel.invalidFont = settings.invalidF;
				break;
			case "confirmV":
				try {
					spinVFR.commitEdit();
					spinVFG.commitEdit();
					spinVFB.commitEdit();
				}catch(ParseException ex){
					System.err.println("SettingsFListener - Could not parse valid color");
				}
				
				LotoLabel.validFont = settings.validF;
				break;
			case "cancelN":
				settings.normalF = LotoLabel.normalFont;
				spinNFR.setValue(settings.normalF.getRed());
				spinNFG.setValue(settings.normalF.getGreen());
				spinNFB.setValue(settings.normalF.getBlue());
				jpNFShow.setBackground(settings.normalF);
				break;
			case "cancelI":
				settings.invalidF = LotoLabel.invalidFont;
				spinIFR.setValue(settings.invalidF.getRed());
				spinIFG.setValue(settings.invalidF.getGreen());
				spinIFB.setValue(settings.invalidF.getBlue());
				jpIFShow.setBackground(settings.invalidF);
				break;
			case "cancelV":
				settings.validF = LotoLabel.validFont;
				spinVFR.setValue(settings.validF.getRed());
				spinVFG.setValue(settings.validF.getGreen());
				spinVFB.setValue(settings.validF.getBlue());
				jpVFShow.setBackground(settings.validF);
				break;
			case "resetN":
				settings.normalF = Data.getDefaultSettings().normalF;
				spinNFR.setValue(settings.normalF.getRed());
				spinNFG.setValue(settings.normalF.getGreen());
				spinNFB.setValue(settings.normalF.getBlue());
				jpNFShow.setBackground(settings.normalF);
				break;
			case "resetI":
				settings.invalidF = Data.getDefaultSettings().invalidF;
				spinIFR.setValue(settings.invalidF.getRed());
				spinIFG.setValue(settings.invalidF.getGreen());
				spinIFB.setValue(settings.invalidF.getBlue());
				jpIFShow.setBackground(settings.invalidF);
				break;
			case "resetV":
				settings.validF = Data.getDefaultSettings().validF;
				spinVFR.setValue(settings.validF.getRed());
				spinVFG.setValue(settings.validF.getGreen());
				spinVFB.setValue(settings.validF.getBlue());
				jpVFShow.setBackground(settings.validF);
				break;
			default:
				System.err.println("-SettingsFListener- Component not listed in switch : "+((JComponent)e.getSource()).getName());
				break;
			}
			jpNFShow.repaint();
			jpIFShow.repaint();
			jpVFShow.repaint();
			repaintOut();
		}
	}
	
	private class SpinWait implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			jpStandby.setWaitTime(((int)spinWait.getValue()) * 1000);
		}
	}
	private class SpinBg implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			switch(((Component)e.getSource()).getName()){
				case "normal":
					settings.normalBg = new Color((int)spinNBgR.getValue(),(int)spinNBgG.getValue(),(int)spinNBgB.getValue());
					jpNBgShow.setBackground(settings.normalBg);
					jpNBgShow.repaint();
					break;
				case "dark":
					settings.darkBg = new Color((int)spinDBgR.getValue(),(int)spinDBgG.getValue(),(int)spinDBgB.getValue());
					jpDBgShow.setBackground(settings.darkBg);
					jpDBgShow.repaint();
					break;
				case "light":
					settings.lightBg = new Color((int)spinLBgR.getValue(),(int)spinLBgG.getValue(),(int)spinLBgB.getValue());
					jpLBgShow.setBackground(settings.lightBg);
					jpLBgShow.repaint();
					break;
				default:
					break;
			}
			settingsFrame.repaint();
		}
	}
	private class SpinF implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			switch(((Component)e.getSource()).getName()){
				case "normal":
					settings.normalF = new Color((int)spinNFR.getValue(),(int)spinNFG.getValue(),(int)spinNFB.getValue());
					jpNFShow.setBackground(settings.normalF);
					jpNFShow.repaint();
					break;
				case "invalid":
					settings.invalidF = new Color((int)spinIFR.getValue(),(int)spinIFG.getValue(),(int)spinIFB.getValue());
					jpIFShow.setBackground(settings.invalidF);
					jpIFShow.repaint();
					break;
				case "valid":
					settings.validF = new Color((int)spinVFR.getValue(),(int)spinVFG.getValue(),(int)spinVFB.getValue());
					jpVFShow.setBackground(settings.validF);
					jpVFShow.repaint();
					break;
				default:
					break;
			}
			settingsFrame.repaint();
		}
	}
	private class BgTypeListener implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			settings.reverseValid = jcbVBgRev.isSelected();
			LotoLabel.setVBgReversed(settings.reverseValid);
			repaintOut();
		}
	}	
	
	private class RadioPlain implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			settings.bold = jrBold.isSelected();
			if(settings.bold)
				LotoLabel.setBold(true);
			else
				LotoLabel.setBold(false);
			repaintOut();
		}
	}
	private class RadioStdby implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			settings.standbyDefault = jrStdbyDefT.isSelected();			
			jpStandby.setDefault(settings.standbyDefault);
			if(standBy)
				repaintOut();
		}
	}
	private class RadioWindows implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			boolean rw1,rw2,rw3,rw4,rw5;
			rw1 = w1.isSelected();
			rw2 = w2.isSelected();
			rw3 = w3.isSelected();
			rw4 = w4.isSelected();
			rw5 = w5.isSelected();
			
			if(switchable)
				if(rw1){
					mainFrame.toFront();
					switchable=false;
					mainFrame.repaint();
					repaintOut();
					if(adminFrame.isVisible())
						adminFrame.repaint();
					if(controlFrame.isVisible())
						controlFrame.repaint();
					if(settingsFrame.isVisible())
						settingsFrame.repaint();
					miClose.setEnabled(false);
				}else if(rw2){
					outputFrame.toFront();
					switchable=false;
					mainFrame.repaint();
					repaintOut();
					if(adminFrame.isVisible())
						adminFrame.repaint();
					if(controlFrame.isVisible())
						controlFrame.repaint();
					if(settingsFrame.isVisible())
						settingsFrame.repaint();
					miClose.setEnabled(false);
				}else if(rw3){
					adminFrame.toFront();
					switchable=false;
					mainFrame.repaint();
					repaintOut();
					adminFrame.repaint();
					if(controlFrame.isVisible())
						controlFrame.repaint();
					if(settingsFrame.isVisible())
						settingsFrame.repaint();
				}else if(rw4){
					controlFrame.toFront();
					switchable=false;
					mainFrame.repaint();
					repaintOut();
					if(adminFrame.isVisible())
						adminFrame.repaint();
					if(settingsFrame.isVisible())
						settingsFrame.repaint();
				}else if(rw5){
					settingsFrame.toFront();
					switchable=false;
					mainFrame.repaint();
					repaintOut();
					if(adminFrame.isVisible())
						adminFrame.repaint();
					if(controlFrame.isVisible())
						controlFrame.repaint();
				}
		}
	}
	private class RadioSerie implements ItemListener{
		public void itemStateChanged(ItemEvent arg0){
			boolean r1,r2,r3;
			r1 = s1.isSelected();
			r2 = s2.isSelected();
			r3 = s3.isSelected();
						
			if(r1){
				jLblSerieA.setText(lang.seriesTypeA);
				jLblRequired.setText(lang.requiredA);
				nSerie = lang.seriesNameA;
			}else if(r2){
				jLblSerieA.setText(lang.seriesTypeB);
				jLblRequired.setText(lang.requiredB);
				nSerie = lang.seriesNameB;
			}else if(r3){
				jLblSerieA.setText(lang.seriesTypeC);
				jLblRequired.setText(lang.requiredC);
				nSerie = lang.seriesNameC;
			}
			jLblSerieA.repaint();
			jLblRequired.repaint();
		}	
	}
	private class RadioAutosave implements ItemListener{
		public void itemStateChanged(ItemEvent e){
			settings.autosave = jrASaveT.isSelected();
		}
	}
	
	private class FrameMenuState implements MenuListener{
		public void menuDeselected(MenuEvent e){}
		public void menuCanceled(MenuEvent e){}

		public void menuSelected(MenuEvent e){
			switchable=true;
		}
	}
	
	private class MiListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(((JComponent)e.getSource()).getName()){
			case "miQuit":
				exit();
				break;
			case "miClose":
				KeyboardFocusManager kFM;
				JFrame focused;
				kFM = KeyboardFocusManager.getCurrentKeyboardFocusManager();
				focused = (JFrame)SwingUtilities.getRoot(kFM.getFocusOwner());
				
				switch(focused.getName()){
					case "admin":
						adminFrame.setVisible(false);
						(new FrameListener()).windowClosing(new WindowEvent(adminFrame, WindowEvent.WINDOW_CLOSING));
						break;
					case "control":
						controlFrame.setVisible(false);
						(new FrameListener()).windowClosing(new WindowEvent(controlFrame, WindowEvent.WINDOW_CLOSING));
						break;
					case "settings":
						settingsFrame.setVisible(false);
						(new FrameListener()).windowClosing(new WindowEvent(settingsFrame, WindowEvent.WINDOW_CLOSING));
						break;
					default:
						break;
				}
				break;
			case "miChooseDir":
				Path p = JTFDialog.showJTFDialog(null,Data.getFDRoot(data.getPath()),lang, font, true);
				if(p!=null){
					data.setDirectory(p);
					settings.savePath = data.getPath().toString();
					if(settingsFrame.isVisible()){
						jtfSaveFolder.setText(settings.savePath);
						settingsFrame.repaint();
					}
				}
				break;
			case "miSaveStats":
				String fName;
				data.setName(nSerie);
				fName = data.saveStats(grid.getNumbers());
				switch(fName){
				case "":
					JOptionPane.showMessageDialog(mainFrame,lang.noDataMsg, lang.noData,JOptionPane.PLAIN_MESSAGE);
					break;
				case "-1":
					JOptionPane.showMessageDialog(mainFrame,lang.saveFailedMsg,lang.saveFailed, JOptionPane.WARNING_MESSAGE);
					break;
				default:
					JOptionPane.showMessageDialog(mainFrame, lang.savedMsg+fName, lang.saved, JOptionPane.PLAIN_MESSAGE);
					break;
				}
				break;
			case "miSaveAs":
				File f,fL[];
				String name;
				Path newP,oldP;
				
				fd.setVisible(true);
				fL = fd.getFiles();
				if(fL.length!=0 && fL[0]!=null){
					f=fL[0];
					name = f.getName();
					newP = Data.getCanonOrAbs(f).toPath(); 
					oldP = data.getPath();
					data.setDirectory(newP);
					name = data.saveStats(grid.getNumbers(), name);
					data.setDirectory(oldP);
					switch(name){
					case "":
						JOptionPane.showMessageDialog(mainFrame,lang.noDataMsg, lang.noData,JOptionPane.PLAIN_MESSAGE);
						break;
					case "-1":
						JOptionPane.showMessageDialog(mainFrame,lang.saveFailedMsg,lang.saveFailed, JOptionPane.WARNING_MESSAGE);
						break;
					default:
						JOptionPane.showMessageDialog(mainFrame, lang.savedMsg+name, lang.saved, JOptionPane.PLAIN_MESSAGE);
						break;
				}}
				break;
			case "miStandBy":
				if(!standBy){
					deactivateButtons();
					outputFrame.setContentPane(jpStandby);
					miStandBy.setText(lang.normalMenuItem);
					jpStandby.setVisible(true);
					jpStandby.revalidate();
				}else{
					restoreButtons();
					outputFrame.setContentPane(jpBaseOut);
					miStandBy.setText(lang.standByMenuItem);
					jpStandby.setVisible(false);
					(new FrameResized()).componentResized(new ComponentEvent(jpBaseOut, ComponentEvent.COMPONENT_RESIZED));
				}
				standBy=!standBy;
				outputFrame.repaint();
				break;
			case "miResTabPos":
				outputFrame.setLocationRelativeTo(null);
				break;
			case "miResTabSize":
				int s = Data.params.outputDefaultSize,x,y;
				outputFrame.setSize(s,s);
				outputFrame.revalidate();
				x=jpBaseOut.getWidth();
				y=jpBaseOut.getHeight();
				if(x!=s || y!=s)
					outputFrame.setSize(2*s-x,2*s-y);
				break;
			case "miResMainPos":
				mainFrame.setLocationRelativeTo(null);
				break;
			case "miResAdminPos":
				adminFrame.setLocationRelativeTo(null);
				break;
			case "miResAllPos":
				outputFrame.setLocationRelativeTo(null);
				mainFrame.setLocationRelativeTo(null);
				adminFrame.setLocationRelativeTo(null);
				break;
			case "miResTabAll":
				miResTabSize.doClick(Data.params.clickTime);
				miResTabPos.doClick(Data.params.clickTime);
				break;
			case "miReset":
				if(!clearMode || jbClear.isEnabled()){
					clearMode=false;
					jbClear.doClick(Data.params.clickTime);
				}
				break;
			case "miAdmin":
				grid.bootAdmin();
				adminFrame.setVisible(true);
				w3.setEnabled(true);
				w3.setVisible(true);
				miClose.setEnabled(true);
				miStandBy.setEnabled(false);
				subResPos.add(miResAdminPos,2);
				break;
			case "miControl":
				controlFrame.setVisible(true);
				w4.setEnabled(true);
				w4.setVisible(true);
				miClose.setEnabled(true);
				break;
			case "miSettings":
				settingsFrame.setVisible(true);
				jtfSaveFolder.setText(data.getPath().toString());
				w5.setEnabled(true);
				w5.setVisible(true);
				miClose.setEnabled(true);
				break;
			default:
				System.err.println("-MiListener- Component not listed in switch : "+((JComponent)e.getSource()).getName());
				break;
			}
		}
	}
	
	private class FrameResized implements ComponentListener{
		public void componentMoved(ComponentEvent e){}
		public void componentShown(ComponentEvent e){}
		public void componentHidden(ComponentEvent e){}

		public void componentResized(ComponentEvent e){
			switch(e.getComponent().getName()){
			case "jpOut":
				jtOutput.setRowHeight(outputFrame.getContentPane().getHeight()/9);
				break;
			case "jpControl":
				jtControl.setRowHeight(controlFrame.getContentPane().getHeight()/9);
				break;
			case "standbyPanel":
				jpStandby.revalidate();
				break;
			default :
				System.err.println("-FrameResized- Component not listed in switch :"+e.getComponent().getName());
				break;
			}
		}
	}
	private class FrameListener implements WindowListener{
		public void windowOpened(WindowEvent e){}
		public void windowClosed(WindowEvent e){}
		public void windowIconified(WindowEvent e){}
		public void windowDeiconified(WindowEvent e){}
		
		public void windowDeactivated(WindowEvent e){
			if(e.getWindow().getName()=="admin")
				adminFrame.toFront();
		}
		public void windowActivated(WindowEvent e){
			switch(e.getWindow().getName()){
			case "main":
				w1.setSelected(true);
				mainFrame.repaint();
				miClose.setEnabled(false);
				break;
			case "output":
				w2.setSelected(true);
				miClose.setEnabled(false);
				repaintOut();
				break;
			case "admin":
				w3.setSelected(true);
				adminFrame.repaint();
				miClose.setEnabled(true);
				break;
			case "control":
				w4.setSelected(true);
				repaintOut();
				miClose.setEnabled(true);
				break;
			case "settings":
				w5.setSelected(true);
				settingsFrame.repaint();
				miClose.setEnabled(true);
				break;
			default:
				System.err.println("- FrameListener (windowActivated) - Component not listed in switch : "+e.getWindow().getName());
				break;
			}
		}
		public void windowClosing(WindowEvent e){
			switch(e.getWindow().getName()){
				case "admin":
					miClose.setEnabled(false);
					w3.setEnabled(false);
					w3.setVisible(false);
					subResPos.remove(miResAdminPos);
					miStandBy.setEnabled(true);
					break;
				case "control":
					miClose.setEnabled(false);
					w4.setEnabled(false);
					w4.setVisible(false);
					break;
				case "settings":
					miClose.setEnabled(false);
					w5.setEnabled(false);
					w5.setVisible(false);
					break;
				case "main":
					exit();
					break;
				default:
					break;
			}
		}
	}

	private class ShortcutListener implements KeyListener{
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e){}

		public void keyPressed(KeyEvent e){
			int code = e.getKeyCode();
			if(code >= KeyEvent.VK_F1 && code <= KeyEvent.VK_F5)
				switchable=true;
		}
	}
}