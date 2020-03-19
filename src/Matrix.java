public class Matrix {
    private int size;
    private double eps;
    private Double[][] matrix;
    private double[] epsVector;
    private double[] previousVariableValues;
    private int amount;

    public Matrix(int size, Double[][] matrix, double eps){
        this.eps = eps;
        this.size = size;
        this.matrix = matrix;
    }

    public void doMethod() {
        // Введем вектор значений неизвестных на предыдущей итерации,
        // размер которого равен числу строк в матрице, т.е. size,
        // причем согласно методу изначально заполняем его нулями
        previousVariableValues = new double[size];
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = 0.0;
        }
        //Вектор погрешностей
        epsVector = new double[size];

        //число итераций
        amount = -1;
        // Будем выполнять итерационный процесс до тех пор,
        // пока не будет достигнута необходимая точность
        while (true) {
            amount++;
            // Введем вектор значений неизвестных на текущем шаге
            double[] currentVariableValues = new double[size];
            // Посчитаем значения неизвестных на текущей итерации
            // в соответствии с теоретическими формулами
            for (int i = 0; i < size; i++) {
                // Инициализируем i-ую неизвестную значением
                // свободного члена i-ой строки матрицы
                currentVariableValues[i] = matrix[i][size];
                // Вычитаем сумму по всем отличным от i-ой неизвестным
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j];
                    }
                }
                // Делим на коэффициент при i-ой неизвестной
                currentVariableValues[i] /= matrix[i][i];
            }
            // Посчитаем текущую погрешность относительно предыдущей итерации
            double error = -1.0;
            for (int i = 0; i < size; i++) {
                //System.out.println(previousVariableValues[i] + " " + currentVariableValues[i]);
                epsVector[i] = Math.abs(previousVariableValues[i] - currentVariableValues[i]);
                if (Math.abs(previousVariableValues[i] - currentVariableValues[i]) > error) {
                    error = Math.abs(previousVariableValues[i] - currentVariableValues[i]);
                }
            }

            // Если необходимая точность достигнута, то завершаем процесс
            if (error < eps) {
                if (!Double.isNaN(currentVariableValues[0])) {
                    previousVariableValues = currentVariableValues;
                }
                break;
            }

            // Переходим к следующей итерации, так что текущие значения неизвестных
            // становятся значениями на предыдущей итерации
            previousVariableValues = currentVariableValues;
        }
    }


    public int getAmount() {
        return amount;
    }

    public double[] getEpsVector() {
        return epsVector;
    }

    public double[] getPreviousVariableValues() {
        return previousVariableValues;
    }
}
