import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostfix {

  private char[] digits = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
  private char[] operators = {'+', '-', '*', '/', '(', ')', '^'};

  InfixToPostfix() {
  }

  public ArrayList<Element> toPostfix(String equation) {
    ArrayList<Element> smashedEq = changeEquationToArrayList(equation);
    ArrayList<Element> infixEq = new ArrayList<>();
    Stack<Element> operators = new Stack<>();

    for (int i = 0; i < smashedEq.size(); i++) {

      if (smashedEq.get(i).getTyp() == Types.NUMBER) {
        infixEq.add(smashedEq.get(i));
      } else {

        if (smashedEq.get(i).getOperator().equals("(")) {

          operators.push(smashedEq.get(i));

        } else if (smashedEq.get(i).getOperator().equals(")")) {

          while (!operators.peek().getOperator().equals("(")) {
            infixEq.add(operators.pop());
          }

          operators.pop();

          while (operators.empty() != true) {
            infixEq.add(operators.pop());
          }

        } else if (operators.empty()) {

          operators.push(smashedEq.get(i));

        } else if (operators.peek().getPriority() < smashedEq.get(i).getPriority()) {

          operators.push(smashedEq.get(i));

        } else if (operators.peek().getPriority() >= smashedEq.get(i).getPriority()) {

          while (!operators.empty()) {

            if (operators.peek().getPriority() >= smashedEq.get(i).getPriority())
              infixEq.add(operators.pop());
            else
              break;

          }

          operators.push(smashedEq.get(i));

        }

      }

    }

    while (!operators.empty()) {
      infixEq.add(operators.pop());
    }

    return infixEq;
  }

  private ArrayList<Element> changeEquationToArrayList(String equation) {
    ArrayList<Element> smashedEq = new ArrayList<Element>();
    String tmp = "";

    for (int i = 0; i < equation.length(); i++) {

      for (int j = 0; j < digits.length; j++) {

        if (equation.charAt(i) == digits[j]) {
          tmp = tmp + equation.charAt(i);

          break;
        }

      }

      for (int j = 0; j < operators.length; j++) {

        if (equation.charAt(i) == operators[j]) {

          if (tmp.length() > 0) {
            smashedEq.add(new Element(Double.parseDouble(tmp), Types.NUMBER));
          }

          tmp = "" + equation.charAt(i);

          switch (tmp) {
            case "+":

              smashedEq.add(new Element(tmp, Types.ADDITION));

              break;
            case "-":

              smashedEq.add(new Element(tmp, Types.SUBTRACTION));

              break;
            case "*":

              smashedEq.add(new Element(tmp, Types.MULTIPLICATION));

              break;
            case "/":

              smashedEq.add(new Element(tmp, Types.DIVISION));

              break;
            case "(":

              smashedEq.add(new Element(tmp, Types.PARENTHESIS_LEFT));

              break;
            case ")":

              smashedEq.add(new Element(tmp, Types.PARENTHESIS_RIGHT));

              break;

            case "^":

              smashedEq.add(new Element(tmp, Types.POWER));

              break;

          }

          tmp = "";
        }

      }

    }
    //If tmp is length more tham 0, then tmp is number, because only when it's iterating through operators array there can be add a number.
    if (tmp.length() > 0)
      smashedEq.add(new Element(Double.parseDouble(tmp), Types.NUMBER));

    return smashedEq;
  }

  /*
  private int isUnrecognizableChars()
  {
    return 0;
  }
  */

}
