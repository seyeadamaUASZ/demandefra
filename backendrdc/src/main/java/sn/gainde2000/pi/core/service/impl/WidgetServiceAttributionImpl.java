package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Profil;
import sn.gainde2000.pi.core.entity.Widget;
import sn.gainde2000.pi.core.entity.WidgetAttribution;
import sn.gainde2000.pi.core.repository.WidgetAttrRepository;
import sn.gainde2000.pi.core.service.IWidgetAttributionService;

@Service
public class WidgetServiceAttributionImpl implements IWidgetAttributionService {
	
	@Autowired
	private WidgetAttrRepository widgetAttrRepository;

	@Override
	public List<Widget> getListWidgetProfil(Profil profil) {
		return widgetAttrRepository.findByProfiles(profil);
	}

	@Override
	public WidgetAttribution getWidgetProfil(Profil profil, Widget widget) {
		return widgetAttrRepository.findByProfileWidget(profil,widget);
	}

	@Override
	public Iterable<WidgetAttribution> attributionWidget(Long id) {
		return widgetAttrRepository.attributionWidget(id);
	}

	@Override
	public WidgetAttribution save(WidgetAttribution wa) {
		return widgetAttrRepository.save(wa);
	}

	@Override
	public void removedWidgetAttribution(Profil profil, Widget widget) {
		widgetAttrRepository.removedWidgetAttribution(profil, widget);
	}

}
