
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Clasa Oras contine numele orasului si un HashMap cu toate numele hotelurilor
 * si obiectele corespunzatoare fiecarui hotel
 *
 * @author daniel
 */
public class Oras extends Locatie {

    private final String name;
    private LinkedHashMap<String, Hotel> hoteluri = null;

    Oras(String name) {
        this.name = name;
        this.hoteluri = new LinkedHashMap<>();
    }

    /**
     * Adauga hotel in HashMap
     *
     * @param s numele hotelului
     * @param h hotelul
     */
    @Override
    public void addHotel(String s, Hotel h) {
        this.hoteluri.put(s, h);
    }

    /**
     * Verifica existenta hotelului cu numele s in HashMap, daca exista se
     * intoarce obiectul corespunzator hotelului pentru a putea fi folosit,
     * altfel null
     *
     * @param s numele
     * @return obiectul cautat sau null
     */
    @Override
    public Hotel Exista(String s) {
        if (hoteluri.containsKey(s)) {
            return hoteluri.get(s);
        }
        return null;
    }

    /**
     * Metoda de afisare a mesajului pentru un oras si care pentru fiecare hotel
     * din orasul respectiv apeleaza metoda de afisare a datelor sale
     */
    @Override
    public void show() {
        System.out.println("Pentru orasul " + name + " avem urmatoarea oferta");
        for (Map.Entry<String, Hotel> entry : hoteluri.entrySet()) {
            entry.getValue().show();
        }
    }

    /**
     * Metoda care realizeaza operatia de cautare pentru locatia cea mai potrivita
     * pentru o anumita activitate pe toate hotelurile din HashMap
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param activitate activitatea
     */
    @Override
    public void searchBest(PriorityQueue<InfoActiv> queue, String activitate) {
        for (Map.Entry<String, Hotel> entry : hoteluri.entrySet()) {
            entry.getValue().searchBest(queue, activitate);
        }
    }

    /**
     * Metoda care realizeaza operatia de cautare pentru top5 pe toate
     * hotelurile din HashMap
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param date1 data de inceput a sejurului cautat
     * @param date2 data de final a sejurului cautat
     */
    @Override
    public void top5(PriorityQueue<InfoActiv> queue, Date date1, Date date2) {
        for (Map.Entry<String, Hotel> entry : hoteluri.entrySet()) {
            entry.getValue().top5(queue, date1, date2);
        }
    }
}
