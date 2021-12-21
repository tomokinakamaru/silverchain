package utility;

import java.util.function.Function;
import org.antlr.v4.runtime.ParserRuleContext;
import silverchain.ag.builder.AgParser;

@FunctionalInterface
public interface ParserSelector extends Function<AgParser, ParserRuleContext> {}
