package command;

import java.io.ByteArrayInputStream;
import java.io.IOException;

final class BrokenInputStream extends ByteArrayInputStream {

  public BrokenInputStream(String text) {
    super(text.getBytes());
  }

  @Override
  public void close() throws IOException {
    throw new IOException("-");
  }
}
