package com.puputan.infi.Utils;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;
import com.puputan.infi.Objects.BaseObject;

public class BodyUtils {

    public static BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("/Users/patrykrutkowski/Desktop/Projects/InfiEnv/box-2d-edit-project/project.json"));

    public static Body defineBody(BodyDef.BodyType bodyType, BaseObject baseObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(baseObject.getX() + baseObject.getWidth()/2, baseObject.getY() + baseObject.getHeight()/2);
        Body body = GameScreen.world.createBody(bodyDef);

        return body;
    }

    public static void createCircleFixture(BaseObject baseObject){
        CircleShape circle = new CircleShape();
        circle.setRadius(baseObject.getWidth()/2);
        circle.setPosition(new Vector2(baseObject.getX() + baseObject.getWidth()/2, baseObject.getY() + baseObject.getHeight()/2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        baseObject.getBody().createFixture(fixtureDef);
    }

    public static void createFixtureFromSprite(Body body, Texture texture){
            Array<Vector2> vertices = generatePolygonPoints(texture);

            FloatArray floatVertices = new FloatArray(vertices.size * 2);
            for (Vector2 vertex : vertices) {
                floatVertices.add(vertex.x);
                floatVertices.add(vertex.y);
            }

            EarClippingTriangulator triangulator = new EarClippingTriangulator();
            ShortArray triangleIndices = triangulator.computeTriangles(floatVertices);

            for (int i = 0; i < triangleIndices.size; i += 3) {
                PolygonShape polygonShape = new PolygonShape();
                Vector2[] triangle = new Vector2[3];
                for (int j = 0; j < 3; j++) {
                    triangle[j] = vertices.get(triangleIndices.get(i + j));
                }
                polygonShape.set(triangle);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = polygonShape;
                fixtureDef.density = 1f;
                body.createFixture(fixtureDef);

                polygonShape.dispose();
            }
     }

    public static FixtureDef getDefaultFixture(){
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        return fd;
    }

    private static Array<Vector2> generatePolygonPoints(Texture texture) {

        final int[] dx = {1, 0, -1, 0};  // Right, Down, Left, Up
        final int[] dy = {0, 1, 0, -1};  // Right, Down, Left, Up

        Pixmap pixmap = getPixmapFromTexture(texture);
        Array<Vector2> points = new Array<>();

        int width = pixmap.getWidth();
        int height = pixmap.getHeight();

        boolean[][] visited = new boolean[width][height];

        // Find the first non-transparent pixel
        int startX = 0, startY = 0;
        outer:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((pixmap.getPixel(x, y) & 0x000000ff) != 0) {  // Check for non-transparent pixel
                    startX = x;
                    startY = y;
                    break outer;
                }
            }
        }

        // Trace the outline
        int x = startX, y = startY;
        int direction = 0; // 0: right, 1: down, 2: left, 3: up

        do {
            // Ensure x and y are within bounds
            if (x >= 0 && x < width && y >= 0 && y < height) {
                points.add(new Vector2(x / (float) width, 1 - y / (float) height));
                visited[x][y] = true;
            } else {
                break;  // Break if x or y are out of bounds
            }

            // Try to turn left
            int newDirection = (direction + 3) % 4;
            int newX = x + dx[newDirection];
            int newY = y + dy[newDirection];

            // Check bounds and non-transparent pixel
            if (newX >= 0 && newX < width && newY >= 0 && newY < height &&
                    (pixmap.getPixel(newX, newY) & 0x000000ff) != 0) {
                x = newX;
                y = newY;
                direction = newDirection;
            } else {
                // Move in the current direction
                newX = x + dx[direction];
                newY = y + dy[direction];

                // Ensure bounds check when moving in the current direction
                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    x = newX;
                    y = newY;
                } else {
                    break;  // Break if out of bounds
                }
            }
        } while (x != startX || y != startY);

        pixmap.dispose();
        return points;
    }

    private static Pixmap getPixmapFromTexture(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

}