package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody6Impl implements melody.intermediates.CanonMelody6 {

  melody.CanonMelodyAction action;

  CanonMelody6Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody7 a() {
    this.action.state6$a();
    return new melody.CanonMelody7Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody7 cSharp() {
    this.action.state6$cSharp();
    return new melody.CanonMelody7Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody7 e() {
    this.action.state6$e();
    return new melody.CanonMelody7Impl(this.action);
  }
}
