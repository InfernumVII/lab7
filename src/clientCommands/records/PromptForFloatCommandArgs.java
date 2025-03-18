package clientCommands.records;

import java.io.Serializable;

public record PromptForFloatCommandArgs(String prompt, boolean allowNull, Float min, Float max) implements Serializable { }