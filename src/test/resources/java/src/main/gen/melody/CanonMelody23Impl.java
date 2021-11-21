package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody23Impl implements melody.intermediates.CanonMelody23 {

  melody.CanonMelodyAction action;

  CanonMelody23Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody24 a() {
    this.action.state23$a();
    return new melody.CanonMelody24Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody24 d() {
    this.action.state23$d();
    return new melody.CanonMelody24Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody24 fSharp() {
    this.action.state23$fSharp();
    return new melody.CanonMelody24Impl(this.action);
  }
}
