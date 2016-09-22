package edu.stanford.common;

import java.util.Arrays;

import static edu.stanford.common.CommonUtils.checkMatrixRowLength;
import static edu.stanford.common.MatrixPrinter.Mode.TABLE;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.*;

public class MatrixPrinter<T> {

    public enum Mode {
        VAR_ROW, TABLE
    }

    private final T[][] matrix;

    private final Mode mode;

    public MatrixPrinter(T[][] matrix, Mode mode) {
        this.matrix = matrix;
        this.mode = mode;
    }

    public static <T> String printMatrix(T[][] matrix, Mode mode) {
        return new MatrixPrinter<>(matrix, mode).print();
    }

    public String print() {
        return mode == TABLE ? printWithEqualRows() : printWithVarRows();
    }

    private String printWithVarRows() {
        StringBuilder strBuilder = new StringBuilder();

        int cellWidth = maxWidth();

        for (T[] row : matrix) {
            for (T cell : row) {
                strBuilder.append(rightPad(valueOf(cell), cellWidth + 1, SPACE));
            }
            strBuilder.append(LF);
        }

        return strBuilder.toString();
    }

    private String printWithEqualRows() {

        StringBuilder strBuilder = new StringBuilder();

        int rowLength = checkMatrixRowLength(matrix);

        for (int j = 0; j < rowLength; j++) {
            int width = columnWidth(j);
            for (T[] aMatrix : matrix) {
                String cell = valueOf(aMatrix[j]);
                strBuilder.append(rightPad(cell, width + 1, " "));
            }
            strBuilder.append(LF);
        }

        return strBuilder.toString();
    }


    private int columnWidth(int col) {
        int width = valueOf(matrix[0][col]).length();
        for (int i = 1; i < matrix.length; i++) {
            width = max(width, valueOf(matrix[i][col]).length());
        }
        return width;
    }

    private int maxWidth() {
        return stream(matrix)
                .flatMap(Arrays::stream)
                .map(String::valueOf)
                .mapToInt(String::length)
                .max()
                .getAsInt();
    }

}
