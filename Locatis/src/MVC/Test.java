package MVC;

import Locatis.*;
import javax.swing.*;

public class Test {

    public static void main(String[] args) {
        //On soumet l'intialisation de l'interface graphique à la file d'attente de l'EDT (Event Dispatching Thread)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Création d'une contrôleur qui communique avec une vue et un modèle
                Utilisateur adm=new Utilisateur(0,"admin","admin","adm");
                //Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(adm),new Modele_Gestion("locataires"), adm);
                Controleur_Connexion controleur = new Controleur_Connexion(new Vue_Connexion(), new Modele_Connexion(adm));
                //Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("locataire"),new Modele_Gestion("locataires"), adm, "locataire");                
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                controleur.getVue().setSize(500,300);
                controleur.getVue().setVisible(true);
                /*Controleur_Gestion controleur = new Controleur_Gestion(new AutreVue(),new Modele_Gestion());                
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                controleur.getVue().setVisible(true);*/
            }
        });
    }
}
