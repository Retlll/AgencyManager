/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Lenovo
 */
public class Agent {

    private Long id;
    private String name;
    private Calendar born;
    private boolean active;
    private int rank;
    private String notes;

    public Agent(Long id, String name, Calendar born, boolean active, int rank, String notes) {
        this.id = id;
        this.name = name;
        this.born = born;
        this.active = active;
        this.rank = rank;
        this.notes = notes;
    }

    //builder moze pomoct
    public Agent() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Calendar getBorn() {
        return born;
    }

    public boolean isActive() {
        return active;
    }

    public int getRank() {
        return rank;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorn(Calendar born) {
        this.born = born;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Agent other = (Agent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agent{" + "id=" + id + ", name=" + name + ", born=" + born + ", active=" + active + ", rank=" + rank + ", notes=" + notes + '}';
    }
}
