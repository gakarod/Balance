package com.pointless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Random;

/**
 * Created by vaibh on 7/4/2017.
 */

public class Asteroid {

    public int SPEED = 250;
    public int WIDTH = 32;
    public int HEIGHT = 32;
    public static final int radius = 15;
    public  int TILE_WIDTH=32;
    public  int TILE_HEIGHT=32 ;
    private static Texture texture;
    public final Circle AsteroidCircle;
    private Animation animation;
    private float animationTimer = 0;
    public static final float FRAME_DURATION = .25F;
    public int flag = 0;
    int max = 4 ;
    int min = 0 ;
    int type = 0 ;

    float x, y;

    public boolean remove = false;

    public Asteroid (float y ) {

        type = min + (int)(Math.random() * max);
            if(type == 1)
                texture = new Texture("asteroid.png");

            else if(type == 2) {
                TILE_HEIGHT = 16;
                TILE_WIDTH = 16;
                WIDTH = 16 ;
                HEIGHT = 16 ;
                texture = new Texture("asteroid2.png");
            }

            else if(type == 3) {
                TILE_HEIGHT = 32;
                TILE_WIDTH = 16;
                WIDTH = 16 ;
                HEIGHT = 32 ;
                texture = new Texture("asteroid3.png");
            }

            else  {
            texture = new Texture("asteroid.png");
        }


        TextureRegion[][] textures = new TextureRegion(texture).split(TILE_WIDTH, TILE_HEIGHT);       // TEXTURE REGION FROM A TEXTURE
        animation = new Animation(FRAME_DURATION, textures[0][0],textures[0][1]);             // ANIMATION FRAME FIND
        animation.setPlayMode(Animation.PlayMode.LOOP);                                       //  ANIAMTION LOOP

        this.x = GameScreen.WORLD_WIDTH/2;
        this.y = y;

        AsteroidCircle = new Circle(x , y , radius);
    }


    public void update (float deltaTime) {
        animationTimer += deltaTime;                               // ANIMATION TIMER
        float accelX = Gdx.input.getAccelerometerX();              // ACCELEROMETER
        float accelY = Gdx.input.getAccelerometerY();


        if (accelX < -1){
            //go up
            y += SPEED*deltaTime;
        }


        if (accelY < -1 ){
            //go left
            x -= SPEED*deltaTime;                         // MOVING OBJECTS ACCORDING TO THE ORIENTATION OF  DEVICE
        }

        if (accelX > +1 ){
        //go down
            y -= SPEED*deltaTime;
        }

        if (accelY > +1){
            //go right
            x += SPEED*deltaTime;
        }

        if (x < - GameScreen.WORLD_WIDTH)
            remove = true;

        updateCollisionCircle();

    }

    private void updateCollisionCircle() {
        AsteroidCircle.setX(x);
        AsteroidCircle.setY(y);
    }



    public void render (SpriteBatch sb) {
        TextureRegion asteroidSlice = (TextureRegion) animation.getKeyFrame(animationTimer);        // ANIMATION DRAW
        float textureX = AsteroidCircle.x - TILE_WIDTH / 2;
        float textureY = AsteroidCircle.y - TILE_HEIGHT / 2;
        sb.draw(asteroidSlice, textureX, textureY, WIDTH, HEIGHT);                           // RENDER TEXTURE USING SPRITE BATCH
    }


  public void drawDebug(ShapeRenderer shapeRenderer) {

      shapeRenderer.circle(AsteroidCircle.x, AsteroidCircle.y, radius);             // SHAPE RENDERER
  }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }
}
