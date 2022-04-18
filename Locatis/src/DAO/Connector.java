package DAO;

import java.util.Objects;

/**
 * Classe abstraite permettant de créer des classes permettant d'instancier des
 * objets ayant pour rôle de contenir les informations de connection à une base
 *
 * @author Hervé Martinez
 */
public abstract class Connector {

    /**
     * URL de connexion à la base
     */
    private final String URL;
    /**
     * Nom de l'utilisateur pour connection à la base
     */
    private final String USER;
    /**
     * Mot de passe de l'utilisateur
     */
    private final String PWD;
    /**
     * Un nom pour la base
     */
    private final String name;

    /**
     * Getter de l'URL de la base
     *
     * @return
     */
    protected final String getURL() {
        return URL;
    }

    /**
     * Getter du nom d'utilisateur
     *
     * @return
     */
    protected final String getUSER() {
        return USER;
    }

    /**
     * Pas de getter pour le mot de passe
     *
     * @return
     */
    protected final String getPWD() {
        return PWD;
    }

    /**
     * Getter du nom de la base
     *
     * @return
     */
    public final String getNAME() {
        return name;
    }

    /**
     *
     * @param URL
     * @param USER
     * @param PWD
     */
    protected Connector(String URL, String USER, String PWD) {
        this.URL = URL;
        this.USER = USER;
        this.PWD = PWD;
        this.name = "<none>";
    }

    /**
     *
     * @param URL
     * @param USER
     * @param PWD
     * @param NAME
     */
    protected Connector(String URL, String USER, String PWD, String NAME) {
        this.URL = URL;
        this.USER = USER;
        this.PWD = PWD;
        this.name = NAME;
    }

    /**
     *
     * @param aConnector
     * @return
     */
    @Override
    public final boolean equals(Object aConnector) {
        if (this == aConnector) {
            return true;
        }
        if (aConnector == null || getClass() != aConnector.getClass()) {
            return false;
        }

        final Connector anotherConnector = (Connector) aConnector;

        if (!Objects.equals(this.URL, anotherConnector.URL)) {
            return false;
        }
        if (!Objects.equals(this.USER, anotherConnector.USER)) {
            return false;
        }
        return Objects.equals(this.PWD, anotherConnector.PWD);
    }

}
