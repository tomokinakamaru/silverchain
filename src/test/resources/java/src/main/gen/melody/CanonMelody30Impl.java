package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody30Impl implements melody.intermediates.CanonMelody30 {

  melody.CanonMelodyAction action;

  CanonMelody30Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody31 a() {
    this.action.state30$a();
    return new melody.CanonMelody31Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody31 cSharp() {
    this.action.state30$cSharp();
    return new melody.CanonMelody31Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody31 e() {
    this.action.state30$e();
    return new melody.CanonMelody31Impl(this.action);
  }
}
