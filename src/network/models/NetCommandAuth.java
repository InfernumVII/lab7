package network.models;

import java.io.Serializable;

import collection.User;

public record NetCommandAuth(String command, Object arg, User user) implements Serializable { }