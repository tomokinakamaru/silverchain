package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody22Impl implements melody.intermediates.CanonMelody22 {

  melody.CanonMelodyAction action;

  CanonMelody22Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody23 a() {
    this.action.state22$a();
    return new melody.CanonMelody23Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody23 d() {
    this.action.state22$d();
    return new melody.CanonMelody23Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody23 fSharp() {
    this.action.state22$fSharp();
    return new melody.CanonMelody23Impl(this.action);
  }
}
