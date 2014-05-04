/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.util.Objects;


/**
 *
 * @author Lenovo
 */
public class Mission implements Comparable<Mission> {
    private Long id;
    private String name;
    private int difficulty;
    private String details;
    private String Location;

    //private int capacity;
    public Mission(Long id, String name, int difficulty, String details, String Location) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.details = details;
        this.Location = Location;
    }

    public Mission() {
    }

    @Override
    public String toString() {
        return "Mission{" + "id=" + id + ", name=" + name + ", difficulty=" + difficulty + ", details=" + details + ", Location=" + Location + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getDetails() {
        return details;
    }

    public String getLocation() {
        return Location;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mission other = (Mission) obj;
        if (this.id == null || other.id == null)
            return false;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Mission mission) {
        //int temp;
        if (mission == null) {
            return -1;
        }
        if (mission.id == null) {
            if (this.id == null) {
                return 0;
            } else {
                return -1;
            }
        } else {
            if (this.id == null) {
                return 1;
            } else {
                if (this.id - mission.id == 0) {
                    return 0;
                } else {
                    if (this.id - mission.id > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }
    
    
}
