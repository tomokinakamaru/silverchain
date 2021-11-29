package silverchain;

import java.util.function.Function;
import silverchain.diagram.Diagrams;

@FunctionalInterface
public interface ValidatorProvider extends Function<Diagrams, Validator> {}
