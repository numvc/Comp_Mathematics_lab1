import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        int size;
        Double[][] matrix;
        double eps;

        System.out.println("Файл(1) или консоль(2):");
        Scanner scanner = new Scanner(System.in);
        //scanner.useLocale(new Locale("Russian"));
        int variant = scanner.nextInt();
        if (variant == 2) {
        // Считываем размер вводимой матрицы

            System.out.println("Введите размер матрицы: ");
            try {
                size = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Введено некорректное число. Повторите:");
                size = scanner.nextInt();
            }

        // Будем хранить матрицу в векторе, состоящем из
        // векторов вещественных чисел
            matrix = new Double[size][size + 1];

        // Матрица будет иметь размер (size) x (size + 1),
        // c учетом столбца свободных членов
            System.out.println("Введите элементы матрицы: ");
            try {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size + 1; j++) {
                        matrix[i][j] = scanner.nextDouble();
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println("Некорректный ввод");
                System.exit(0);
            }

            // Считываем необходимую точность решения
            String demoEps;
            System.out.println("Введите точность: ");
            demoEps = scanner.next();

            //проверяем точку запятую у точности
            eps = Validater.pointAndComma(demoEps);

            //файл
        } else {
            FileReader fr = null;
            try {
                fr = new FileReader("lab1.txt");
            } catch (FileNotFoundException e) {
                System.out.println("Нет такого файла!");
                System.exit(0);
            }
            Scanner sc = new Scanner(fr);
            String[] lines = new String[100];
            int forLine = 0;
            while (sc.hasNext()) {
                lines[forLine] = sc.nextLine();
                forLine++;
            }

            size = new Integer(lines[0]);
            eps = Validater.pointAndComma(lines[1]);

            sc.close();
            fr.close();
            //System.out.println(lines[6].toCharArray());
            matrix = new Double[size][size + 1];
            String line[];
            for (int i = 2; i < forLine; ++i) {
                line = lines[i].split(" ");
                for (int j = 0; j <= size; ++j) {
                    matrix[i - 2][j] = Double.parseDouble(line[j]);
                }
            }
        }

        //диагональное преобразование
        matrix = Validater.diagonalTransfer(size, matrix);

        if(matrix == null){
            System.out.println("Невозможно добиться диагонального преобразования!");
            System.exit(0);
        }

        System.out.println("Имеем матрицу:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }


        Matrix matr = new Matrix(size, matrix, eps);
        matr.doMethod();
        double[] vectorX = matr.getPreviousVariableValues();
        double[] epsVector = matr.getEpsVector();
        int amount = matr.getAmount();


        // Выводим найденные значения неизвестных
        System.out.print("Вектор неизвестных: ");
        for (int i = 0; i < size; i++) {
            System.out.printf("%.5f ", vectorX[i]);
        }
        System.out.println();
        System.out.print("Вектор погрешности: ");
        for (int i = 0; i < size; i++) {
            System.out.printf("%.5f ", epsVector[i]);
        }
        System.out.println();
        System.out.println("Число итераций: " + amount);
        scanner.close();
    }
}