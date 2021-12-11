package silverchain.internal.utility;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TreeWalker {

  public static void walk(ParseTreeListener listener, ParseTree tree) {
    ParseTreeWalker.DEFAULT.walk(listener, tree);
  }
}
