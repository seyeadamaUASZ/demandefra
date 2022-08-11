package sn.gainde2000.pi.core.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.repository.WidgetRepository;
import sn.gainde2000.pi.core.service.IWidgetService;

/**
 *
 * @author Serigne Asse Gueye
 */
@Service
public class WidgetServiceImpl implements IWidgetService {

    @Autowired
    private WidgetRepository widgetRepository;

    @Override
    public List<Widget> getListWidget() {
        return widgetRepository.findAll();
    }

    @Override
    public List<Widget> getListWidgetProfil(Profil profil) {
        return widgetRepository.findAllWidget(profil.getProId());
    }

    @Override
    public List<Widget> getListWidgetProfilNoAttr(Profil profil) {
        return widgetRepository.findAllWidgetNoAttr(profil.getProId());
    }

    @Override
    public List<Widget> getListWidgetProfilId(Long id) {
        return widgetRepository.findAllWidget(id);
    }

    @Override
    public Optional<Widget> findById(Long id) {
        return widgetRepository.findById(id);
    }

    @Override
    public Widget save(Widget widget) {
        return widgetRepository.save(widget);
    }

    @Override
    public void delete(Widget Widget) {
        widgetRepository.delete(Widget);
    }

    

   

}
