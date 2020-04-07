import melody.CanonMelody;
import org.junit.jupiter.api.Test;

final class MelodyTest {

  @Test
  void testCanon() {
    new CanonMelody()
        .a()
        .d()
        .fSharp()
        .d()
        .cSharp()
        .a()
        .e()
        .a()
        .d()
        .fSharp()
        .b()
        .fSharp()
        .cSharp()
        .a()
        .fSharp()
        .a()
        .d()
        .d()
        .g()
        .d()
        .fSharp()
        .d()
        .a()
        .d()
        .g()
        .d()
        .b()
        .g()
        .cSharp()
        .a()
        .e()
        .a()
        .play(2);
  }
}
