/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Giacomo Mariani
 */

public class Person extends AbstractPersistable<Long> {
    

    private String email;
    private String password; 
    private double credit; 

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    
    
    public void setEmail(String email){
        this.email = email; 
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getCredit() {
        return credit;
    }
    
}
