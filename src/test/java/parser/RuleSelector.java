package parser;

import silverchain.parser.ASTNode;
import silverchain.parser.ParseException;

@FunctionalInterface
interface RuleSelector<T, S extends ASTNode> {
  S apply(T arg) throws ParseException;
}
