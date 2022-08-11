package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.HistoriqueSession;
import sn.gainde2000.pi.core.repository.HistoriqueSessionRepository;
import sn.gainde2000.pi.core.service.IHistoriqueSession;

@Service
public class HistoriqueSessionImpl implements IHistoriqueSession{
	  @Autowired
	    HistoriqueSessionRepository histSessionRepository;
	  
	@Override
	public void addHistSession(HistoriqueSession historiqueSession) {
		this.histSessionRepository.save(historiqueSession);
		
	}

	@Override
	public List<HistoriqueSession> listHistSession() {

		return this.histSessionRepository.findAll();
	}

	@Override
	public HistoriqueSession getHistoriqueSession(Long id) {
		return this.histSessionRepository.getOne(id);
	}

	@Override
	public void deleteHistSession(HistoriqueSession historiqueSession) {
		this.histSessionRepository.delete(historiqueSession);
		
	}

	@Override
	public HistoriqueSession historiqueSessionByIdUser(Long idUser) {
		return null;
	}

	@Override
	public void updateDate(Long id) {
		// TODO Auto-generated method stub
		histSessionRepository.updateDate(id);
	}

}
