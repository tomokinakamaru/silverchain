package parser;

import silverchain.parser.ParseException;

@FunctionalInterface
interface RuleSelector<T, S> {
  S apply(T arg) throws ParseException;
}
