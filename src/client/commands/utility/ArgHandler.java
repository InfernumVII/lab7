package client.commands.utility;

import java.util.Arrays;

/**
 * Класс для проверки и обработки аргументов команд.
 * Предоставляет методы для проверки типов и диапазонов значений аргументов.
 */
public class ArgHandler {

    /**
     * Проверяет, является ли аргумент целым числом в заданном диапазоне.
     *
     * @param arg строка, содержащая аргумент.
     * @param min минимальное допустимое значение (исключительно).
     * @param max максимальное допустимое значение (включительно).
     * @return {@code true}, если аргумент является целым числом в заданном диапазоне, иначе {@code false}.
     */
    public static boolean checkArgForInt(String arg, int min, int max) throws Exception{
        try {
            int in = Integer.parseInt(arg);

            if (in <= min || in > max) {
                throw new Exception(String.format("Число должно быть между %s и %s.\n", min, max));
                //System.out.printf("Число должно быть между %s и %s.\n", min, max);
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            throw new Exception("Аргумент должен быть числом.");
            //System.out.println("Аргумент должен быть числом.");
            
        }

    }

    /**
     * Проверяет, является ли аргумент целым числом.
     *
     * @param arg строка, содержащая аргумент.
     * @return {@code true}, если аргумент является целым числом, иначе {@code false}.
     */
    public static boolean checkArgForInt(String arg) throws Exception{
        return checkArgForInt(arg, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Проверяет, соответствует ли аргумент одному из значений перечисления (enum).
     * Аргумент может быть как строковым представлением значения перечисления (без учета регистра),
     * так и числовым представлением его порядкового номера (начиная с 1).
     *
     * @param <E> тип перечисления.
     * @param arg строка, содержащая аргумент.
     * @param enums массив значений перечисления.
     * @return {@code true}, если аргумент соответствует одному из значений перечисления, иначе {@code false}.
     */
    public static <E extends Enum<E>> boolean checkArgForEnumString(String arg, E[] enums) throws Exception{
        String joinedEnums = Arrays.toString(enums);
        boolean isInEnums = false;
        for (E enu : enums) {
            if (arg.equalsIgnoreCase(enu.name()) || arg.equals(Integer.toString(enu.ordinal() + 1))) {
                return true;
            }
        }
        if (isInEnums == false){
            throw new Exception(String.format("Аргумент должен быть одним из вариантов: (%s)", joinedEnums));
            //System.out.println(String.format("Аргумент должен быть одним из вариантов: (%s)", joinedEnums));
            //return false;
        }
        return true;
        

    }

    /**
     * Проверяет, является ли аргумент числом с плавающей точкой в заданном диапазоне.
     *
     * @param arg строка, содержащая аргумент.
     * @param min минимальное допустимое значение (исключительно).
     * @param max максимальное допустимое значение (включительно).
     * @return {@code true}, если аргумент является числом с плавающей точкой в заданном диапазоне, иначе {@code false}.
     */
    public static boolean checkArgForFloat(String arg, float min, float max){
        try {
            float in = Float.parseFloat(arg);

            if (in <= min || in > max) {
                System.out.printf("Число должно быть между %s и %s.\n", min, max);
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Аргумент должен быть числом.");
            
        }
        return false;
    }

    /**
     * Проверяет, является ли аргумент числом с плавающей точкой.
     *
     * @param arg строка, содержащая аргумент.
     * @return {@code true}, если аргумент является числом с плавающей точкой, иначе {@code false}.
     */
    public static boolean checkArgForFloat(String arg){
        return checkArgForFloat(arg, -Float.MAX_VALUE, Float.MAX_VALUE);
    }


} 
