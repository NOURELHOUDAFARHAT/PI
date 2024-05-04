package services;

import entities.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



public class UserService implements IService<User> {
    private Connection cnx;

    public UserService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO user (email, roles, password, nom, prenom, adresse, sexe, is_activated) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, Arrays.toString(user.getRoles()));
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getNom());
            preparedStatement.setString(5, user.getPrenom());
            preparedStatement.setString(6, user.getAdresse());
            preparedStatement.setString(7, user.getSexe());
            preparedStatement.setBoolean(8, user.isIs_activated());

            preparedStatement.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUser(User user) {
        String sql = "UPDATE user SET email=?, nom=?, prenom=?, adresse=?, sexe=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getAdresse());
            preparedStatement.setString(5, user.getSexe());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    @Override
    public void update(User user, int id) {
        String sql = "UPDATE user SET nom=?, prenom=?, adresse=?, sexe=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getAdresse());
            preparedStatement.setString(4, user.getSexe());
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM user WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String[] roles = rs.getString("roles").split(","); // Supposons que les rôles soient stockés sous forme de chaîne séparée par des virgules
                list.add(new User(rs.getInt("id"), rs.getString("email"), roles, rs.getString("password"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("sexe"), rs.getBoolean("is_activated")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM user WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String[] roles = rs.getString("roles").split(","); // Supposons que les rôles soient stockés sous forme de chaîne séparée par des virgules
                return new User(rs.getInt("id"), rs.getString("email"), roles, rs.getString("password"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("sexe"), rs.getBoolean("is_activated"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Retourne null si aucun utilisateur trouvé avec cet ID
    }
    public boolean authenticate(String email, String password) {
        User user = getByEmail(email);
        if (user != null) {
            String hashedPasswordFromDB = user.getPassword();
            return BCrypt.checkpw(password, hashedPasswordFromDB);
        }
        return false;
    }


    public User getByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String[] roles = rs.getString("roles").split(",");
                return new User(rs.getInt("id"), rs.getString("email"), roles, rs.getString("password"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("sexe"), rs.getBoolean("is_activated"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Méthode pour récupérer les rôles d'un utilisateur par son email
    public List<String> getRoles(String email) {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT roles FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String rolesString = rs.getString("roles");
                roles = Arrays.asList(rolesString.split(","));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }
    public int getUserIdByEmail(String email) {
        String sql = "SELECT id FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1; // Retourne -1 si aucun utilisateur n'est trouvé avec cet e-mail
    }
    public User connexion(String email,String password)throws  Exception{
        String req = "SELECT * FROM user WHERE email=? and password=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1, email);
        pre.setString(2, password);
        ResultSet resultSet = pre.executeQuery();
        while (resultSet.next()){
            return new User(resultSet.getInt("id_user"),resultSet.getString("nom"),resultSet.getString("prenom"),resultSet.getString("email"),resultSet.getString("password"),resultSet.getString("adresse"),resultSet.getString("sexe"));
        }

        return  null;
    }
    public void changePassword(int userId, String newPassword) {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        String sql = "UPDATE user SET password=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            System.out.println("Mot de passe changé avec succès pour l'utilisateur avec l'ID : " + userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUserr(User user) {
        String sql = "UPDATE user SET email=?, nom=?, prenom=?, adresse=?, sexe=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getAdresse());
            preparedStatement.setString(5, user.getSexe());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

