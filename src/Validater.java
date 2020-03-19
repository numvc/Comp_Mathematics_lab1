import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validater {

    public static double pointAndComma(String demoEps) {
        String regex = "^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:(\\.|,)\\d+)?$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(demoEps);
        if (matcher.matches()) {
            return Double.parseDouble(demoEps.replace(",", "."));

        } else {
            System.out.println("Введена некорректная точность. Точность принимается за 0.5");
            return 0.5;
        }

    }

    public static Double[][] alternativeDiagTransform(Double[][] matrix, int size) {

        double max = Double.MIN_VALUE;
        int numberLine;
        Double[] tmp = new Double[size];
        for (int i = 0; i < size; i++) {
            numberLine = i;
            max = matrix[i][i];
            for (int j = 0; j < size; j++) {
                if (matrix[j][i] > max) {
                    max = matrix[j][i];
                    numberLine = j;
                }
            }
            if (numberLine >= i) {
                tmp = matrix[i];
                matrix[i] = matrix[numberLine];
                matrix[numberLine] = tmp;
            } else {
                System.out.println("Невозможно добиться диагонального преобладания!");
                System.exit(0);
                //return matrix;

            }
        }
        return matrix;
    }

    public static Double[][] diagonalTransfer(int size, Double[][] matrix) {

        boolean[][] boolMatr = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            double sum = 0;
            for (int j = 0; j < size; j++) {
                sum += Math.abs(matrix[i][j]);
            }
            for (int j = 0; j < size; j++) {
                if (2 * Math.abs(matrix[i][j]) >= sum) {
                    boolMatr[i][j] = true;
                }
            }
        }
        boolean found;
        Double[][] tmp = new Double[size][size];
        for (int i = 0; i < size; i++) {
            found = false;
            for (int j = 0; j < size; j++) {
                if (boolMatr[j][i]) {
                    found = true;
                    tmp[i] = matrix[j];
                    break;
                }
            }
            if (!found){
                return null;
            }

        }
        matrix = tmp;
        return matrix;
    }

}
