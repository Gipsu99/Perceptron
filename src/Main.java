import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        double alfa = 0.01; //wspolczynnik uczenia

        Perceptron p = new Perceptron();

        p.train(args[0],alfa);
        System.out.println(p.test(args[1]) + "%");
        Scanner sc = new Scanner(System.in);

        while(true) {
           String a =  sc.nextLine();

           if(a.split(",").length == p.weights.length) {
               System.out.println(p.testLine(a));
           } else {
               System.out.println("invalid columns");
           }

        }


    }
}
