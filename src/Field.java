import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Field extends JPanel implements ActionListener{

    private Dimension dimension;
    private Timer timer;
    private int scale;
    private int w , h;
    private int figureO[][] = {{0, 0},{ 1, 0}, {0, 1}, {1, 1}},//последняя координата каждой фигуры - точка вращения
                figureI[][] = {{0, 0},{ 1, 0}, {3, 0}, {2, 0}},//фигура O и I вращаются вокруг точки, поэтому в методе rotate() идет проверка на соотвестиве фигуре
                figureT[][] = {{0, 1}, {2, 1}, {1, 0}, {1, 1}},//остальный фигуры вращаются вокруг блока заданного последней координатой.
                figureJ[][] = {{0, 2}, {1, 2}, {1, 0}, {1, 1}},
                figureL[][] = {{0, 0}, {0, 2}, {1, 2}, {0, 1}},
                figureS[][] = {{0, 0}, {0, 1}, {1, 2}, {1, 1}},
                figureZ[][] = {{1, 0}, {1, 1}, {0, 2}, {0, 1}},
                figure[][] = new int[4][2];
    private int startX;
    private int startY;

    protected boolean left = false,
                    right = false,
                    boost = false,
                    rot = false,
                    game = true;

    private ArrayList<Integer> floorX;
    private ArrayList<Integer> floorY;

    private int rnd;



    public Field(int w, int h){
        this.w = w;
        this.h = h;
        dimension = new Dimension( w, h);
        setSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);

        floorX = new ArrayList<Integer>();
        floorY = new ArrayList<Integer>();
        timer = new Timer(300 ,this);
        scale = 20;
        gameInit();
    }

    public void gameInit(){
        startX = 3;
        startY = 2;
        timer.start();
        spawnFigure();
    }

    public void spawnFigure(){

        for (int i = 0 ; i< 4;i++){
            figure[i][0] = 0;
            figure[i][1] = 0;

        }

        rnd  = new Random().nextInt(4);
        //rnd = 0;
        switch (rnd){
            case 0 :  swap(figureO); break;
            case 1 :  swap(figureT); break;
            case 2 :  swap(figureI); break;
            case 3 :  swap(figureJ); break;
            case 4 :  swap(figureL); break;
            case 5 :  swap(figureZ); break;
            case 6 :  swap(figureS); break;
            default:  break;
        }
        for (int i = 0; i < new Random().nextInt(4); i++) {
            rotate();
        }
        for (int i = 0; i < 4 ;i++){
            figure[i][0] = figure[i][0] + startX;
            figure[i][1] = figure[i][1] + startY;
            //System.out.println( "X: " + figure[i][0] + ", Y: " + figure[i][1]);
        }




    }

    public void swap(int[][]f){
        for (int i = 0; i < 4 ;i++){
            figure[i][0] = f[i][0];
            figure[i][1] = f[i][1];
            //System.out.println( "X: " + figureO[i][0] + ", Y: " + figureO[i][1]);
        }
    }

    private void move() {
        for (int i = 0; i < 4 ;i++){
            figure[i][1] += 1;
        }

        for (int i = 0; i < 4 ;i++) {
            if (figure[i][1] == 25){
                for(int j = 0; j < 4; j++){
                    //System.out.println("Im here");
                    figure[j][1] -= 1;
                    //System.out.println(j +". " +"X: " + figure[j][0] + ", Y: " +figure[j][1]);
                    floorX.add(figure[j][0]);
                    floorY.add(figure[j][1]);
                }
                delete();
                spawnFigure();
                break;
            }
            for (int bk = 0 ; bk < floorY.size();bk ++ ){

                if (figure[i][0] == (int)floorX.get(bk) && figure[i][1] == (int)floorY.get(bk)){
                    for(int j = 0; j < 4; j++){
                        //System.out.println("Im here");
                        figure[j][1] -= 1;
                        //System.out.println(j +". " +"X: " + figure[j][0] + ", Y: " +figure[j][1]);
                        floorX.add(figure[j][0]);
                        floorY.add(figure[j][1]);
                    }
                    delete();
                    spawnFigure();
                    break;
                }
            }
        }

        if (boost){
            timer.setDelay(150);
            System.out.println("DELAY: " + timer.getDelay());
        }
        else {
            timer.setDelay(300);
            System.out.println("DELAY ELSE: " + timer.getDelay());
        }
        if (left){
            for (int i = 0; i < 4; i++){
                figure[i][0] -= 1;
            }

            for (int i = 0; i < 4 ;i++) {
                if (figure[i][0] < 0){
                    for(int j = 0; j < 4; j++){
                        figure[j][0] += 1;
                    }
                }
            }

            repaint();

        }
        if (right){
            for (int i = 0; i < 4; i++){
                figure[i][0] += 1;
            }

            for (int i = 0; i < 4 ;i++) {
                if (figure[i][0] >= 10){
                    for(int j = 0; j < 4; j++){
                        figure[j][0] -= 1;
                    }
                }
            }
            repaint();
        }
        if (rot) {
            rotate();
            rot = false;
            repaint();
        }

        for (int i = 0; i < 4 ;i++) {
            if (figure[i][0] == 0){
                game = false;
                timer.stop();
            }

        }

    }

    private void delete() {

        int count = 0;
        ArrayList delX = new ArrayList();
        ArrayList delY = new ArrayList();


        for (int i = 0; i < floorY.size(); i++){
            for (int j = 0; j < floorY.size(); j++){
                if (floorY.get(i) == floorY.get(j)) {
                    count++;
                    delX.add(floorX.get(j));
                    delY.add(floorY.get(j));
                }
            }
            if (count == 10){
                System.out.println("Ряд!");
                for (int q = 0; q < 10; q++){
                    floorX.remove(delX.get(q));
                    floorY.remove(delY.get(q));
                }
            }
            else {
                System.out.println(count +" под удаление" +delY.size());
                count = 0;

            }
            delX.clear();
            delY.clear();
            break;
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0,w,h);
        for (int i = 0; i < 4; i++){
                g.setColor(Color.GREEN);
                g.fillRect(figure[i][0] *scale,figure[i][1] *scale, scale-1,scale-1);
        }

        if (!floorX.isEmpty() && !floorY.isEmpty()) {
            for (int i = 0; i < floorX.size(); i++) {
                g.setColor(Color.RED);
                //System.out.println("X: " +  floorX.get(i) + ", Y: " + floorY.get(i));
                g.fillRect((int)floorX.get(i) * scale, (int)floorY.get(i) * scale, scale - 1, scale - 1);
            }

        }
        if (game ==false)
        {

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 25; j++) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * scale, j * scale, scale - 1, scale - 1);
                }
            }

            g.setColor(Color.BLACK);
            g.drawString("ИГРА ОКОНЧЕНА", 50, 50);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    public void rotate(){
        int[][] tmp = new int[4][2];

        int rotPointX = figure[3][0];
        int rotPointY = figure[3][1];


        System.out.println("X: " + rotPointX + ", Y " + rotPointY);
        for(int j = 0; j < 4; j++){
            tmp[j][0] = rotPointX  -  (figure[j][0] - rotPointX) * 0 - (figure[j][1] - rotPointY) * 1 ;
            tmp[j][1] = rotPointY  -  (figure[j][1] - rotPointY) * 0 + (figure[j][0] - rotPointX) * 1 ;

            if (rnd == 0 || rnd == 2) {
                tmp[j][0] -= 1;


            }
            //System.out.println("X: " +  tmp[j][0] + ", Y: " + tmp[j][1]);
            /*
               X = x0 + (x - x0) * cos(a) - (y - y0) * sin(a);
               Y = y0 + (y - y0) * cos(a) + (x - x0) * sin(a);

               cos 90dig = 0
               sin 90dig = 1
            */
        }
        for (int i = 0 ; i< 4;i++){
            figure[i][0] = 0;
            figure[i][1] = 0;

        }
        for(int j = 0; j < 4; j++){
            figure[j][0] = tmp[j][0] ;
            figure[j][1] = tmp[j][1] ;
            System.out.println("X: " +  figure[j][0] + ", Y: " + figure[j][1]);
        }
        repaint();
    }
}
