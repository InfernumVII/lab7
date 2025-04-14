package server.psql.auth;

public class Key {
    private int id;
    private String key;

    public Key(int id, String key){
        this.id = id;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
    
    @Override
    public String toString(){
        return String.format("Key{id=%d, key=%s}", id, key);
    }
    
}
