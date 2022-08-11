package sn.gainde2000.pi.core.service;

import java.util.List;



import sn.gainde2000.pi.core.entity.HistoriqueSession;


public interface IHistoriqueSession {
    public void addHistSession(HistoriqueSession historiqueSession);
    public List<HistoriqueSession> listHistSession();
    public HistoriqueSession getHistoriqueSession(Long id);
    public void deleteHistSession(HistoriqueSession historiqueSession);
    public HistoriqueSession historiqueSessionByIdUser(Long idUser);
    void updateDate(Long id);
}
