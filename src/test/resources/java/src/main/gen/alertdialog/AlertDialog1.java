package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog1 implements alertdialog.state1.AlertDialog {

  alertdialog.IAlertDialogAction action;

  AlertDialog1(alertdialog.IAlertDialogAction action) {
    this.action = action;
  }

  @Override
  public alertdialog.state2.AlertDialog setMessage(String message) {
    this.action.state1$setMessage(message);
    return new alertdialog.AlertDialog2(this.action);
  }
}
