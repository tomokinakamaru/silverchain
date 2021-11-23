package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody3Impl implements melody.intermediates.CanonMelody3 {

  melody.CanonMelodyAction action;

  CanonMelody3Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody4 a() {
    this.action.state3$a();
    return new melody.CanonMelody4Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody4 d() {
    this.action.state3$d();
    return new melody.CanonMelody4Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody4 fSharp() {
    this.action.state3$fSharp();
    return new melody.CanonMelody4Impl(this.action);
  }
}
