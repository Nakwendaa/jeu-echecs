/**
 * Classe de la pièce Tour.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Tour extends Piece {
	
	/**
	 *  Attribut compteur qui permet de compter le nombre de mouvements de la tour depuis le début de la partie.
	 *  
	 *  @see Tour#getCompteur()
	 *  @see Tour#incrCompteur()
	 *  @see Roi#deplacementValide(int, int)
	 *  @see Piece#deplace(int, int)
	 */
	
	private int compteur = 0;
	
	/**
	 * Constructeur de la pièce Tour qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Tour.
	 * @param ligne
	 * 				Ligne où se situe la pièce Tour.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Tour.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Tour.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Tour (boolean est_blanc, int ligne, int colonne, Echiquier echiquier){
		super(est_blanc, ligne, colonne, echiquier);
	}
	
	/**
	 *  Permet d'incrémenter le compteur de la tour.
	 *  
	 *  @see Tour#compteur
	 *  @see Piece#deplace(int, int)
	 */
	
	public void incrCompteur() {
		this.compteur++;
	}
	
	/**
	 * Permet d'obtenir le nombre de coups joués par le roi depuis le début de la partie.
	 * 
	 * @return le compteur de tour
	 * 
	 * @see Tour#compteur
	 * @see Roi#deplacementValide(int, int)
	 */
	
	public int getCompteur() {
		return this.compteur;
	}
    
	/**
	 * Méthode qui permet de savoir si la tour peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle la tour souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle la tour souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement de la tour est autorisé, false sinon.
	 * 
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#getEchiquier()
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see String#equalsIgnoreCase(String)
	 * @see Math#abs(int)
	 * @see Math#max(int, int)
	 * @see Math#min(int, int)
	 */
	
	public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
        if (!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))
            return false;
        if (this.getLigne() != nouvelle_ligne && this.getColonne() == nouvelle_colonne) { // Si c'est un déplacement vertical
        	for (int i=Math.min(this.getLigne(),nouvelle_ligne) + 1; i < Math.max(this.getLigne(), nouvelle_ligne); i++) {
        		if (this.getEchiquier().examinePiece(nouvelle_colonne, i) != null)
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
        
        if (this.getColonne() != nouvelle_colonne && this.getLigne() == nouvelle_ligne) { // Si c'est un déplacement horizontal
        	for (int i = Math.min(this.getColonne(), nouvelle_colonne) + 1; i < Math.max(this.getColonne(), nouvelle_colonne); i++) {
        		if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null)
        			return false;
        	}
        	Echiquier e = this.getEchiquier().copie();
        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	int[] positionRoi = this.getEchiquier().trouverRoi(this.estBlanc());
        	if(e.verifieEchec(positionRoi[0], positionRoi[1])){
        		e = null;
        		return false;
        	}
        	e = null;
        	return true;
        }
        return false; // Tous les autres déplacements non verticaux ou non horizontaux sont faux.
    }
    
    /**
     *  Méthode permettant l'affichage Ascii du roi.
     *  
     *  @return Retourne un string, "T" si la tour est blanche, "t" si la tour est noire.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
    
    public String representationAscii() {
        if (this.estBlanc()) return "T";
        else return "t";
    }
    

    /**
     *  Méthode permettant l'affichage Unicode de la tour.
     *  
     *  @return Retourne la représentation Unicode de la tour selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */ 
    
    public String representationUnicode() {
		if (this.estBlanc()) return "♖";
		else return "♜";
	}
    
}