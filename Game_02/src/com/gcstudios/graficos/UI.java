package com.gcstudios.graficos;


import com.gcstudios.main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 18));
        g.drawString("frutas: " + Game.frutas_atual + "/" + Game.quantidade_frutas, 30, 30);
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("arial", Font.BOLD, 35));
        g.drawString("Game Over" ,145, 100);
        g.drawString("Restar: press Enter" ,110, 225);
        g.drawString("Quit: press Esc" ,110, 270);
    }

}
