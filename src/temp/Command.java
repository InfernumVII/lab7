package temp;

import java.io.Serializable;

public record Command(String command, Object arg) implements Serializable { }