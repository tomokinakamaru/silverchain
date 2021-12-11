package silverchain.internal.translator.graph;

import java.util.function.BiFunction;
import silverchain.internal.middle.graph.ir.attribute.TypeDeclaration;
import silverchain.internal.middle.graph.ir.graph.Node;

@FunctionalInterface
public interface PkgNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
