
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Classe contenant la fonction main du jeu d'échecs.
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class JeuEchec {	
	
	
	/**
	 *	Attribut static permettant de compter le nombre de tours de la partie.
	 *
	 *@see Piece#deplace(int, int)
	 *@see Pion#deplacementValide(int, int)
	 *@see JeuEchec#getNombreTours()
	 *@see JeuEchec#incrNombreTours()
	 */
	private static int nombreTours = -1;
	
	/**
	 * Méthode static permettant d'incrémenter le nombre de tours dans la partie.
	 * 
	 * @see Piece#deplace(int, int)
	 */
	
	public static void incrNombreTours() {
		nombreTours++;
	}
	
	/**
	 * Méthode static pour obtenir le nombre de tours dans la partie.
	 * 
	 * @return Nombre de tours dans la partie.
	 * 
	 * @see Pion#deplacementValide(int, int)
	 */
	
	public static int getNombreTours() {
		return nombreTours;
	}
	
	/**
	 *  L'argument entré par l'utilisateur à l'éxécution.
	 *  
	 *  @see JeuEchec#getArgument()
	 */
	
	public static String[] argument = new String[1];
	
	/**
	 * Méthode static qui permet de retourner l'argument de l'utilisateur.
	 * 
	 * @return l'argument entrée par l'utilisateur.
	 * @see JeuEchec#argument
	 */
	
	public static String getArgument() {
		return argument[0];
	}
	
	/**
	 * Méthode static qui permet de vérifier les coordonnées entrées par l'utilisateur.
	 * 
	 * @param scan
	 * 				Entrée de l'utilisateur.
	 * 
	 * @param echiquier
	 * 				L'échiquier utilisé lors de la partie.
	 * 
	 * @return Un booléen qui vaut true si les coordonnées sont conformes pour l'échiquier, false sinon.
	 * 
	 * @see Echiquier
	 * @see Echiquier#caseValide(int, int)
	 * @see String#charAt(int)
	 * @see String#length()
	 * @see String#substring(int, int)
	 * @see Math#abs(int)
	 */
	
	public static boolean verifieCoordonnees(String scan, Echiquier echiquier) {
		for (int i = 0; i < scan.length(); i++) {
			if (scan.charAt(i) == ' ') {
				scan = scan.substring(0, i) + scan.substring(i + 1, scan.length());
				i--;
			}
		}
		scan = scan.toLowerCase();
		if (scan.length() == 2){
		    if(!echiquier.caseValide((int) scan.charAt(0) - 97, Math.abs((int) scan.charAt(1) - 56)))
		    	return false;
		    else return true;
		}		
		else if (scan.length() == 4){
			if (!echiquier.caseValide((int) scan.charAt(0) - 97, Math.abs((int) scan.charAt(1) - 56)) || 
			   !echiquier.caseValide((int) scan.charAt(2) - 97, Math.abs((int) scan.charAt(3) - 56)))
				return false;
		    else return true;
		}
		return false;
	}
	
	/**
	 * Méthode static qui assure la conversion de coordonnées conformes entrées par l'utilisateur.
	 * 
	 * @param scan
	 * 				Coordonnées entrées par l'utilisateur.
	 * 
	 * @return un tableau de coordonnées contenant les lignes et colonnes indiquées par l'utilisateur
	 * 
	 * @see String#length()
	 * @see String#charAt(int)
	 * @see String#substring(int, int)
	 * @see String#toLowerCase()
	 * @see Math#abs(int)
	 */
	
	public static int[] convertirCoordonnees(String scan){
		int[] coordonnees;
		for (int i = 0; i < scan.length(); i++) {
			if (scan.charAt(i) == ' ') {
				scan = scan.substring(0, i) + scan.substring(i + 1, scan.length());
				i--;
			}
		}
		scan = scan.toLowerCase();
		if (scan.length() == 2) {
			coordonnees = new int[]{(int) scan.charAt(0) - 97, Math.abs((int) scan.charAt(1) - 56)};
		}
		else {
			coordonnees = new int[]{(int) scan.charAt(0) - 97, Math.abs((int) scan.charAt(1) - 56),
					(int) scan.charAt(2) - 97, Math.abs((int) scan.charAt(3) - 56)};
		}
		return coordonnees;
	}
	
	/**
	 * Initialise une matrice de booléens des couleurs dans un échiquier. True si blanc, false sinon
	 * @return
	 * 		Cette matrice de boléens
	 * 
	 * @see Echiquier#Echiquier(boolean[][], String[][])
	 * @see JeuEchec#main(String[])
	 */
	
	public static boolean[][] initialiseCouleur() {
		boolean[][] t = new boolean[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch(i) {
				case 0 :
					t[i][j] = false;
					break;
				case 1 :
					t[i][j] = false;
					break;
				case 6 :
					t[i][j] = true;
					break;
				case 7 :
					t[i][j] = true;
					break;
				default : 
					t[i][j] = true;
					break;
					
				}
			}
		}
		return t;
	}
	
	/**
	 * Initialise une matrice de String des représentations ascii des pièces.
	 * 
	 * @return
	 * 		Une matrice de string des représentations ascii des pièces de l'échiquier.
	 */
	
	public static String[][] initialiseTypePiece() {
		String[][] t = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch(i) {
				case 0 :
					switch(j) {
					case 0 : t[i][j] = "t";
							 break;
					case 1 : t[i][j] = "c";
							 break;
					case 2 : t[i][j] = "f";
							 break;
					case 3 : t[i][j] = "d";
							 break;
					case 4 : t[i][j] = "r";
							 break;
					case 5 : t[i][j] = "f";
							 break;
					case 6 : t[i][j] = "c";
							 break;
					case 7 : t[i][j] = "t";
							 break;
					}
					break;
					
				case 1 : t[i][j]="p";
						 break;
				
				case 6 : t[i][j]="P";
						 break;
				case 7 :
					switch(j) {
					case 0 : t[i][j] = "T";
							 break;
					case 1 : t[i][j] = "C";
							 break;
					case 2 : t[i][j] = "F";
							 break;
					case 3 : t[i][j] = "D";
							 break;
					case 4 : t[i][j] = "R";
							 break;
					case 5 : t[i][j] = "F";
							 break;
					case 6 : t[i][j] = "C";
							 break;
					case 7 : t[i][j] = "T";
							 break;
					}
					break;
					default :  t[i][j]=".";
							   break;
				}
			}
		}
		return t;
	}
	
	/**
	 * Converties colonne et ligne en coordonnées d'échiquier réels.
	 * 
	 * @param colonne
	 * 			Colonne du tableau
	 * @param ligne
	 * 			Ligne du tableau
	 * @return
	 * 		Coordonnées sous formes "a1"
	 */
	
	public static String convertiePosition(int colonne, int ligne){
		char[] colonneEnLettre = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		return "" + colonneEnLettre[colonne] +Math.abs(ligne - 8);
	}
	
	/**
	 * Fonction principale du jeu d'échec.
	 * 
	 * @param args
	 * 				Doit contenir un argument "ascii" ou "unicode" qui déterminera l'affichage de l'échiquier désiré.
	 * 
	 * @throws UnsupportedEncodingException
	 * 				Gestion d'erreur en cas de mauvais affichage unicode.
	 * 
	 * @see JeuEchec#argument
	 * @see Echiquier
	 * @see Scanner
	 * @see Piece
	 * @see JeuEchec#verifieCoordonnees(String, Echiquier)
	 * @see JeuEchec#convertirCoordonnees(String)
	 * @see Scanner#nextLine()
	 * @see String#length()
	 * @see String#equals(Object)
	 * @see String#equalsIgnoreCase(String)
	 * @see Echiquier#verifieEchecEtMat(int, int, int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Echiquier#trouverRoi(boolean)
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#promotionPion()
	 * @see Echiquier#afficheAscii()
	 * @see Echiquier#afficheDeplacements(boolean[][])
	 * @see Echiquier#afficheUnicode()
	 * @see Echiquier#afficheDeplacementsUnicode(boolean[][])
	 * @see Piece#estBlanc()
	 * @see Piece#deplace(int, int)
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 */
	
    public static void main(String args[]) throws UnsupportedEncodingException {
	
		if (args == null || args.length == 0) {
    		args = new String[]{"unicode"};
    	}
		
		argument[0] = args[0];
		boolean[][] tableauCouleur = initialiseCouleur();
    	String[][] typePiece = initialiseTypePiece();
    	Echiquier echiquier = new Echiquier(tableauCouleur, typePiece); //Echiquier initial.
		Scanner scan = new Scanner(System.in);
		boolean echecEtMat = false;
		boolean echec = false; //Variable qui indique si le roi du joueur courant est en echec.
	    boolean erreur = true; //Variable boolean qui indique si il y a une erreur dans le deplacement demandé
		boolean couleurBool = false; //Variable boolean qui vaut false quand le tour est noir. True quan blanc.
	    String couleur = "Noir"; //Variable qui indique si c'est le tour du joueur noir ou celui du blanc
		int a = 0; // vaut 1 quand le joueur veut obtenir les déplacements possibles, empêchant ainsi le réaffichage immédiat de l'échiquer normal
		int compteur = 0; // Si le compteur est différent de 0, alors il n'est pas nécessaire de réafficher l'échiquier puisque que le déplacement n'est pas légal.
		while (!echecEtMat) {
			if (couleur.equals("Noir")) {couleur = "Blanc"; couleurBool = true;}
			else {couleur = "Noir"; couleurBool = false;}
			do{  //Boucle qui demande le deplacement au joueur tant que erreur = false.
				if (a == 0 && compteur == 0 && args[0].equals("ascii")) {
					echiquier.afficheAscii();
					nombreTours++;
					int temp = echiquier.promotionPion();
					if (temp == 1)
						echec = true;
				}
				else if (a == 0 && compteur == 0) {
					echiquier.afficheUnicode();
					nombreTours++;
					int temp = echiquier.promotionPion();
					if (temp == 1)
						echec=true;
				}
				if (echec) //Si le roi du joueur courant est en echec on l'indique au debut du tour.
					System.out.println("\nRoi en échec.\n");
				erreur = true;
				System.out.print("Joueur " + couleur + " ? ");
				String deplacement = scan.nextLine();
				if (!verifieCoordonnees(deplacement,echiquier)) { //SI les coordonnées sont au mauvais format.
					if (deplacement.length() != 0) {
						System.out.println("Les coordonnées ne sont pas au bon format. Ex : a6 b6");
						compteur++;
					}
					else {
						if (args[0].equals("ascii")) { // Cas où le joueur rentre une ligne vide
							echiquier.afficheAscii();
							echiquier.promotionPion();
						}
						else {
							echiquier.afficheUnicode();
							echiquier.promotionPion();
						}
					}
				}	
				else { //Cas ou les coordonnées sont au bon format.
					int[] coordonnees = convertirCoordonnees(deplacement);
					if (echiquier.examinePiece(coordonnees[0], coordonnees[1]) == null) { //Cas ou la case initiale est vide
						System.out.println("Déplacement invalide. La case " + convertiePosition(coordonnees[0], coordonnees[1]) + " est vide.");
						compteur++;
					}
					else if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).estBlanc() != couleurBool) {//Cas ou la case initiale contient une piece adverse.
						System.out.println("Vous ne pouvez pas déplacer une pièce de l'adversaire.");
						compteur++;
					}
					else if (coordonnees.length == 2) { //Cas où le joueur veut connaitre les déplacements possibles d'une piece. 
						boolean[][] deplacements = new boolean[8][8];
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).deplacementValide(j, k)) {
									deplacements[j][k] = true;
									a = 1;
								}
							}
						}
						if (args[0].equals("ascii")) {
							echiquier.afficheDeplacements(deplacements);
						}
						else {
							echiquier.afficheDeplacementsUnicode(deplacements);
						}
					}
					else if(!echec){ //Cas ou il n'y a pas d'echec et les cordonnées sont dans un format correct.
						if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).deplacementValide(coordonnees[2], coordonnees[3])) { //Cas ou le deplacement est valide.
							if(echiquier.examinePiece(coordonnees[2], coordonnees[3])!=null) {//Cas ou il y a une capture.
								if (args[0].equals("ascii")){
									System.out.println("\n"+echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii()+" a capturé "+
											echiquier.examinePiece(coordonnees[2], coordonnees[3]).representationAscii()+" en "+convertiePosition(coordonnees[2], coordonnees[3]));
								}
								else{
									System.out.println("\n"+echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationUnicode()+" a capturé "+
											echiquier.examinePiece(coordonnees[2], coordonnees[3]).representationUnicode()+" en "+convertiePosition(coordonnees[2], coordonnees[3]));
								}
							}
							if (echiquier.examinePiece(coordonnees[2], coordonnees[3]) == null) {
								if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii().equalsIgnoreCase("r")) {
									if (coordonnees[3] == coordonnees[1] && Math.abs(coordonnees[2] - coordonnees[0]) == 2) {//déplacements pour le roque
										if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).estBlanc() && coordonnees[2] == 2) {
											echiquier.examinePiece(0, coordonnees[1]).deplace(3, coordonnees[1]); //déplacements pour le roque
										}
										else if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).estBlanc() && coordonnees[2] == 6) {
											echiquier.examinePiece(7, coordonnees[1]).deplace(5, coordonnees[1]);
										}
										else if (!echiquier.examinePiece(coordonnees[0], coordonnees[1]).estBlanc() && coordonnees[2] == 2) {
											echiquier.examinePiece(0, coordonnees[1]).deplace(3, coordonnees[1]);
										}
										else {
											echiquier.examinePiece(7, coordonnees[1]).deplace(5, coordonnees[1]);
										} //fin déplacement roque
									}
								}
								if (echiquier.examinePiece(coordonnees[2], coordonnees[1]) != null) {
									if(echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationAscii().equals("p") && coordonnees[1] == 3 && 
											echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii().equals("P")) { // case de la prise en passant pion blanc
										if (getArgument() == "ascii") {
			                	    		System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii() + " a capturé " + echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationAscii() + " en passant.\n");
			                	   		}
			                	   		else {
			                	   			System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationUnicode() + " a capturé " + echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationUnicode() + " en passant.\n");
			                	   		}
										echiquier.capturePiece(coordonnees[2], coordonnees[1]);
									}
									else if (echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationAscii().equals("P") && coordonnees[1] == 4 && 
											echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii().equals("p")) { //pion noir
										if (getArgument() == "ascii") {
			                	    		System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii() + " a capturé " + echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationAscii() + " en passant.\n");
			                	   		}
			                	   		else {
			                	   			System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationUnicode() + " a capturé " + echiquier.examinePiece(coordonnees[2], coordonnees[1]).representationUnicode() + " en passant.\n");
			                	   		}
										echiquier.capturePiece(coordonnees[2], coordonnees[1]);
									}
								}
					
							}
							erreur = false;
							compteur = 0;
							a = 0;
							int[] positionRoiAdverse = echiquier.trouverRoi(!couleurBool);
							echiquier.examinePiece(coordonnees[0],coordonnees[1]).deplace(coordonnees[2], coordonnees[3]); //On fait le deplacement demandé
							if (echiquier.examinePiece(coordonnees[2], coordonnees[3]).deplacementValide(positionRoiAdverse[0], positionRoiAdverse[1])) {//Et on verifie si la piece deplacée provoque un echec
								if (echiquier.verifieEchecEtMat(positionRoiAdverse[0],positionRoiAdverse[1],coordonnees[2], coordonnees[3])) {//Cas ou elle met en echec le roi adverse
									echecEtMat = true; //Cas ou c'est un echec et mat
								}
								else {echec = true;} //Cas d'un simple echec
							}
							else { //Cas ou la piece deplacé ne met pas directement en echec le roi
								if (echiquier.verifieEchec(positionRoiAdverse[0], positionRoiAdverse[1])) {//On vérifie si son mouvement permet a un autre piece de mettre le roi adverse
									echec=true;                                                                //en échec.
								}
							}
						}
						else { //Cas ou le deplacement est non valide
							if (echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii().equalsIgnoreCase("r")) { //Cas ou c'est un roi
								Echiquier e = echiquier.copie();
					        	e.examinePiece(coordonnees[0], coordonnees[1]).deplace(coordonnees[2], coordonnees[3]);
					        	if (!e.verifieEchec(coordonnees[2], coordonnees[3])) { //Cas ou le roque est invalide car la tour ou le roi a deja bougé
					        		System.out.println("Roque non valide");
					        		e = null;
					        		compteur++;
					        	}
					        	else { //Cas ou le deplacement est invalide car le roi se met en echec
					        		System.out.println("Echec si votre Roi est déplacé en "+JeuEchec.convertiePosition(coordonnees[2],coordonnees[3])+".");
					        		compteur++;
					        	}
							}
							else { //Cas ou le déplacement est invalide et que la piece est autre qu'un roi.
								compteur++;
								System.out.println("Déplacement invalide. Entrez la case d'une pièce pour connaitre tous ses déplacements valides.");
							}
						}
					}
					else { //Cas ou il y a echec.
						if(echiquier.examinePiece(coordonnees[0],coordonnees[1]).deplacementValide(coordonnees[2], coordonnees[3])){//Cas ou le deplacement est valide
							if(echiquier.examinePiece(coordonnees[2], coordonnees[3])!=null){
								if (args[0].equals("ascii")){
									System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationAscii()+" a capturé "+
											echiquier.examinePiece(coordonnees[2], coordonnees[3]).representationAscii()+" en "+convertiePosition(coordonnees[2], coordonnees[3]));
								}
								else {
									System.out.println(echiquier.examinePiece(coordonnees[0], coordonnees[1]).representationUnicode()+" a capturé "+
											echiquier.examinePiece(coordonnees[2], coordonnees[3]).representationUnicode()+" en "+convertiePosition(coordonnees[2], coordonnees[3]));
								}
							}
							echiquier.examinePiece(coordonnees[0], coordonnees[1]).deplace(coordonnees[2], coordonnees[3]);
							compteur = 0;
							a = 0;
							echec = false;
							erreur = false;
						}
						else{
							if(echiquier.examinePiece(coordonnees[0],coordonnees[1]).representationAscii().equalsIgnoreCase("r")){
								System.out.print("Échec si votre Roi est déplacé en "+JeuEchec.convertiePosition(coordonnees[2],coordonnees[3])+".");
								compteur++;
							}

							else {
								System.out.println("Déplacement invalide. Votre Roi est toujours en échec.");
								compteur++;
							}
						}
					}
				}
		}while(erreur);
	}
		if (args[0].equals("ascii")) { //On sort de la boucle donc il y a échec et mat, la partie se termine: on affiche l'échiquier final
			echiquier.afficheAscii();
		}
		else {
			echiquier.afficheUnicode();
		}
		System.out.println("Échec et Mat. Joueur "+couleur+" a gagné.");
}
						
}