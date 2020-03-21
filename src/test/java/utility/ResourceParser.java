package utility;

import silverchain.grammar.Grammar;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public class ResourceParser extends ResourceReader {

  public ResourceParser(String... names) {
    super(names);
  }

  public Grammar parse(String fileName) {
    try {
      return new Parser(read(fileName)).grammar();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
