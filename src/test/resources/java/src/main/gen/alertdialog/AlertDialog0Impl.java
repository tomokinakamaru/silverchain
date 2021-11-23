package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog0Impl implements alertdialog.intermediates.AlertDialog0 {

  alertdialog.AlertDialogAction action;

  AlertDialog0Impl(alertdialog.AlertDialogAction action) {
    this.action = action;
  }

  @Override
  public alertdialog.intermediates.AlertDialog1 setTitle(String title) {
    this.action.state0$setTitle(title);
    return new alertdialog.AlertDialog1Impl(this.action);
  }
}
