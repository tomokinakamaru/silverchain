package utility;

import java.io.File;
import java.nio.file.Path;

public final class FileDeleter {

  public static void delete(Path path) {
    if (!delete(path.toFile())) {
      throw new RuntimeException("Failed to clean " + path.toString());
    }
  }

  private static boolean delete(File file) {
    if (!file.exists()) {
      return true;
    }
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        delete(f);
      }
    }
    return file.delete();
  }
}
