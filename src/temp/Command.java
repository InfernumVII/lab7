package temp;

import java.io.Serializable;

public record Command(String command, String argument) implements Serializable { }