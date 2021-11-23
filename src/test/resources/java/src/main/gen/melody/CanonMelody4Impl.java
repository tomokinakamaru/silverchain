package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody4Impl implements melody.intermediates.CanonMelody4 {

  melody.CanonMelodyAction action;

  CanonMelody4Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody5 a() {
    this.action.state4$a();
    return new melody.CanonMelody5Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody5 cSharp() {
    this.action.state4$cSharp();
    return new melody.CanonMelody5Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody5 e() {
    this.action.state4$e();
    return new melody.CanonMelody5Impl(this.action);
  }
}
