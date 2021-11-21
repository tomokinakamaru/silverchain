package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody28Impl implements melody.intermediates.CanonMelody28 {

  melody.CanonMelodyAction action;

  CanonMelody28Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody29 a() {
    this.action.state28$a();
    return new melody.CanonMelody29Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody29 cSharp() {
    this.action.state28$cSharp();
    return new melody.CanonMelody29Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody29 e() {
    this.action.state28$e();
    return new melody.CanonMelody29Impl(this.action);
  }
}
