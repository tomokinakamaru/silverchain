package silverchain.internal.frontend.core;

import java.util.function.BiFunction;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
@FunctionalInterface
public interface TreeConstructor
    extends BiFunction<ParserRuleContext, Integer, ParserRuleContext> {}
