package appPbru.sunit.Thai;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture,
			pigTexture, coinsTexture, rainTexture;
	private OrthographicCamera objOrthographicCamera; //ปรับ scale ให้ตามขนาดจอ
	private BitmapFont nameBitmapFont, scoreBitmapFont, showScoreBitmapFont;
	private int xCloundAnInt, yCloundAnInt = 600;
	private boolean cloundABoolean = true, finishABoolean = false;
	private Rectangle pigRectangle,coinsRectangle, rainRectangle;
	private Vector3 objVector3;
	private Sound pigSound, waterDropSound, coinsDropSound;
	private Array<Rectangle> coinsArray, rainArray;
	private long lastDropCoins,lastDropRain;
	private Iterator<Rectangle> coinsIterator, rainIterator; // ==> Java.util
	private int scoreAnInt = 0, falseAnInt = 0, finalScoreAnInt;  //คะแนนเริ่มต้น
	private Music rainMusic, backgroundMusic;


	@Override
	public void create() {
		batch = new SpriteBatch();

		//คือการกำหนดขนาดของจอที่ต้องการ Setup screen
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);
		//setup wallpaper
		wallpaperTexture = new Texture("wallpapers_a_05.png");

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
		pigRectangle.width = 70; //64
		pigRectangle.height = 70;

		//setup pig sound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));
		//setup coins
		coinsTexture = new Texture(("coins.png"));

		//Create coinsArray
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop();

		//Setup WaterDrop
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));
		//setup coin dound
		coinsDropSound = Gdx.audio.newSound(Gdx.files.internal("dog.wav"));

		//setup scoreBitMapfont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(Color.BLACK);
		scoreBitmapFont.setScale(4);
		//setup rainTecture
		rainTexture = new Texture("droplet.png");

		//creat rain array
		rainArray = new Array<Rectangle>();
		rainRandomDrop();

		//setup rain music
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//setup background
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

		//setup ShowScore
		showScoreBitmapFont = new BitmapFont();
		showScoreBitmapFont.setColor(230, 28, 223, 255);
		showScoreBitmapFont.setScale(5);

	}  //create เอาไว้กำหนดค่า

	private void rainRandomDrop() {

		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 1136);
		rainRectangle.y = 800;
		rainRectangle.width = 64;
		rainRectangle.height = 64;

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();

	}	//rainRandomDrop

	private void coinsRandomDrop() {

		coinsRectangle = new Rectangle();
		coinsRectangle.x = MathUtils.random(0, 1136);
		coinsRectangle.y = 800;
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;
		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime(); //ถ้า randomได้เลขเดียวกันไม่เอา

	}

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
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 750);

		//Drawable pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		//drawable coins
		for (Rectangle forCoins : coinsArray) {
			batch.draw(coinsTexture, forCoins.x, forCoins.y);
		}

		// Drawable score
		scoreBitmapFont.draw(batch, "Score = " + Integer.toString(scoreAnInt), 800, 750);

		//Draw rain
		for (Rectangle forRain : rainArray) {
			batch.draw(rainTexture, forRain.x, forRain.y);
		}

		if (finishABoolean) {

			batch.draw(wallpaperTexture, 0, 0);
			showScoreBitmapFont.draw(batch, "Your score ==> " + Integer.toString(finalScoreAnInt), 500, 750);

		}

		batch.end();

		//Move clound
		moveClound();

		//Active When Touch Screen
		activeTouchScreen();

		//random Drop Coins
		randomDropCoins();

		//random Drop Rain
		randomDropRain();

		//play rain music
		rainMusic.play();

		//play background music
		backgroundMusic.play();

	}    //render ไว้วนลูป

	private void randomDropRain() {

		if (TimeUtils.nanoTime() - lastDropRain > 1E9) {
			rainRandomDrop();
		} //if

		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) {

			Rectangle myRainRectangle = rainIterator.next();
			myRainRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//when rain drop into floor
			if (myRainRectangle.y + 64 < 0) {
				waterDropSound.play();
				rainIterator.remove();

			} //if

			//When rain overlap pig
			if (myRainRectangle.overlaps(pigRectangle)) {

				scoreAnInt -= 1;
				waterDropSound.play();
				rainIterator.remove();

			}//if


		} //while

	} //randomDropRain

	private void randomDropCoins() {

		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {

			coinsRandomDrop();

		}

		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) {

			Rectangle myConsRectangle = coinsIterator.next();
			myConsRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//When Coins into Floor clear coins
			if (myConsRectangle.y + 64 < 0) {
				falseAnInt += 1;
				waterDropSound.play(); //เหรียญโดนพื้นแล้วดัง
				coinsIterator.remove();
				checkFalse();
			}	//if

			//When Coins Overlap Pig
			if (myConsRectangle.overlaps(pigRectangle)) {
				scoreAnInt += 1;
				coinsDropSound.play();
				coinsIterator.remove();

			}	//if

		}	//while loop

	}	//randomDropCoins

	private void checkFalse() {

		if (falseAnInt > 20) {
			dispose();

			if (!finishABoolean) {
				finalScoreAnInt = scoreAnInt;

				finishABoolean = true;

			}

		}//if

	}//checkFalse

	@Override
	public void dispose() {
		super.dispose();

		backgroundMusic.dispose();  //ให้เสียงเพลงหยุด
		rainMusic.dispose();
		pigSound.dispose();
		waterDropSound.dispose();
		coinsDropSound.dispose();

	}//dispose

	private void activeTouchScreen() {
		//ถ้านิ้วโดนจอ ทำงาน
		if (Gdx.input.isTouched()) {

			//Sound Effect Pig
			pigSound.play();
			
			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			//if (objVector3.x < 600) {
			if (objVector3.x < Gdx.graphics.getWidth()/2) {
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
