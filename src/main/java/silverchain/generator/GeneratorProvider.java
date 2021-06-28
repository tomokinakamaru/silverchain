package silverchain.generator;

import java.util.function.BiFunction;
import silverchain.diagram.Diagrams;
import silverchain.javadoc.Javadocs;

@FunctionalInterface
public interface GeneratorProvider extends BiFunction<Diagrams, Javadocs, Generator> {}
