import linalg.*;
import java.util.ArrayList;

public class Pandemic{

  private static final int noCities = 48;
  private static final String[] cities = new String[] {
    "SanFran", "Chicago", "Atlanta", "Montreal", "DC", "NYC", "Madrid", "London", "Paris", "Essen",
    "Milan", "St.Petersburg", "LA", "MexicoCity", "Lima", "Santiago", "Bogota", "Miami", "BuenosAires",
    "SaoPaulo", "Lagos", "Kinshasha", "Johannesburg", "Khartoum", "Algiers", "Cairo", "Istanbul", "Baghdad",
    "Moscow", "Riyadh", "Tehran", "Karachi", "Mumbai", "Delhi", "Chennai", "Kolkata", "Jakarta", "Bangkok",
    "HoChiMinh", "HongKong", "Shanghai", "Beijing", "Seoul", "Taipei", "Manila", "Sydney", "Osaka", "Tokyo"
  };

  public static void main(String args[]){
    Network board = board();
    System.out.println(board.deepToString());
    MarkovChain mc = new MarkovChain(board);
    System.out.println(mc.sortedSteadyStateToString());
    System.out.println(mc.getSteadyState().sum());
  }

  public static Network board(){
    EvenNetwork<Node> nw = new EvenNetwork<Node>();
    ArrayList<Node> al = new ArrayList<Node>(noCities);
    // blues
    al.add(new Node("SanFran", new String[] {"LA","Chicago","Manila","Tokyo"}));
    al.add(new Node("Chicago", new String[] {"SanFran","LA","MexicoCity","Atlanta","Montreal"}));
    al.add(new Node("Atlanta", new String[] {"Chicago","Miami","DC"}));
    al.add(new Node("Montreal", new String[] {"Chicago","NYC","DC"}));
    al.add(new Node("DC", new String[] {"NYC","Miami","Atlanta","Montreal"}));
    al.add(new Node("NYC", new String[] {"DC","Montreal","London","Madrid"}));
    al.add(new Node("Madrid", new String[] {"NYC","London","SaoPaulo","Paris","Algiers"}));
    al.add(new Node("London", new String[] {"NYC","Madrid","Algiers","Milan","Essen"}));
    al.add(new Node("Paris", new String[] {"London","Madrid","Algiers","Milan","Essen"}));
    al.add(new Node("Essen", new String[] {"Paris","St.Petersburg","Milan","London"}));
    al.add(new Node("Milan", new String[] {"Paris","Istanbul","Essen"}));
    al.add(new Node("St.Petersburg", new String[] {"Essen","Istanbul","Moscow"}));
    // yellows
    al.add(new Node("LA", new String[] {"SanFran","MexicoCity","Chicago","Sydney"}));
    al.add(new Node("MexicoCity", new String[] {"LA","Miami","Chicago","Bogota","Lima"}));
    al.add(new Node("Lima", new String[] {"Santiago","Bogota","MexicoCity"}));
    al.add(new Node("Santiago", new String[] {"Lima"}));
    al.add(new Node("Bogota", new String[] {"MexicoCity","Miami","Lima","SaoPaulo","BuenosAires"}));
    al.add(new Node("Miami", new String[] {"DC","Atlanta","MexicoCity","Bogota"}));
    al.add(new Node("BuenosAires", new String[] {"SaoPaulo","Bogota"}));
    al.add(new Node("SaoPaulo", new String[] {"Bogota","BuenosAires","Madrid","Lagos"}));
    al.add(new Node("Lagos", new String[] {"SaoPaulo","Kinshasha","Khartoum"}));
    al.add(new Node("Kinshasha", new String[] {"Lagos","Khartoum","Johannesburg"}));
    al.add(new Node("Johannesburg", new String[] {"Kinshasha","Khartoum"}));
    al.add(new Node("Khartoum", new String[] {"Cairo","Lagos","Kinshasha","Johannesburg"}));
    // blacks
    al.add(new Node("Algiers", new String[] {"Madrid","Paris","Istanbul","Cairo"}));
    al.add(new Node("Cairo", new String[] {"Algiers","Khartoum","Istanbul","Riyadh","Baghdad"}));
    al.add(new Node("Istanbul", new String[] {"Milan","St.Petersburg","Algiers","Cairo","Moscow","Baghdad"}));
    al.add(new Node("Baghdad", new String[] {"Istanbul","Tehran","Karachi","Riyadh","Cairo"}));
    al.add(new Node("Moscow", new String[] {"St.Petersburg","Istanbul","Tehran"}));
    al.add(new Node("Riyadh", new String[] {"Baghdad","Karachi","Cairo"}));
    al.add(new Node("Tehran", new String[] {"Moscow","Baghdad","Karachi","Delhi"}));
    al.add(new Node("Karachi", new String[] {"Tehran","Baghdad","Riyadh","Mumbai","Delhi"}));
    al.add(new Node("Mumbai", new String[] {"Chennai","Karachi","Delhi"}));
    al.add(new Node("Delhi", new String[] {"Mumbai","Tehran","Karachi","Kolkata","Chennai"}));
    al.add(new Node("Chennai", new String[] {"Delhi","Mumbai","Bangkok","Jakarta","Kolkata"}));
    al.add(new Node("Kolkata", new String[] {"Delhi","HongKong","Bangkok","Chennai"}));
    // reds
    al.add(new Node("Jakarta", new String[] {"HoChiMinh","Chennai","Sydney","Bangkok"}));
    al.add(new Node("Bangkok", new String[] {"Kolkata","Chennai","HongKong","HoChiMinh","Jakarta"}));
    al.add(new Node("HoChiMinh", new String[] {"Manila","Bangkok","HongKong","Jakarta"}));
    al.add(new Node("HongKong", new String[] {"Kolkata","Bangkok","HoChiMinh","Taipei","Shanghai","Manila"}));
    al.add(new Node("Shanghai", new String[] {"HongKong","Seoul","Beijing","Tokyo","Taipei"}));
    al.add(new Node("Beijing", new String[] {"Seoul","Shanghai"}));
    al.add(new Node("Seoul", new String[] {"Beijing","Tokyo","Shanghai"}));
    al.add(new Node("Taipei", new String[] {"Osaka","Manila","HongKong","Shanghai"}));
    al.add(new Node("Manila", new String[] {"Taipei","Sydney","HongKong","HoChiMinh","SanFran"}));
    al.add(new Node("Sydney", new String[] {"Manila","Jakarta","LA"}));
    al.add(new Node("Osaka", new String[] {"Tokyo","Taipei"}));
    al.add(new Node("Tokyo", new String[] {"Osaka","SanFran","Seoul","Shanghai"}));

    nw.addNodes(al);

    return nw;
  }

  public static Vector city(String neighbors, ArrayList<Vector> al){
    double[] vals = new double[noCities];
    for (int n = 0; n < noCities; n++){
      if (neighbors.indexOf(cities[n]) == -1){
        vals[n] = 0;
      }else{
        vals[n] = 1;
      }
    }
    Vector ci = new Vector(vals);
    ci.stochasticize();
    al.add(ci);
    return ci;
  }

  public static Vector city(String neighbors){
    double[] vals = new double[noCities];
    for (int n = 0; n < noCities; n++){
      if (neighbors.indexOf(cities[n]) == -1){
        vals[n] = 0;
      }else{
        vals[n] = 1;
      }
    }
    Vector ci = new Vector(vals);
    ci.stochasticize();
    return ci;
  }

}
