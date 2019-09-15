
/**
 * Classe de la pièce Pion.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Pion extends Piece {

	/**
	 *  Attribut compteur qui va permettre de gérer le cas où un pion ne peut pas être capturé par la prise "en passant".
	 *  En effet, si un pion après avoir avancé de deux cases n'est pas capturé dans le tour d'après "en passant", 
	 *  alors la prise "en passant" ne peut s'effectuer.
	 *  
	 *  @see Pion#deplacementValide(int, int)
	 */
	
	private int nombreDeTours = 0;
	
	/**
	 * Constructeur de la pièce Pion qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Pion.
	 * @param ligne
	 * 				Ligne où se situe la pièce Pion.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Pion.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Pion.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Pion (boolean est_blanc, int ligne, int colonne, Echiquier echiquier) {
		super(est_blanc, ligne, colonne, echiquier);
	}
    
	/**
	 * Méthode qui permet de savoir si le pion peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle le pion souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle le pion souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement du pion est autorisé, false sinon.
	 * 
	 * @see JeuEchec#getArgument()
	 * @see JeuEchec#getNombreTours()
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#getEchiquier()
	 * @see Piece#estBlanc()
	 * @see Piece#estNoir()
	 * @see Piece#getEchiquier()
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 * @see Piece#representationUnicode()
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#capturePiece(int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Math#abs(int)
	 * @see Math#max(int, int)
	 * @see String#equalsIgnoreCase(String)
	 */
	
    public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
    	if (!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))
            return false;
        if (this.getLigne() > nouvelle_ligne && !this.estBlanc()) // Déplacements arrières interdits
        	return false;
        if (this.getLigne() < nouvelle_ligne && this.estBlanc())
        	return false;
        if (Math.abs(this.getLigne() - nouvelle_ligne) == 1 && this.getColonne() == nouvelle_colonne) { // On peut avancer d'une case sur la colonne
        	if (this.getEchiquier().examinePiece(nouvelle_colonne, nouvelle_ligne) != null) // si il y a une piece, on retourne false
	        		return false;
        	else {
        		Echiquier e = this.getEchiquier().copie();
            	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne,nouvelle_ligne);
            	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
            	if (e.verifieEchec(positionRoi[0],positionRoi[1])) {
            		e = null;
            		return false;
            	}
            	e = null;
            	return true; //sinon on avance
        	}
        }
        else if (Math.abs(this.getLigne() - nouvelle_ligne) == 2 && this.getColonne() == nouvelle_colonne) { // On avance de deux cases
        	if (this.getEchiquier().examinePiece(nouvelle_colonne, nouvelle_ligne) != null ||
        	   this.getEchiquier().examinePiece(nouvelle_colonne, Math.max(nouvelle_ligne, this.getLigne()) - 1) != null) // si pas de pieces devant sinon false
	        		return false;
        	if (this.getLigne() == 6 && this.estBlanc()) { // si blanc
        		Echiquier e = this.getEchiquier().copie();
	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
	        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
	        	if (e.verifieEchec(positionRoi[0], positionRoi[1])) {
	        		e = null;
	        		return false;
	        	}
	        	e = null;
	        	this.nombreDeTours = JeuEchec.getNombreTours(); //nombreDeTours affecté pour s'assurer que la prise en passant n'est possible qu'au tour d'après
	        	return true;
        	}
        	if (this.getLigne() == 1 && !this.estBlanc()) {	 //si noir
        		Echiquier e = this.getEchiquier().copie();
	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
	        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
	        	if (e.verifieEchec(positionRoi[0], positionRoi[1])) {
	        		e = null;
	        		return false;
	        	}
	        	e = null;
	        	this.nombreDeTours = JeuEchec.getNombreTours(); //nombreDeTours affecté pour s'assurer que la prise en passant n'est possible qu'au tour d'après
	        	return true;
        	}
        	return false;
        }
        else if (Math.abs(this.getLigne() - nouvelle_ligne) == 1 && Math.abs(this.getColonne() - nouvelle_colonne) == 1) { //déplacement diagonale de 1
        	if (this.getEchiquier().examinePiece(nouvelle_colonne, nouvelle_ligne) == null && !(this.getLigne() ==  3 && this.estBlanc() || this.getLigne() == 4 && this.estNoir())) {
        		return false; // si pas de piece, false (sauf ligne 3 et ligne 4 selon la couleur)
        	}
        	else if (this.getLigne() ==  3 && this.estBlanc() || this.getLigne() == 4 && this.estNoir()) { // Début du codage de la prise "en passant"
    			if (this.getEchiquier().examinePiece(nouvelle_colonne, nouvelle_ligne) == null && this.getEchiquier().examinePiece(nouvelle_colonne, this.getLigne()) != null)	{ //si la case d'arrivée est vide, et qu'il y a une piece meme ligne, et colonne d'arrivé
    				if (this.getEchiquier().examinePiece(nouvelle_colonne, this.getLigne()).representationAscii().equals("p") && this.getLigne() == 3 && this.estBlanc()) { //que cette piece soit un pion noir et que mon pion soit blanc
    					if (((Pion)this.getEchiquier().examinePiece(nouvelle_colonne, this.getLigne())).nombreDeTours == JeuEchec.getNombreTours() - 1) { //on vérifie que la prise en passant s'effectue au tour d'après (sinon, la prise en passant ne sera plus possible
    						Echiquier e = this.getEchiquier().copie();
    						e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
    			        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
    			        	if (e.verifieEchec(positionRoi[0], positionRoi[1])) {
    			        		e = null;
    			        		return false;
    			        	}
    			        	e = null;
            				return true;
    					}
    					else return false;
        			}
        			else if (this.getEchiquier().examinePiece(nouvelle_colonne, this.getLigne()).representationAscii().equals("P") && this.getLigne() == 4 && this.estNoir()) { //si le pion est noir pour la prise en passant
        				if (((Pion)this.getEchiquier().examinePiece(nouvelle_colonne, this.getLigne())).nombreDeTours == JeuEchec.getNombreTours() - 1) {
    						Echiquier e = this.getEchiquier().copie();
    						e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
    			        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
    			        	if (e.verifieEchec(positionRoi[0],positionRoi[1])) {
    			        		e = null;
    			        		return false;
    			        	}
    			        	e = null;
            				return true;
    					}
    					else return false;
    				}
    				else return false;
    			}
    			else return false;
        	}
        	else { //s'il y a une piece en diagonale
        		Echiquier e = this.getEchiquier().copie();
	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
	        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
	        	if (e.verifieEchec(positionRoi[0], positionRoi[1])) {
	        		e = null;
	        		return false;
	        	}
	        	e = null;
        		return true;
        	}
        }
        else return false;
    }
	
	/**
     *  Méthode permettant l'affichage Ascii du pion.
     *  
     *  @return Retourne un string, "P" si le pion est blanc, "p" si le pion est noir.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
	
	public String representationAscii() {
	    if (this.estBlanc()) return "P";
	    else return "p";
	} 
	    
	/**
     *  Méthode permettant l'affichage Unicode du pion.
     *  
     *  @return Retourne la représentation Unicode du pion selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */ 
	 
	public String representationUnicode() {
		if (this.estBlanc()) return "♙";
		else return "♟";
	}
}