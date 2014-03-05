/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Sebastian
 */
public class Contract {
    
    private Mission mission;
    
    private Agent agent;
    
    private long budget;
    
    private Calendar startTime;
    
    private Calendar endTime;

    public Contract() {
    }

    public Contract(Mission mission, Agent agent, long budget, Calendar startTime, Calendar endTime) {
        this.mission = mission;
        this.agent = agent;
        this.budget = budget;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.mission);
        hash = 19 * hash + Objects.hashCode(this.agent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contract other = (Contract) obj;
        if (!Objects.equals(this.mission, other.mission)) {
            return false;
        }
        if (!Objects.equals(this.agent, other.agent)) {
            return false;
        }
        return true;
    }
    
    
}
