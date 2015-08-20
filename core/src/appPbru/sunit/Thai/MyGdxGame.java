package appPbru.sunit.Thai;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture;
	private OrthographicCamera objOrthographicCamera; //ปรับ scale ให้ตามขนาดจอ
	private BitmapFont nameBitmapFont;


	@Override
	public void create() {
		batch = new SpriteBatch();

		//คือการกำหนดขนาดของจอที่ต้องการ Setup screen
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);
		//setup wallpaper
		wallpaperTexture = new Texture("wallpapers_a_02.png");

		//Setup BitMapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(Color.WHITE);
		nameBitmapFont.setScale(4);


	}  //create เอาไว้กำหนดค่า

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Setup screen
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//เอาไว้วาด Object
		batch.begin();

		//Drawable Wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//DrawableBitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 600);

		batch.end();
	}    //render ไว้วนลูป
}    //main class
