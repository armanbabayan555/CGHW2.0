import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Demo {

    public static Graphics g;
    public static BufferedImage img;

    public static void solidSetRaster(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3]; //RGB

        for (int y = 0; y < height; y++) {
            int val = 0;
            for (int shift = 1; shift < 3; shift++) {
                pixel[shift] = val;
            }

            for (int x = 0; x < width; x++) {
                raster.setPixel(x, y, pixel);
            }
        }
    }

    public static void pipeline(Cube cube, Camera camera, BufferedImage image, Graphics g) {
        Cube c = new Cube();
        float[][] projectionMatrix = camera.getProjectionMatrix();
        float[][] viewMatrix = camera.getViewMatrix();
        float[][] worldMatrix = cube.getWorldMatrix();
        float[][] transformationMatrix = Formulas.matrixMultiplication(projectionMatrix,
                Formulas.matrixMultiplication(viewMatrix, worldMatrix));

        ArrayList<Point> points = c.getPoints();
        for (Point p : points) {
            p.setPoint(Formulas.matrixMultiplication(transformationMatrix, p.getPoint()));
            p.w();
            p.setPoint(
                    new float[][]{
                            {
                                    (1 + p.getPoint()[0][0]) * image.getWidth() / 2
                            },
                            {
                                    (1 + p.getPoint()[1][0]) * image.getHeight() / 2
                            },
                            {
                                    1
                            },
                            {
                                    1
                            }
                    }
            );
        }

        IntStream.range(0, cube.getPolygon().size()).forEach(i -> c.getPolygon().get(i).draw(image, g));


    }

    public static void main(String[] args) {
        Frame w = new Frame("Window");
        final int imageWidth = 1000;
        final int imageHeight = 1000;
        float[] theta = {60};
        float n = 5;
        float f = 500;

        w.setSize(imageWidth, imageHeight);
        w.setLocation(50, 50);
        w.setVisible(true);
        g = w.getGraphics();

        img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        solidSetRaster(img);
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);

        Cube cube = new Cube();
        cube.translate(0, 0, -5);

        cube.scale(5, 5, 5);
        Camera camera = new Camera(theta[0], imageWidth, imageHeight, n, f);
        pipeline(cube, camera, img, g);
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> false);

        w.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                solidSetRaster(img);
                // translate the camera forward using UP key
                if (e.getKeyCode() == 38) {
                    camera.translateCamera(0, 0, -10);
                }
                // translate the camera backward using DOWN key
                if (e.getKeyCode() == 40) {
                    camera.translateCamera(0, 0, 10);
                }
                // translate the camera left using LEFT key
                if (e.getKeyCode() == 37) {
                    camera.translateCamera(-5, 0, 0);
                }
                // translate the camera right using RIGHT key
                if (e.getKeyCode() == 39) {
                    camera.translateCamera(5, 0, 0);
                }
               // decrease the FOV using F key
                if (e.getKeyCode() == 70) {
                    theta[0] -= 5;
                    camera.setProjectionMatrix(camera.constructProjectionMatrix(theta[0], (float) imageWidth / imageHeight, n, f));
                }
                // increase the FOV using G key
                if (e.getKeyCode() == 71) {
                    theta[0] += 5;
                    camera.setProjectionMatrix(camera.constructProjectionMatrix(theta[0], (float) imageWidth / imageHeight, n, f));
                }
                // translate the camera up using E key
                if (e.getKeyCode() == 69) {
                    camera.translateCamera(0, -5, 0);
                }
                // translate the camera down using Q key
                if (e.getKeyCode() == 81) {
                    camera.translateCamera(0, 5, 0);
                }
                // rotate the cube CCW around the y-axis using R key
                if (e.getKeyCode() == 82) {
                    cube.rotateY(-5f);
                }
                // rotate the cube CW around the y-axis using T key
                if (e.getKeyCode() == 84) {
                    cube.rotateY(5f);
                }
                solidSetRaster(img);
                pipeline(cube, camera, img, g);
                g.drawImage(img, 0, 0, (img, infoflags, x, y, width, height) -> false);
            }
        });

        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                w.dispose();
                g.dispose();
                System.exit(0);
            }
        });
    }
}
