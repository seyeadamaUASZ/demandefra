/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Map;
import sn.gainde2000.pi.core.entity.TimerTask;

/**
 *
 * @author jsarr
 */
public interface ITimerTaskService {
    public TimerTask findByTmId(Long tmId);
    public TimerTask save(TimerTask tt);
    public List<TimerTask> getAllTimerTask();
    public List<Map<String, Object>> fetchdonneetaskandtimer(Long tmId);
    public List<TimerTask> findByTimerTaskWrkId(Long id);
}
