// Copyright (C) 2026 Remi Lemaire

package content.cstm;

/**
 * The {@code Language} enumeration contains all the {@link Strings} required for
 * all the loto application's texts display ({@link LotoLabel} text excluded).
 * 
 * @author Remi Lemaire
 * 
 * @see GUI
 * @see LotoLabel
 */

public enum Language{
	Blank (""
			,"","","","","","","","","","","","","","","","","","","","","",
			"","","","","","","","","","","","","","","","","","","","","",
			"","","","","","","","","","","",
			"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
			"","","","","","","","","","","","","","","","","","","","","","",
			"","","","","","","","","","","","","","","","","","","","","","","",""),
	//> French 
	French ("french",
			"Valider","Annuler","Annuler","R\u00e9tablir","Afficher tout","Effacer tout","V\u00E9rifier","Supprimer","Retirer la v\u00e9rification","Ajouter",
			"R\u00e9initialiser", "Gras", "Classique", "Oui","Non","R\u00e9initialiser toutes les options","Fond color\u00e9","Explorer","Modifier","Charger des options",
			"Sauvegarder les options",
			
			"Attention","Entr\u00E9es","Tableau des nombres","Corrections","Affichage secondaire","Options","O\u00f9 sauvegarder les donn\u00e9es ?","Ordre de sortie",
			"Choisissez un dossier", "Aucune donnée à sauvegarder","Erreur lors de la sauvegarde","Fichier sauvegardé","G\u00e9n\u00e9ral","Couleurs de fond",
			"Couleurs de police","Images d'attente","Pour entrer les nombres", "Pour les v\u00E9rifications","Police des nombres","Autre","Fond normal","Fond pour le dernier nombre",
			"Fond pour les invalid\u00e9s","Police normale","Police pour les invalid\u00E9s","Police pour les valid\u00E9s","Options trouvées","Sauvegarde",
			"Un fichier d'options a \u00e9t\u00e9 d\u00e9tect\u00e9 mais il semble provenir d'une version plus ancienne et ne peut pas \u00eatre charg\u00e9.",
			"Erreur de lecture des options",
			
			"Nombre :","Nombre concerné :","Taille :","Police :","Sauvegarder les stats automatiquement :","Dossier de sauvegarde :","R :","V :","B :","S\u00e9rie \u00e0 1 ligne",
			"S\u00e9rie \u00e0 2 lignes","S\u00e9rie \u00e0 3 lignes (carton plein)","1 ligne","2 lignes","3 lignes","5 nombres requis pour gagner",
			"10 nombres requis pour gagner","15 nombres requis pour gagner"," nombre valid\u00e9"," nombres valid\u00e9s"," nombre faux"," nombres faux",
			"Ordre de sortie du nombre :","Indiquez le chemin du dossier de sauvegare :","Nombre d'images d'attente charg\u00e9es : ",
			
			"Il n'y a aucune donnée à enregistrer, aucun fichier n'a été créé", "Une erreur est apparue lors de la sauvegarde des données", "Les stats on étés sauvegardés dans : \n",
			"Toutes les options seront r\u00e9initialis\u00e9es.\nConfirmer ?","Un fichier d'options trouvé, charger les options ?","Aucun fichier d'options trouv\u00e9",
			"Les options ont \u00e9t\u00e9 sauvegard\u00e9es avec succ\u00e8s","Image d'attente par defaut :","Temps d'attente par image :","s", "Chemin vers le dossier contenant les images :",
			
			"Affiche le nombre dans la grille","Affiche tous les nombres de la grille","Efface tous les nombres de la grille",
			"Permet de r\u00e9initialiser la grille compl\u00e9tement","Annule la derni\u00E8re action effectu\u00E9e","R\u00e9tabli la derni\u00e8re action annul\u00e9e",
			"V\u00E9rifie si le nombre est pr\u00E9sent dans la grille","V\u00e9rifie tous les nombres de la grille","R\u00e9initialise les v\u00e9rifications",
			"Remet la grille en mode classique (pas de vert ni de rouge)","Confirme toutes les corrections apportés","Annule toutes les corrections apportés",
			"Supprime le nombre de la grille","Remet le nombre en mode normal (pas de vert ni de rouge)",
			"Permet de sauvegarder les stats à chaque r\u00e9initialisation de la grille",
			"Donnez un ordre de sortie au nombre (utilis\u00e9 lors des annulations et dans les stats)","Confirme et change le dossier de sauvegarde",
			"Annule les changements effectu\u00e9s","Ouvre un explorateur de fichier pour s\u00e9lectionner un dossier",
			"Chemin du dossier o\u00f9 les fichiers sont enregistr\u00e9s","Charge les options si le fichier est disponible",
			
			"Fichier", "Edition","S\u00e9ries","Pr\u00e9sentation","Fen\u00eatres","R\u00e9initialiser les positions","Fermer la fen\u00eatre","Quitter","Annuler",
			"Annuler les v\u00e9rifications","Choisir le dossier de sauvegarde","Sauvegarder les nombres sortis","Sauvegarder sous...","R\u00e9initialiser la taille du tableau",
			"R\u00e9initialiser la position du tableau","R\u00e9initialiser la position de la fen\u00eatre principale",
			"R\u00e9initialiser la position de la fen\u00eatre de correction","R\u00e9initialiser toutes les positions","R\u00e9initialiser la taille et position du tableau",
			"Ouvrir la fen\u00eatre de correction","Ouvrir la fen\u00eatre de controle","Ouvrir la fen\u00eatre d'options","Passer en mode attente","Retour en mode normal");
	//<>
	
	public final String confirm, cancel, undo, redo, showAll, clearAll, check, delete, deleteCheck, add, reset, bold, plain, yes, no, resetAllSettings, reversedBackground, explore,
						change,loadSettings,saveSettings,
						
						warning, mainTitle, outputTitle, adminTitle, controlTitle, settingsTitle, filedialogTitle ,sliderdialogTitle, jtfdialogTitle, noData, saveFailed, saved,
						generalTab, backgroundTab, fontTab, standbyTab, numberBorder, checkBorder, fontBorder, otherBorder, normalBgBorder, darkBgBorder, lightBgBorder, normalFBorder,
						invalidFBorder, validFBorder, loadSettingsTitle, saveSettingsBorder, loadSettingsErrorMessage, loadSettingsErrorTitle,
						
						numberLabel, adminNbrLabel, fontSizeLabel, fontLabel, autoSaveLabel, currentFolderLabel, redLabel, greenLabel, blueLabel, seriesTypeA, seriesTypeB, seriesTypeC, seriesNameA,
						seriesNameB, seriesNameC, requiredA, requiredB, requiredC, validNbr, validNbrs, wrongNbr, wrongNbrs, orderLabel, pathLabel, nbStandbyLabel,
						
						noDataMsg, saveFailedMsg, savedMsg, settingsResetMsg, loadSettingsMsg, noSettingsMsg, settingsSavedMsg, settingsStdbyDefault, settingsWaitTime,
						settingsWaitUnit, settingsStdbyPath,
						
						confirmAddTTT, showAllTTT, clearAllTTT, resetTTT, undoTTT, redoTTT, checkTTT, checkAllTTT, resetCheckTTT, emptyCheckTTT, adminConfirmTTT, adminCancelTTT,
						deleteTTT, deleteCheckTTT, autosaveTTT, sliderTTT, confirmPathTTT, cancelPathTTT, exploreTTT, currentFolderTTT, loadSettingsTTT,
						
						fileMenu, editMenu, seriesMenu, viewMenu, windowMenu, resetPosMenu, closeMItem, quitMItem, cancelMItem, emptyCheckMItem, pathMItem, saveMItem, saveAsMItem,
						resetTabSizeMItem, resetTabPosMItem, resetMainPosMItem, resetAdminPosMItem, resetAllPosMItem, resetTabAllMItem, adminMItem, controlMItem, settingsMItem,
						standByMenuItem, normalMenuItem;
	private final String LanguageName;
	
	Language(String LanguageName,
			
			String cnfrm, String cncl, String nd, String rd, String shwll, String clrll, String chck, String dlt, String dltChck, String dd, String rst, String bld, String pln,
			String ys, String n, String srtllSttngs, String rvrsdBckgrnd, String xplr, String chng, String ldSttngs, String svSttngs,
			
			String wrnng, String mnTtl, String tptTtl, String dmnTtl, String cntrlTtl, String SttngsTtl, String fldlgTtl, String sldrdlgTtl, String jtfdlgTtl, String nDt,
			String svFld, String svd, String gnrlTb, String bckgrndTb, String fntTb, String sadyTb, String nmbrBrdr, String chckBrdr, String fntBrdr, String thrBrdr,
			String nrmlBgBrdr, String drkBgBrdr, String lghtBgBrdr, String nrmlFBrdr, String nvldFBrdr, String vldFBrdr, String ldSttngsTtl, String svSttngsBrdr,
			String ldSttngsrrrMssg ,String ldSttngsrrrTtl,
			
			String nmbrLbl, String dmnNbrLbl, String fntSzLbl, String fntLbl, String tSvLbl, String crrtFldrLbl, String rdLbl, String grnLbl, String blLbl, String srsTpA,
			String srsTpB, String srsTpC, String srsNA, String srsNB, String srsNC, String rqrdA, String rqrdB, String rqrdC, String vldNbr, String vldNbrs, String wrngNbr,
			String wrngNbrs, String rdrLbl, String pthLbl, String nbStndbLbl,
			
			String nDtMsg, String svFldMsg, String svdMsg, String sttngsRstMsg, String ldSttngsMsg, String nSttngsMsg,
			String sttngsSvdMsg, String sttngsStdbDflt,String sttngsWtTm, String sttngsWtnt, String sttngsStdbPth,
			
			String cnfrmddTTT, String shwllTTT, String clrllTTT, String rstTTT,String ndTTT, String rdTTT, String chckTTT, String chckllTTT, String rstChckTTT,
			String mptChckTTT, String dmnCnfrmTTT, String dmnCnclTTT, String dltTTT, String dltChckTTT, String tsvTTT, String sldrTTT, String cnfrmPthTTT, String cnclPthTTT,
			String xplrTTT, String crrtFldrTTT, String ldSttngsTTT,
			
			String flMn, String dtMn, String srsMn, String vwMn, String wndwMn, String rstPsMn, String clsMtm, String qtMtm, String cnclMtm, String mptChckMtm, String pthMtm,
			String svMtm, String svsMtm, String rstTbSzMtm, String rstTbPsMtm, String rstMnPsMtm, String rstdmnPsMtm, String rstllPsMtm, String rstTbllMtm, String dmnMtm,
			String cntrlMtm, String sttngsMtm, String stndBMntm, String nrmlMntm){
		
		this.LanguageName = LanguageName;
		
		confirm = cnfrm;
		cancel = cncl;
		undo = nd;
		redo = rd;
		showAll = shwll;
		clearAll = clrll;
		check = chck;
		delete = dlt;
		deleteCheck = dltChck;
		add = dd;
		reset = rst;
		bold = bld;
		plain = pln;
		yes = ys;
		no = n;
		resetAllSettings = srtllSttngs;
		reversedBackground = rvrsdBckgrnd;
		explore = xplr;
		change = chng;
		loadSettings = ldSttngs;
		saveSettings = svSttngs;
		
		warning = wrnng;
		mainTitle = mnTtl;
		outputTitle = tptTtl;
		adminTitle = dmnTtl;
		controlTitle = cntrlTtl;
		settingsTitle = SttngsTtl;
		filedialogTitle = fldlgTtl;
		sliderdialogTitle = sldrdlgTtl;
		jtfdialogTitle = jtfdlgTtl;
		noData = nDt;
		saveFailed = svFld;
		saved = svd;
		generalTab = gnrlTb;
		backgroundTab = bckgrndTb;
		fontTab = fntTb;
		standbyTab = sadyTb;
		numberBorder = nmbrBrdr;
		checkBorder = chckBrdr;
		fontBorder = fntBrdr;
		otherBorder = thrBrdr;
		loadSettingsTitle = ldSttngsTtl;
		saveSettingsBorder = svSttngsBrdr;
		loadSettingsErrorMessage = ldSttngsrrrMssg;
		loadSettingsErrorTitle = ldSttngsrrrTtl;

		settingsStdbyDefault = sttngsStdbDflt;
		settingsWaitTime = sttngsWtTm;
		settingsWaitUnit = sttngsWtnt;
		settingsStdbyPath =sttngsStdbPth;
		normalBgBorder = nrmlBgBrdr;
		darkBgBorder = drkBgBrdr;
		lightBgBorder = lghtBgBrdr;
		normalFBorder = nrmlFBrdr;
		invalidFBorder = nvldFBrdr;
		validFBorder = vldFBrdr;
		
		numberLabel = nmbrLbl;
		adminNbrLabel = dmnNbrLbl;
		fontSizeLabel = fntSzLbl;
		fontLabel = fntLbl;
		autoSaveLabel = tSvLbl;
		currentFolderLabel = crrtFldrLbl;
		redLabel = rdLbl;
		greenLabel = grnLbl;
		blueLabel = blLbl;
		seriesTypeA = srsTpA;
		seriesTypeB = srsTpB;
		seriesTypeC = srsTpC;
		seriesNameA = srsNA;
		seriesNameB = srsNB;
		seriesNameC = srsNC;
		requiredA = rqrdA;
		requiredB = rqrdB;
		requiredC = rqrdC;
		validNbr = vldNbr;
		validNbrs = vldNbrs;
		wrongNbr = wrngNbr;
		wrongNbrs = wrngNbrs;
		orderLabel = rdrLbl;
		pathLabel = pthLbl;
		nbStandbyLabel = nbStndbLbl;
		
		noDataMsg = nDtMsg;
		saveFailedMsg = svFldMsg;
		savedMsg = svdMsg;
		settingsResetMsg = sttngsRstMsg;
		loadSettingsMsg = ldSttngsMsg;
		noSettingsMsg = nSttngsMsg;
		settingsSavedMsg = sttngsSvdMsg;
		
		confirmAddTTT = cnfrmddTTT;
		showAllTTT = shwllTTT;
		clearAllTTT = clrllTTT;
		resetTTT = rstTTT;
		undoTTT = ndTTT;
		redoTTT = rdTTT;
		checkTTT = chckTTT;
		checkAllTTT = chckllTTT;
		resetCheckTTT = rstChckTTT;
		emptyCheckTTT = mptChckTTT;
		adminConfirmTTT = dmnCnfrmTTT;
		adminCancelTTT = dmnCnclTTT;
		deleteTTT = dltTTT;
		deleteCheckTTT = dltChckTTT;
		autosaveTTT = tsvTTT;
		sliderTTT = sldrTTT;
		confirmPathTTT = cnfrmPthTTT;
		cancelPathTTT = cnclPthTTT;
		exploreTTT = xplrTTT;
		currentFolderTTT = crrtFldrTTT;
		loadSettingsTTT = ldSttngsTTT;
		
		fileMenu = flMn;
		editMenu = dtMn;
		seriesMenu = srsMn;
		viewMenu = vwMn;
		windowMenu = wndwMn;
		resetPosMenu = rstPsMn;
		closeMItem = clsMtm;
		quitMItem = qtMtm;
		cancelMItem = cnclMtm;
		emptyCheckMItem = mptChckMtm;
		pathMItem = pthMtm;
		saveMItem = svMtm;
		saveAsMItem = svsMtm;
		resetTabSizeMItem = rstTbSzMtm;
		resetTabPosMItem = rstTbPsMtm;
		resetMainPosMItem = rstMnPsMtm;
		resetAdminPosMItem = rstdmnPsMtm;
		resetAllPosMItem = rstllPsMtm;
		resetTabAllMItem = rstTbllMtm;
		adminMItem = dmnMtm;
		controlMItem = cntrlMtm;
		settingsMItem = sttngsMtm;
		standByMenuItem = stndBMntm;
		normalMenuItem = nrmlMntm;
	}
	
	public String toString(){
		return LanguageName;
	}
	
	public static Language getLanguage(String name){
		if(name.equals(French.LanguageName))
			return French;
		return Blank;
	}
}