package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody4 implements melody.state4.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody4(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state5.CanonMelody a() {
    this.action.state4$a();
    return new melody.CanonMelody5(this.action);
  }

  @Override
  public melody.state5.CanonMelody cSharp() {
    this.action.state4$cSharp();
    return new melody.CanonMelody5(this.action);
  }

  @Override
  public melody.state5.CanonMelody e() {
    this.action.state4$e();
    return new melody.CanonMelody5(this.action);
  }
}
