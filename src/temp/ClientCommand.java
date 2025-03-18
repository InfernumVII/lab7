package temp;

import java.io.Serializable;

public record ClientCommand(String command, Object args) implements Serializable { }