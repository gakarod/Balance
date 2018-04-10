package com.pointless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by vaibh on 7/4/2017.
 */

public class GameScreen extends ScreenAdapter  {


    public  static float WORLD_WIDTH = 480;
    public  static float WORLD_HEIGHT = 320;
    private static final float GAP_BETWEEN_FLOWERS = 175F;
    public static final float MIN_ASTEROID_SPAWN_TIME = 0.4f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.8f;

    Random random;
    Random rand = new Random();

    private ShapeRenderer shapeRenderer;
    private FitViewport viewPort;
    public Camera camera;
    private SpriteBatch sb;

    Sound sound ;
    Sound blast ;

    float asteroidSpawnTimer;



    public boolean flag = false;

    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;
    long startTime = System.currentTimeMillis();

    private Texture bg;

    Game gamenow;


    public ArrayList<Asteroid> asteroids;


    ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewPort.update(width, height);               // VIEWPORT UPDATE
    }

    public GameScreen(Game game ) {
        this.gamenow = game;
    }

    @Override
    public void show() {               // SHOW ON FIRST USE
        super.show();
        bitmapFont = new BitmapFont();                   // BITMAP USE
        glyphLayout = new GlyphLayout();
        asteroids = new ArrayList<Asteroid>();
        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);              // CAMERA
        camera.update();
        viewPort = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);                     // VIEWPORT
        shapeRenderer = new ShapeRenderer();                                              // SHAPERENDERER
        sb = new SpriteBatch();                                                              // SPITE BATCH DECLARATION

        sound = Gdx.audio.newSound(Gdx.files.internal("boom.wav")) ;
        blast = Gdx.audio.newSound(Gdx.files.internal("big.wav")) ;
        bg = new Texture(Gdx.files.internal("bg.png"));



    }


    @Override
    public void dispose() {
        super.dispose();
    }


    @Override
    public void render(float delta) {                  // APPLICATION LISTENER

                super.render(delta);
                update(delta, startTime);
                clearScreen();
                draw(startTime);
             //   drawDebug();

    }

    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);


        for (Asteroid asteroid : asteroids) {
            asteroid.drawDebug(shapeRenderer);
        }
}

    private void update(float delta, long timer) {

        if (Gdx.input.isTouched()){                           // INPUT POLLING
            Gdx.input.vibrate(400);
            sound.play();                              // AUDIO PLAYING
        }


        buildAsteroids(delta);
        updateAsteroids(delta);

        flag = false;

    }

    private void draw(long time) {
        long elapsedtime = System.currentTimeMillis() - time;
        sb.setProjectionMatrix(camera.projection);      //
        sb.setTransformMatrix(camera.view);
        sb.begin();
        sb.draw(bg, 0, 0);

        drawAsteroids();
     //   sb.draw(pause,WORLD_WIDTH-75 , WORLD_HEIGHT-75);
        sb.setColor(Color.WHITE);
        sb.end();
    }





    private void buildAsteroids(float delta) {
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt((int) (GameScreen.WORLD_HEIGHT - 32))));
        }
    }

    private void drawAsteroids() {
        for (Asteroid asteroid : asteroids) {
            asteroid.render(sb);
        }
    }

    private void updateAsteroids(float delta) {

        for (Asteroid asteroid : asteroids) {
            if (flag) {
                asteroid.SPEED += 50;
            }
            asteroid.update(delta);
            if (asteroid.remove)
                asteroidsToRemove.add(asteroid);
        }
        asteroids.removeAll(asteroidsToRemove);
    }




    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}

