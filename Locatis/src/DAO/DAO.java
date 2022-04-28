package DAO;

import Locatis.MyDate;
import Locatis.MyTime;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.sql.Time;


/**
 * Classe abstraite générique Permet de déclarer des objets d'accès à une base
 * de données
 *
 * @author Hervé Martinez
 * @param <T> Représente la classe des objets Java à manipuler
 */
public abstract class DAO<T> {

    /**
     * champ connection
     */
    protected Connection connection;

    /**
     * Constructeur
     *
     * @param connection permet l'initialisation du champ connection
     */
    public DAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Méthode de création d'un enregistrement
     *
     * @param obj un objet Auteur à écrire dans la base
     * @return boolean qui vaut true si la création a réussi, false dans le cas
     * contraire
     */
    public abstract boolean create(T obj);

    /**
     * Méthode de suppression d'un enregistrement
     *
     * @param obj un objet Auteur à supprimer dans la base
     * @return boolean qui vaut true si la suppression a réussi, false dans le
     * cas contraire
     */
    public abstract boolean delete(T obj);

    /**
     * Méthode de mise à jour d'un enregistrement
     *
     * @param obj un objet Auteur à mettre à jour dans la base
     * @return boolean qui vaut true si la mise à jour a réussi, false dans le
     * cas contraire
     */
    public abstract boolean update(T obj);

    /**
     * Méthode de recherche des informations par l'id
     *
     * @param id l'identificateur à rechercher
     * @return T
     */
    public abstract T selectById(int id);

    /**
     * Méthode de recherche des informations par un nom
     *
     * @param name
     * @return T
     */
    public abstract T selectByName(String name);

    /**
     * Retourne tous les objets de la table sous forme de liste
     *
     * @return
     */
    public abstract List<T> getAll();
    
    protected MyDate getMyDate(Date d){
        int jour = d.toLocalDate().getDayOfMonth();
        int annee = d.toLocalDate().getYear();
        int mois = d.toLocalDate().getMonthValue();
        return new MyDate(annee, mois, jour);
    }
    
    protected MyTime getMyTime(Time t){
        int heure = t.toLocalTime().getHour();
        int minute = t.toLocalTime().getMinute();
        return new MyTime(heure, minute);
    }
    /*protected Date getDate(MyDate d){
        new Date;
    }*/
}
