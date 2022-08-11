/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.gainde2000.pi.core.entity.TimerTask;
import sn.gainde2000.pi.core.repository.TimerTaskRepository;
import sn.gainde2000.pi.core.service.ITimerTaskService;

/**
 *
 * @author jsarr
 */
@Service
public class TimerTaskImpl implements ITimerTaskService{
     @Autowired
     TimerTaskRepository timerTaskRepository;

    @Override
    public TimerTask findByTmId(Long tmId) {
        return timerTaskRepository.findByTmId(tmId);
    }

    @Override
    public TimerTask save(TimerTask tt) {
        return timerTaskRepository.save(tt);
    }

    @Override
    public List<TimerTask> getAllTimerTask() {
       return timerTaskRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> fetchdonneetaskandtimer(Long tmId) {
      return timerTaskRepository.fetchdonneetaskandtimer(tmId);
    }

    @Override
    public List<TimerTask> findByTimerTaskWrkId(Long id) {
        return timerTaskRepository.findByTimerTaskWrkId(id);
    }
}
