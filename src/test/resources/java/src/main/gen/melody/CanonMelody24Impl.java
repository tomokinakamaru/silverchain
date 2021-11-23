package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody24Impl implements melody.intermediates.CanonMelody24 {

  melody.CanonMelodyAction action;

  CanonMelody24Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody25 b() {
    this.action.state24$b();
    return new melody.CanonMelody25Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody25 d() {
    this.action.state24$d();
    return new melody.CanonMelody25Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody25 g() {
    this.action.state24$g();
    return new melody.CanonMelody25Impl(this.action);
  }
}
