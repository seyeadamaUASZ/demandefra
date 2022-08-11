/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.event;

/**
 *
 * @author indiaye
 */
public class NotificationEvent {
    public String action;
    public String notifinfos;

    public NotificationEvent(String action) {
        this.action = action;
    }
    
    public NotificationEvent(String action, String notifinfos) {
        this.action = action;
        this.notifinfos = notifinfos;
    }
        
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }       
    
    
    public String getNotifinfos() {
        return notifinfos;
    }

    public void setNotifinfos(String notifinfos) {
        this.notifinfos = notifinfos;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" + "action=" + action + ", notifinfos=" + notifinfos + '}';
    }            
}
