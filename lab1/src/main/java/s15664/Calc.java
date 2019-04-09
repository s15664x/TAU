package s15664;


public class Calc {
    public double sum(double x, double y) {
        return x+y;
    }
    public double timesTen(double x) {

        for(int i=0; i<10; i++) {
            x+=x;
        }
        return x;
    }
}