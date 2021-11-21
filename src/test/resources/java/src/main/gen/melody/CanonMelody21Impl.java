package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody21Impl implements melody.intermediates.CanonMelody21 {

  melody.CanonMelodyAction action;

  CanonMelody21Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody22 a() {
    this.action.state21$a();
    return new melody.CanonMelody22Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody22 d() {
    this.action.state21$d();
    return new melody.CanonMelody22Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody22 fSharp() {
    this.action.state21$fSharp();
    return new melody.CanonMelody22Impl(this.action);
  }
}
