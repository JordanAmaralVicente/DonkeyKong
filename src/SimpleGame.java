import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SimpleGame extends ApplicationAdapter {
   private Texture dropImage;
   private Texture bucketImage;
   private Sound dropSound;
   private Music rainMusic;
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Rectangle bucket;
   private Array<Rectangle> raindrops;
   private long lastDropTime;

   @Override
   public void create() {
      // load the images for the droplet and the bucket, 64x64 pixels each
      //dropImage = new Texture(Gdx.files.internal("assets/images/droplet.png"));


      // load the drop sound effect and the rain background "music"
      //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/rain.mp3"));

      // start the playback of the background music immediately
      //rainMusic.setLooping(true);
     // rainMusic.play();

      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 480);
      batch = new SpriteBatch();

      // create a Rectangle to logically represent the bucket
      /*bucket = new Rectangle();
      bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
      bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
      bucket.width = 64;
      bucket.height = 64;*/

   }


   @Override //aqui é o loop do game
   public void render() {

      Gdx.gl.glClearColor(0, 0, 0.1f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      camera.update();
      batch.setProjectionMatrix(camera.combined);

      // begin a new batch and draw the bucket and
      // all drops
      batch.begin();
         //desenhar os itens aqui dentro
      batch.end();

      //verifica input do teclado
      /*
      if(Gdx.input.isKeyPressed(Keys.LEFT)) {
         bucket.x -= 200 * Gdx.graphics.getDeltaTime();
      }
      if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
         bucket.x += 200 * Gdx.graphics.getDeltaTime();
      }*/

      // verifica se o elemento está dentro da tela
      /*if(bucket.x < 0) bucket.x = 0;
      if(bucket.x > 800 - 64) bucket.x = 800 - 64;*/

   }

   @Override
   public void dispose() {
      // dispose of all the native resources
      batch.dispose();
   }
}