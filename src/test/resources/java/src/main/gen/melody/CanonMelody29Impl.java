package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody29Impl implements melody.intermediates.CanonMelody29 {

  melody.CanonMelodyAction action;

  CanonMelody29Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody30 a() {
    this.action.state29$a();
    return new melody.CanonMelody30Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody30 cSharp() {
    this.action.state29$cSharp();
    return new melody.CanonMelody30Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody30 e() {
    this.action.state29$e();
    return new melody.CanonMelody30Impl(this.action);
  }
}
