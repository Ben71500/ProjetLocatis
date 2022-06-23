package Main;

import Vues.Vue_Connexion;
import Modeles.Modele_Connexion;
import Controleurs.Controleur_Connexion;
import Objets_Locatis.SurveillanceCampagne;
import javax.swing.*;

public class Test {

    public static void main(String[] args) {
        //On soumet l'intialisation de l'interface graphique à la file d'attente de l'EDT (Event Dispatching Thread)
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Création d'une contrôleur qui communique avec une vue et un modèle
                //Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(adm),new Modele_Gestion("locataires"), adm);
                Controleur_Connexion controleur = new Controleur_Connexion(new Vue_Connexion(), new Modele_Connexion());
                //Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("locataire"),new Modele_Gestion("locataires"), adm, "locataire");                
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                controleur.getVue().setSize(500,300);
                controleur.getVue().setVisible(true);
                /*Controleur_Gestion controleur = new Controleur_Gestion(new AutreVue(),new Modele_Gestion());                
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                controleur.getVue().setVisible(true);*/
                /*Recevoir_DAO r= new Recevoir_DAO(ConnectionBDD.getInstance(new Connexion()));
                ArrayList<String> liste = r.getListeEmails(92);
                for(int i=0;i<liste.size();i++)
                    System.out.println(liste.get(i));*/
            }
        });
        SurveillanceCampagne surveillance = new SurveillanceCampagne();
        surveillance.start();
    }
}
