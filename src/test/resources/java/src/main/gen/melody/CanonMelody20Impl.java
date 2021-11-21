package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody20Impl implements melody.intermediates.CanonMelody20 {

  melody.CanonMelodyAction action;

  CanonMelody20Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody21 a() {
    this.action.state20$a();
    return new melody.CanonMelody21Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody21 d() {
    this.action.state20$d();
    return new melody.CanonMelody21Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody21 fSharp() {
    this.action.state20$fSharp();
    return new melody.CanonMelody21Impl(this.action);
  }
}
