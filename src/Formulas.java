import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Formulas {
    public static float[][] getTranslationMatrix(float tx, float ty, float tz) {
        return new float[][]{{1, 0, 0, tx}, {0, 1, 0, ty}, {0, 0, 1, tz}, {0, 0, 0, 1}};
    }

    public static float[][] getRotationMatrixY(float angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new float[][]{{(float) cos, 0, (float) sin, 0},
                {0, 1, 0, 0},
                {(float) -sin, 0, (float) cos, 0},
                {0, 0, 0, 1}};
    }

    public static float[][] getScalingMatrix(float sx, float sy, float sz) {
        return new float[][]{{sx, 0, 0, 0}, {0, sy, 0, 0}, {0, 0, sz, 0}, {0, 0, 0, 1}};
    }

    public static float[][] matrixMultiplication(float[][] m1, float[][] m2) throws IllegalArgumentException {
        float[][] newMatrix = new float[m1.length][m2[0].length];

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    newMatrix[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return newMatrix;

    }

    public static void horizontalLine(int start, int end, int coord, BufferedImage img, int[] pixel) {
        WritableRaster raster = img.getRaster();
        while (start <= end) {
            raster.setPixel(start, coord, pixel);
            start++;
        }
    }

    public static void verticalLine(int start, int end, int coord, BufferedImage img, int[] pixel) {
        WritableRaster raster = img.getRaster();
        while (start <= end) {
            raster.setPixel(coord, start, pixel);
            start++;
        }
    }

    public static void firstOctant(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int d = 2 * dy - dx;

        int dE = 2 * dy;
        int dNE = 2 * (dy - dx);
        int x, y, xEnd;

        if (x1 > x2) {
            x = x2;
            y = y2;
            xEnd = x1;
        } else {
            x = x1;
            y = y1;
            xEnd = x2;
        }

        WritableRaster raster = img.getRaster();
        raster.setPixel(x, y, pixel);

        while (x < xEnd) {
            if (d <= 0) {
                d += dE;
            } else {
                d += dNE;
                y--;
            }
            x++;
            raster.setPixel(x, y, pixel);

        }
    }

    public static void secondOctant(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int d = 2 * dy - dx;

        int dE = 2 * dy;
        int dNE = 2 * (dy - dx);
        int x, y, xEnd;

        if (x1 > x2) {
            x = x2;
            y = y2;
            xEnd = x1;
        } else {
            x = x1;
            y = y1;
            xEnd = x2;
        }

        WritableRaster raster = img.getRaster();
        raster.setPixel(y, x, pixel);

        while (x < xEnd) {
            if (d <= 0) {
                d += dE;
            } else {
                d += dNE;
                y--;
            }
            x++;
            raster.setPixel(y, x, pixel);

        }
    }

    public static void seventhOctant(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int d = 2 * dy - dx;

        int dE = 2 * dy;
        int dNE = 2 * (dy - dx);
        int x, y, xEnd;

        if (x1 > x2) {
            x = x2;
            y = y2;
            xEnd = x1;
        } else {
            x = x1;
            y = y1;
            xEnd = x2;
        }

        WritableRaster raster = img.getRaster();
        raster.setPixel(y, x, pixel);

        while (x < xEnd) {
            if (d <= 0) {
                d += dE;
            } else {
                d += dNE;
                y++;
            }
            x++;
            raster.setPixel(y, x, pixel);

        }
    }

    public static void eigthOctant(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int d = 2 * dy - dx;

        int dE = 2 * dy;
        int dNE = 2 * (dy - dx);
        int x, y, xEnd;

        if (x1 > x2) {
            x = x2;
            y = y2;
            xEnd = x1;
        } else {
            x = x1;
            y = y1;
            xEnd = x2;
        }

        WritableRaster raster = img.getRaster();
        raster.setPixel(x, y, pixel);

        while (x < xEnd) {
            if (d <= 0) {
                d += dE;
            } else {
                d += dNE;
                y++;
            }
            x++;
            raster.setPixel(x, y, pixel);

        }
    }

    public static void bresenham(int x1, int y1, int x2, int y2, BufferedImage img, int[] pixel) {
        int check_x = x2 - x1;
        int check_y = -(y2 - y1);
        int max;
        int min;

        if (x1 == x2) {
            max = Math.max(y1, y2);
            min = Math.min(y1, y2);
            verticalLine(min, max, x1, img, pixel);
        } else if (y1 == y2) {
            max = Math.max(x1, x2);
            min = Math.min(x1, x2);
            horizontalLine(min, max, y1, img, pixel);
        } else {
            if (check_x * check_y > 0) {
                if (Math.abs(check_x) > Math.abs(check_y)) {
                    firstOctant(x1, y1, x2, y2, img, pixel);
                } else {
                    secondOctant(y1, x1, y2, x2, img, pixel);
                }
            } else {
                if (Math.abs(check_x) < Math.abs(check_y)) {
                    seventhOctant(y1, x1, y2, x2, img, pixel);
                } else {
                    eigthOctant(x1, y1, x2, y2, img, pixel);
                }
            }
        }
    }

}
