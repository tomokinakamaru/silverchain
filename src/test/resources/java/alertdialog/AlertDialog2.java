package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog2 implements alertdialog.state2.AlertDialog {

  alertdialog.IAlertDialogAction action;

  AlertDialog2(alertdialog.IAlertDialogAction action) {
    this.action = action;
  }

  @Override
  public void show() {
    this.action.state2$show();
  }
}
