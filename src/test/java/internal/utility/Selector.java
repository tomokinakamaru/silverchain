package internal.utility;

import java.util.function.Function;
import org.antlr.v4.runtime.ParserRuleContext;
import silverchain.internal.frontend.parser.antlr.AgParser;

public interface Selector extends Function<AgParser, ParserRuleContext> {}
