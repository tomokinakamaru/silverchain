package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody31Impl implements melody.intermediates.CanonMelody31 {

  melody.CanonMelodyAction action;

  CanonMelody31Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody32 a() {
    this.action.state31$a();
    return new melody.CanonMelody32Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody32 cSharp() {
    this.action.state31$cSharp();
    return new melody.CanonMelody32Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody32 e() {
    this.action.state31$e();
    return new melody.CanonMelody32Impl(this.action);
  }
}
