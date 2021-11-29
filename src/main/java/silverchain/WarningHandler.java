package silverchain;

import java.util.function.Consumer;

@FunctionalInterface
public interface WarningHandler extends Consumer<Warning> {}
