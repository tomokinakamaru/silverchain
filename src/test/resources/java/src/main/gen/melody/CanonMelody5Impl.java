package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody5Impl implements melody.intermediates.CanonMelody5 {

  melody.CanonMelodyAction action;

  CanonMelody5Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody6 a() {
    this.action.state5$a();
    return new melody.CanonMelody6Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody6 cSharp() {
    this.action.state5$cSharp();
    return new melody.CanonMelody6Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody6 e() {
    this.action.state5$e();
    return new melody.CanonMelody6Impl(this.action);
  }
}
