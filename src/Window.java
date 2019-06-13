import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

  private JButton buttonConfirm;
  private JTextField textFieldEq;
  private JLabel description;
  private JLabel result;
  private GridLayout gridLayout;
  private JPanel panel;

  Window() {
    //basic settings of window
    setSize(300, 200);
    setTitle("RPNCalc");
    setResizable(false);

    panel = new JPanel();
    gridLayout = new GridLayout(4,1);

    //Elements of JPanel
    description = new JLabel("Equation:");
    textFieldEq = new JTextField("222*(5+2)+3-2");
    result = new JLabel("Your result is: ...");
    buttonConfirm = new JButton("Solve");

    //set layot
    panel.setLayout(gridLayout);

    panel.add(description);
    panel.add(textFieldEq);
    panel.add(result);
    panel.add(buttonConfirm);

    buttonConfirm.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        RPNCalc simpleCalc = new RPNCalc(textFieldEq.getText());
        result.setText("Your result is: " + simpleCalc.solve());

      }
    });

    add(panel, BorderLayout.CENTER);

  }

}
