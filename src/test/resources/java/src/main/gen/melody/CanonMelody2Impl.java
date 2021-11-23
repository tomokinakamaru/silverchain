package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody2Impl implements melody.intermediates.CanonMelody2 {

  melody.CanonMelodyAction action;

  CanonMelody2Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody3 a() {
    this.action.state2$a();
    return new melody.CanonMelody3Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody3 d() {
    this.action.state2$d();
    return new melody.CanonMelody3Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody3 fSharp() {
    this.action.state2$fSharp();
    return new melody.CanonMelody3Impl(this.action);
  }
}
