
import java.util.Comparator;

/**
 *
 * @author daniel
 */
public class InfoComparator implements Comparator<InfoActiv> {

        @Override
        public int compare(InfoActiv o1, InfoActiv o2) {
            if (o1.getPret() < o2.getPret()) {
                return -1;
            } else if (o1.getPret() > o2.getPret()) {
                return 1;
            }
            return 0;
        }

    }