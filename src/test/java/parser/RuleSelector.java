package parser;

import org.antlr.v4.runtime.RecognitionException;
import silverchain.parser.ASTNode;

@FunctionalInterface
interface RuleSelector<T, S extends ASTNode> {
  S apply(T arg) throws RecognitionException;
}
