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
public class Mission {
    private Long id;
    private String name;
    private int difficulty;
    private String details;

    //private int capacity;
    //private String Location;
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Mission other = (Mission) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
