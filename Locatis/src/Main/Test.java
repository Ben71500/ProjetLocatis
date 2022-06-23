package Main;

import Vues.Vue_Connexion;
import Modeles.Modele_Connexion;
import Controleurs.Controleur_Connexion;
import Objets_Locatis.SurveillanceCampagne;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Test {

    public static void main(String[] args) {
        //On soumet l'intialisation de l'interface graphique à la file d'attente de l'EDT (Event Dispatching Thread)
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Création d'une contrôleur qui communique avec une vue et un modèle
                Controleur_Connexion controleur = new Controleur_Connexion(new Vue_Connexion(), new Modele_Connexion());
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //Centrage de la fenetre sur l'écran
                Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
                int height = 400;
                int width = 800;
                controleur.getVue().setBounds((tailleEcran.width-width)/2, (tailleEcran.height-height)/2, width, height);
                controleur.getVue().setVisible(true);
            }
        });
        SurveillanceCampagne surveillance = new SurveillanceCampagne();
        surveillance.start();
    }
}