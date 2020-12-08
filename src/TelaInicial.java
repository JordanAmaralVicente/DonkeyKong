import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TelaInicial extends ApplicationAdapter {
    private Texture botaoIniciar;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    @Override
    public void create() {
        botaoIniciar = new Texture("assets/images/start.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            batch.draw(botaoIniciar, 800/2, 600/2, 100, 100);
        batch.end();


        if(Gdx.input.isTouched()) {
            int areaCliqueX = Gdx.input.getX();
            int areaCliqueY = Gdx.input.getY();
            System.out.println("Clicou X" + areaCliqueX + " Y: " + areaCliqueY);
            if((areaCliqueX >= 400 && areaCliqueX <= 500) && (areaCliqueY >= 200 && areaCliqueY <= 300)){
                System.out.println("clique");
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        botaoIniciar.dispose();
    }
}