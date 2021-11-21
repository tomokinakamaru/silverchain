package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody1Impl implements melody.intermediates.CanonMelody1 {

  melody.CanonMelodyAction action;

  CanonMelody1Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody2 a() {
    this.action.state1$a();
    return new melody.CanonMelody2Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody2 d() {
    this.action.state1$d();
    return new melody.CanonMelody2Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody2 fSharp() {
    this.action.state1$fSharp();
    return new melody.CanonMelody2Impl(this.action);
  }
}
