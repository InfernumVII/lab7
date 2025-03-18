package clientCommands.records;

import java.io.Serializable;

public record PromptForLongCommandArgs(String prompt, boolean allowNull, long min, long max) implements Serializable { }