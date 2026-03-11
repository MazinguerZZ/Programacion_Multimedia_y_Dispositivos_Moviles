package com.pmm.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pmm.games.objects.Obstacle;
import com.pmm.games.objects.Player;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SpaceEscape extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    // ESTADOS
    private GameState gameState, nextGameState;
    private Texture gameLogo;
    private boolean gameStateChanged;

    // OBJETOS
    private Player player;
    private Array<Obstacle> obstacles;
    private Texture obstacleTexture;
    private float spawnTimer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("images/libgdx.png");
        font= new BitmapFont();

        gameLogo= new Texture("images/space_escape.png");
        gameState= GameState.MENU;
        gameStateChanged= false;

        Texture playerTexture= new Texture("images/player_texture.png");
        float x= (Gdx.graphics.getWidth() - 64)/2.0f;
        player= new Player(playerTexture, x, 50, 64, 64, 5f);

        // Inicializamos los asteroides
        obstacles= new Array<>();
        obstacleTexture= new Texture("images/asteroid_texture.png");
        spawnTimer= 0;

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        actualizarObjetos();

        gestionarInputs();

        batch.begin();

        representacionEstado();

        batch.end();

        if(gameStateChanged) {
            if(nextGameState==GameState.MENU && gameState==GameState.GAME_OVER){
                obstacles.clear();
            }

            gameState= nextGameState;
            gameStateChanged= false;
        }
    }

    private void gestionarInputs() {
        switch(gameState) {
            case MENU:
                if(Gdx.input.isTouched()){
                    nextGameState= GameState.PLAYING;
                    gameStateChanged= true;
                }

                if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    nextGameState= GameState.PLAYING;
                    gameStateChanged= true;
                }
                break;
            case PLAYING:

                if(Gdx.input.isTouched()){
                    float deltaX= Gdx.input.getX() - player.getX();
                    if(deltaX > 0) player.moveRight();
                    else if(deltaX < 0) player.moveLeft();
                }


                if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    player.moveLeft();
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.moveRight();
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    player.moveUp();
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    player.moveDown();
                }

                break;

            case GAME_OVER:

                if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    nextGameState= GameState.MENU;
                    gameStateChanged= true;
                }

                break;
        }
    }

    private void actualizarObjetos() {

        float deltaTime= Gdx.graphics.getDeltaTime();

        spawnTimer+= deltaTime;

        if(spawnTimer > 1.2f) {
            addObstacle();
            spawnTimer= 0;
        }

        for(int i= obstacles.size - 1; i >= 0; i--) {
            Obstacle obstacle= obstacles.get(i);
            obstacle.update();

            if(obstacle.isOutOfScreen()) {
                obstacles.removeIndex(i);
            }
        }

    }

    private void addObstacle() {
        float width= 48;
        float heigth= 48;

        float x= MathUtils.random(0, Gdx.graphics.getWidth() - width);
        float y= Gdx.graphics.getHeight();

        obstacles.add(new Obstacle(obstacleTexture, x, y, width, heigth, 3f));
    }

    private void representacionEstado() {

        int screenWidth= Gdx.graphics.getWidth();
        int screenHeight= Gdx.graphics.getHeight();

        switch(gameState) {
            case MENU:
                batch.draw(gameLogo, 0, 0, screenWidth, screenHeight);
                font.draw(batch, "Pulsa ESPACIO para comenzar", 100, 100);
                break;
            case PLAYING:
                ScreenUtils.clear(Color.BLACK);
                batch.draw(image, 140, 210);
                font.setColor(Color.WHITE);
                font.draw(batch, "Pulsa ESC para terminar", 10, screenHeight-10);
                font.setColor(Color.RED);
                font.draw(batch, "Jugando...", (float)(screenWidth*0.45), 100);

                player.render(batch);

                for(int i= obstacles.size - 1; i >= 0; i--) {
                    Obstacle obstacle= obstacles.get(i);
                    obstacle.render(batch);
                }

                for(Obstacle obstacle : obstacles) {
                    if (obstacle.getBounds().overlaps(player.getBounds())) {
                        gameState= GameState.GAME_OVER;
                        gameStateChanged= false;
                        break;
                    }
                }


                break;

            case GAME_OVER:
                ScreenUtils.clear(Color.RED);
                font.setColor(Color.YELLOW);
                font.draw(batch, "GAME OVER", 100, 150);
                font.draw(batch, "Pulsa ENTER para volver al menú inicial", 100, 100);
                break;
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        font.dispose();
        player.dispose();
        obstacleTexture.dispose();
    }
}
