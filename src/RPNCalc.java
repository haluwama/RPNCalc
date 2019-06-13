import java.util.ArrayList;
import java.util.Stack;

public class RPNCalc {

  //Enum stores the names of mathematical operations.
  private enum Types {
    NUMBER,
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    POWER,
    PARENTHESIS_LEFT,
    PARENTHESIS_RIGHT
  }

  //numbs[]
  private char numbs[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'};
  //Chars of math operations
  private char chars[] = {'+', '-', '*', '(', ')', '^', '/'};

  //A value entered by a user.
  private String equation;
  //
  private ArrayList<Element> smashedEq = new ArrayList<>();
  private ArrayList<Element> infixEq = new ArrayList<>();


  RPNCalc(String eq) {
    equation = eq;
  }


  public String solve() {

    smashedEq();
    infixToOnp();

    Stack<Element> numbers = new Stack<>();

    double numb = 0.0, tmp1 = 0.0, tmp2 = 0.0;

    for(int i = 0; i < infixEq.size(); i++) {

      if(infixEq.get(i).typ == Types.NUMBER) {
        numbers.push(infixEq.get(i));
      } else {
        switch(infixEq.get(i).getTyp()) {

          case ADDITION:

            numb = numbers.pop().getNumber() + numbers.pop().getNumber();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case SUBTRACTION:

            tmp1 = numbers.pop().getNumber();
            tmp2 = numbers.pop().getNumber();

            numb =  tmp2 - tmp1;

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case MULTIPLICATION:

            numb =  numbers.pop().getNumber() * numbers.pop().getNumber();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case DIVISION:

            tmp1 = numbers.pop().getNumber();
            tmp2 = numbers.pop().getNumber();

            numb =  tmp2 / tmp1;

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case POWER:

            tmp1 = numbers.pop().getNumber();
            tmp2 = numbers.pop().getNumber();

            numb =  Math.pow(tmp2, tmp1);

            numbers.push(new Element(numb, Types.NUMBER));

            break;

        }
      }
    }

    return Double.toString(numb);
  }

  private void smashedEq() {

    String tmp = "";

    for(int i = 0; i < equation.length(); i++) {

      for(int j = 0; j < numbs.length; j++) {

        if(equation.charAt(i) == numbs[j]) {
          tmp = tmp + equation.charAt(i);

          break;
        }

      }

      for(int j = 0; j < chars.length; j++) {

        if(equation.charAt(i) == chars[j]) {

          if(tmp.length() > 0) {
            smashedEq.add(new Element(Double.parseDouble(tmp), Types.NUMBER));
          }

          tmp = "" + equation.charAt(i);

          switch(tmp) {
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
    //If tmp is length more tham 0, then tmp is number, because only when it's iterating through chars array there can be add a number.
    if(tmp.length() > 0)
      smashedEq.add(new Element(Double.parseDouble(tmp), Types.NUMBER));

  }

  private void infixToOnp() {

    Stack<Element> operators = new Stack<>();

    for (int i = 0; i < smashedEq.size(); i++) {

      if (smashedEq.get(i).getTyp() == Types.NUMBER) {
        infixEq.add(smashedEq.get(i));
      } else {

        if( smashedEq.get(i).getOperator().equals("(") ) {

          operators.push(smashedEq.get(i));

        } else if( smashedEq.get(i).getOperator().equals(")") ) {

          while(!operators.peek().getOperator().equals("(")) {
            infixEq.add(operators.pop());
          }

          operators.pop();

          while (operators.empty() != true) {
            infixEq.add(operators.pop());
          }

        } else if( operators.empty() ) {

          operators.push(smashedEq.get(i));

        } else if(operators.peek().getPriority() <  smashedEq.get(i).getPriority()) {

          operators.push(smashedEq.get(i));

        } else if(operators.peek().getPriority() >=  smashedEq.get(i).getPriority()){

          while(!operators.empty()) {

            if(operators.peek().getPriority() >= smashedEq.get(i).getPriority())
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

  }

  //This class
  private class Element {

    private double number;
    private String operator;
    private Types typ;
    private int priority;

    Element(String operator, Types typ) {
      this.operator = operator;
      this.typ = typ;
      this.number = Double.MIN_VALUE;

      this.priority = setPriority(typ);

    }

    Element(double number, Types typ) {
      this.operator = "";
      this.typ = typ;
      this.number = number;

      this.priority = setPriority(typ);
    }

    //setters/getters

    private int setPriority(Types typ) {
      int priority = 0;

      switch(typ) {
        case NUMBER:

          priority = -1;
          break;

        case PARENTHESIS_LEFT:

          priority = 0;
          break;

        case PARENTHESIS_RIGHT:

          priority = 1;
          break;
        case ADDITION:

          priority = 1;
          break;

        case SUBTRACTION:

          priority = 1;
          break;

        case MULTIPLICATION:

          priority = 2;
          break;
        case DIVISION:

          priority = 2;
          break;

        case POWER:

          priority = 3;
          break;
      }

      return  priority;

    }

    public double getNumber() {
      return number;
    }

    public String getOperator() {
      return operator;
    }

    public Types getTyp() {
      return typ;
    }

    public int getPriority() {
      return priority;
    }
  }

}


