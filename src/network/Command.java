package network;

import java.io.Serializable;

public record Command(String command, Object arg) implements Serializable { }