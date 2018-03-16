
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Clasa care contine numele hotelului si un HashMap cu toate activitatile care
 * se pot practica la hotelul respectiv inserate cu numele lor ca si key in
 * HashMap pentru a putea fi gasite usor, iar ca valoare corespunzatoare cheii
 * exista o coada de prioritati in care se insereaza toate ofertele pentru acea
 * activitate la hotelul respectiv in ordinea crescatoare a preturilor, tocmai
 * pentru ca atunci cand se doreste obtinerea activitatii cu pretul minim sa se
 * ia primul element din coada de prioritati
 *
 * @author daniel
 */
public class Hotel {

    private final String name;
    private LinkedHashMap<String, PriorityQueue<InfoActiv>> map = null;

    Hotel(String name) {
        this.name = name;
        this.map = new LinkedHashMap<>();
    }

    /**
     * Metoda de adaugare activitati pentru un hotel prin adaugarea fiecarei
     * activitati in parte in HashMap-ul hotelului in PriorityQueue-ul
     * corespunzator numelui activitatii
     *
     * @param tokens linia citita dupa ce a fost sparta in tokeni dupa virgula
     */
    public void addPeriod(String[] tokens) {
        int i;
        InfoActiv info = new InfoActiv(tokens[2] + " " + tokens[3] + " " + tokens[4] + " " + tokens[1],
                Integer.parseInt(tokens[5]), tokens[tokens.length - 2], tokens[tokens.length - 1]);
        for (i = 6; i < tokens.length - 2; i++) {
            if (map.get(tokens[i]) == null) {
                map.put(tokens[i], new PriorityQueue<>(10, new InfoComparator()));
            }
            map.get(tokens[i]).offer(info);
        }
    }

    /**
     * Afisarea datelor corespunzatoare hotelului si a tuturor activitatilor din
     * HashMap-ul hotelului
     */
    public void show() {
        System.out.println("\tHotelul " + name + " ofera urmatoarele facilitati:");
        for (Map.Entry<String, PriorityQueue<InfoActiv>> entry : map.entrySet()) {
            for (InfoActiv info : entry.getValue()) {
                System.out.println("\t\t" + entry.getKey() + " la pretul de " + info.show());
            }
        }
    }

    /**
     * Metoda folosita la calcularea top5 locatii pentru o anumita perioada. Se
     * parcurge HashMap-ul si se verifica pentru fiecare perioada daca perioada
     * ceruta de client este inclusa in totalitate in perioada din oferta
     * hotelului(primul if), daca este asa atunci se creeaza un nou obiect de
     * tipul activitate cu locatia respectiva si cu pretul total ca fiind pretul
     * cel mai mic pentru acea perioada(prima valoare din coada de prioritati)
     * inmultit cu numarul de zile ale perioadei cerute, obiect care va fi
     * introdus in coada rezultat de prioritati pentru top5. Daca nu este
     * inclusa in totalitate, atunci pentru perioada in care data de inceput a
     * perioadei cautate este dupa data de inceput a ofertei hotelului, iar data
     * de final ceruta este dupa data de final a ofertei hotelului, se cauta in
     * toate celelalte perioade daca exista o alta oferta care sa aiba data de
     * inceput a ofertei egala cu data de final a primei oferte si care sa
     * contina si data de final a ofertei cerute de utilizator. Daca se gaseste
     * o astfel de perioada pretul noului obiect care se va introduce in lista
     * finala va fi dat de pretul pentru zilele din prima perioada inmultit cu
     * numarul de zile la care se adauga zilele din a doua perioada inmultite cu
     * pretul pentru acea perioada.
     *
     * @param queue coada de prioritati cu raspunsul final
     * @param date1 data de inceput a perioadei cautate
     * @param date2 date de final a perioadei cautate
     */
    public void top5(PriorityQueue<InfoActiv> queue, Date date1, Date date2) {
        PriorityQueue<InfoActiv> aux = new PriorityQueue<>(10, new InfoComparator());
        InfoActiv newActiv = null;

        Calendar dayB = Calendar.getInstance(), dayE = Calendar.getInstance();
        dayB.setTime(date1);
        dayE.setTime(date2);

        for (Map.Entry<String, PriorityQueue<InfoActiv>> entry : map.entrySet()) {
            InfoActiv x = entry.getValue().peek();

            if ((x.getDateEnd().after(date2) || x.getDateEnd().equals(date2))
                    && (x.getDateBegin().before(date1) || x.getDateBegin().equals(date1))) {

                newActiv = new InfoActiv(x.getLoc(), (dayE.get(Calendar.DAY_OF_YEAR)
                        - dayB.get(Calendar.DAY_OF_YEAR)) * x.getPret());
                aux.offer(newActiv);

            } else if ((x.getDateBegin().before(date1) || x.getDateBegin().equals(date1))
                    && x.getDateEnd().before(date2)) {

                for (Map.Entry<String, PriorityQueue<InfoActiv>> entry1 : map.entrySet()) {
                    PriorityQueue<InfoActiv> q = entry1.getValue();

                    for (InfoActiv inf : q) {
                        if (inf.getDateBegin().equals(x.getDateEnd())
                                && inf.getDateEnd().after(date2) && inf.getDateBegin().after(date1)) {

                            newActiv = new InfoActiv(x.getLoc(), (x.getDay2().get(Calendar.DAY_OF_YEAR)
                                    - dayB.get(Calendar.DAY_OF_YEAR)) * x.getPret() + (dayE.get(Calendar.DAY_OF_YEAR)
                                    - inf.getDay1().get(Calendar.DAY_OF_YEAR)) * inf.getPret());
                            aux.offer(newActiv);
                        }
                    }
                }
            }
        }
        if (!aux.isEmpty()) {
            queue.offer(aux.peek());
        }
    }

    /**
     * Cautarea activitatii activitate in HashMap-ul hotelului Daca activitatea
     * este gasita si perioada din oferta hotelului este mai mare de 10
     * zile(perioada ceruta in enunt) atunci se adauga primul element din coada
     * de prioritati corespunzatoare activitatii care contine pretul din
     * perioada cu pretul minim in coada de prioritati care va contine
     * rezultatul final
     *
     * @param queue coada cu rezultatul final
     * @param activitate activitatea cautata
     */
    public void searchBest(PriorityQueue<InfoActiv> queue, String activitate) {
        if (map.get(activitate) != null) {
            for (InfoActiv inf : map.get(activitate)) {
                if (inf.getDay2().get(Calendar.DAY_OF_YEAR) - inf.getDay1().get(Calendar.DAY_OF_YEAR) >= 10) {
                    queue.offer(map.get(activitate).peek());
                }
            }
        }
    }
}
