package silverchain.internal.middle.java.parser;

import java.util.function.BiFunction;
import silverchain.internal.middle.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middle.graph.data.graph.Node;

@FunctionalInterface
public interface TypeNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
