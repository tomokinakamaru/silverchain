package silverchain.warning;

import java.util.function.Consumer;

@FunctionalInterface
public interface WarningHandler extends Consumer<Warning> {}
