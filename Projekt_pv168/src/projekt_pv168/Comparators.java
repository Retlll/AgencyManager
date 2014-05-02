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
    
    public static class MissionComparatorByName implements Comparator<Mission> {

        @Override
        public int compare(Mission o1, Mission o2) {
            if (o1.getName().compareTo(o2.getName()) == 0) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        }
    }
    
    public static class MissionComparatorByDifficulty implements Comparator<Mission> {

        @Override
        public int compare(Mission o1, Mission o2) {
            if (o1.getDifficulty() == o2.getDifficulty()) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getDifficulty() - o2.getDifficulty();
            }
        }
    }
    
    public static class MissionComparatorByLocation implements Comparator<Mission> {

        @Override
        public int compare(Mission o1, Mission o2) {
            if (o1.getLocation().compareTo(o2.getLocation()) == 0) {
                return o1.getId().compareTo(o2.getId());
            } else {
                return o1.getLocation().compareTo(o2.getLocation());
            }
        }
    }
    
    public static class MissionComparatorById implements Comparator<Mission> {

        @Override
        public int compare(Mission o1, Mission o2) {
            return o1.compareTo(o2);
        }
    }
    
    public static class ContractComparatorByMission implements Comparator<Contract> {

        @Override
        public int compare(Contract o1, Contract o2) {
            Mission m1 = o1.getMission();
            Mission m2 = o2.getMission();
            if (m1.getName().compareTo(m2.getName()) == 0) {
                return m1.getId().compareTo(m2.getId());
            } else {
                return m1.getName().compareTo(m2.getName());
            }
        }
    }
    
    public static class ContractComparatorByAgent implements Comparator<Contract> {

        @Override
        public int compare(Contract o1, Contract o2) {
            Agent a1 = o1.getAgent();
            Agent a2 = o2.getAgent();
            if (a1.getName().compareTo(a2.getName()) == 0) {
                return a1.getId().compareTo(a2.getId());
            } else {
                return a1.getName().compareTo(a2.getName());
            }
        }
    }
    
    public static class ContractComparatorByBudget implements Comparator<Contract> {

        @Override
        public int compare(Contract o1, Contract o2) {
            Agent a1 = o1.getAgent();
            Agent a2 = o2.getAgent();
            if (o1.getBudget() == o2.getBudget()) {
                return a1.getId().compareTo(a2.getId());
            } else {
                if(o1.getBudget() > o2.getBudget()){
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
    
    public static class ContractComparatorByStartTime implements Comparator<Contract> {

        @Override
        public int compare(Contract o1, Contract o2) {
            Agent a1 = o1.getAgent();
            Agent a2 = o2.getAgent();
            if(o1.getStartTime() == null && o2.getStartTime() == null)
                return a1.getId().compareTo(a2.getId());
            if(o1.getStartTime() == null)
                return 1;
            if(o2.getStartTime() == null)
                return -1;
            
            if (o1.getStartTime().compareTo(o2.getStartTime()) == 0) {
                return a1.getId().compareTo(a2.getId());
            } else {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        }
    }
    
    
    
    public static class ContractComparatorByEndTime implements Comparator<Contract> {

        @Override
        public int compare(Contract o1, Contract o2) {
            Agent a1 = o1.getAgent();
            Agent a2 = o2.getAgent();
            if(o1.getEndTime() == null && o2.getEndTime() == null)
                return a1.getId().compareTo(a2.getId());
            if(o1.getEndTime() == null)
                return 1;
            if(o2.getEndTime() == null)
                return -1;
            if (o1.getEndTime().compareTo(o2.getEndTime()) == 0) {
                return a1.getId().compareTo(a2.getId());
            } else {
                return o1.getEndTime().compareTo(o2.getEndTime());
            }
        }
    }
}
