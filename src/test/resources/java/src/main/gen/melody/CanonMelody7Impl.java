package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody7Impl implements melody.intermediates.CanonMelody7 {

  melody.CanonMelodyAction action;

  CanonMelody7Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody8 a() {
    this.action.state7$a();
    return new melody.CanonMelody8Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody8 cSharp() {
    this.action.state7$cSharp();
    return new melody.CanonMelody8Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody8 e() {
    this.action.state7$e();
    return new melody.CanonMelody8Impl(this.action);
  }
}
