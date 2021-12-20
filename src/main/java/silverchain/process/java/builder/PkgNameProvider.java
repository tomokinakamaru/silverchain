package silverchain.process.java.builder;

import java.util.function.BiFunction;
import org.apiguardian.api.API;
import silverchain.data.graph.Node;
import silverchain.data.graph.attribute.TypeDeclaration;

@API(status = API.Status.INTERNAL)
@FunctionalInterface
public interface PkgNameProvider extends BiFunction<TypeDeclaration, Node, String> {}
