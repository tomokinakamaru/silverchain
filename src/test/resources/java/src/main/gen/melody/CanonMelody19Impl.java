package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody19Impl implements melody.intermediates.CanonMelody19 {

  melody.CanonMelodyAction action;

  CanonMelody19Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody20 b() {
    this.action.state19$b();
    return new melody.CanonMelody20Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody20 d() {
    this.action.state19$d();
    return new melody.CanonMelody20Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody20 g() {
    this.action.state19$g();
    return new melody.CanonMelody20Impl(this.action);
  }
}
