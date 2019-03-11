import dao.DroitAccesDao;
import dao.UtilisateurDAO;
import entity.Utilisateur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class MonProgramme
 * Created by Alexis on 25/02/2019
 */
public class MonProgramme {
    public static void main(String[] args) {
        try (Connection connection =
                     DriverManager.getConnection("jdbc:mysql://localhost:6603/droitacces","root", "password")) {
           /* DroitAccesDao droitAccesDao = new DroitAccesDao(connection);
            droitAccesDao.desactiverAnciensUtilisateurs();

            for (Utilisateur utilisateur : droitAccesDao.getUtilisateurs()) {
                System.out.println(utilisateur.getLogin());
            }*/

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setLogin("alexis");
            utilisateur.setInscription( new Date(System.currentTimeMillis()));
            utilisateur.setActif(true);

            connection.setAutoCommit(false);
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(connection);

            utilisateurDAO.addUtilisateur(utilisateur, "connexion", "lecture");


//            System.out.println(droitAccesDao.isUserAuthorized("vincent", "connexion"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
