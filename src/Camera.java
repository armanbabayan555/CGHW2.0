public class Camera {

    private float[][] projectionMatrix;
    private float[][] viewMatrix;

    public Camera(float theta, int width, int height, float n, float f) {
        this.projectionMatrix = constructProjectionMatrix(theta, (float) width / height, n, f);

        this.viewMatrix = new float[][]{{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, -1, 0},
                {0, 0, 0, 1}};
    }

    public void translateCamera(float tx, float ty, float tz) {
        this.viewMatrix = Formulas.matrixMultiplication(Formulas.getTranslationMatrix(tx, ty, tz), this.viewMatrix);
    }

    public float[][] constructProjectionMatrix(float theta, float aspectRatio, float n, float f) {
        double thetaHalfRadians = Math.toRadians(theta / 2);
        return new float[][]
                {
                        {
                                (float) (1 / Math.tan(thetaHalfRadians)) / aspectRatio,
                                0,
                                0,
                                0
                        },
                        {
                                0,
                                (float) (1 / Math.tan(thetaHalfRadians)),
                                0,
                                0
                        },
                        {
                                0,
                                0,
                                -(f + n) / (f - n),
                                -2 * f * n / (f - n)
                        },
                        {
                                0,
                                0,
                                -1,
                                0
                        }
                };
    }

    public float[][] getViewMatrix() {
        return this.viewMatrix;
    }

    public float[][] getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public void setProjectionMatrix(float[][] matrix) {
        this.projectionMatrix = matrix;
    }
}
