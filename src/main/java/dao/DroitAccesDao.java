package dao;

import entity.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class dao.DroitAccesDao
 * Created by Alexis on 25/02/2019
 */
public class DroitAccesDao {

    private final Connection connexion;

    public DroitAccesDao(Connection connection) {
        this.connexion = connection;
    }

    public void desactiverAnciensUtilisateurs() throws SQLException {
        try(Statement stmt = this.connexion.createStatement()) {
            stmt.executeUpdate("UPDATE Utilisateur SET actif = false WHERE year(CURRENT_DATE) - year(dateInscription) > 10");
        }
    }

    public List<Utilisateur> getUtilisateurs() throws SQLException {

        List<Utilisateur> utilisateurs = new ArrayList<>();

        try(Statement stmt = this.connexion.createStatement()) {

            try (ResultSet resultSet = stmt.executeQuery("SELECT * FROM Utilisateur")) {
                while (resultSet.next()) {
                    Utilisateur utilisateur = new Utilisateur();

                    Integer id = resultSet.getInt("id");
                    String login = resultSet.getString("login");
                    Date inscription = resultSet.getDate("dateInscription");
                    boolean actif = resultSet.getBoolean("actif");

                    utilisateur.setId(id);
                    utilisateur.setLogin(login);
                    utilisateur.setInscription(inscription);
                    utilisateur.setActif(actif);

                    utilisateurs.add(utilisateur);
                }
            }
        }
        return utilisateurs;
    }

    public boolean isUserAuthorized(String login, String droit) throws SQLException {

        String request = "select * from Utilisateur_Droit where id_utilisateur = (select id from Utilisateur where login = ?) and id_droit = (select id from Droit where libelle = ?)";

        try (PreparedStatement stmt = this.connexion.prepareStatement(request)) {

            stmt.setString(1, login);
            stmt.setString(2, droit);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
