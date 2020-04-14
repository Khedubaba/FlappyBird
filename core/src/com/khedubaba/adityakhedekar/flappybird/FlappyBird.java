package com.khedubaba.adityakhedekar.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	float gap = 400f;

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

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//Game Satate code
		if (gameState != 0){
			batch.draw(topTube, Gdx.graphics.getWidth()/2f - topTube.getWidth()/2f, Gdx.graphics.getHeight()/2f + gap/2f);
			batch.draw(bottomTube, Gdx.graphics.getWidth()/2f - topTube.getWidth()/2f, Gdx.graphics.getHeight()/2f - gap/2f - bottomTube.getHeight());
			if (Gdx.input.justTouched()){
				velocity = -30;
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
