/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.event;

import org.springframework.stereotype.Component;

/**
 *
 * @author indiaye
 */
@Component
public class NotificationChecker {
    public String action;
    public NotificationEvent event;
    
    public NotificationChecker() {
    }
    
    public NotificationChecker(String action) {
        this.action = action;
    }        

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public NotificationEvent getEvent() {
        return event;
    }

    public void setEvent(NotificationEvent event) {
        this.event = event;
    }        
    
    public String notificationCheckEvent(String action){
        return action;
    }
    
    public NotificationEvent notificationCheckEvent(NotificationEvent event){
        return event;
    }
}
