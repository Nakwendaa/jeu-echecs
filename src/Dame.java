
/**
 * Classe de la pièce Dame.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Dame extends Piece
{
	
	/**
	 * Constructeur de la pièce Dame qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Dame.
	 * @param ligne
	 * 				Ligne où se situe la pièce Dame.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Dame.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Dame.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Dame (boolean est_blanc, int ligne, int colonne, Echiquier echiquier) {
		super(est_blanc, ligne, colonne, echiquier);
	}
	
	/**
	 * Méthode qui permet de savoir si la dame peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle la dame souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle la dame souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement de la dame est autorisé, false sinon.
	 * 
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#getEchiquier()
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Math#abs(int)
	 * @see Math#max(int, int)
	 * @see Math#min(int, int)
	 * @see String#equalsIgnoreCase(String)
	 */

	public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
        if(!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))  // On vérifie si les conditions primaires du déplacement sont respectées
            return false;												// en faisant appel a la methode de la super class.
        if (this.getLigne() != nouvelle_ligne && this.getColonne() == nouvelle_colonne) { //Cas ou c'est un déplacement verticale.
        	for (int i = Math.min(this.getLigne(), nouvelle_ligne) + 1; i < Math.max(this.getLigne(), nouvelle_ligne); i++) {
        		if (this.getEchiquier().examinePiece(nouvelle_colonne, i) != null) // On vérifie qu'aucune pièce est sur le chemin.
        			return false;
        	}
        	// On verifie si le déplacement de la pièce ne provoque pas l'échec de son roi.
        	// Pour cela on créé une copie de l'échiquier sur laquelle on fait le déplacement puis on regarde si le roi est en échec.
        	Echiquier e = this.getEchiquier().copie();
        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
        	if (e.verifieEchec(positionRoi[0], positionRoi[1])) {
        		e = null;
        		return false; // Si le roi est en échec ce n'est pas un déplacement autorisé car il met en échec le roi du joueur courant
        	}
        	e = null;
        	return true; // Sinon le déplacement est valide.
        }
        if (this.getColonne() != nouvelle_colonne && this.getLigne() == nouvelle_ligne) { // Cas d'un déplacement horizontal
        	for (int i = Math.min(this.getColonne(), nouvelle_colonne) + 1; i < Math.max(this.getColonne(), nouvelle_colonne); i++){
        		if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null)
        			return false;
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
        if(Math.abs(this.getLigne() - nouvelle_ligne) == Math.abs(this.getColonne() - nouvelle_colonne)) { // Cas d'un déplacement en diagonale
        	if (this.getLigne() > nouvelle_ligne && this.getColonne() > nouvelle_colonne) { //Diagonale 1
        		int i = nouvelle_ligne + 1;
        		for (int j = nouvelle_colonne + 1; j < this.getColonne(); j++) {
	   			    if (this.getEchiquier().examinePiece(j, i) != null)
	   			    	return false;
	   			    i++;
	   			}
        		Echiquier e = this.getEchiquier().copie();
            	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne,nouvelle_ligne);
            	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
            	if (e.verifieEchec(positionRoi[0],positionRoi[1])) {
            		e = null;
            		return false;
            	}
            	e = null;
            	return true;
            }
	      
        	else if (this.getLigne() < nouvelle_ligne && this.getColonne() < nouvelle_colonne) { // Diagonale 2
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
        	else if (this.getLigne() > nouvelle_ligne && this.getColonne() < nouvelle_colonne) { // Diagonale 3
        		int i = nouvelle_ligne + 1;
        		for (int j=nouvelle_colonne - 1; j > this.getColonne(); j--) {
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
            	if (e.verifieEchec(positionRoi[0],positionRoi[1])) {
            		e = null;
            		return false;
            	}
            	e = null;
            	return true;
	       	}
        }
        return false;  // Si aucun des cas au dessus n'est vrai alors le déplacement est invalide.
    }
    
    /**
     *  Méthode permettant l'affichage Ascii de la dame.
     *  
     *  @return Retourne un string, "D" si la dame est blanche, "d" si la dame est noire.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
    
    public String representationAscii() {
        if (this.estBlanc()) return "D";
        else return "d";
    }
    
    /**
     *  Méthode permettant l'affichage Unicode de la dame.
     *  
     *  @return Retourne la représentation Unicode de la dame selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */
    
    public String representationUnicode() {
		if (this.estBlanc()) return "♕";
		else return "♛";
	}
}
