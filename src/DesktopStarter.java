import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter{

	public static void main (String[] args) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

        new LwjglApplication(new TelaInicial(), "Donkey Kong - Tela Inicial",
				800, 600, false);
	}

}