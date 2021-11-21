package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody10Impl implements melody.intermediates.CanonMelody10 {

  melody.CanonMelodyAction action;

  CanonMelody10Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody11 b() {
    this.action.state10$b();
    return new melody.CanonMelody11Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody11 d() {
    this.action.state10$d();
    return new melody.CanonMelody11Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody11 fSharp() {
    this.action.state10$fSharp();
    return new melody.CanonMelody11Impl(this.action);
  }
}
