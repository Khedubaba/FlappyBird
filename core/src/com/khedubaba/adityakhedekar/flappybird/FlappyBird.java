package com.khedubaba.adityakhedekar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, topTube, bottomTube;
//	ShapeRenderer mShapeRenderer;

	Texture[] bird;
	int flapState = 0;
	int pause = 0;
	float birdY = 0f;
	float velocity = 0f;
	float gravity = 2f;
	int gameState = 0;
	float gap = 450f;

	Circle birdCircle;
	Rectangle[] topTubeRectangles, bottomTubeRectangles;

	float maxTubeOffset;
	Random mRandom;
	float tubeVelocity = 4f;
	int numOfTube = 4;
	float[] tubeX = new float[numOfTube];
	float[] tubeOffset = new float[numOfTube];
	float distBtwTubes;

	int score = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

//		mShapeRenderer = new ShapeRenderer();

		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2f - bird[0].getHeight()/2f;
		birdCircle = new Circle();

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight()/2f - gap/2f - 100;
		mRandom = new Random();
		distBtwTubes = Gdx.graphics.getWidth() * 3f / 4f;
		topTubeRectangles = new Rectangle[numOfTube];
		bottomTubeRectangles = new Rectangle[numOfTube];

		for (int i = 0; i < numOfTube; i++){
			tubeOffset[i] = (mRandom.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2f - topTube.getWidth()/2f +  Gdx.graphics.getWidth() + i * distBtwTubes;
			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//Game Satate code
		if (gameState != 0){

			if (Gdx.input.justTouched()){
				velocity = -30;
			}

			for (int i = 0; i < numOfTube; i++) {
				if (tubeX[i] < - topTube.getWidth()){
					tubeX[i] += numOfTube * distBtwTubes;
					tubeOffset[i] = (mRandom.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				}
				else{
					tubeX[i] = tubeX[i] - tubeVelocity;
					if (tubeX[i] < Gdx.graphics.getWidth()){

					}
				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2f + gap / 2f + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2f - gap / 2f - bottomTube.getHeight() + tubeOffset[i]);

				//Setting Rectangles for tubes
				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2f + gap / 2f + tubeOffset[i],
						topTube.getWidth(), topTube.getHeight());

				bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2f - gap / 2f - bottomTube.getHeight() + tubeOffset[i],
						bottomTube.getWidth(), bottomTube.getHeight());
			}

			if (birdY > 25 || velocity < 0){
				velocity = velocity + gravity;
				birdY -= velocity;
			}

		}
		else{
			if (Gdx.input.justTouched()){
				Gdx.app.log("Touched: ", "Yes");
				gameState = 1;
			}
		}

		//Flappy bird wing animation
		if (pause < 6){
			pause++;
		}
		else{
			pause = 0;
			if (flapState == 0){
				flapState = 1;
			}
			else {
				flapState = 0;
			}
		}

		batch.draw(bird[flapState], Gdx.graphics.getWidth()/2f - bird[flapState].getWidth()/2f, birdY);
		batch.end();

		birdCircle.set(Gdx.graphics.getWidth() / 2f, birdY + bird[flapState].getHeight()/2f, bird[flapState].getWidth()/2f);

//		mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		mShapeRenderer.setColor(Color.RED);
//		mShapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		for (int i = 0; i < numOfTube; i++) {
//			mShapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2f + gap / 2f + tubeOffset[i],
//					topTube.getWidth(), topTube.getHeight());
//
//			mShapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2f - gap / 2f - bottomTube.getHeight() + tubeOffset[i],
//					bottomTube.getWidth(), bottomTube.getHeight());

			if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])){
				Gdx.app.log("Collison: ", "Yes");
			}
		}
//		mShapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		bird[0].dispose();
		bird[1].dispose();
	}
}
