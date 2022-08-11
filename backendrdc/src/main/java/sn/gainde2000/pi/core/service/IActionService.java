package sn.gainde2000.pi.core.service;

import java.util.List;
import java.util.Optional;

import sn.gainde2000.pi.core.entity.Action;
import sn.gainde2000.pi.core.entity.Menu;

public interface IActionService {
    public void saveAction(Action action);
    public List<Action> getListAction();
    public List<Action> findActionByMenId(Menu m);
    public Optional<Action> findById(Long id);
    public Action  findByCode(String code);
    public void supprimer(Action action);
}
