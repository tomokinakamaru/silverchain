package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody30 implements melody.state30.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody30(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state31.CanonMelody a() {
    this.action.state30$a();
    return new melody.CanonMelody31(this.action);
  }

  @Override
  public melody.state31.CanonMelody cSharp() {
    this.action.state30$cSharp();
    return new melody.CanonMelody31(this.action);
  }

  @Override
  public melody.state31.CanonMelody e() {
    this.action.state30$e();
    return new melody.CanonMelody31(this.action);
  }
}
