/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import sn.gainde2000.pi.core.entity.Application;

/**
 *
 * @author yougadieng
 */
public interface IApplicationService {

    public List<Application> getListApplication();

    public Application save(Application application);

    public Optional<Application> findById(Long id);

    public Application findByappId(Long appId);

    public Application findByappNom(String appNom);

    public void delete(Application application);

    public void update(String etape, String id);

    public void deleteJointureWorkflow(Application idApp);

    public void deleteJointureFormulaire(Long idApp);

    public void deleteJointureFichier(Application idApp);

    public void deleteJointureTableRelationnel(Long idApp);
    int nombreAplication();
    public List<Application> listApplicationPubliee();
    public List<Application> list2LeastRecentApplicationPubliee();


}
