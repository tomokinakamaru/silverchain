package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody9Impl implements melody.intermediates.CanonMelody9 {

  melody.CanonMelodyAction action;

  CanonMelody9Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody10 b() {
    this.action.state9$b();
    return new melody.CanonMelody10Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody10 d() {
    this.action.state9$d();
    return new melody.CanonMelody10Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody10 fSharp() {
    this.action.state9$fSharp();
    return new melody.CanonMelody10Impl(this.action);
  }
}
