package com.pmm.games.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float x;

    private float y;
    private float width, height;
    private float speed;

    private Texture texture;

    private final Rectangle bounds;

    public Player(Texture texture, float x, float y, float width, float height, float speed) {
        this.texture= texture;
        this.x= x;
        this.y= y;
        this.width= width;
        this.height= height;
        this.speed= speed;

        bounds= new Rectangle(
            x + width/8,
            y + height/8,
            width - width/4,
            height - height/4);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void moveLeft() {
        x-= speed;

        float limit= 0;
        if(x < limit) x= limit;

        updateBounds();
    }

    public void moveRight() {
        x+= speed;

        float limit= Gdx.graphics.getWidth() - width;
        if(x > limit) x= limit;

        updateBounds();
    }

    public void moveUp() {
        y+= speed;

        updateBounds();
    }

    public void moveDown() {
        y-= speed;

        updateBounds();
    }

    private void updateBounds() {
        bounds.setPosition(
            x + width/8,
            y + height/8);
    }

    public void dispose(){
        texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
