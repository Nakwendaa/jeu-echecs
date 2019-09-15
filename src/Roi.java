
/**
 * Classe de la pièce Roi.
 * 
 * @see Piece
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Roi extends Piece {
	
	/**
	 *  Attribut compteur qui permet de compter le nombre de mouvements du roi depuis le début de la partie.
	 *  
	 *  @see Roi#getCompteur()
	 *  @see Roi#incrCompteur()
	 *  @see Roi#deplacementValide(int, int)
	 *  @see Piece#deplace(int, int)
	 */
	
	private int compteur = 0;
	
	/**
	 * Constructeur de la pièce Roi qui hérite du constructeur de la classe Piece.
	 * 
	 * @param est_blanc
	 *				Couleur de la pièce Roi.
	 * @param ligne
	 * 				Ligne où se situe la pièce Roi.
	 * 
	 * @param colonne
	 * 				Colonne où se situe la pièce Roi.
	 * 
	 * @param echiquier
	 * 				Échiquier auquel appartient la pièce Roi.
	 * 
	 * @see Piece#Piece(boolean, int, int, Echiquier)
	 */
	
	public Roi (boolean est_blanc, int ligne, int colonne, Echiquier echiquier){
		super(est_blanc, ligne, colonne, echiquier);
	}
	
	/**
	 *  Permet d'incrémenter le compteur du roi.
	 *  
	 *  @see Roi#compteur
	 *  @see Piece#deplace(int, int)
	 */
	
	public void incrCompteur() {
		this.compteur++;
	}
	
	/**
	 * Permet d'obtenir le nombre de coups joués par le roi depuis le début de la partie.
	 * 
	 * @return le compteur de roi
	 * 
	 * @see Roi#compteur
	 * @see Roi#deplacementValide(int, int)
	 */
	
	public int getCompteur() {
		return this.compteur;
	}
    
	/**
	 * Méthode qui permet de savoir si le roi peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle le roi souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle le roi souhaite se déplacer.
	 * 
	 * @return un booléen qui vaut true si le déplacement du roi est autorisé, false sinon.
	 * 
	 * @see Piece#getColonne()
	 * @see Piece#getLigne()
	 * @see Piece#getEchiquier()
	 * @see Piece#estBlanc()
	 * @see Piece#estNoir()
	 * @see Piece#deplace(int, int)
	 * @see Piece#deplacementValide(int, int)
	 * @see Piece#representationAscii()
	 * @see Piece#representationUnicode()
	 * @see Roi#getCompteur()
	 * @see Tour#getCompteur()
	 * @see Echiquier#examinePiece(int, int)
	 * @see Echiquier#getEchiquier()
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Echiquier#capturePiece(int, int)
	 * @see Echiquier#verifieEchec(int, int, int, int)
	 * @see Math#abs(int)
	 * @see Math#max(int, int)
	 * @see String#equalsIgnoreCase(String)
	 */
	
	public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
        if(!super.deplacementValide(nouvelle_colonne, nouvelle_ligne))
            return false;
        if (Math.abs(this.getLigne() - nouvelle_ligne) == 1 && this.getColonne() == nouvelle_colonne) { // Si c'est un déplacement vertical.
        	Echiquier e = this.getEchiquier().copie();
        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	if (e.verifieEchec(nouvelle_colonne, nouvelle_ligne)) {
        		e = null;
        		return false;
        	}
        	e = null;
        	return true;
        }
        if (Math.abs(this.getColonne() - nouvelle_colonne) == 1 && this.getLigne() == nouvelle_ligne) { // Si c'est un déplacement horizontal.
        	Echiquier e = this.getEchiquier().copie();
        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	if (e.verifieEchec(nouvelle_colonne,nouvelle_ligne)) {
        		e = null;
        		return false;
        	}
        	e = null;
        	return true;
        	
        }
        if (Math.abs(this.getLigne() - nouvelle_ligne) == 1 && Math.abs(this.getColonne() - nouvelle_colonne) == 1) { // Si c'est un déplacement en diagonale.
        	Echiquier e = this.getEchiquier().copie();
        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	if (e.verifieEchec(nouvelle_colonne, nouvelle_ligne)) {
        		e = null;
        		return false;
        	}
        	e = null;
        	return true;
        }
        if (((this.getColonne() == 4 && this.estBlanc() && this.getLigne() == 7 && (nouvelle_colonne == 2 || nouvelle_colonne == 6) && nouvelle_ligne == 7) || 
        	 (this.getColonne() == 4 && !this.estBlanc() && this.getLigne() == 0 && (nouvelle_colonne == 2 || nouvelle_colonne == 6) && nouvelle_ligne == 0)) && 
        	 this.getCompteur() == 0) {
        	if (this.estBlanc() && nouvelle_colonne == 2) { // Début du codage du roque avec les 3 conditions suivantes: Le Roi et la Tour ne doivent pas avoir déjà bougé
        		if(this.getEchiquier().examinePiece(0, this.getLigne()) != null) {                              //     Aucune des 3 cases traversées par le roi doivent être en échec  
        			if (this.getEchiquier().examinePiece(0, this.getLigne()) instanceof Tour) {                 // Aucune pièce entre la tour et le roi.
        				if (((Tour) this.getEchiquier().examinePiece(0, this.getLigne())).getCompteur() == 0) {
        	        			for (int i = 1; i < 4; i ++) {
        	        				if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null) {
        	        					return false;
        	        				}
        	        			}
        	        			if (this.getEchiquier().examinePiece(0, nouvelle_ligne) != null) {
        	        				Echiquier e = this.getEchiquier().copie();
        	        	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	        	        	if (e.verifieEchec(nouvelle_colonne, nouvelle_ligne)) {
        	        	        		e = null;
        	        	        		return false;
        	        	        	}
        	        	        	else{
        	        	        		e = null;
        	        	        		Echiquier e1 = this.getEchiquier().copie();
            	        	        	e1.examinePiece(this.getColonne(), this.getLigne()).deplace(Math.max(nouvelle_colonne, this.getColonne()) - 1, nouvelle_ligne);
            	        	        	if (e1.verifieEchec(Math.max(nouvelle_colonne, this.getColonne()) - 1,nouvelle_ligne)) {
            	        	        		e1 = null;
            	        	        		return false;
            	        	        	}
            	            			return true;
        	        	        	}
        	        			}
        	        			else return false;
        	        		}
        	        		else return false;
        				}
        				else return false;
        			}
        			else return false;
  //      		}
   //     		else return false;
        	} // Fin du if this.estBlanc() && nouvelle_colonne == 2
        	else if (this.estBlanc() && nouvelle_colonne == 6) {
        		if (this.getEchiquier().examinePiece(7, this.getLigne()) != null) {
        			if (this.getEchiquier().examinePiece(7, this.getLigne()) instanceof Tour) {
        				if (((Tour) this.getEchiquier().examinePiece(7, this.getLigne())).getCompteur() == 0) {
        	        			for (int i = 5; i < 7; i ++) {
        	        				if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null) {
        	        					return false;
        	        				}
        	        			}
        	        			if (this.getEchiquier().examinePiece(7, nouvelle_ligne) != null) {
        	        				Echiquier e = this.getEchiquier().copie();
        	        	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	        	        	if (e.verifieEchec(nouvelle_colonne ,nouvelle_ligne)) {
        	        	        		e = null;
        	        	        		return false;
        	        	        	}
        	        	        	else{
        	        	        		e = null;
        	        	        		Echiquier e1 = this.getEchiquier().copie();
            	        	        	e1.examinePiece(this.getColonne(), this.getLigne()).deplace(Math.max(nouvelle_colonne, this.getColonne()) - 1, nouvelle_ligne);
            	        	        	if (e1.verifieEchec(Math.max(nouvelle_colonne, this.getColonne()) - 1, nouvelle_ligne)) {
            	        	        		e1 = null;
            	        	        		return false;
            	        	        	}
            	            			return true;
        	        	        	}
        	        			}
        	        			else return false;
        				}
        				else return false;
        			}
        			else return false;
        		}
        		else return false;
        	} // Fin du else if this.estBlanc() && nouvelle_colonne == 6
        	else if (!this.estBlanc() && nouvelle_colonne == 2) {
          		if(this.getEchiquier().examinePiece(0, this.getLigne()) != null) {
        			if (this.getEchiquier().examinePiece(0, this.getLigne()) instanceof Tour) {
        				if (((Tour) this.getEchiquier().examinePiece(0, this.getLigne())).getCompteur() == 0) {
        	        			for (int i = 1; i < 4; i ++) {
        	        				if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null) {
        	        					return false;
        	        				}
        	        			}
        	        			if (this.getEchiquier().examinePiece(0, nouvelle_ligne) != null) {
        	        				Echiquier e = this.getEchiquier().copie();
        	        	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne, nouvelle_ligne);
        	        	        	if (e.verifieEchec(nouvelle_colonne, nouvelle_ligne)) {
        	        	        		e = null;
        	        	        		return false;
        	        	        	}
        	        	        	else{
        	        	        		e = null;
        	        	        		Echiquier e1 = this.getEchiquier().copie();
            	        	        	e1.examinePiece(this.getColonne(), this.getLigne()).deplace(Math.max(nouvelle_colonne, this.getColonne()) - 1, nouvelle_ligne);
            	        	        	if (e1.verifieEchec(Math.max(nouvelle_colonne,this.getColonne()) -1 , nouvelle_ligne)) {
            	        	        		e1 = null;
            	        	        		return false;
            	        	        	}
            	            			return true;
        	        	        	}
        	        			}
        	        			else return false;
        	        	}
        				else return false;
        			}
        			else return false;
        		}
          		else return false;
          	} // Fin du else if !this.estBlanc() && nouvelle_colonne == 2
        	else if (!this.estBlanc() && nouvelle_colonne == 6) {
        		if(this.getEchiquier().examinePiece(7, this.getLigne()) != null) {
        			if (this.getEchiquier().examinePiece(7, this.getLigne()) instanceof Tour) {
        				if (((Tour) this.getEchiquier().examinePiece(7, this.getLigne())).getCompteur() == 0) {
        	        			for (int i = 5; i < 7; i ++) {
        	        				if (this.getEchiquier().examinePiece(i, nouvelle_ligne) != null) {
        	        					return false;
        	        				}
        	        			}
        	        			if (this.getEchiquier().examinePiece(7, nouvelle_ligne) != null) {
        	        				if (this.getEchiquier().examinePiece(0, nouvelle_ligne) != null) {
        	        					Echiquier e = this.getEchiquier().copie();
            	        	        	e.examinePiece(this.getColonne(), this.getLigne()).deplace(nouvelle_colonne,nouvelle_ligne);
            	        	        	if(e.verifieEchec(nouvelle_colonne,nouvelle_ligne)){
            	        	        		e = null;
            	        	        		return false;
            	        	        	}
            	        	        	else{
            	        	        		e = null;
            	        	        		Echiquier e1 = this.getEchiquier().copie();
                	        	        	e1.examinePiece(this.getColonne(), this.getLigne()).deplace(Math.max(nouvelle_colonne,this.getColonne())-1,nouvelle_ligne);
                	        	        	if(e1.verifieEchec(Math.max(nouvelle_colonne,this.getColonne())-1,nouvelle_ligne)){
                	        	        		e1 = null;
                	        	        		return false;
                	        	        	}
                	            			return true;
            	        	        	}
            	        			}
            	        			else return false;
        	        			}
        	        			else return false;
        	        		}
        				else return false;
        			}
        			else return false;
        		}
        		else return false;
        	} // Fin du else if
        	else return false;
        }
        else return false;
    }
    
    /**
     *  Méthode permettant l'affichage Ascii du roi.
     *  
     *  @return Retourne un string, "R" si le roi est blanc, "r" si le roi est noir.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     */
    
    public String representationAscii() {
        if (this.estBlanc() == true) return "R";
        else return "r";
    }
    
    /**
     *  Méthode permettant l'affichage Unicode du roi.
     *  
     *  @return Retourne la représentation Unicode du roi selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     */ 
    
    public String representationUnicode() {
		if (this.estBlanc()) return "♔";
		else return "♚";	
	}
    
    
}