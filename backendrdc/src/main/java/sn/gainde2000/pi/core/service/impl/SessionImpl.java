package sn.gainde2000.pi.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Session;
import sn.gainde2000.pi.core.repository.SessionRepository;
import sn.gainde2000.pi.core.service.ISession;

@Service
public class SessionImpl implements ISession{

    private final SessionRepository sessionReporitory;
	
	@Autowired 
	public SessionImpl(SessionRepository sessionReporitory) {

		this.sessionReporitory = sessionReporitory;
	}

	@Override
	public void addSession(Session session) {
		this.sessionReporitory.save(session);
		
	}

	@Override
	public List<Session> listSession() {
		
		return this.sessionReporitory.findAll();
	}

	@Override
	public Session getSession(Long id) {
		
		return null;
	}

	@Override
	public void deleteSession(Session session) {
		this.sessionReporitory.delete(session);
		
	}

	@Override
	public void updateSession(Session session) {
		this.sessionReporitory.save(session);
		
	}

	@Override
	public void deleteByIdSession(Long id) {
		this.sessionReporitory.deleteByIdSession(id);
		
	}

	@Override
	public int nombreUserConnecter() {
		
		return this.sessionReporitory.nombreUserConnecter();
	}

	@Override
	public int VerifierSession(String username) {
		// TODO Auto-generated method stub
		return this.sessionReporitory.VerifierSession(username);
	}

}
