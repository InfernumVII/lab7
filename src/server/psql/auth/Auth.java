package server.psql.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import server.psql.PSQL;
import server.psql.exceptions.UserNotFound;

public class Auth extends PSQL {

    public Auth(Connection connection) {
        super(connection);
    }


    public User getUser(String login) throws UserNotFound{
        try {
            PreparedStatement pStatement = createPreparedStatement("SELECT id, password FROM auth WHERE login = ?");
            pStatement.setString(1, login);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("id"), login, resultSet.getString("password"));
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public User getUserByAuthKey(String password) throws UserNotFound{
        try {
            PreparedStatement pStatement = createPreparedStatement("SELECT id, login FROM auth WHERE password = ?");
            pStatement.setString(1, password);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("id"), resultSet.getString("login"), password);
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage()); //serr
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean userIsExists(String login){
        System.out.println(3);
        try {
            getUser(login);
            System.out.println(4);
        } catch (UserNotFound e) {
            return false;
        }
        return true;
    }
    
    public boolean passwordIsExist(String password){
        try {
			PreparedStatement pStatement = createPreparedStatement("SELECT 1 FROM auth WHERE password = ?");
            pStatement.setString(1, password);
            ResultSet resultSet = pStatement.executeQuery();
            return resultSet.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
            return false;
		}
    }

    public boolean passwordCheck(String login, String password){
        try {
			User user = getUser(login);
            return user.getPassword().equals(password);
            
		} catch (UserNotFound e) {
			return false;
		}
    }

    public boolean insertUser(String login, String password){
        try {
            PreparedStatement pStatement = createPreparedStatement("INSERT INTO auth (login, password) values (?, ?)");
            pStatement.setString(1, login);
			pStatement.setString(2, password);
            int rowsInserted = pStatement.executeUpdate();
            return rowsInserted > 0;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
            return false;
		}
    }
}
