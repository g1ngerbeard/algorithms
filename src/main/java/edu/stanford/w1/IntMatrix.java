package edu.stanford.w1;

import static edu.stanford.common.CommonUtils.*;
import static edu.stanford.common.MatrixPrinter.Mode.TABLE;
import static edu.stanford.common.MatrixPrinter.printMatrix;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class IntMatrix {
    private static final int MIN_VALUE = 0;

    private static final int MAX_VALUE = 100000;

    private final Integer[][] matrix;

    public IntMatrix(Integer[][] matrix) {
        checkMatrixRowLength(matrix);
        this.matrix = copyMatrix(matrix);
    }

    public IntMatrix multiply(IntMatrix m) {
//        todo: implement
        return null;
    }

    @Override
    public String toString() {
        return printMatrix(matrix, TABLE);
    }

    public static IntMatrix fillRandomly(int rows, int columns) {
        Integer[][] matrix = new Integer[rows][columns];

        for (int i = 0; i < rows; i++) {
            matrix[i] = fillArray(new Integer[columns], () -> nextInt(MIN_VALUE, MAX_VALUE));
        }

        return new IntMatrix(matrix);
    }
}
