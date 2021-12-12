package silverchain.internal.middle.graph.compiler;

import java.util.function.BiFunction;
import silverchain.internal.middle.data.attribute.TypeDeclaration;
import silverchain.internal.middle.data.graph.Node;

@FunctionalInterface
public interface TypeNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
