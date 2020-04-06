import alertdialog.AlertDialog;
import org.junit.jupiter.api.Test;

final class AlertDialogTest {

  @Test
  void main() {
    new AlertDialog().setTitle("Warning").setMessage("Are you sure?").show();
  }
}
