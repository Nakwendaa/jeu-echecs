import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Classe de l'échiquier.
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Echiquier {
	
	/**
	 * L'échiquier possède un tableau de jeu composé de pièces. La position d'une pièce dans ce tableau est fonction de la position de la pièce dans l'échiquier.
	 * 
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#prendsPiece(int, int)
	 * @see Echiquier#posePiece(Piece)
	 */
	
	  	private Piece[][] tableau_de_jeu;
	    
	/**
	  * Ce tableau contient les pièces blanches capturées.
	  * 
	  *  @see Echiquier#capturePiece(int, int)	
	  */
	  	
	  	private Piece[] blancs_captures;
	  	
	  /**
	   	* Ce tableau contient les pièces noires capturées.
	    * 
	    *  @see Echiquier#capturePiece(int, int)	
	    */
	  	
	    private Piece[] noirs_captures;
	    
	    /**
	     * Constructeur de l'objet Echiquier. Le constructeur initialise un tableau de jeu de 64 cases auquel est ajouté les pièces
	     * du jeu des échecs et initialise deux tableaux de pièces capturés.
	     * 
	     * @see Echiquier#tableau_de_jeu
	     * @see Echiquier#blancs_captures
	     * @see Echiquier#noirs_captures
	     * @see Piece
	     * @see Tour
	     * @see Cavalier
	     * @see Fou
	     * @see Dame
	     * @see Roi
	     * @see Pion
	     */
	    
	    public Echiquier (boolean[][] couleur, String[][] typePiece) {
	    	this.blancs_captures = new Piece[16];
			this.noirs_captures = new Piece[16];
			this.tableau_de_jeu = new Piece[8][8];
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (typePiece[i][j].equals("."))
						this.tableau_de_jeu[i][j] = null;
					else {
						switch(typePiece[i][j].toUpperCase()) { 
						case "R" :
							this.tableau_de_jeu[i][j] = new Roi(couleur[i][j], i, j, this);
							break;
						case "D" :
							this.tableau_de_jeu[i][j] = new Dame(couleur[i][j], i, j, this);
							break;
						case "T" :
							this.tableau_de_jeu[i][j] = new Tour(couleur[i][j], i, j, this);
							break;
						case "F" :
							this.tableau_de_jeu[i][j] = new Fou(couleur[i][j], i, j, this);
							break;
						case "C" :
							this.tableau_de_jeu[i][j] = new Cavalier(couleur[i][j], i, j, this);
							break;
						case "P" :
							this.tableau_de_jeu[i][j] = new Pion(couleur[i][j], i, j, this);
							break;
						}
					}
				}
			}
	    }
	    
	    /**
	     * Méthode qui crée une copie de l'échiquier en cours, 
	     * afin de permettre de faire des déplacements virtuels pour tester des positions sans affecter l'échiquier en cours.
	     * 
	     * @return
	     * 			Un objet échiquier identique à celui sur lequel la méthode a été utilisée.
	     * 
		 * @see Roi#deplacementValide(int, int)
	     * @see Dame#deplacementValide(int, int)
	     * @see Fou#deplacementValide(int, int)
	     * @see Cavalier#deplacementValide(int, int)
	     * @see Tour#deplacementValide(int, int)
	     * @see Pion#deplacementValide(int, int)
	     * @see JeuEchec#main(String[])
	     */		
	    
	    public Echiquier copie(){
	    	boolean[][] couleur = new boolean[8][8]; // Couleur est un tableau contenant la couleur de la pièce se trouvant a la position i, j du tableau de jeu.
	    	String[][] typePiece = new String[8][8]; // typePiece est un tableau contenant le type de la piece se trouvant a la position i, j du tableau de jeu.
	    	for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (this.tableau_de_jeu[i][j] != null) {
						couleur[i][j]= this.tableau_de_jeu[i][j].estBlanc();
						typePiece[i][j]=this.tableau_de_jeu[i][j].representationAscii();
					}
					else {
						typePiece[i][j]=".";
						couleur[i][j]= true;
					}
				}
	    	}
	    	Echiquier copie = new Echiquier(couleur, typePiece); // On créé la copie de l'échiquier.
	    	return copie; // On retourne l'échiquier copié.
	    }
	    
	    /**
	     * Vérifie que la case est bien dans l'échiquier.
	     * 
	     * @param colonne
	   	 *			Colonne de la case.
	   	 *
	     * @param ligne
	     * 			Ligne de la case.
	     * 
	     * @return Vrai si la case est dans l'échiquier.
	     */
	    
	    public boolean caseValide(int colonne, int ligne) {
			if (ligne >= 8 || colonne >= 8 || ligne < 0 || colonne < 0) 
				return false;
			else return true;
	    }
	    
	    /**
	     * Regarde s'il y a une pièce ou non sur la case choisie.
	     * 
	     * @param colonne
	     * 			Colonne de la case à examiner.
	     * @param ligne
	     * 			Ligne de la case à examiner.
	     * @return Une pièce de l'échiquier, ou null s'il n'y a aucune pièce sur cette case.
	     * 
	     * @see Echiquier#tableau_de_jeu
	     */
	    
	    public Piece examinePiece(int colonne, int ligne) {
	    	if (this.tableau_de_jeu[ligne][colonne] != null)
	    		return this.tableau_de_jeu[ligne][colonne];
	    	else return null;
	    }
	    
	    /**
	     * Prise d'une pièce sur l'échiquier à une case spécifiée.
	     * 
	     * @param colonne
	     * 			Colonne où se situe la pièce.
	     *
	     * @param ligne
	     *			Ligne où se situe la pièce.
	     *
	     * @return La pièce qui se trouve à la case spécifiée.
	     * 
	     * @see Echiquier#tableau_de_jeu
	     */
	    
	    public Piece prendsPiece(int colonne, int ligne) {
			Piece temp = this.tableau_de_jeu[ligne][colonne];
			this.tableau_de_jeu[ligne][colonne] = null;
			return temp;
	    }

	    /**
	     * Pose une pièce sur la case.
	     * 
	     * @param p
	     * 			Un objet Pièce que l'on veut poser sur une case
	     * @see Echiquier#tableau_de_jeu
	     * @see Piece#getLigne()
	     * @see Piece#getColonne()
	     */
	    
	    public void posePiece(Piece p) {
	    	this.tableau_de_jeu[p.getLigne()][p.getColonne()] = p;
	    }

	    /**
	     * Prend la pièce à la case spécifiée et l'ajoute au bon tableau de capture.
	     * 
	     * @param colonne
	     * 			Colonne de la pièce à capturer.
	     * 
	     * @param ligne
	     * 			Ligne de la pièce à capturer.
	     * 
	     * @see Echiquier#blancs_captures
	     * @see Echiquier#noirs_captures
	     * @see Echiquier#prendsPiece(int, int)
	     * @see Piece#estNoir()
	     */
	    
	    public void capturePiece(int colonne, int ligne) {
			Piece p = this.prendsPiece(colonne, ligne);
			if (p.estNoir()) {
				for (int i = 0; i < 16; i++) {
					if (this.blancs_captures[i] == null) {
						this.blancs_captures[i] = p;
						p.meSuisFaitCapture();
						break;
					}
				}
			}
			else {
				for (int i = 0; i < 16; i++) {
					if (this.noirs_captures[i] == null) {
						this.noirs_captures[i] = p;
						p.meSuisFaitCapture();
						break;
					}
			    }
			}
		}
	    
	    /**
	     * Méthode qui retourne un booléen qui vaut true si le roi est échec et mat.
	     * 
	     * @param colonneRoi
	     * 			La colonne où se trouve le roi
	     * @param ligneRoi
	     * 			La ligne où se trouve le roi
	     * @param colonnePieceEchec
	     * 			La colonne où se trouve la pièce qui met en échec
	     * @param lignePieceEchec
	     * 			La ligne où se trouve la pièce qui met en échec
	     * @return
	     * 			Un booléen qui vaut true si le roi est échec et mat, false sinon
	     * 
	     * @see JeuEchec#main(String[])
	     */
	    
	    
	    public boolean verifieEchecEtMat(int colonneRoi, int ligneRoi, int colonnePieceEchec, int lignePieceEchec) {
	    	for (int i = 0; i < 8; i++) {
	    		for (int j = 0; j < 8; j++) {
		    		if (this.examinePiece(colonneRoi, ligneRoi).deplacementValide(i, j)) // On vérifie si le roi peut faire un déplacement valide.
		    			return false;	// Si oui pas d'échec et mat.
		    		if (this.examinePiece(i, j) != null) { // Sinon on vérifie si une pièce peut capturer la pièce qui met en échec le roi.
			    		if (this.examinePiece(i, j).deplacementValide(colonnePieceEchec, lignePieceEchec) &&
			    		    this.examinePiece(i, j).estBlanc() != this.examinePiece(colonnePieceEchec, lignePieceEchec).estBlanc())
			    					return false; // Si oui, pas d'échec et mat.
			    	}
			    }
	    	}
	    	if (Math.abs(ligneRoi - lignePieceEchec) <= 1 && Math.abs(colonneRoi - colonnePieceEchec) <= 1) // Si le roi ne peut pas se déplacer, qu'aucune pièce ne peut capturer celle qui met en échec.
	    		return true;  // alors si la pièce qui met en échec est collée au roi, il y a échec et mat.
	    	else {  // Sinon il faut vérifier si une pièce peut s'interposer entre son roi et la pièce qui le met en échec.
		    	if (colonnePieceEchec == colonneRoi) { // Si le roi et la pièce qui met en échec son sur la même colonne.
		    		for (int k = Math.min(ligneRoi, lignePieceEchec); k < Math.max(ligneRoi, lignePieceEchec); k++) {
		    			for (int i = 0; i < 8; i++){
		    	    		for (int j = 0; j<8; j++) {
		    	    			if (this.examinePiece(i, j) != null) {
		    		    			if (this.examinePiece(i, j).deplacementValide(colonneRoi, k) &&
		    		    				this.examinePiece(i,j).estBlanc() == this.examinePiece(colonneRoi,ligneRoi).estBlanc())
		    		    					return false;
		    	    			}
		    	    		}
		    			}
		    		}
		    	}
		    	else if (lignePieceEchec == ligneRoi) { //Si le roi et la pièce qui met en échec son sur la meme ligne
		    		for (int k = Math.min(colonneRoi, colonnePieceEchec); k < Math.max(colonneRoi, colonnePieceEchec); k++){
		    			for (int i = 0; i < 8; i++) {
		    	    		for (int j = 0; j < 8; j++) {
		    	    			if (this.examinePiece(i, j) != null) {
		    		    			if (this.examinePiece(i, j).deplacementValide(ligneRoi, k) &&
		    		    				this.examinePiece(i, j).estBlanc() == this.examinePiece(colonneRoi, ligneRoi).estBlanc())
		    		    				return false;
		    	    			}
		    	    		}
		    			}
		    		}
		    	}
		    	else if (Math.abs(ligneRoi - lignePieceEchec) == Math.abs(colonneRoi - colonnePieceEchec)) { // Si le roi et la pièce qui met en échec son sur la même diagonale.
		    		if (ligneRoi < lignePieceEchec && colonneRoi < colonnePieceEchec) { // Premier cas de diagonale.
		        		int i = lignePieceEchec - 1; // La case (j, i)  est une case sur le chemin de la pièce qui met le roi en échec et le roi.
		        		for (int j = colonnePieceEchec - 1; j > colonneRoi; j--) {
		        			for (int c = 0; c < 8; c++) {  // Ces deux boucles for vérifient si un pièce de la même couleur que le roi en échec peut
			    	    		for (int l = 0; l < 8; l++) { // faire un déplacement valide sur la case (j,i)
			    	    			if (this.examinePiece(i, j) == null) {
			    	    				if (this.examinePiece(c,l) != null) {
							   			    if(this.examinePiece(c,l).deplacementValide(j,i) &&
							   			       this.examinePiece(c,l).estBlanc() == this.examinePiece(colonneRoi, ligneRoi).estBlanc())
							   			    		return false; // SI oui il n'y a pas d'echec et mat un pièce peut s'interposer.
			    	    				}
			    	    			}
			    	    		}
		        			}
		        			i--;
			   			}
			       	}
		    		if (ligneRoi > lignePieceEchec && colonneRoi > colonnePieceEchec) { // Deuxième cas de diagonale.
		        		int i = lignePieceEchec + 1;
		        		for (int j = colonnePieceEchec + 1; j < colonneRoi; j++) {
		        			for (int c = 0; c < 8; c++) {
			    	    		for (int l = 0; l < 8; l++) {
			    	    			if (this.examinePiece(i, j) == null) {
			    	    				if (this.examinePiece(c, l) != null) {
							   			    if (this.examinePiece(c, l).deplacementValide(j, i) &&
							   			    	this.examinePiece(c, l).estBlanc() == this.examinePiece(colonneRoi, ligneRoi).estBlanc())
							   			    		return false;
			    	    				}
			    	    			}
			    	    		}
		        			}
		        			i++;
			   			}
			       	}
		    		if (ligneRoi > lignePieceEchec && colonneRoi < colonnePieceEchec) { // Troisième cas de diagonale.
		        		int i = lignePieceEchec + 1;
		        		for (int j = colonnePieceEchec - 1; j > colonneRoi; j--) {
		        			for (int c = 0; c < 8; c++) {
			    	    		for (int l = 0; l < 8; l++) {
			    	    			if (this.examinePiece(i, j) == null) {
			    	    				if (this.examinePiece(c, l) != null) {
							   			    if (this.examinePiece(c, l).deplacementValide(j, i) &&
							   			    	this.examinePiece(c, l).estBlanc() == this.examinePiece(colonneRoi, ligneRoi).estBlanc())
							   			    		return false;
			    	    				}
			    	    			}
			    	    		}
		        			}
		        			i++;
			   			}
			       	}
		    		if (ligneRoi < lignePieceEchec && colonneRoi > colonnePieceEchec) { // Quatrième cas de diagonale.
		        		int i = lignePieceEchec - 1;
		        		for (int j = colonnePieceEchec + 1; j < colonneRoi; j++) {
		        			for (int c = 0; c < 8; c++) {
			    	    		for (int l = 0; l < 8; l++) {
			    	    			if (this.examinePiece(i, j) == null) {
			    	    				if (this.examinePiece(c, l) != null) {
							   			    if (this.examinePiece(c, l).deplacementValide(j, i) &&
							   			    	this.examinePiece(c, l).estBlanc() == this.examinePiece(colonneRoi, ligneRoi).estBlanc())
							   			    		return false;
			    	    				}
			    	    			}
			    	    		}
		        			}
			   			}
		        		i--;
			       	}
		    	}
	    	}
	    	return true;
	    }
	    
	    /**
	     * Méthode qui vérifie si le roi se trouvant à la colonne "nouvelle_colonne" et à la ligne "nouvelle_ligne" est en échec.
	     * 
	     * @param nouvelle_colonne
	     * 				la colonne du roi
	     * @param nouvelle_ligne
	     * 				la ligne du roi
	     * @return
	     * 		Un boléen, true si le roi est échec, false sinon.
	     * 
	     * @see Roi#deplacementValide(int, int)
	     * @see Dame#deplacementValide(int, int)
	     * @see Fou#deplacementValide(int, int)
	     * @see Cavalier#deplacementValide(int, int)
	     * @see Tour#deplacementValide(int, int)
	     * @see Pion#deplacementValide(int, int)
	     * @see JeuEchec#main(String[])
	     */
	    
	    public boolean verifieEchec(int nouvelle_colonne, int nouvelle_ligne) {
	    	for (int i = 0; i < 8; i++) { //Boucle qui vérifie pour chaque case du tableau de jeu si il y a une pièce de couleur différente du roi qui peut
	    		for (int j = 0; j < 8; j++){// faire un déplacement valide sur la position du roi.
	    			if (this.examinePiece(i, j) !=null ) {
		    				if (this.examinePiece(i, j).estBlanc() != this.examinePiece(nouvelle_colonne, nouvelle_ligne).estBlanc() &&
		    					this.examinePiece(i, j).deplacementValide(nouvelle_colonne, nouvelle_ligne)){
		    					if (this.examinePiece(i, j).representationAscii().equalsIgnoreCase("p") && 
		    							nouvelle_colonne==i && Math.abs(nouvelle_ligne-j)<=2)
		    						return false;
		    					else return true;
		    				}
	    			}
	    		}
	    	}
	    	return false;
	    }
	    
	    /**
	     * Méthode qui trouve les coordonnées du roi de la couleur passée en argument.
	     * 
	     * @param couleur
	     * 			la couleur du roi (true si blanc, false sinon).
	     * @return
	     * 			un tableau de coordonnées du roi sur un échiquier.
	     */
	    
	    public int[] trouverRoi(boolean couleur) {
	    	int[] t = new int[]{-1, -1};
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8 ; j++) {
					if (this.examinePiece(i, j) != null)
						if (this.examinePiece(i, j).representationAscii() == "r" && !couleur ||
							this.examinePiece(i,j).representationAscii() == "R" && couleur) {
							t[0] = i; t[1] = j;
							return t;
						}
				}
			}
			return t;
		}
	    
	    /**
	     * Méthode qui gère le cas où le pion arrive sur la dernière ligne.
	     * L'utilisateur aura alors le choix de changer sa pièce en celle qui désire.
	     * 
	     * @throws UnsupportedEncodingException
	     * 			Gère les exceptions liées à l'affichage Unicode.
	     * 
	     * @see Echiquier#tableau_de_jeu
	     * @see Scanner#nextLine()
	     * @see String#substring(int, int)
	     * @see String#equals(Object)
	     * @see String#toLowerCase()
	     * @see String#length()
	     * @see Piece#representationAscii()
	     * @see Piece#representationUnicode()
	     * @see Echiquier#afficheAscii()
	     * @see Echiquier#afficheUnicode()
	     * @see JeuEchec#getArgument()
	     * @see Dame
	     * @see Fou
	     * @see Cavalier
	     * @see Tour
	     * @see Pion
	     */
	    
	    public int promotionPion() throws UnsupportedEncodingException {
	    	Scanner scan = new Scanner(System.in);
	    	for (int i = 0; i < 8; i++) {
	    		if (this.tableau_de_jeu[0][i] != null && this.tableau_de_jeu[0][i].representationAscii().equals("P")) {
	    			System.out.print("En quelle pièce voulez-vous changer votre pion ? ");
	    			Piece a = this.tableau_de_jeu[0][i];
	    			do {
	    				String entree = scan.nextLine();
	    				entree.toLowerCase();
	    				for (int j = 0; j < entree.length(); j++) {
	    					if (entree.charAt(j) == ' ') {
		    					entree = entree.substring(0, j) + entree.substring(j + 1, entree.length());
		    					j--;
		    				}
		    			}
		   				if (entree.equals("dame")) {
		   					a = new Dame(true, 0, i, this);
		   				}
		   				else if (entree.equals("fou")) {
	    					a = new Fou(true, 0, i, this);
	    				}
		   				else if (entree.equals("cavalier")) {
		    				a = new Cavalier(true, 0, i, this);
		    			}
		    			else if (entree.equals("tour")) {
		   					a = new Tour(true, 0, i, this);
		   				}
		   				else if (entree.equals("pion")) {
		   					a = new Pion(true, 0, i, this);
	    				}
	    				else {
	    					System.out.println("Entrée invalide. Vous pouvez changer votre pion en dame, fou, cavalier, tour, pion.");
		    				System.out.print("En quelle pièce voulez-vous changer votre pion ? ");
		    			}
	    			} while (a == this.tableau_de_jeu[0][i]);
		    		if (JeuEchec.getArgument() == "ascii") {
		   				System.out.println(this.tableau_de_jeu[0][i].representationAscii() + " est promu en " + a.representationAscii());
		   			}
		   			else {
		   				System.out.println(this.tableau_de_jeu[0][i].representationUnicode() + " est promu en " + a.representationUnicode());
	    			}
	    			this.tableau_de_jeu[0][i] = a;
	    			if (JeuEchec.getArgument() == "ascii") {
		    			this.afficheAscii();
		    		}
		   			else {
		   				this.afficheUnicode();
		   			}
	    			int[] positionRoiAdverse = this.trouverRoi(!a.estBlanc());
	    			if(a.deplacementValide(positionRoiAdverse[0],positionRoiAdverse[1]))
	    				return 1;
	    		}
	    		else if (this.tableau_de_jeu[7][i] != null && this.tableau_de_jeu[7][i].representationAscii().equals("p")) {
	    			System.out.print("En quelle pièce voulez-vous changer votre pion ? ");
	    			Piece a = this.tableau_de_jeu[7][i];
	    			do {
	    				String entree = scan.nextLine();
	    				entree.toLowerCase();
	    				for (int j = 0; j < entree.length(); j++) {
	    					if (entree.charAt(j) == ' ') {
		    					entree = entree.substring(0, j) + entree.substring(j + 1, entree.length());
		    					j--;
		    				}
		    			}
		   				if (entree.equals("dame")) {
		   					a = new Dame(false, 7, i, this);
		   				}
		   				else if (entree.equals("fou")) {
	    					a = new Fou(false, 7, i, this);
	    				}
		   				else if (entree.equals("cavalier")) {
		    				a = new Cavalier(false, 7, i, this);
		    			}
		    			else if (entree.equals("tour")) {
		   					a = new Tour(false, 7, i, this);
		   				}
		   				else if (entree.equals("pion")) {
		   					a = new Pion(false, 7, i, this);
	    				}
	    				else {
	    					System.out.println("Entrée invalide. Vous pouvez changer votre pion en dame, fou, cavalier, tour, pion.");
		    				System.out.print("En quelle pièce voulez-vous changer votre pion ? ");
		    			}
	    			} while (a == this.tableau_de_jeu[7][i]);
		    		if (JeuEchec.getArgument() == "ascii") {
		   				System.out.println(this.tableau_de_jeu[7][i].representationAscii() + " est promu en " + a.representationAscii());
		   			}
		   			else {
		   				System.out.println(this.tableau_de_jeu[7][i].representationUnicode() + " est promu en " + a.representationUnicode());
	    			}
	    			this.tableau_de_jeu[7][i] = a;
	    			if (JeuEchec.getArgument() == "ascii") {
		    			this.afficheAscii();
		    		}
		   			else {
		   				this.afficheUnicode();
		   			}
	    			int[] positionRoiAdverse = this.trouverRoi(!a.estBlanc());
	    			if (a.deplacementValide(positionRoiAdverse[0],positionRoiAdverse[1]))
	    				return 1;
	    		}
	    	}
	    	return 0;
	    }
	    
	    /**
	     * Méthode qui affiche en mode Ascii l'échiquier.
	     * 
	     * @see Echiquier#blancs_captures
	     * @see Echiquier#noirs_captures
	     * @see Echiquier#tableau_de_jeu
	     * @see Piece#representationAscii()
	     */
	    
		public void afficheAscii() {
			System.out.println("\n");
			String afficheBlancCapture = "";
			for (int i=0; i<this.blancs_captures.length; i++) {
				if (this.blancs_captures[i] != null)
					afficheBlancCapture += " " + this.blancs_captures[i].representationAscii();
			}
			String afficheNoirCapture = "";
		    for (int i = 0; i < this.noirs_captures.length; i++) {
		    	if (this.noirs_captures[i] != null)
		    	afficheNoirCapture += " " + this.noirs_captures[i].representationAscii();
		    }    	
		    System.out.println("Les noirs ont capturé :" + afficheBlancCapture);
			System.out.println("\n   a b c d e f g h");
			System.out.println("   ---------------");
			for (int i = 0; i < 8; i++) {
				System.out.print(8 - i +"|");
			    for (int j = 0; j < 8; j++) {
			    	if (this.tableau_de_jeu[i][j] == null)
			    		System.out.print(" .");
					else
						System.out.print(" " + this.tableau_de_jeu[i][j].representationAscii());
			    }
			    System.out.println(" |" + (8 - i));
			}
			System.out.println("   ---------------");
			System.out.println("   a b c d e f g h");
			System.out.println("\nLes blancs ont capturé :" + afficheNoirCapture);
	        System.out.println();

	    }
		
		/**
		 * Méthode qui permet d'afficher les déplacements possibles d'une pièce en mode Ascii.
		 * 
		 * @param deplacements
		 * 				Une matrice de booléens qui contient true lorsqu'un déplacement est valide sur la ligne et la colonne spécifée.
		 * 
		 * @see Echiquier#noirs_captures
		 * @see Echiquier#blancs_captures
		 * @see Echiquier#tableau_de_jeu
		 * @see Piece#representationAscii()
		 */
		
		public void afficheDeplacements(boolean[][] deplacements) {
			System.out.println("\n");
			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					System.out.print("Les noirs ont capturé:");
					for (int k = 0; k < 16; k++) {
						if (this.noirs_captures[k] != null) {
							System.out.print((this.noirs_captures[k]).representationAscii() + " ");
						}
						else {
							System.out.print("");
						}
					}
					System.out.println("\n");
					System.out.println("   a b c d e f g h");
					System.out.println("   ---------------");
				}
				for (int j = 0; j < 8; j++) {
					if (j == 0) {
						System.out.print(8 - i + "| ");
					}
					if (this.tableau_de_jeu[i][j] != null) {
						if (deplacements[j][i]) {
							System.out.print("X ");
						}
						else {
							System.out.print((this.tableau_de_jeu[i][j]).representationAscii() + " ");
						}
					}
					else {
						if (deplacements[j][i]) {
							System.out.print("x ");
						}
						else {
							System.out.print(". ");
						}
					}
					if (j == 7) {
						System.out.print("|" + (8 - i));
					}
				}
				System.out.println();
				if (i == 7) {
					System.out.println("   ---------------");
					System.out.println("   a b c d e f g h\n");		
					System.out.print("Les blancs ont capturé:");
					for (int k = 0; k < 16; k++) {
						if (this.blancs_captures[k] != null) {
							System.out.print((this.blancs_captures[k]).representationAscii() + " ");
						}
						else {
							System.out.print("");
						}
					}
					System.out.println("\n");
				}
			}
			System.out.println();
		}
		
		/**
	     * Méthode qui affiche en mode Unicode l'échiquier.
	     * 
	     * @see Echiquier#blancs_captures
	     * @see Echiquier#noirs_captures
	     * @see Echiquier#tableau_de_jeu
	     * @see Piece#representationUnicode()
	     */

		public void afficheUnicode() throws UnsupportedEncodingException {
			System.out.println("\n");
			PrintStream sysout = new PrintStream(System.out, true, "UTF-8");
			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					sysout.print("Les noirs ont capturé: ");
					for (int k = 0; k < 16; k++) {
						if (this.noirs_captures[k] != null) {
							sysout.print((this.noirs_captures[k]).representationUnicode() + " ");
						}
						else {
							sysout.print("");
						}
					}
					sysout.println("\n");
					sysout.println("   a   b   c   d   e   f   g   h");
					sysout.println(" ┌───┬───┬───┬───┬───┬───┬───┬───┐");
				}
				for (int j = 0; j < 8; j++) {
					if (j == 0) {
						sysout.print(8 - i + "│");
					}
					if (this.tableau_de_jeu[i][j] != null) {
						if (j != 7) {
							sysout.print(" " + (this.tableau_de_jeu[i][j]).representationUnicode() + " │");
						}
						else {
							sysout.print(" " + (this.tableau_de_jeu[i][j]).representationUnicode() + " ");
						}
					}
					else {
						if (j != 7) {
							sysout.print("   │");
						}
						else {
							sysout.print("   ");
						}
					}
					if (j == 7) {
						sysout.print("│" + (8 - i));
					}
				}
				sysout.println();
				if (i != 7) {
					sysout.println(" ├───┼───┼───┼───┼───┼───┼───┼───┤");
				}
				if (i == 7) {
					sysout.println(" └───┴───┴───┴───┴───┴───┴───┴───┘");
					sysout.println("   a   b   c   d   e   f   g   h\n");		
					sysout.print("Les blancs ont capturé: ");
					for (int k = 0; k < 16; k++) {
						if (this.blancs_captures[k] != null) {
							sysout.print((this.blancs_captures[k]).representationUnicode() + " ");
						}
						else {
							sysout.print("");
						}
					}
					sysout.println("\n");
				}
			}
			sysout.println();
		}
		
		/**
		 * Méthode qui permet d'afficher les déplacements possibles d'une pièce en mode Unicode.
		 * 
		 * @param deplacements
		 * 				Une matrice de booléens qui contient true lorsqu'un déplacement est valide sur la ligne et la colonne spécifée.
		 * 
		 * @see Echiquier#noirs_captures
		 * @see Echiquier#blancs_captures
		 * @see Echiquier#tableau_de_jeu
		 * @see Piece#representationUnicode()
		 */
		
		public void afficheDeplacementsUnicode(boolean[][] deplacements) throws UnsupportedEncodingException {
			System.out.println("\n");
			PrintStream sysout = new PrintStream(System.out, true, "UTF-8");
			for (int i = 0; i < 8; i++) {
				if (i == 0) {
					sysout.print("Les noirs ont capturé: ");
					for (int k = 0; k < 16; k++) {
						if (this.noirs_captures[k] != null) {
							sysout.print((this.noirs_captures[k]).representationUnicode() + " ");
						}
						else {
							sysout.print("");
						}
					}
					sysout.println("\n");
					sysout.println("   a   b   c   d   e   f   g   h");
					sysout.println(" ┌───┬───┬───┬───┬───┬───┬───┬───┐");
				}
				for (int j = 0; j < 8; j++) {
					if (j == 0) {
						sysout.print(8 - i + "│");
					}
					if (this.tableau_de_jeu[i][j] != null) {
						if (deplacements[j][i]) {
							sysout.print(" X │");
						}
						else {
							sysout.print(" " + (this.tableau_de_jeu[i][j]).representationUnicode() + " │");
						}
					}
					else {
						if (deplacements[j][i]) {
							sysout.print(" x │");
						}
						else {
							if (j == 7) {
								sysout.print("   │");
							}
							else {
								sysout.print("   │");
							}
						}
					}
					if (j == 7) {
						sysout.print((8 - i));
					}
				}
				sysout.println();
				if (i != 7) {
					sysout.println(" ├───┼───┼───┼───┼───┼───┼───┼───┤");
				}
				if (i == 7) {
					sysout.println(" └───┴───┴───┴───┴───┴───┴───┴───┘");
					sysout.println("   a   b   c   d   e   f   g   h\n");		
					sysout.print("Les blancs ont capturé: ");
					for (int k = 0; k < 16; k++) {
						if (this.blancs_captures[k] != null) {
							sysout.print((this.blancs_captures[k]).representationUnicode() + " ");
						}
						else {
							sysout.print("");
						}
					}
					sysout.println("\n");
				}
			}
			sysout.println();
		}
}
