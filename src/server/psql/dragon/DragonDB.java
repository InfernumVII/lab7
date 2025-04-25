package server.psql.dragon;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import server.managers.ServerCommandManager;
import server.psql.PSQL;
import server.psql.auth.Pair;
import server.psql.auth.User;
import server.psql.exceptions.UserNotFound;

public class DragonDB extends PSQL {
    public DragonDB(Connection connection) {
        super(connection);
    }

    public int insertCords(Coordinates dCoordinates){
        try {
            PreparedStatement coordStmt  = createPreparedStatement("INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING coord_id");
            coordStmt.setLong(1, dCoordinates.getX());
            coordStmt.setLong(2, dCoordinates.getY());
            ResultSet cordSet = coordStmt.executeQuery();
            if (cordSet.next())
                return cordSet.getInt("coord_id");
            
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
    public int insertHead(DragonHead DragonHead){
        try {
            PreparedStatement headStmt  = createPreparedStatement("INSERT INTO dragon_heads (eyes_count) VALUES (?) RETURNING head_id");
            headStmt.setFloat(1, DragonHead.getEyesCount());
            ResultSet headSet = headStmt.executeQuery();
            if (headSet.next())
                return headSet.getInt("head_id");
            
            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public List<Dragon> getAllDragons() {
        List<Dragon> dragons = new ArrayList<>();
        try {
            String sql = "SELECT d.id, d.name, d.creation_date, d.age, d.color::text, " 
                       + "d.type::text, d.character::text, c.x, c.y, h.eyes_count, o.auth_id "
                       + "FROM dragons d "
                       + "JOIN coordinates c ON d.coord_id = c.coord_id "
                       + "JOIN dragon_heads h ON d.head_id = h.head_id "
                       + "JOIN owner_table o ON d.id = o.dragon_id";

            PreparedStatement stmt = createPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dragon dragon = new Dragon.Builder()
                    .withId(rs.getInt("id"))
                    .withName(rs.getString("name"))
                    .withCoordinates(new Coordinates(
                        rs.getLong("x"), 
                        rs.getLong("y")
                    ))
                    .withDate(rs.getDate("creation_date").toLocalDate())
                    .withAge(rs.getLong("age"))
                    .withColor(Color.valueOf(rs.getString("color")))
                    .withType(DragonType.valueOf(rs.getString("type")))
                    .withCharacter(DragonCharacter.valueOf(rs.getString("character")))
                    .withHead(new DragonHead(rs.getFloat("eyes_count")))
                    .withOwnerId(rs.getInt("auth_id"))
                    .build();

                dragons.add(dragon);
            }
        } catch (SQLException e) {
            //System.err.println("Ошибка загрузки драконов из БД: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dragons;
    }
    //todo сделать перегрузку
    public boolean deleteDragonByIdAndAuth(int dragonId, String authKey){
        try {
            User user = ServerCommandManager.getAuthInstance().getUserByAuthKey(authKey);
            int user_id = user.getId();

            try {
                PreparedStatement pStatement = createPreparedStatement("DELETE FROM dragons USING owner_table WHERE dragons.id = owner_table.dragon_id AND owner_table.auth_id = ? AND dragons.id = ?");
                pStatement.setInt(1, user_id);
                pStatement.setInt(2, dragonId);
                int editedRows = pStatement.executeUpdate();
                if (editedRows > 0)
                    return true;
                return false;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        } catch (UserNotFound e) {
            return false;
        }
    }

    public boolean deleteDragonsWithAuth(String authKey){
        try {
            User user = ServerCommandManager.getAuthInstance().getUserByAuthKey(authKey);
            int user_id = user.getId();

            try {
                PreparedStatement pStatement = createPreparedStatement("DELETE FROM dragons USING owner_table WHERE dragons.id = owner_table.dragon_id AND owner_table.auth_id = ?");
                pStatement.setInt(1, user_id);
                int editedRows = pStatement.executeUpdate();
                if (editedRows > 0)
                    return true;
                return false;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        } catch (UserNotFound e) {
            return false;
        }
        
        /*
         DELETE FROM dragons
USING owner_table
WHERE dragons.id = owner_table.dragon_id
  AND dragons.id = 1
  AND owner_table.auth_id = 1;
         */


    }

    public int createOwner(int id, String authKey){
        try {
            User user = ServerCommandManager.getAuthInstance().getUserByAuthKey(authKey);
            int user_id = user.getId();
            PreparedStatement ownerStmt  = createPreparedStatement("INSERT INTO owner_table (auth_id, dragon_id) VALUES (?, ?)");
            ownerStmt.setInt(1, user_id);
            ownerStmt.setInt(2, id);

            int update = ownerStmt.executeUpdate();
            if (update > 0)
                return user_id;
            return -1;
        } catch (UserNotFound | SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public Pair<Integer, Integer> insertDragon(Dragon dragon, String authKey){ //should return id
        try {
            int coordId = insertCords(dragon.getCoordinates());
            if (coordId == -1)
                return new Pair<Integer,Integer>(-1, -1);
            int headId = insertHead(dragon.getHead());
            if (headId == -1)
                return new Pair<Integer,Integer>(-1, -1);
            String dragonSql = "INSERT INTO dragons (id, name, creation_date, age, color, type, character, coord_id, head_id) " +
                               "VALUES (nextval('dragon_id_seq'), ?, ?, ?, ?::color_enum, ?::dragon_type_enum, ?::dragon_character_enum, ?, ?) RETURNING id";
            PreparedStatement dPreparedStatement = createPreparedStatement(dragonSql);
            dPreparedStatement.setString(1, dragon.getName());
            dPreparedStatement.setDate(2, Date.valueOf(dragon.getCreationDate()));
            dPreparedStatement.setLong(3, dragon.getAge());
            dPreparedStatement.setString(4, dragon.getColor().name());
            dPreparedStatement.setString(5, dragon.getType().name());
            dPreparedStatement.setString(6, dragon.getCharacter().name());
            dPreparedStatement.setInt(7, coordId);
            dPreparedStatement.setInt(8, headId);
            ResultSet dSet = dPreparedStatement.executeQuery();
            if (dSet.next()){
                int dragonId = dSet.getInt("id");
                int user_id = createOwner(dragonId, authKey);
                return new Pair<Integer, Integer>(dragonId, user_id);
            }
            return new Pair<Integer,Integer>(-1, -1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return new Pair<Integer,Integer>(-1, -1);
        }
        
    }    
}
