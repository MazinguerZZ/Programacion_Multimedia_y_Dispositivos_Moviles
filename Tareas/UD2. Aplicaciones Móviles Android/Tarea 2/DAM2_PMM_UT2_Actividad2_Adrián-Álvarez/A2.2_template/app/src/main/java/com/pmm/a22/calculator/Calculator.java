package com.pmm.a22.calculator;

public class Calculator {

    private boolean newOperation= true;
    private String firstOperand="";
    private String secondOperand="";
    private Calculator.Operators operator= null;

    public void setOperand(String number) {
        if(newOperation){
            firstOperand+= number;
        }
        else{
            secondOperand+= number;
        }
    }

    public void setOperator(Operators operator) {
        if (operator != null){
            newOperation = false;
            this.operator = operator;
        }
    }

    public boolean isNewOperation() {
        return newOperation;
    }

    public Float calculate() throws MissingOperandException, DivisionByZeroException {

        Float operand1 = Float.parseFloat(firstOperand);
        if (secondOperand.isEmpty()){
            throw new Calculator.MissingOperandException();
        }
        Float operand2 = Float.parseFloat(secondOperand);
        if (operator.equals(Calculator.Operators.DIVIDE) && operand2 == 0) {
            throw new Calculator.DivisionByZeroException();
        }

        float resultado= 0;
        if (operator == Operators.ADD) resultado= operand1 + operand2;
        else if (operator == Operators.SUBSTRACT) resultado= operand1 - operand2;
        else if (operator == Operators.MULTIPLY) resultado= operand1 * operand2;
        else if (operator == Operators.DIVIDE) resultado= operand1 / operand2;

        return resultado;
    }

    public void clear() {
        newOperation= true;
        operator= null;
        firstOperand= "";
        secondOperand= "";
    }

    public enum Operators {
        ADD("+"),
        SUBSTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private final String _operator;

        Operators(String operator) {
            _operator = operator;
        }

        public static Operators from(String operator) {

            if(operator.equals("+")) return Operators.ADD;
            else if(operator.equals("-")) return Operators.SUBSTRACT;
            else if(operator.equals("/")) return Operators.DIVIDE;
            else if(operator.equals("*")) return Operators.MULTIPLY;
            else throw new IllegalArgumentException(String.format("Unknown operator %s", operator));
        }

        @Override
        public String toString() {
            return _operator;
        }
    }

    public static class DivisionByZeroException extends RuntimeException {
    }

    public static class MissingOperandException extends RuntimeException {
    }

}
