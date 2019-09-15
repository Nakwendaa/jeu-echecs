
/**
 * Classe d'une pièce d'échec.
 * 
 * @author Paul Chaffanet, Samuel Guigui
 * @version 1.0
 */

public class Piece 
{
    /**
     * True si la pièce est blanche, false sinon.
     * 
     * @see Piece#estBlanc()
     * @see Piece#estNoir()
     */
	
    private boolean est_blanc;
    
    /**
     * True si la pièce est capturée, false sinon.
     * 
     * @see Piece#estCapture()
     * @see Piece#meSuisFaitCapture()
     */
    
    private boolean est_capture;
    
    /**
     * Colonne de la pièce.
     * 
     * @see Piece#getColonne()
     */
    
    private int colonne ;
    
    /**
     * Ligne de la pièce.
     * 
     * @see Piece#getLigne()
     */
    
    private int ligne;
    
    /**
     * Echiquier auquel est rattaché la pièce.
     * 
     * @see Piece#getEchiquier()
     */
    private Echiquier echiquier;
    
    /**
     * Constructeur de la classe pièce.
     * 
     * @param est_blanc
     * 			Couleur de la pièce.
     * @param ligne
     * 			Ligne de la pièce.
     * @param colonne
     * 			Colonne de la pièce.
     * @param echiquier
     * 			Échiquier de la pièce.
     * 
     * @see Piece#est_blanc
     * @see Piece#ligne
     * @see Piece#colonne
     * @see Piece#echiquier
     */
    
    public Piece (boolean est_blanc, int ligne, int colonne, Echiquier echiquier) {
		this.est_blanc = est_blanc;
		this.est_capture = false;
		this.ligne = ligne;
		this.colonne = colonne;
		this.echiquier = echiquier;
    }
    
    /**
     * Permet de connaître la couleur de la pièce.
     * 
     * @return True si blanc, false sinon.
     * 
     * @see Piece#est_blanc
     */

    public boolean estBlanc() { return this.est_blanc; }
    
    /**
     * Permet de connaître la couleur de la pièce.
     * 
     * @return True si noir, false sinon.
     * 
     * @see Piece#est_blanc
     */
    
    public boolean estNoir() { return !this.est_blanc; }
    
    /**
     * Permet de savoir si une pièce a été capturée ou non.
     * 
     * @return True si capturé, false sinon.
     * 
     * @see Piece#est_capture
     */
    
    public boolean estCapture() { return this.est_capture; }
    
    /**
     * Permet de connaître la ligne d'une pièce.
     * 
     * @return Ligne de la pièce.
     * 
     * @see Piece#ligne
     */
    
    public int getLigne() { return this.ligne; }
    
    /**
     * Permet de connaître la colonne d'une pièce.
     * 
     * @return Colonne de la pièce.
     * 
     * @see Piece#colonne
     */
    
    public int getColonne() { return this.colonne; }
    
    /**
     * Permet de connaître l'échiquier auquel est rattachée une pièce.
     * 
     * @return Echiquier de la pièce.
     * 
     * @see Piece#echiquier
     */
    
    public Echiquier getEchiquier() { return this.echiquier; } 
    
    /**
     * Permet de changer l'état de capture d'une pièce en vraie.
     * 
     * @see Piece#est_capture
     */
    
    public void meSuisFaitCapture() { this.est_capture = true; }
    
    /**
	 * Méthode qui permet de savoir si la pièce peut légalement se déplacer sur ces nouvelles coordonnées.
	 * 
	 * @param nouvelle_colonne
	 * 				Colonne sur laquelle la pièce souhaite se déplacer.
	 * 
	 * @param nouvelle_ligne
	 * 				Ligne sur laquelle la pièce souhaite se déplacer.
	 * 
	 * @return Un booléen qui vaut true si le déplacement de la pièce est autorisé, false sinon.
	 * 
	 * @see Piece#est_capture
	 * @see Piece#echiquier
	 * @see Piece#estBlanc()
	 * @see Echiquier#caseValide(int, int)
	 * @see Echiquier#examinePiece(int, int)
	 * @see Roi#deplacementValide(int, int)
	 * @see Dame#deplacementValide(int, int)
	 * @see Fou#deplacementValide(int, int)
	 * @see Cavalier#deplacementValide(int, int)
	 * @see Tour#deplacementValide(int, int)
	 * @see Pion#deplacementValide(int, int)
	 */
    
    public boolean deplacementValide(int nouvelle_colonne, int nouvelle_ligne) {
    	if (this.est_capture)
    		return false;
        if (!this.echiquier.caseValide(nouvelle_colonne, nouvelle_ligne))
        	return false;
        if (this.echiquier.examinePiece(nouvelle_colonne, nouvelle_ligne) != null) {
        	if (this.echiquier.examinePiece(nouvelle_colonne, nouvelle_ligne).estBlanc() == this.est_blanc)
        		return false;
        }
        return true;
    }

    /**
     * Méthode qui permet de déplacer une pièce sur l'échiquier
     * 
     * @param nouvelle_colonne
     * 			Colonne sur laquelle la pièce souhaite se déplacer.
     * 
     * @param nouvelle_ligne
     * 			Ligne sur laquelle la pièce souhaite se déplacer.
     * 
     * @see JeuEchec#getArgument()
     * @see JeuEchec#incrNombreTours()
     * @see Piece#echiquier
     * @see Piece#getLigne()
     * @see Piece#getColonne()
     * @see Piece#representationAscii()
     * @see Piece#representationUnicode()
     * @see Echiquier#examinePiece(int, int)
     * @see Echiquier#prendsPiece(int, int)
     * @see Echiquier#capturePiece(int, int)
     * @see Echiquier#posePiece(Piece)
     * @see Roi#incrCompteur()
     * @see Tour#incrCompteur()
     */
    
    public void deplace (int nouvelle_colonne, int nouvelle_ligne) {
    	if (this.echiquier.examinePiece(nouvelle_colonne, nouvelle_ligne) != null) {
    		this.echiquier.capturePiece(nouvelle_colonne, nouvelle_ligne);
    	}
		Piece p = this.echiquier.prendsPiece(this.getColonne(), this.getLigne());
		p.colonne = nouvelle_colonne;
		p.ligne = nouvelle_ligne;
		this.echiquier.posePiece(p);
		if (p instanceof Roi) {
			((Roi)p).incrCompteur();
		}
		else if (p instanceof Tour) {
			((Tour)p).incrCompteur();
		}
	}

    /**
     * Méthode qui permet d'obtenir l'état d'une pièce sous forme de chaîne de caractères.
     * 
     * @return Un String représentant l'état de la pièce.
     * 
     * @see Piece#colonne
     * @see Piece#ligne
     * @see Piece#est_blanc
     * @see Piece#est_capture
     */
    
    public String toString() {
		String toString = "" + this.est_blanc + " " + this.est_capture + " " + this.colonne + " " + this.ligne;
		return toString;
    }

    /**
     *  Méthode permettant l'affichage Ascii de la pièce.
     *  
     *  @return Retourne un string, "B" si la pièce est blanche, "N" si la pièce est noire.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationAscii()
     *  @see Roi#representationAscii()
     *  @see Dame#representationAscii()
     *  @see Fou#representationAscii()
     *  @see Cavalier#representationAscii()
     *  @see Tour#representationAscii()
     *  @see Pion#representationAscii()
     */
    
    public String representationAscii() {
		if (this.est_blanc) return "B";
		else return "N";
    }
    
    /**
     *  Méthode permettant l'affichage Unicode d'une pièce, qui sera redéfinie dans chaque sous-classe de la pièce.
     *  
     *  @return Retourne un String qui la représentation Unicode d'une pièce selon sa couleur.
     *  
     *  @see Piece#estBlanc()
     *  @see Piece#representationUnicode()
     *  @see Roi#representationUnicode()
     *  @see Dame#representationUnicode()
     *  @see Fou#representationUnicode()
     *  @see Cavalier#representationUnicode()
     *  @see Tour#representationUnicode()
     *  @see Pion#representationUnicode()
     */ 
    
    public String representationUnicode() {
		if (this.estBlanc()) return "B";
		else return "N";
	}
    
    
}
