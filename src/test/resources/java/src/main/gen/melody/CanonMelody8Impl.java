package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody8Impl implements melody.intermediates.CanonMelody8 {

  melody.CanonMelodyAction action;

  CanonMelody8Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody9 b() {
    this.action.state8$b();
    return new melody.CanonMelody9Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody9 d() {
    this.action.state8$d();
    return new melody.CanonMelody9Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody9 fSharp() {
    this.action.state8$fSharp();
    return new melody.CanonMelody9Impl(this.action);
  }
}
