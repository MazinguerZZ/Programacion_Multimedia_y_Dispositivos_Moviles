package com.pmm.games.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle {

    private float x;
    private float y;
    private float width, height;
    private float speed;

    private Texture texture;

    private final Rectangle bounds;

    public Obstacle(Texture texture, float x, float y, float width, float height, float speed) {
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

    public void update() {
        y-= speed;

        bounds.setPosition(
            x + width/8,
            y + height/8);

    }

    public boolean isOutOfScreen() {
        return (y + height) < 0;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
