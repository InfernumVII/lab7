package temp;
import java.io.Serializable;



public record Answer(String answer, ClientCommand command) implements Serializable { }