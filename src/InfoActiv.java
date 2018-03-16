
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa care contine toate informatiile despre oferta pe o perioada a unui
 * hotel. loc: locatia completa a hotelului, pret: pretul pentru activitatea
 * corespunzatoare, dateBegin, dateEnd: data de inceput si final pentru perioada
 * din oferta, day1, day2: folosite ulterior pentru obtinerea zilei din an
 * corespunzatoare datelor de inceput si de final pentru calcule
 *
 * @author daniel
 */
public class InfoActiv {

    private String loc;
    private int pret;
    private Date dateBegin = null, dateEnd = null;
    private Calendar day1, day2;

    InfoActiv(String loc, int pret) {
        this.loc = loc;
        this.pret = pret;
    }

    InfoActiv(String loc, int pret, String date1, String date2) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        this.loc = loc;
        this.pret = pret;
        try {
            this.dateBegin = dateformat.parse(date1);
            this.dateEnd = dateformat.parse(date2);
            day1 = Calendar.getInstance();
            day2 = Calendar.getInstance();
            day1.setTime(dateBegin);
            day2.setTime(dateEnd);

        } catch (ParseException ex) {
            Logger.getLogger(InfoActiv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public int getPret() {
        return pret;
    }

    public String getLoc() {
        return loc;
    }

    public Calendar getDay1() {
        return day1;
    }

    public Calendar getDay2() {
        return day2;
    }

    /**
     * Afisarea datelor despre activitatea hotelului: pret si datele de inceut
     * si final a ofertei
     *
     * @return string-ul cu toate datele
     */
    public String show() {
        return pret + " RON de la " + dateBegin.toString() + " la " + dateEnd.toString();
    }
}
