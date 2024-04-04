

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Othello extends JPanel implements MouseListener {
	
	private static JPanel panel;
    private static int taille = 8;
    private static int[][] plateau = new int[taille][taille];
    private static int tour = 1;
    private static int noir = 0;
    private static int blanc = 0;
    private static int vide = 0;
    private static int coupPossible = 0;
    private static int aucunCoupPossible = 0;
    private static int txtX = 10;
    private static int tailleCase = 60;
    private static int txtY = taille*tailleCase + 15;
    private static boolean egalité = false;
    boolean jeuTermine = false;
    
    //constructeur et parametrage de l'affichage
    public Othello() {

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // cette partie traite les cases 
                for (int i = 0; i < taille; i++)
                    for (int j = 0; j < taille; j++) {
                        g.setColor(new Color(0, 144, 0));
                        // fillRect s'occupe de colorier les cases 
                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.setColor(Color.WHITE);
                        // drawRect s'occupe de colorier les inter-Cases
                        g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    }
                // cette partie traite les pions 
                int taillePion = tailleCase - 10;
                for (int i = 0; i < plateau.length; i++) {
                    for (int j = 0; j < plateau[i].length; j++) {
                        if (plateau[i][j] == 1) {
							g.setColor(Color.BLACK);
							// fillOval s'occupe de colorier les pions 
                            g.fillOval((tailleCase - taillePion) / 2 + i * tailleCase,
                                    (tailleCase - taillePion) / 2 + j * tailleCase, tailleCase - 10, taillePion);
                        } else if (plateau[i][j] == 2) {
                            g.setColor(Color.WHITE);
                            g.fillOval((tailleCase - taillePion) / 2 + i * tailleCase,
                                    (tailleCase - taillePion) / 2 + j * tailleCase, tailleCase - 10, taillePion);
                        } else if (plateau[i][j] == -1) {
                            g.setColor(new Color(204, 0, 0));
                            g.fillOval(tailleCase / 3 + i * tailleCase, tailleCase / 3 + j * tailleCase, tailleCase / 3,
                                    tailleCase / 3);
                        }
                    }
                }
                // cette partie traite le texte qui gère le score
           	    g.setColor(Color.BLACK);
                int txtSize = ((int) (12.5 * (taille / 8.0))) + 4; // taille des lettres
        	    g.setFont(new Font ("Comic sans Ms", Font.PLAIN, txtSize)); //type de police
        	    if(vide == 0 || egalité){ // si la partie est fini
        		    if(noir > blanc) g.drawString("victoire des noir     noir = " + noir + "  blanc = " + blanc, txtX, txtY);
        		    else if(blanc > noir) g.drawString("victoire des blanc     noir = " + noir + "  blanc = " + blanc, txtX, txtY);
        		    else g.drawString("egalité     noir = " + noir + "  blanc = " + blanc, txtX, txtY);
                } else { // la partie n'est pas fini, on annonce donc le tour à chaque fois 
              	    if(tour == 1) g.drawString("tour des noir     noir = " + noir + "  blanc = " + blanc, txtX, txtY);
              	    else g.drawString("tour des blanc     noir = " + noir + "  blanc = " + blanc, txtX, txtY);
                }
            }
            
        };

        panel.setPreferredSize(new Dimension(taille*tailleCase, taille*tailleCase+20)); // taille de la fenetre
        panel.addMouseListener(this); // controle la souris pour permettre à l'utilisateur de cliquer 
        JFrame frame = new JFrame();
        frame.setTitle("Othello"); // titre de la fenetre
        frame.add(panel);
        frame.pack();// permet de controler la taille de la fenetre
        frame.setLocationRelativeTo(null); // centralise la fenetre et la mets au milieu
        frame.setVisible(true); 

    }
    // initialise le plateau 
	public static void main(String[] args) {
		// positionnement des 4 premiers pions (2 noirs et 2 blancs)
		plateau[taille / 2 - 1][taille / 2] = 1;
		plateau[taille / 2 - 1][taille / 2 - 1] = 2;
		plateau[taille / 2][taille / 2 - 1] = 1;
		plateau[taille / 2][taille / 2] = 2;
		casePossible();
		comptePion();
		new Othello();
	}
	
    /*
     * ce qui ce passe quand on clique sur le panel
     * sachant que le panel est un mouseListener lui même
     */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (jeuTermine) { // Empêche de jouer si le jeu est terminé
			return;
		}

		int x, y;
		int i = 0;
		int j = 0;
		x = e.getX(); // suit les ordonnés
		y = e.getY(); // suit les abcisses
		i = x / tailleCase;
		j = y / tailleCase;
		if (valideCoup(i, j)) {
			plateau[i][j] = tour;
			actualisePlateau(i, j);
			tour = 3 - tour;
			casePossible();
			panel.repaint();

			if (jeuTerminé()) { // Vérifie si le jeu est terminé après chaque coup
				jeuTermine = true;
			}
		}
	}
	
	// Méthode pour vérifier si la partie est terminée
	public boolean jeuTerminé() {
		// Le jeu est terminé si aucun coup n'est possible pour les deux joueurs ou si
		// le plateau est complètement rempli
		return (noir + blanc == taille * taille) || (egalité);
	}
	
	/**
     * après que les case pouvant être jouée aient été marqué par -1 quand 
     * on clique sur le plateau on verifie si la case est marqué par -1 si oui 
     * on joue dessus sinon le coup est invalide et on ne fait rien
     */ 
	boolean valideCoup(int i,int j){
		if(i < taille && j < taille){
			if(plateau[i][j] == -1) return true;
		}
		return false;
	}
	
	/**
     * cette fonction mets des -1 sur le plateau en fct des coup futur pouvant être joué
     * ensuite les case avc -1 sont celles qui auront des point rouge.
     * Elle utilise coupAutoriser(i,j) sur chaque case ayant un pion du joueur courant
     * en verité c'est coupAutoriser qui mets les -1
     * cf commentaire de coupAutoriser(i,j)
     */
	public static void casePossible(){
		 for(int i = 0 ;i < plateau.length;i++){
		   	 for(int j = 0 ;j < plateau[i].length;j++){
		   		if(plateau[i][j] < 0) plateau[i][j] = 0; // si la case est égale à -1 cela signifie que c'est un coup autorisé mais ça reste quand meme un vide
		   	 } 
		 }
		
		for(int i = 0 ;i < plateau.length;i++){
			for(int j = 0 ;j < plateau[i].length;j++){
				if(plateau[i][j] == tour) coupAutoriser(i,j); // si la case est égale à tour (1 ou 2) on appelle donc la fonction coupAutoriser() pour vérifier tous les coups valables
			 }
		}
		comptePion(); 	 
		if(coupPossible == 0 && vide != 0){ // signifie que le plateau n'est pas totalement rempli, mais qu'il y a aucun coup possinle pour l'utilisateur
			aucunCoupPossible ++; 
			tour = 3 - tour; // le joueur actuel passe la main à l'adverse
		    if(aucunCoupPossible == 2) egalité = true; // signifie que la partie est fini
			if(!egalité) casePossible(); 
		} else aucunCoupPossible = 0;
	}
	
	/**
     * compte le nombre de pion blanc et noir, le nombre de case vide et le nombre de coupPossible.
     *  Attention: une case peut etre compté dans vide ET dans coupPossible,(ce qui semble logique mais tjr bon de le preciser)
     */
	public static void comptePion(){
		coupPossible = 0;
		vide = 0;
		blanc = 0;
		noir = 0;
		
		 for(int i = 0 ;i < plateau.length;i++){
        	 for(int j = 0 ;j < plateau[i].length;j++){
        		 if(plateau[i][j] == -1) coupPossible++;//pour rappel coupPossible est le pion rouge
        		 else if(plateau[i][j] == 0) vide++;
        		 else if(plateau[i][j] == 1) noir++;
        		 else blanc++;
        	 }
        }
    }
	
	/**
     * la fonction prend le pion a la case (i,j) et regarde tout autours de lui s'il y'a un chemin de
     * pion adverse qui peut se terminer sur une case vide.
     * Cette case vide sera marqué par -1
     * exemple:
     * x = joueur courant, Y = autre joueur, Z = vide,
     * X est au coordonnée (i,j) on va regardé autour de lui s'il y'a un chemin et s'il debouche sur du vide:
     * XYYYYYZ ici a droite de X il y'a un chemin de Y debouchant sur Z, Z sera marqué par -1.
     */
	public static void coupAutoriser(int i,int j){
		int oI = i;
		int oJ = j;
        int pasLeTour;
        if(tour == 1){
			pasLeTour = 2;
		}else{
			pasLeTour = 1;
		}
		boolean verifié = false;
		//up
		while(i >= 0 && i < taille && j-1 >= 0 && j-1 < taille && !verifié){
        	if(plateau[i][j-1] == pasLeTour){
        		if(i >= 0 && i < taille && j-2 >= 0 && j-2 < taille){
    				if(plateau[i][j-2] == 0){
    					plateau[i][j-2] = -1; // coup possible
    					verifié = true;
        			}else if(plateau[i][j-2] == pasLeTour){
        				j--;
        			}else{
        				verifié = true;
        			}
    			}else{
    				verifié = true;	
    			}
    		} else{
    			verifié = true;
    		}
		}
		
        i = oI;
        j = oJ;
        verifié = false;
    	
      //up-right
    	while(i+1 >= 0 && i+1 < taille && j-1 >= 0 && j-1 < taille && !verifié){
        	if(plateau[i+1][j-1] == pasLeTour){
    			if(i+2 >= 0 && i+2 < taille && j-2 >= 0 && j-2 < taille){
    				if(plateau[i+2][j-2] == 0){
    					plateau[i+2][j-2] = -1;
    					verifié = true;
        			}else if(plateau[i+2][j-2] == pasLeTour){
        				j = j-1;
        				i = i+1;
        			}else{
        				verifié = true;
        			}
    			}else{
    				verifié = true;	
    			}
    		}else{
    			verifié = true;
    		}
		}
    	   i = oI;
           j = oJ;
           verifié = false;
       	
         //right
       	while(i+1 >= 0 && i+1 < taille && j >= 0 && j < taille && !verifié){
           	if(plateau[i+1][j] == pasLeTour){
       			if(i+2 >= 0 && i+2 < taille && j >= 0 && j < taille){
       				if(plateau[i+2][j] == 0){
       					plateau[i+2][j] = -1;
       					verifié = true;
           			}else if(plateau[i+2][j] == pasLeTour){
           				i = i+1;
           			}else{
           				verifié = true;
           			}
       			}else{
       				verifié = true;	
       			}
       		}else{
       			verifié = true;
       		}
   		}
       	
        i = oI;
        j = oJ;
        verifié = false;
    	
      //right-down
    	while(i+1 >= 0 && i+1 < taille && j+1 >= 0 && j+1 < taille && !verifié){
        	if(plateau[i+1][j+1] == pasLeTour){
    			if(i+2 >= 0 && i+2 < taille && j+2 >= 0 && j+2 < taille){
    				if(plateau[i+2][j+2] == 0){
    					plateau[i+2][j+2] = -1;
    					verifié = true;
        			}else if(plateau[i+2][j+2] == pasLeTour){
        				i = i+1;
        				j = j+1;
        			}else{
        				verifié = true;
        			}
    			}else{
    				verifié = true;	
    			}
    		}else{
    			verifié = true;
    		}
		}
    	
    	 i = oI;
         j = oJ;
         verifié = false;
     	
       //down
     	while(i >= 0 && i < taille && j+1 >= 0 && j+1 < taille && !verifié){
         	if(plateau[i][j+1] == pasLeTour){
     			if(i >= 0 && i < taille && j+2 >= 0 && j+2 < taille){
     				if(plateau[i][j+2] == 0){
     					plateau[i][j+2] = -1;
     					verifié = true;
         			}else if(plateau[i][j+2] == pasLeTour){
         				j = j+1;
         			}else{
         				verifié = true;
         			}
     			}else{
     				verifié = true;	
     			}
     		}else{
     			verifié = true;
     		}
 		}
     	
     	 i = oI;
         j = oJ;
         verifié = false;
     	
       //down-left
     	while(i-1 >= 0 && i-1 < taille && j+1 >= 0 && j+1 < taille && !verifié){
         	if(plateau[i-1][j+1] == pasLeTour){
     			if(i-2 >= 0 && i-2 < taille && j+2 >= 0 && j+2 < taille){
     				if(plateau[i-2][j+2] == 0){
     					plateau[i-2][j+2] = -1;
     					verifié = true;
         			}else if(plateau[i-2][j+2] == pasLeTour){
         				j = j+1;
         				i = i-1;
         			}else{
         				verifié = true;
         			}
     			}else{
     				verifié = true;	
     			}
     		}else{
     			verifié = true;
     		}
 		}
     	
   	 i = oI;
     j = oJ;
     verifié = false;
 	
   //left
 	while(i-1 >= 0 && i-1  < taille && j >= 0 && j < taille && !verifié){
     	if(plateau[i-1][j] == pasLeTour){
 			if(i-2 >= 0 && i-2 < taille && j >= 0 && j < taille){
 				if(plateau[i-2][j] == 0){
 					plateau[i-2][j] = -1;
 					verifié = true;
     			}else if(plateau[i-2][j] == pasLeTour){
     				i = i-1;
     			}else{
     				verifié = true;
     			}
 			}else{
 				verifié = true;	
 			}
 		}else{
 			verifié = true;
 		}
		}
 	
	 i = oI;
     j = oJ;
     verifié = false;
 	
   //left-up
 	while(i-1 >= 0 && i-1 < taille && j-1 >= 0 && j-1 < taille && !verifié){
     	if(plateau[i-1][j-1] == pasLeTour){
 			if(i-2 >= 0 && i-2 < taille && j-2 >= 0 && j-2 < taille){
 				if(plateau[i-2][j-2] == 0){
 					plateau[i-2][j-2] = -1;
 					verifié = true;
     			}else if(plateau[i-2][j-2] == pasLeTour){
     				i = i-1;
     				j = j-1;
     			}else{
     				verifié = true;
     			}
 			}else{
 				verifié = true;	
 			}
 		}else{
 			verifié = true;
 		}
		}
   		
	}
	
	/**
     * actualise le plateau qd un coup est joué en fonction du coup.
     * place le coup joué après qu'il ai été validé ensuite modifie les pions qui doivent l'être
     */
	public static void actualisePlateau(int i,int j){
		int pasLeTour ;
		int oI = i;
		int oJ = j;
		boolean verifié = false;
		if(tour == 1){
			pasLeTour = 2;
		}else{
			pasLeTour = 1;
		}
		
		//up-actualisePlateau
		while(i >= 0 && i < taille && j-1 >= 0 && j-1 < taille && !verifié){
        	if(plateau[i][j-1] == pasLeTour){
        		if(i >= 0 && i < taille && j-2 >= 0 && j-2 < taille){
                    if (plateau[i][j - 2] == tour) {
                        // c'est à ce moment que les cases prendrons la couleur du tour joué 
    					for(int k = j-1;k <= oJ;k++){
    						plateau[i][k] = tour;
    					}
    					verifié = true;
        			}else if(plateau[i][j-2] == pasLeTour){
        				j = j-1;
        			}else verifié = true;
     			}else verifié = true;
     		}else verifié = true;
    	}
		
		verifié = false;
    	i = oI;
        j = oJ;
    	
      //up-right
    	while(i+1 >= 0 && i+1 < taille && j-1 >= 0 && j-1 < taille && !verifié){
        	if(plateau[i+1][j-1] == pasLeTour){
        		if(i+2 >= 0 && i+2 < taille && j-2 >= 0 && j-2 < taille){
					if (plateau[i + 2][j - 2] == tour) {
						// c'est à ce moment que les pions changent de couleur 
    					int m = i+1;
    					for(int k = j-1;k < oJ;k++){
        					plateau[m][k] = tour;
        					m--;
    					}
    					verifié = true;
        			}else if(plateau[i+2][j-2] == pasLeTour){
        				j = j-1;
        				i = i+1;
        			}else verifié = true;
     			}else verifié = true;
     		}else verifié = true;
    	}
    	
    	verifié = false;
    	i = oI;
        j = oJ;
       	
         //right
       	while(i+1 >= 0 && i+1 < taille && j >= 0 && j < taille && !verifié){
          	if(plateau[i+1][j] == pasLeTour){
        		if(i+2 >= 0 && i+2 < taille && j >= 0 && j < taille){
       				if(plateau[i+2][j] == tour){
       				for(int k = i+1;k > oI;k--){
        					plateau[k][j] = tour;		
    					}
       					verifié = true;
           			}else if(plateau[i+2][j] == pasLeTour){
           				i = i+1;
           			}else verifié = true;
     			}else verifié = true;
     		}else verifié = true;
    	}
       	
       	verifié = false;
    	i = oI;
        j = oJ;
   	
      //right-down
    	while(i+1 >= 0 && i+1 < taille && j+1 >= 0 && j+1 < taille && !verifié){
        	if(plateau[i+1][j+1] == pasLeTour){
        		if(i+2 >= 0 && i+2 < taille && j+2 >= 0 && j+2 < taille){
    				if(plateau[i+2][j+2] == tour){
    					int m = i+1;
    					for(int k = j+1;k > oJ;k--){	
        					plateau[m][k] = tour;
        					m--;
    					}
    					verifié = true;
        			}else if(plateau[i+2][j+2] == pasLeTour){
        				i = i+1;
        				j = j+1;
        			}else verifié = true;
        		}else verifié = true;
        	}else verifié = true;
    	}
    	
    	verifié = false;
    	i = oI;
        j = oJ;
     	
       //down
     	while(i >= 0 && i < taille && j+1 >= 0 && j+1 < taille && !verifié){
         	if(plateau[i][j+1] == pasLeTour){
         		if(i >= 0 && i < taille && j+2 >= 0 && j+2 < taille){
     				if(plateau[i][j+2] == tour){
     					for(int k = j+1;k > oJ;k--){
        					plateau[i][k] = tour;				
    					}
     					verifié = true;
         			}else if(plateau[i][j+2] == pasLeTour){
         				j = j+1;
         			}else verifié = true;
         		}else verifié = true;
         	}else verifié = true;
     	}
     	
     	verifié = false;
    	i = oI;
        j = oJ;
         
     	
       //down-left
     	while(i-1 >= 0 && i-1 < taille && j+1 >= 0 && j+1 < taille && !verifié){
        	if(plateau[i-1][j+1] == pasLeTour){
        		if(i-2 >= 0 && i-2 < taille && j+2 >= 0 && j+2 < taille){
     				if(plateau[i-2][j+2] == tour){
     					int m = i-1;
     					for(int k = j+1;k > oJ;k--){						
        					plateau[m][k] = tour;
        					m++;
    					}
     					verifié = true;
         			}else if(plateau[i-2][j+2] == pasLeTour){
         				j = j+1;
         				i = i-1;
         			}else verifié = true;
        		}else verifié = true;
        	}else verifié = true;
     	}
     	
     	verifié = false;
    	i = oI;
        j = oJ;
 	
   //left
 	while(i-1 >= 0 && i-1  < taille && j >= 0 && j < taille && !verifié){
     	if(plateau[i-1][j] == pasLeTour){
     		if(i-2 >= 0 && i-2 < taille && j >= 0 && j < taille){
 				if(plateau[i-2][j] == tour){
 					for(int k = i-2;k < oI;k++){
    					plateau[k][j] = tour;					
    				}
 					verifié = true;
     			}else if(plateau[i-2][j] == pasLeTour){
     				i = i-1;
     			}else verifié = true;
 			}else verifié = true;
 		}else verifié = true;
 	}
 	
 	verifié = false;
	i = oI;
    j = oJ;
	
   //left-up
 	while(i-1 >= 0 && i-1 < taille && j-1 >= 0 && j-1 < taille && !verifié){
     	if(plateau[i-1][j-1] == pasLeTour){
     		if(i-2 >= 0 && i-2 < taille && j-2 >= 0 && j-2 < taille){
 				if(plateau[i-2][j-2] == tour){
 					int m = i-1;
 					for(int k = j-1;k < oJ;k++){				
    					plateau[m][k] = tour;
    					m++;
					}
 					verifié = true;
     			}else if(plateau[i-2][j-2] == pasLeTour){
     				i = i-1;
     				j = j-1;
     			}else verifié = true;
 			}else verifié = true;
 		}else verifié = true;
	}
	}
		
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}