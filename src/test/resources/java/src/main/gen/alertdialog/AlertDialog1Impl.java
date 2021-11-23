package alertdialog;

@SuppressWarnings({"rawtypes", "unchecked"})
class AlertDialog1Impl implements alertdialog.intermediates.AlertDialog1 {

  alertdialog.AlertDialogAction action;

  AlertDialog1Impl(alertdialog.AlertDialogAction action) {
    this.action = action;
  }

  @Override
  public alertdialog.intermediates.AlertDialog2 setMessage(String message) {
    this.action.state1$setMessage(message);
    return new alertdialog.AlertDialog2Impl(this.action);
  }
}
