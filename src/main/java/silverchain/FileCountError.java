package silverchain;

public final class FileCountError extends SilverchainException {

  private static final String FORMAT =
      "File limit (%d) exceeded - the AG file results in %d generated files.\n"
          + "If this is intentional, use the --max-file-count to increase the limit.";

  FileCountError(int maxCount, int count) {
    super(FORMAT, maxCount, count);
  }
}
