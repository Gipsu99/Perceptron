import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Perceptron {
    double weights[];
    double error = Math.random()*6;
    Map<Integer,String> types = new HashMap<>();
    public  void weight(double[] weights) {
        for(int i = 0 ; i < weights.length; i++) {
            weights[i] = (Math.random()*4)-2; // (-2,2)
        }
    }

    public void  train(String perceptronTrain, double alfa) {
        int d;
        int y;
        String compare;
        int counterCol = 0;
        final double Emax = 0.02;
        double E = 0;
        try {
            String tmp[];
            BufferedReader br = new BufferedReader(new FileReader(perceptronTrain));
            String line;
            int linesTrain = 0;
            while ((line = br.readLine()) != null) {
                String b[] = line.split(",");
                counterCol = b.length;
                linesTrain++;
            }

            weights = new double[counterCol - 1];
            weight(weights);

            for(int i = 0 ; i < 3; i++) {
                E=0;
                br = new BufferedReader(new FileReader(perceptronTrain));
            while ((line = br.readLine()) != null) {

                tmp = line.split(",");

                double net = 0;
                net -= error;
                for (int j = 0; j < weights.length; j++) {
                    net += weights[j] * Double.parseDouble(tmp[j]);

                }
                if (net >= 0) {
                    y = 1;
                } else {
                    y = 0;
                }


                compare = tmp[tmp.length - 1];
                String str[] = Files.lines(Paths.get(perceptronTrain)).map(s -> s.split(",")).findFirst().get();

                if (str[tmp.length - 1].equals(compare)) {
                    d = 1;
                    types.put(1,compare);
                } else {
                    d = 0;
                    types.put(0,compare);
                }


                for (int j = 0; j < weights.length; j++) {
                    weights[j] = weights[j] + (alfa * (d - y) * Double.parseDouble(tmp[j]));

                }
                error = (double) (error - (alfa * (d - y)));
                E+=Math.pow(d-y,2);


            }
                E /= linesTrain;

                if(E<Emax) {
                    break;
                }

        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  double test(String testPerceptron) {
        double acc = 0;
        double a = 0;
        double b = 0;

        String found = "";
        String expected = "";
        int count = 0;
        try {

            String line;

            BufferedReader  br = new BufferedReader(new FileReader(testPerceptron));
            while((line = br.readLine()) != null) {
                String tmp[] = line.split(",");
                double net = 0;
                for(int i = 0 ; i < tmp.length-1; i++) {
                    net+= weights[i]*Double.parseDouble(tmp[i]);
                }
                expected = tmp[tmp.length-1];
                net-=error;

                if(net>=0) {
                    found = types.get(1);
                } else {
                    found = types.get(0);
                }

                if(found.equals(expected)) {
                    a++;
                }
                b++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        acc = (a/b)*100;
        return acc;
    }

    public String testLine(String a) {
        String found = "";
        String tmp[] = a.split(",");
        double net = 0;
        for(int i = 0 ; i < tmp.length-1; i++) {
            net+= weights[i]*Double.parseDouble(tmp[i]);
        }

        net-=error;

        if(net>=0) {
            found = types.get(1);
        } else {
            found = types.get(0);
        }

        return found;
    }
}