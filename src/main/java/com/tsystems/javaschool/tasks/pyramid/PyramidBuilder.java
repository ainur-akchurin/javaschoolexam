package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {

        int[][] result;
        if(!canBuild(inputNumbers))
            throw new CannotBuildPyramidException();
        else  result = build(inputNumbers);

        return result;
    }

    /*
          Пирамиду можно построить, если кол-во элементов ПОСЛЕДУЮЩЕГО слоя,
          больше на единицу, чем кол-во элементов ПРЕДЫДУЩЕГО слоя, что является арифметической прогрессией с разностью 1.
          Пример:
                 {0, 0, 1, 0, 0}     1 элемент
                 {0, 2, 0, 3, 0}     2 элемента
                 {4, 0, 5, 0, 9}     3 элемента
                                     Всего 6 элементов == длина полученного списка
          Следовательно, если длина полученного списка является суммой арифметической последовательности (с разностью 1),
          значит возможно построить пирамиду из данных элементов.
    */
    private static boolean canBuild(List<Integer> inputNumbers){

        if(inputNumbers.contains(null))
            return false;
        //предположим, что из данного списка можно построить пирамиду
        int n = calculateN(inputNumbers);   //вычислим кол-во элементов в арифметической прогрессии
        int sum = calculateSum(n);          //находим для нее сумму
        if(sum == inputNumbers.size())      //если сумма членов арифметической прогрессии равна количеству элементов списка(ее размеру)
            return true;                    //значит из нее можно построить пирамиду
        else return false;                  //иначе нет
    }


    private static int[][] build(List<Integer> inputNumbers){

        int n = calculateN(inputNumbers);
        int[][] result = new int[n][n+(n-1)];

        Collections.sort(inputNumbers);

        int count = 0;                                              // для подсчет элементов в строке
        int startPosition = result.length-1;                        //стартовая позиция элемента для первой строки
        int position = startPosition;                               // текущая позиция элемента
        int pointer = 0;                                            // указатель на элемент списка
        for(int i = 0; i<result.length;i++){
            count=0;
            for (int j = 0; j<result[i].length;j++){
                result[i][position] = inputNumbers.get(pointer);
                count++;
                pointer++;
                if(count!=i+1)                                      // если кол-во элементов в строчке, не равно НОМЕРУ(не индексу) строки
                    position+=2;                                    //то пропускаем место для 0 (+1), и устанавливаем позицию за нулем (+1).
                else {position = (startPosition)-1-i;               // иначе позиция для новой строки (-1 влево на 1, -i влево учитывая строчку)
                    break;
                }
            }
        }
        return result;
    }


    /*
        Вычисление суммы арифметической прогрессии с разностью 1 для первых n членов.
        Sum(n) = ((a(1)+a(n))*n)/2
        т.к. арифметическая прогрессия в нашем случае имеет разность 1, то a(n) = n.
            => Sum(n) = ((1+n)*n)/2
     */
    private static int calculateSum(int n){
        return ((1+n)*n)/2;
    }

    /*
        Вычисление n, при известной Sum(n).
        После всех преобразований и упрощений обычного квадратного уравнения получется следующая формула:
            n = (-1 + √(Sum(n)*8+1))/2
     */
    private static int calculateN(List<Integer> inputNumbers){
        return (-1 + (int)Math.sqrt((inputNumbers.size()*8)+1))/2;
    }
}
