import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Stack;

public class RPNCalc {
  //A value entered by a user.
  //
  private ArrayList<Element> infixEq = new ArrayList<>();
  private InfixToPostfix infixToPostfix = new InfixToPostfix();


  RPNCalc() {
  }


  public String solve(String equation) {

    infixEq = infixToPostfix.toPostfix(equation);

    Stack<Element> numbers = new Stack<>();

    double numb = 0.0;
    BigDecimal bigD1, bigD2;

    for (int i = 0; i < infixEq.size(); i++) {

      if (infixEq.get(i).getTyp() == Types.NUMBER) {
        numbers.push(infixEq.get(i));
      } else {
        switch (infixEq.get(i).getTyp()) {

          case ADDITION:

            bigD1 = new BigDecimal(numbers.pop().getNumber());
            bigD2 = new BigDecimal(numbers.pop().getNumber());

            numb = bigD2.add(bigD1, MathContext.DECIMAL32).doubleValue();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case SUBTRACTION:

            bigD1 = new BigDecimal(numbers.pop().getNumber());
            bigD2 = new BigDecimal(numbers.pop().getNumber());

            numb = bigD2.subtract(bigD1, MathContext.DECIMAL32).doubleValue();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case MULTIPLICATION:

            bigD1 = new BigDecimal(numbers.pop().getNumber());
            bigD2 = new BigDecimal(numbers.pop().getNumber());

            numb = bigD1.multiply(bigD2, MathContext.DECIMAL32).doubleValue();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case DIVISION:

            bigD1 = new BigDecimal(numbers.pop().getNumber());
            bigD2 = new BigDecimal(numbers.pop().getNumber());

            numb = bigD2.divide(bigD1, MathContext.DECIMAL32).doubleValue();

            numbers.push(new Element(numb, Types.NUMBER));

            break;

          case POWER:

            bigD1 = new BigDecimal(numbers.pop().getNumber());
            bigD2 = new BigDecimal(numbers.pop().getNumber());


            numb = Math.pow(bigD2.doubleValue(), bigD1.doubleValue());

            numbers.push(new Element(numb, Types.NUMBER));

            break;

        }
      }
    }

    return Double.toString(numb);
  }

}


