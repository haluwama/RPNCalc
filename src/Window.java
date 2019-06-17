import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

  private JTextField textFieldEq;
  private JPanel panelNumbers;
  private RPNCalc rpnCalc;
  private History history;
  private JFrame main;
  private static int i = 0;

  Window() {
    main = this;
    rpnCalc = new RPNCalc();
    history = new History();

    //basic settings of window
    setSize(350, 250);
    setTitle("RPNCalc");
    setResizable(false);

    textFieldEq = new JTextField("222*(5+2)+3-2");
    panelNumbers = new JPanel();

    InsertAction insert = new InsertAction();
    SolveAction solve = new SolveAction();
    DelAction del = new DelAction();
    ACAction ac = new ACAction();
    ANSAction ans = new ANSAction();

    panelNumbers.setLayout(new GridLayout(4, 5));

    addButton("7", insert);
    addButton("8", insert);
    addButton("9", insert);
    addButton("DEL", del);
    addButton("AC", ac);

    addButton("4", insert);
    addButton("5", insert);
    addButton("6", insert);
    addButton("*", insert);
    addButton("/", insert);

    addButton("1", insert);
    addButton("2", insert);
    addButton("3", insert);
    addButton("+", insert);
    addButton("-", insert);

    addButton("0", insert);
    addButton(".", insert);
    addButton("*10^", insert);
    addButton("Ans", ans);
    addButton("=", solve);

    add(textFieldEq, BorderLayout.CENTER);
    add(panelNumbers, BorderLayout.SOUTH);

  }

  private void addButton(String label, ActionListener listener) {
    JButton button = new JButton(label);
    button.addActionListener(listener);
    panelNumbers.add(button);
  }

  private class InsertAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      String input = e.getActionCommand();
      textFieldEq.setText(textFieldEq.getText() + input);

    }
  }

  private class SolveAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      String result = rpnCalc.solve(textFieldEq.getText());

      history.addItem(textFieldEq.getText(), result);

      textFieldEq.setText(result);

    }
  }

  private class DelAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      textFieldEq.setText(textFieldEq.getText().substring(0, textFieldEq.getText().length() - 1));
    }
  }

  private class ACAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      textFieldEq.setText("");
    }
  }

  private class ANSAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

      int max = history.getLength();

      JDialog dialog = new JDialog(main, "History");

      JPanel panelFields = new JPanel();

      JTextField textField = new JTextField();
      JTextField textFieldResult = new JTextField();

      panelFields.add(textField);
      panelFields.add(textFieldResult);
      panelFields.setLayout(new GridLayout(1,2));

      dialog.add(panelFields, BorderLayout.CENTER);

      JPanel panel = new JPanel();

      JButton leftArrow = new JButton("<-");
      JButton insert = new JButton("Insert");
      JButton rightArrow = new JButton("->");

      panel.setLayout(new GridLayout(1,3));

      panel.add(leftArrow);
      panel.add(insert);
      panel.add(rightArrow);

      textField.setText(history.getItem(i).get(0));
      textFieldResult.setText(history.getItem(i).get(1));

      leftArrow.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

          if(i > 0) {
            i = i - 1;
          }

          textField.setText(history.getItem(i).get(0));
          textFieldResult.setText(history.getItem(i).get(1));

        }
      });

      insert.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

          textFieldEq.setText(textFieldEq.getText() + textFieldResult.getText());

        }
      });

      rightArrow.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

          if(i < max) {
            i = i + 1;
          }

          textField.setText(history.getItem(i).get(0));
          textFieldResult.setText(history.getItem(i).get(1));

        }
      });

      dialog.add(panel, BorderLayout.SOUTH);

      dialog.setSize(250, 100);
      dialog.setVisible(true);

    }
  }
}
