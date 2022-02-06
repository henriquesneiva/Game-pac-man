package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity {

    public boolean right, up, left, down;
    public BufferedImage sprite_left;
    public int lastDir = 1;
    public static boolean isGameOver = false;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        this.sprite_left = Game.spritesheet.getSprite(48, 0, 16, 16);
    }

    public void tick() {
        depth = 1;
        if (right && World.isFree((int) (x + speed), this.getY())) {
            x += speed;
            this.lastDir = 1;
        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            x -= speed;
            this.lastDir = -1;
        }
        if (up && World.isFree(this.getX(), (int) (y - speed))) {
            y -= speed;
        } else if (down && World.isFree(this.getX(), (int) (y + speed))) {
            y += speed;
        }
        this.verificarComeuFruta();
        if (Game.quantidade_frutas == Game.frutas_atual) {
            World.restartGame();
            this.isGameOver = false;
        }
    }

    public void verificarComeuFruta() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Food) {
                if (Entity.isColidding(this, current)) {
                    Game.frutas_atual++;
                    Game.entities.remove(i);
                    return;
                }
            }
        }
    }

    public void verificarColisaoInimigo(Graphics g) {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Enemy) {
                if (Entity.isColidding(this, current)) {
                    World.gameOver(g);
                    this.isGameOver = true;
                    return;
                }
            }
        }
    }

    public void render(Graphics g) {
        this.verificarColisaoInimigo(g);
        if (this.lastDir == 1) {
            super.render(g);
        } else {
            g.drawImage(sprite_left, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }

}
