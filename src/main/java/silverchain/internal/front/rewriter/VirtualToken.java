package silverchain.internal.front.rewriter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

public class VirtualToken implements Token {

  private Token token;

  private Token subToken;

  public Token token() {
    return token;
  }

  public void token(Token token) {
    this.token = token;
  }

  public Token subToken() {
    return subToken;
  }

  public void subToken(Token subToken) {
    this.subToken = subToken;
  }

  @Override
  public String getText() {
    return token.getText();
  }

  @Override
  public int getType() {
    return token.getType();
  }

  @Override
  public int getLine() {
    return token.getLine();
  }

  @Override
  public int getCharPositionInLine() {
    return token.getCharPositionInLine();
  }

  @Override
  public int getChannel() {
    return token.getChannel();
  }

  @Override
  public int getTokenIndex() {
    return token.getTokenIndex();
  }

  @Override
  public int getStartIndex() {
    return token.getStartIndex();
  }

  @Override
  public int getStopIndex() {
    return token.getStopIndex();
  }

  @Override
  public TokenSource getTokenSource() {
    return token.getTokenSource();
  }

  @Override
  public CharStream getInputStream() {
    return token.getInputStream();
  }
}
