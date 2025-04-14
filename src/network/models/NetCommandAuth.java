package network.models;

import java.io.Serializable;

public record NetCommandAuth(String command, Object arg, String authKey) implements Serializable { }