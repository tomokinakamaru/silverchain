package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody29 implements melody.state29.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody29(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state30.CanonMelody a() {
    this.action.state29$a();
    return new melody.CanonMelody30(this.action);
  }

  @Override
  public melody.state30.CanonMelody cSharp() {
    this.action.state29$cSharp();
    return new melody.CanonMelody30(this.action);
  }

  @Override
  public melody.state30.CanonMelody e() {
    this.action.state29$e();
    return new melody.CanonMelody30(this.action);
  }
}
