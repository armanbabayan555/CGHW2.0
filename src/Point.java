public class Point {
    private float[][] point = new float[4][1];

    public Point(int x, int y, int z, int w) {
        this.point[0][0] = x;
        this.point[1][0] = y;
        this.point[2][0] = z;
        this.point[3][0] = w;
    }

    public float[][] getPoint() {
        return this.point;
    }

    public void setPoint(float[][] point) {
        this.point = point;
    }

    public void w() {
        for (int i = 0; i < point.length; i++) {
            point[i][0] /= point[3][0];
        }
    }
}
