package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody25Impl implements melody.intermediates.CanonMelody25 {

  melody.CanonMelodyAction action;

  CanonMelody25Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody26 b() {
    this.action.state25$b();
    return new melody.CanonMelody26Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody26 d() {
    this.action.state25$d();
    return new melody.CanonMelody26Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody26 g() {
    this.action.state25$g();
    return new melody.CanonMelody26Impl(this.action);
  }
}
