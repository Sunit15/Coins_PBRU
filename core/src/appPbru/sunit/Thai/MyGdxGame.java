package appPbru.sunit.Thai;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture, pigTexture;
	private OrthographicCamera objOrthographicCamera; //ปรับ scale ให้ตามขนาดจอ
	private BitmapFont nameBitmapFont;
	private int xCloundAnInt, yCloundAnInt = 600;
	private boolean cloundABoolean = true;
	private Rectangle pigRectangle;
	private Vector3 objVector3;
	private Sound pigSound;


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

		//Setup clound
		cloudTexture = new Texture("cloud.png");
		//Setup pig
		pigTexture = new Texture("pig.png");

		//setup rectangle
		pigRectangle = new Rectangle();
		// ตำแหน่งเริ่มต้นของหมู
		pigRectangle.x = 568;
		pigRectangle.y = 100;
		pigRectangle.width = 64;
		pigRectangle.height = 64;

		//setup pig sound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));

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

		//drawable clound
		batch.draw(cloudTexture, xCloundAnInt,yCloundAnInt);

		//DrawableBitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 600);

		//Drawable pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		batch.end();

		//Move clound
		moveClound();

		//Active When Touch Screen
		activeTouchScreen();

	}    //render ไว้วนลูป

	private void activeTouchScreen() {
		//ถ้านิ้วโดนจอ ทำงาน
		if (Gdx.input.isTouched()) {

			//Sound Effect Pig
			pigSound.play();
			
			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (objVector3.x < 600) {
				if (pigRectangle.x < 0) {
					pigRectangle.x = 0;
				} else {
					pigRectangle.x -= 10;
				}

			} else {
				if (pigRectangle.x > 1136) {
					pigRectangle.x = 1136;
				} else {
					pigRectangle.x += 10;
				}

			}

		} // if

	}	// activeTouchScreen

	private void moveClound() {

		if (cloundABoolean) {
			if (xCloundAnInt < 937) {
				xCloundAnInt += 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloundABoolean = !cloundABoolean;
			}
		} else {
			if (xCloundAnInt > 0) {
				xCloundAnInt -= 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloundABoolean = !cloundABoolean;
			}

		}

	}	//move clound
}    //main class
