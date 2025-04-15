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
            PreparedStatement pStatement = cPreparedStatement("SELECT id, password FROM auth WHERE username = ?");
            pStatement.setString(1, username);
            ResultSet resultSet = pStatement.executeQuery();
            if (resultSet.next()){
                return new User(resultSet.getInt("id"), username, resultSet.getString("password"));
            } else {
                throw new UserNotFound();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
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
			PreparedStatement pStatement = cPreparedStatement("SELECT 1 FROM auth WHERE password = ?");
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
            if (user.getPassword().equals(password))
                return true;
            return false;
		} catch (UserNotFound e) {
			return false;
		}
    }

    public boolean insertUser(String username, String password){
        try {
            PreparedStatement pStatement = cPreparedStatement("INSERT INTO auth (username, password) values (?, ?)");
            pStatement.setString(1, username);
			pStatement.setString(2, password);
            int rowsInserted = pStatement.executeUpdate();
            if (rowsInserted > 0)
                return true;
            return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
            return false;
		}
    }
}
