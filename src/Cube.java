
import java.util.ArrayList;
import java.util.Arrays;

public class Cube {
    private float[][] world;
    private final ArrayList<Point> points;
    private final ArrayList<Polygon> polygons;


    public Cube() {
        world = new float[][]{{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        points = new ArrayList<>(Arrays.asList
                (
                        new Point(-1, -1, 1, 1),
                        new Point(1, -1, 1, 1),
                        new Point(1, 1, 1, 1),
                        new Point(-1, 1, 1, 1),
                        new Point(-1, -1, -1, 1),
                        new Point(1, -1, -1, 1),
                        new Point(1, 1, -1, 1),
                        new Point(-1, 1, -1, 1)
                ));
        polygons = new ArrayList<>(Arrays.asList
                (
                        (new Polygon(points.get(1), points.get(5), points.get(6), points.get(2))), //right
                        (new Polygon(points.get(3), points.get(0), points.get(4), points.get(7))), //left
                        (new Polygon(points.get(2), points.get(6), points.get(7), points.get(3))), //top
                        (new Polygon(points.get(1), points.get(5), points.get(4), points.get(0))), //bottom
                        (new Polygon(points.get(1), points.get(2), points.get(3), points.get(0))), //front
                        (new Polygon(points.get(5), points.get(6), points.get(7), points.get(4))) //back
                ));

    }

    public float[][] getWorldMatrix() {
        return this.world;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public ArrayList<Polygon> getPolygon() {
        return this.polygons;
    }

    public void translate(float tx, float ty, float tz) {
        world = Formulas.matrixMultiplication(Formulas.getTranslationMatrix(tx, ty, tz), world);
    }

    public void scale(float sx, float sy, float sz) {
        world = Formulas.matrixMultiplication(Formulas.getScalingMatrix(sx, sy, sz), world);
    }

    public void rotateY(float angle) {
        world = Formulas.matrixMultiplication(Formulas.getRotationMatrixY(angle), world);
    }

}
