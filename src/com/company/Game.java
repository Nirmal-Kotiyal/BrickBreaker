package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBrick = 100;
    private Timer timer;
    private int delay = 8;

    private int playerX=310;
    private int ballPosX=120;
    private int ballPosY=350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private MapGenerator mapGenerator;
    private PowerUps powerUps;
    private int counter = 3000;
    private boolean value = false;

    public Game(){
    mapGenerator = new MapGenerator(10,10);
    powerUps = new PowerUps();
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay,this);
    timer.start();
    }


    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //bricks display
        mapGenerator.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(682,0,3,592);

        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        //Game Won

        if(totalBrick<=0){
            play=false;
            ballYDir =0;
            ballXDir=0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won Congratulation",190,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart ",230,350);
        }


        // Game Lost
        if(ballPosY>570){
            play= false;
            ballYDir =0;
            ballXDir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game over , Score: "+score,190,300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart ",230,350);
        }

        //Draw PowerUps
        if(score>10&&powerUps.SlowPowerInGame==false){
            powerUps.SlowPowerUp(g,10);
            powerUps.positionY+=3;
//            g.setColor(Color.blue);
//            g.setFont(new Font("serif",Font.BOLD,25));
//            g.drawString(""+counter,400,30);
        }

        //SlowMo PowerUp Started
        if(value==true) {
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Slow Mo Active!!!!",200,300);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    timer.start();

    if(play==true){
        if(new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
            ballYDir = -ballYDir;
        }

        if(new Rectangle(10,powerUps.positionY,25,25).intersects(new Rectangle(playerX,550,100,8))){
            value = true;
            timer.setDelay(16);
            powerUps.SlowPowerInGame=true;
            powerUps.positionY=0;
            Timer timer1 = new Timer(15000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.setDelay(8);
                }
            });
            Timer timer2 = new Timer(800, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    value = false;
                }
            });
            timer2.start();
            timer1.start();
        }
       A: for (int i = 0; i <mapGenerator.map.length ; i++) {
            for (int j = 0; j <mapGenerator.map[i].length; j++) {
                if(mapGenerator.map[i][j]>0){
                    int brickX = j*mapGenerator.brickWidth+80;
                    int brickY = i*mapGenerator.brickHeight+50;
                    int brickWidth = mapGenerator.brickWidth;
                    int brickHeight = mapGenerator.brickHeight;
                    Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                    Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                    Rectangle brickRect = rect;
                    if(ballRect.intersects(brickRect)){
                        mapGenerator.setBrickValue(0,i,j);
                        totalBrick--;
                        score+=5;

                        if(ballPosX+19<=brickRect.x||ballPosX+1>=brickRect.x+brickRect.width){
                            ballXDir = -ballXDir;
                        }
                        else{
                            ballYDir = - ballYDir;
                        }
                        break A;
                    }

                }
            }
        }


        ballPosX +=ballXDir;
        ballPosY +=ballYDir;
        if(ballPosX<=0){
            ballXDir=-ballXDir;
        }
        if(ballPosY<=0){
            ballYDir=-ballYDir;
        }
        if(ballPosX>=660){
            ballXDir=-ballXDir;
        }
    }
    repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_D){
            if(playerX>=600){
                playerX = 600;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            if(playerX<=10){
                playerX = 10;
            }
            else{
                moveLeft();
            }
        }

        if(play==false&&e.getKeyCode()==KeyEvent.VK_ENTER){
            playerX=310;
            ballPosX=120;
            ballPosY=350;
            ballXDir = -1;
            ballYDir = -2;
            score = 0;
            mapGenerator = new MapGenerator(10,10);
            totalBrick=100;
        }


    }

    private void moveLeft() {
    play =true;
    playerX-=20;
    }

    private void moveRight() {
        play = true;
        playerX+=20;
    }

}
