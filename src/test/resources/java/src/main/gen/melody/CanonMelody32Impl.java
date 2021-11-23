package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody32Impl implements melody.intermediates.CanonMelody32 {

  melody.CanonMelodyAction action;

  CanonMelody32Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public void play(int n) {
    this.action.state32$play(n);
  }
}
