public class Element {

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

  private int setPriority(Types typ) {
    int priority = 0;

    switch (typ) {
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

    return priority;

  }
}
