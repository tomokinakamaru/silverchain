package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody32 implements melody.state32.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody32(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public void play(int n) {
    this.action.state32$play(n);
  }
}
