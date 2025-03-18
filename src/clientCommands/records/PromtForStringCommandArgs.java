package clientCommands.records;

import java.io.Serializable;

public record PromtForStringCommandArgs(String prompt, boolean allowNull) implements Serializable { }