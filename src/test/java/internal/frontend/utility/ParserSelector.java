package internal.frontend.utility;

import java.util.function.Function;
import org.antlr.v4.runtime.ParserRuleContext;
import silverchain.internal.frontend.builder.AntlrParser;

@FunctionalInterface
public interface ParserSelector extends Function<AntlrParser, ParserRuleContext> {}
