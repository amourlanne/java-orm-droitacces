package entity;

import java.sql.Date;

/**
 * Class entity.Utilisateur
 * Created by Alexis on 25/02/2019
 */
public class Utilisateur {
    private Integer id;
    private String login;
    private Date inscription;
    private boolean actif;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getInscription() {
        return inscription;
    }

    public void setInscription(Date inscription) {
        this.inscription = inscription;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
