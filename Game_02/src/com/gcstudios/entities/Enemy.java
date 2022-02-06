package com.gcstudios.entities;

import com.gcstudios.main.Game;
import com.gcstudios.world.AStar;
import com.gcstudios.world.Camera;
import com.gcstudios.world.Vector2i;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Enemy extends Entity {

    public boolean ghostMode = false;
    public int ghostFrame = 0;
    public int nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*5;

    public Enemy(int x, int y, int width, int height, int speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
	}

    public void tick() {
        depth = 0;
        if (this.ghostMode == false) {
            if (path == null || path.size() == 0) {
                Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
                path = AStar.findPath(Game.world, start, end);
            }

            if (new Random().nextInt(100) < 85)
                followPath(path);

            if (x % 16 == 0 && y % 16 == 0) {
                if (new Random().nextInt(100) < 10) {
                    Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                    Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
                    path = AStar.findPath(Game.world, start, end);
                }
            }
        }

        this.ghostFrame ++;
        if (this.ghostFrame == this.nextTime) {
             this.nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*5;
            this.ghostFrame = 0;
            if (this.ghostMode == false) {
                System.out.println("MODO FANTASMA");
                this.ghostMode = true;
            } else {
                this.ghostMode = false;
            }
        }

    }


    public void render(Graphics g) {
        if (!this.ghostMode) {
            super.render(g);
        } else {
            g.drawImage(Entity.GHOST, this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }


}
