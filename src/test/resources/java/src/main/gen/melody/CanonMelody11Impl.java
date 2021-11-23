package melody;

@SuppressWarnings({"rawtypes", "unchecked"})
class CanonMelody11Impl implements melody.intermediates.CanonMelody11 {

  melody.CanonMelodyAction action;

  CanonMelody11Impl(melody.CanonMelodyAction action) {
    this.action = action;
  }

  @Override
  public melody.intermediates.CanonMelody12 b() {
    this.action.state11$b();
    return new melody.CanonMelody12Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody12 d() {
    this.action.state11$d();
    return new melody.CanonMelody12Impl(this.action);
  }

  @Override
  public melody.intermediates.CanonMelody12 fSharp() {
    this.action.state11$fSharp();
    return new melody.CanonMelody12Impl(this.action);
  }
}
