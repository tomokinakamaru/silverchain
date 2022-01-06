package silverchain.graph.core;

import java.util.Set;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface Attrs<T extends Attr> extends Attr, Set<T> {}
