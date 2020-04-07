package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody31 implements melody.state31.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody31(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state32.CanonMelody a() {
    this.action.state31$a();
    return new melody.CanonMelody32(this.action);
  }

  @Override
  public melody.state32.CanonMelody cSharp() {
    this.action.state31$cSharp();
    return new melody.CanonMelody32(this.action);
  }

  @Override
  public melody.state32.CanonMelody e() {
    this.action.state31$e();
    return new melody.CanonMelody32(this.action);
  }
}
