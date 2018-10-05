package com.tsystems.javaschool.tasks.calculator;

import java.util.LinkedList;

/*
    Служебный класс для вычисления выражений, заданных в строкой.
    Для решения входных выражений используется преобразование строки к виду обратной польской записи(ОПЗ).
    Интерфейсом взаимодействия с данным классом является метод:
        public static String calculate (String s),
    принимающий и возвращающий результат выражения в виде строки.
    При любых некорректных данных исключения перехватываются,и возвращается null.
 */
public class Calculators {

    private Calculators(){ }

    public static String calculate (String s){
        return isValid(s) ? eval(s):null;
    }

    private static boolean isValid(String s){
        return (s != null && !s.equals(""));
    }

    private static String eval(String s){
        String result;

        try {
            result = parseStr(s);
            result = checkInfinity(result);
            result = round(result); }
        catch (Exception e){
            result = null; }

        return result;
    }

    private static String parseStr(String s){
        LinkedList<Double> number = new LinkedList<>();
        LinkedList<Character> operator = new LinkedList<>();
        char c;

            for (int i = 0; i < s.length(); i++)
            {
                switch (c = s.charAt(i)){
                     case '(':
                         operator.add('(');
                         break;

                     case ')':
                         while (operator.getLast() != '(')
                             processOperator(number, operator.removeLast());
                         operator.removeLast();
                         break;

                         default:
                             if (isOperator(c)) {
                                 while (!operator.isEmpty() && priority(operator.getLast()) >= priority(c))
                                     processOperator(number, operator.removeLast());
                                 operator.add(c);
                             }
                             else {
                                 String operand = "";
                                 while (i < s.length() && Character.isDigit(s.charAt(i)) || isDot(s.charAt(i))){
                                     if(i==s.length()-1){
                                         operand += s.charAt(i);
                                         i++;
                                         break;
                                     }
                                     else
                                         operand += s.charAt(i++);
                                 }
                                 --i;
                                 number.add(Double.parseDouble(operand));
                             }
                 }
            }
            while (!operator.isEmpty())
                processOperator(number, operator.removeLast());

        return String.valueOf(number.get(0));
    }

    private static String round(String s){
        return (s.endsWith(".0")) ? String.valueOf((int)Double.parseDouble(s)):s;
    }

    private static String checkInfinity(String s){
        return s.equals("Infinity")?null:s;
    }

    private static boolean isDot(char c) {
        return c == '.';
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int priority(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static void processOperator(LinkedList<Double> number, char operator) {
        double r = number.removeLast();
        double l = number.removeLast();
        switch (operator) {
            case '+':
                number.add(l + r);
                break;
            case '-':
                number.add(l - r);
                break;
            case '*':
                number.add(l * r);
                break;
            case '/':
                number.add(l / r);
                break;
        }
    }
}
