package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody6 implements melody.state6.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody6(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state7.CanonMelody a() {
    this.action.state6$a();
    return new melody.CanonMelody7(this.action);
  }

  @Override
  public melody.state7.CanonMelody cSharp() {
    this.action.state6$cSharp();
    return new melody.CanonMelody7(this.action);
  }

  @Override
  public melody.state7.CanonMelody e() {
    this.action.state6$e();
    return new melody.CanonMelody7(this.action);
  }
}
