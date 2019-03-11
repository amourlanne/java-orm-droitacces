package dao;

import entity.Utilisateur;

import java.sql.*;

/**
 * Class UtilisateurDAO
 * Created by Alexis on 01/03/2019
 */
public class UtilisateurDAO {

    private final Connection connection;

    public UtilisateurDAO(Connection connexion) {
        this.connection = connexion;
    }

    public void addUtilisateur(Utilisateur utilisateur, String... droits) throws SQLException {

        boolean transactionOk = false;

        try {

            String requeteAjoutUtilisateur =
                    "insert into Utilisateur (login, dateInscription, actif) values (?, ?, ?)";

            int userId = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(requeteAjoutUtilisateur, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, utilisateur.getLogin());
                pstmt.setDate(2, utilisateur.getInscription());
                pstmt.setBoolean(3, utilisateur.isActif());

                pstmt.executeUpdate();

                try (ResultSet resultSet = pstmt.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt(1);
                    }
                }
            }

            for (String droit : droits) {

                String requeteInsertUtilisateurDroit =
                        "insert into Utilisateur_Droit (id_utilisateur, id_droit) select ?, id from Droit where libelle = ?))";

                try (PreparedStatement pstmt = connection.prepareStatement(requeteInsertUtilisateurDroit)) {
                    pstmt.setInt(1, userId);
                    pstmt.setString(2, droit);

                    if(pstmt.executeUpdate() == 0) {
                        // lancer une exception
                    };
                }
            }

            transactionOk = true;
        }
        finally {

            if (transactionOk) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
        }
    }
}
