package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody5 implements melody.state5.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody5(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state6.CanonMelody a() {
    this.action.state5$a();
    return new melody.CanonMelody6(this.action);
  }

  @Override
  public melody.state6.CanonMelody cSharp() {
    this.action.state5$cSharp();
    return new melody.CanonMelody6(this.action);
  }

  @Override
  public melody.state6.CanonMelody e() {
    this.action.state5$e();
    return new melody.CanonMelody6(this.action);
  }
}
