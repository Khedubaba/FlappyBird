package com.khedubaba.adityakhedekar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, topTube, bottomTube;

	Texture[] bird;
	int flapState = 0;
	int pause = 0;

	float birdY = 0f;
	float velocity = 0f;
	float gravity = 2f;
	int gameState = 0;
	float gap = 450f;

	float maxTubeOffset;
	Random mRandom;
	float tubeVelocity = 4f;
	int numOfTube = 4;
	float[] tubeX = new float[numOfTube];
	float[] tubeOffset = new float[numOfTube];
	float distBtwTubes;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		bird = new Texture[2];
		bird[0] = new Texture("bird.png");
		bird[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2f - bird[0].getHeight()/2f;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight()/2f - gap/2f - 100;
		mRandom = new Random();
		distBtwTubes = Gdx.graphics.getWidth() * 3f / 4f;

		for (int i = 0; i < numOfTube; i++){
			tubeOffset[i] = (mRandom.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2f - topTube.getWidth()/2f +  i * distBtwTubes;
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
				}
				tubeX[i] = tubeX[i] - tubeVelocity;
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2f + gap / 2f + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2f - gap / 2f - bottomTube.getHeight() + tubeOffset[i]);
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
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		bird[0].dispose();
		bird[1].dispose();
	}
}
