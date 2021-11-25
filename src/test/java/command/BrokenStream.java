package command;

import java.io.ByteArrayInputStream;
import java.io.IOException;

final class BrokenStream extends ByteArrayInputStream {

  public BrokenStream(String text) {
    super(text.getBytes());
  }

  @Override
  public void close() throws IOException {
    throw new IOException("-");
  }
}
