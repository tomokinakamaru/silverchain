package silverchain.internal.middleware.java.builder;

import java.util.function.BiFunction;
import silverchain.internal.middleware.graph.data.attribute.TypeDeclaration;
import silverchain.internal.middleware.graph.data.graph.Node;

@FunctionalInterface
public interface PkgNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
