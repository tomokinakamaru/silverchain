package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody7 implements melody.state7.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody7(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state8.CanonMelody a() {
    this.action.state7$a();
    return new melody.CanonMelody8(this.action);
  }

  @Override
  public melody.state8.CanonMelody cSharp() {
    this.action.state7$cSharp();
    return new melody.CanonMelody8(this.action);
  }

  @Override
  public melody.state8.CanonMelody e() {
    this.action.state7$e();
    return new melody.CanonMelody8(this.action);
  }
}
