import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Main {

  public static void main(String[] args) throws InvocationTargetException, InterruptedException {

    EventQueue.invokeAndWait(new Runnable() {
      @Override
      public void run() {

        Window win = new Window();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

      }
    });
  }
}
