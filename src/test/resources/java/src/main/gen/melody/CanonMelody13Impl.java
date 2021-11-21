package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody13Impl implements melody.intermediates.CanonMelody13 {

  melody.CanonMelodyAction action;

  CanonMelody13Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody14 a() {
    this.action.state13$a();
    return new melody.CanonMelody14Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody14 cSharp() {
    this.action.state13$cSharp();
    return new melody.CanonMelody14Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody14 fSharp() {
    this.action.state13$fSharp();
    return new melody.CanonMelody14Impl(this.action);
  }
}
