package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog2Impl implements alertdialog.intermediates.AlertDialog2 {

  alertdialog.AlertDialogAction action;

  AlertDialog2Impl(alertdialog.AlertDialogAction action) {
    this.action = action;
  }

  @Override
  public void show() {
    this.action.state2$show();
  }
}
