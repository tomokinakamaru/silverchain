package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody0Impl implements melody.intermediates.CanonMelody0 {

  melody.CanonMelodyAction action;

  CanonMelody0Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody1 a() {
    this.action.state0$a();
    return new melody.CanonMelody1Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody1 d() {
    this.action.state0$d();
    return new melody.CanonMelody1Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody1 fSharp() {
    this.action.state0$fSharp();
    return new melody.CanonMelody1Impl(this.action);
  }
}
