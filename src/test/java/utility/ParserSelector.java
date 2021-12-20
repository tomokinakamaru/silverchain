package utility;

import java.util.function.Function;
import org.antlr.v4.runtime.ParserRuleContext;
import silverchain.process.ag.builder.AntlrParser;

@FunctionalInterface
public interface ParserSelector extends Function<AntlrParser, ParserRuleContext> {}
