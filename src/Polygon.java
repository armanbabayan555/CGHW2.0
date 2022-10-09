import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Polygon {
    private final ArrayList<Point> points = new ArrayList<>();

    public Polygon(Point p1, Point p2, Point p3, Point p4) {
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.points.add(p4);
    }

    public void draw(BufferedImage img, Graphics g) {
        int[] pixel = {255, 255, 255};
        IntStream.range(0, points.size() - 1).forEach(i ->
                Formulas.bresenham(
                        (int) points.get(i).getPoint()[0][0],
                        (int) points.get(i).getPoint()[1][0],
                        (int) points.get(i + 1).getPoint()[0][0],
                        (int) points.get(i + 1).getPoint()[1][0],
                        img,
                        pixel
                )
        );

        Formulas.bresenham(
                (int) points.get(0).getPoint()[0][0],
                (int) points.get(0).getPoint()[1][0],
                (int) points.get(points.size() - 1).getPoint()[0][0],
                (int) points.get(points.size() - 1).getPoint()[1][0],
                img,
                pixel
        );
    }
}
