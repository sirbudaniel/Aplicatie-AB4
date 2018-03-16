
import java.util.Date;
import java.util.PriorityQueue;


/**
 * Clasa abstracta care descrie comportamentul unei locatii, in cazul de fata
 * clasa Oras este o subclasa a clasei Locatie si implementeaza toate aceste metode.
 * O noua clasa de exemplu Sat sau Comuna va trebui sa fie o subclasa a acesteia si sa
 * implementeze toate metodele acestea.
 * @author daniel
 */
public abstract class Locatie {


    abstract void addHotel(String s, Hotel h);

    abstract public Hotel Exista(String s);

    abstract public void show();

    abstract public void top5(PriorityQueue<InfoActiv> queue, Date date1, Date date2);

    abstract public void searchBest(PriorityQueue<InfoActiv> queue, String activitate);
}
