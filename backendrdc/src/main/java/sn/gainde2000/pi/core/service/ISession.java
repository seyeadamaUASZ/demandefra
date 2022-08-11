package sn.gainde2000.pi.core.service;

import java.util.List;


import sn.gainde2000.pi.core.entity.Session;


public interface ISession {
	public void addSession(Session session);
    public List<Session> listSession();
    public Session getSession(Long id);
    public void deleteSession(Session session);
    public void updateSession(Session session);
    public void deleteByIdSession(Long id);
    public int nombreUserConnecter();
    public int VerifierSession(String username);
}
