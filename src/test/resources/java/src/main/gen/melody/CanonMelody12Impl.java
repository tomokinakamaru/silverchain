package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody12Impl implements melody.intermediates.CanonMelody12 {

  melody.CanonMelodyAction action;

  CanonMelody12Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody13 a() {
    this.action.state12$a();
    return new melody.CanonMelody13Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody13 cSharp() {
    this.action.state12$cSharp();
    return new melody.CanonMelody13Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody13 fSharp() {
    this.action.state12$fSharp();
    return new melody.CanonMelody13Impl(this.action);
  }
}
