package server.psql.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import server.psql.PSQL;
import server.psql.exceptions.KeyNotFound;

public class Auth extends PSQL {

    public Auth(Connection connection) {
        super(connection);
    }
    
    public Key getKey(String key) throws KeyNotFound{
        try {
            PreparedStatement pStatement = cPreparedStatement("SELECT * FROM auth WHERE key = ?");
            pStatement.setString(1, key);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()){
                int id = resultSet.getInt("id");
                String keyGet = resultSet.getString("key");
                return new Key(id, keyGet);
            } else {
                throw new KeyNotFound();
            }
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        
        
        
    }

    public boolean keyIsExists(String key){
        try {
            getKey(key);
        } catch (KeyNotFound e) {
            return false;
        }
        return true;
    }

    public boolean insertKey(String key){
        try {
            PreparedStatement pStatement = cPreparedStatement("INSERT INTO auth (key) values (?)");
            pStatement.setString(1, key);
            int rowsInserted = pStatement.executeUpdate();
            if (rowsInserted > 0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        
    }
    

}
