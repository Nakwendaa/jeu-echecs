
/**
 * Classe de la pièce Fou.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Fou extends Piece {
	
	/**
	 * Constructeur de la pièce Fou qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Fou.
	 * @param ligne
	 * 				Ligne où se situe la pièce Fou.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Fou.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Fou.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Fou (boolean est_blanc, int ligne, int colonne, Echiquier echiquier) {
		super(est_blanc, ligne, colonne, echiquier);
	}
	

	/**
	 * Méthode qui permet de savoir si le fou peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle le fou souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle le fou souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement du fou est autorisé, false sinon.
	 * 
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#getEchiquier()
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Math#abs(int)
	 * @see String#equalsIgnoreCase(String)
	 */
    
	public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne)
    {
    	if (!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))
            return false;  
    	if (Math.abs(this.getLigne() - nouvelle_ligne) == Math.abs(this.getColonne() - nouvelle_colonne)) { // Si c'est un déplacement en diagonale.
        	if (this.getLigne() > nouvelle_ligne && this.getColonne() > nouvelle_colonne) { // Diagonale 1
        		int i = nouvelle_ligne + 1;
        		for (int j=nouvelle_colonne + 1; j < this.getColonne(); j++) {
	   			    if (this.getEchiquier().examinePiece(j, i) != null)
	   			    	return false;
	   			    i++;
	   			}
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
        	else if(this.getLigne() < nouvelle_ligne && this.getColonne() < nouvelle_colonne) { //Diagonale 2
        		int i = nouvelle_ligne - 1;
        		for (int j = nouvelle_colonne - 1; j > this.getColonne(); j--){
	   			    if (this.getEchiquier().examinePiece(j, i) != null)
	   			    	return false;
	   			    i--;
	   			}
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
        	else if (this.getLigne() > nouvelle_ligne && this.getColonne() < nouvelle_colonne) { //Diagonale 3
        		int i = nouvelle_ligne + 1;
        		for (int j = nouvelle_colonne - 1; j > this.getColonne(); j--) {
	   			    if (this.getEchiquier().examinePiece(j, i) != null)
	   			    	return false;
	   			    i++;
	   			}
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
        	else { //Diagonale 4
        		int i = nouvelle_ligne - 1;
        		for (int j = nouvelle_colonne + 1; j < this.getColonne(); j++) {
	   			    if (this.getEchiquier().examinePiece(j, i) != null)
	   			    	return false;
	   			    i--;
	   			}
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
        return false;
    }
    
    /**
     *  Méthode permettant l'affichage Ascii du fou.
     *  
     *  @return Retourne un string, "F" si le fou est blanc, "f" si le fou est noir.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
    
    public String representationAscii() {
        if (this.estBlanc()) return "F";
        else return "f";
    }
    
    /**
     *  Méthode permettant l'affichage Unicode du fou.
     *  
     *  @return Retourne la représentation Unicode du fou selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */
    
    public String representationUnicode() {
		if (this.estBlanc()) return "♗";
		else return "♝";
		
	}
}