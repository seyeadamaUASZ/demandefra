/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sn.gainde2000.pi.core.ServeurResponse.ServeurResponse;

/**
 *
 * @author indiaye
 */
@Component
public class EventInterceptor implements HandlerInterceptor {
       
        @Autowired
        private NotificationChecker checkNotif;
        
        @Override
        public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) 
           throws Exception {           
           return true;
        }
        
        @Override
        public void postHandle (HttpServletRequest request, HttpServletResponse response, 
           Object handler, ModelAndView modelAndView) throws Exception {            
        }
        
        @Override
        public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object 
           handler, Exception exception) throws Exception {                        
            if(request.getHeader("action")!=null){           
              checkNotif.notificationCheckEvent(new NotificationEvent(request.getHeader("action"), response.getHeader("notifinfos")));
            }
        }           
}
