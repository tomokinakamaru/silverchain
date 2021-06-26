package silverchain.generator;

import java.util.function.Function;
import silverchain.diagram.Diagrams;

@FunctionalInterface
public interface GeneratorProvider extends Function<Diagrams, Generator> {}
