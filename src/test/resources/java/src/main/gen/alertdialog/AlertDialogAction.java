package alertdialog;

interface AlertDialogAction {

  default void state0$setTitle(String title) {
    setTitle(title);
  }

  default void state1$setMessage(String message) {
    setMessage(message);
  }

  default void state2$show() {
    show();
  }

  void setTitle(String title);

  void setMessage(String message);

  void show();
}
