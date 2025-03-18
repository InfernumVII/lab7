package clientCommands.records;

import java.io.Serializable;

public record PromptForEnumCommandArgs(String prompt, Enum[] enums, boolean allowNull) implements Serializable { }