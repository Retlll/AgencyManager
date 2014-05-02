/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Comparator;

/**
 *
 * @author Lenovo
 */
public class Comparators {

    public static class AgentComparatorByName implements Comparator<Agent> {

        @Override
        public int compare(Agent o1, Agent o2) {
            if (o1.getName().compareTo(o2.getName()) == 0) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        }
    }

    public static class AgentComparatorByBorn implements Comparator<Agent> {

        @Override
        public int compare(Agent o1, Agent o2) {
            if (o1.getBorn().compareTo(o2.getBorn()) == 0) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getBorn().compareTo(o2.getBorn());
            }
        }
    }

    public static class AgentComparatorByActive implements Comparator<Agent> {

        @Override
        public int compare(Agent o1, Agent o2) {
            if (o1.isActive() == o2.isActive()) {
                return o1.getId().compareTo(o2.getId());
            } else {
                if (o1.isActive()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    public static class AgentComparatorByRank implements Comparator<Agent> {

        @Override
        public int compare(Agent o1, Agent o2) {
            if (o1.getRank() == o2.getRank()) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getRank() - o2.getRank();
            }
        }
    }
    
    public static class AgentComparatorById implements Comparator<Agent> {

        @Override
        public int compare(Agent o1, Agent o2) {
            return o1.compareTo(o2);
        }
    }
}
