package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody28 implements melody.state28.CanonMelody {

  melody.ICanonMelodyAction action;

  CanonMelody28(melody.ICanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.state29.CanonMelody a() {
    this.action.state28$a();
    return new melody.CanonMelody29(this.action);
  }

  @Override
  public melody.state29.CanonMelody cSharp() {
    this.action.state28$cSharp();
    return new melody.CanonMelody29(this.action);
  }

  @Override
  public melody.state29.CanonMelody e() {
    this.action.state28$e();
    return new melody.CanonMelody29(this.action);
  }
}
