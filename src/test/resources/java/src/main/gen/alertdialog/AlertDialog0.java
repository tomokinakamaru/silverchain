package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog0 implements alertdialog.IAlertDialog {

  alertdialog.IAlertDialogAction action;

  AlertDialog0(alertdialog.IAlertDialogAction action) {
    this.action = action;
  }

  @Override
  public alertdialog.state1.AlertDialog setTitle(String title) {
    this.action.state0$setTitle(title);
    return new alertdialog.AlertDialog1(this.action);
  }
}
