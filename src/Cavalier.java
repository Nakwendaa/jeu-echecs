/**
 * Classe de la pièce Cavalier.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Cavalier extends Piece
{
	/**
	 * Constructeur de la pièce cavalier qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Cavalier.
	 * @param ligne
	 * 				Ligne où se situe la pièce Cavalier.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Cavalier.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Cavalier.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Cavalier (boolean est_blanc, int ligne, int colonne, Echiquier echiquier) {
		super(est_blanc, ligne, colonne, echiquier);
	}
	
	/**
	 * Méthode qui permet de savoir si le cavalier peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle le cavalier souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle le cavalier souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement du cavalier est autorisé, false sinon.
	 * 
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#deplacementValide(int, int)
	 * @see Math#abs(int)
	 */
    
	public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
    	if (!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))
            return false;
        if ((Math.abs(this.getColonne() - nouvelle_colonne) == 2 && Math.abs(this.getLigne() - nouvelle_ligne) == 1) ||
		   (Math.abs(this.getColonne() - nouvelle_colonne) == 1 && Math.abs(this.getLigne() - nouvelle_ligne) == 2)){ // Si c'est un deplacement en L
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
        return false;
    }
    
    /**
     *  Méthode permettant l'affichage Ascii du cavalier.
     *  
     *  @return Retourne un string, "C" si le cavalier est blanc, "c" si la cavalier est noir.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
    
    public String representationAscii() {
        if (this.estBlanc()) return "C";
        else return "c";
    }
    
    /**
     *  Méthode permettant l'affichage Unicode du cavalier.
     *  
     *  @return Retourne la représentation Unicode du cavalier selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */
    
    public String representationUnicode() {
		if (this.estBlanc()) return "♘";
		else return "♞";
	}
}