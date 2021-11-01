package silverchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public final class SilverchainProperties {
  private static final Properties PROPERTIES = load();
  public static final String VERSION = getProperty("version");

  private static Properties load() {
    try (InputStream propertiesStream = getPropertiesStream();
        InputStreamReader reader = new InputStreamReader(propertiesStream)) {
      Properties properties = new Properties();
      properties.load(reader);
      return properties;
    } catch (IOException e) {
      throw new IllegalStateException("Cannot read properties file from Silverchain JAR", e);
    }
  }

  private static InputStream getPropertiesStream() {
    InputStream result = SilverchainProperties.class.getResourceAsStream("/silverchain.properties");
    if (result == null) {
      throw new IllegalStateException("Properties file missing from Silverchain JAR");
    }
    return result;
  }

  private static String getProperty(String key) {
    String result = PROPERTIES.getProperty(key);
    if (result == null) {
      throw new IllegalStateException("Missing property. Invalid Silverchain JAR");
    }
    return result;
  }

  private SilverchainProperties() {}
}
