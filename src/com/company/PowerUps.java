package com.company;

import java.awt.*;

public class PowerUps {

    public int positionY=0;
    public boolean SlowPowerInGame= false;

    void SlowPowerUp(Graphics g,int x){
            g.setColor(Color.blue);
            g.fillOval(x,positionY,25,25);
    }
    }

