/* Logiciel d'affichage pour une grille de Loto (1-90).
 * Copyright (C) 2026 Remi Lemaire
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import content.cstm.Data;
import content.visuals.GUI;
import splash.Splash;

/**
 * The {@code Loto} class is the main class of the loto application, it doesn't do much and
 * only contains the <code>public static void main(String[] args)</code> method.
 * Its only purpose are to have the application named "Loto" in the mac menu bar, and a cleaner
 * and invisible package structure.
 * 
 * @author Remi Lemaire
 * 
 * @see GUI
 */

public class Loto {//version 1.3.0
	public static void main(String[] args){
		Splash splash = new Splash(Data.params.splashAddress);
		splash.setVisible(true);
		GUI gui = new GUI();
		splash.dispose();
		gui.setVisible();
	}
}