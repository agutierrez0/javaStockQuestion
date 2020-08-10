import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
        Main.matchBenchmark(line);
        }
    }
 
    public static void matchBenchmark(String input) {
        String arrBoth[] = input.split(":");
        String beginningEquity[] = arrBoth[0].split("\\|");
        String endingEquity[] = arrBoth[1].split("\\|");

        List<Equity> beginningObjs = new ArrayList<Equity>();
        List<Equity> endingObjs = new ArrayList<Equity>();
        String objs[] = new String[0];
        for (String a : beginningEquity) {
            objs = a.split(",");
            beginningObjs.add(new Equity(objs[0], objs[1], "", Integer.parseInt(objs[2])));
        }

        for (String a : endingEquity) {
            objs = a.split(",");
            endingObjs.add(new Equity(objs[0], objs[1], "", Integer.parseInt(objs[2])));
        }

        List<Equity> returnList = new ArrayList<Equity>();
        List<Equity> extraList = new ArrayList<Equity>();
        for (Equity e : beginningObjs) {
            for (Equity f : endingObjs) {
                if (e.name.equals(f.name) && e.type.equals(f.type)) {
                    if (f.quantity > e.quantity)
                        returnList.add(new Equity(e.name, e.type, "BUY", f.quantity - e.quantity));
                    else if (f.quantity < e.quantity)
                        returnList.add(new Equity(e.name, e.type, "SELL", e.quantity - f.quantity));
                    else
                        continue;
                }
                else
                    extraList.add(new Equity(f.name, f.type, "", f.quantity));
            }
        }
        
        for (Equity f : extraList) {
            Equity thing = returnList.stream()
            .filter(c -> c.type.equals(f.type) && c.name.equals(f.name))
            .findAny()
            .orElse(null);

            Equity intlCheck = beginningObjs.stream()
            .filter(c -> c.type.equals(f.type) && c.name.equals(f.name))
            .findAny()
            .orElse(null);

            f.action = "BUY";
            if (thing == null && intlCheck == null)
                returnList.add(f);
        }

        for (Equity e : returnList) {
            System.out.print(e.action + ",");
            System.out.print(e.name + ",");
            System.out.print(e.type + ",");
            System.out.print(e.quantity);
            System.out.println();
        }
    
    }
    public static class Equity {
        String name;
        String type;
        String action;
        int quantity;

        public Equity() {}
        public Equity(String name, String type, String action, int quantity) {
            this.name = name;
            this.quantity = quantity;
            this.type = type;
            this.action = action;
        }
    }
}