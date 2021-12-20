package silverchain.process.java.builder;

import java.util.function.BiFunction;
import silverchain.data.graph.Node;
import silverchain.data.graph.attribute.TypeDeclaration;

@FunctionalInterface
public interface PkgNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
