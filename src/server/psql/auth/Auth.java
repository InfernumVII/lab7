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


    public User getUser(String username) throws UserNotFound{
        try {
            PreparedStatement pStatement = createPreparedStatement("SELECT id, password FROM auth WHERE username = ?");
            pStatement.setString(1, username);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("id"), username, resultSet.getString("password"));
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
            PreparedStatement pStatement = createPreparedStatement("SELECT id, username FROM auth WHERE password = ?");
            pStatement.setString(1, password);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("id"), resultSet.getString("username"), password);
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage()); //serr
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean userIsExists(String username){
        System.out.println(3);
        try {
            getUser(username);
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

    public boolean passwordCheck(String username, String password){
        try {
			User user = getUser(username);
            return user.getPassword().equals(password);
            
		} catch (UserNotFound e) {
			return false;
		}
    }

    public boolean insertUser(String username, String password){
        try {
            PreparedStatement pStatement = createPreparedStatement("INSERT INTO auth (username, password) values (?, ?)");
            pStatement.setString(1, username);
			pStatement.setString(2, password);
            int rowsInserted = pStatement.executeUpdate();
            if (rowsInserted > 0) //todo refac
                return true;
            return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
            return false;
		}
    }
}
