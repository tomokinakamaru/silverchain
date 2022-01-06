package silverchain.ag.data;

import java.util.Set;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface SetTree<T extends Tree> extends Set<T>, Tree {}
