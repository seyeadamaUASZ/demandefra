/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.gainde2000.pi.core.service;

import java.util.List;


import sn.gainde2000.pi.core.entity.JbpmFormInfos;

/**
 *
 * @author jsarr
 */
public interface IJbpmFormInfos {
    public JbpmFormInfos findByJfrmFormulaire(String jfrmFormulaire);
    public JbpmFormInfos findByJfrmWorkflow(String jfrmWorkflow);
    public JbpmFormInfos findByJfrmId(Long jfrmId);
    public JbpmFormInfos save(JbpmFormInfos jf);
    void delete(Long jfrmId);
    public List<JbpmFormInfos> getListJbpmFormInfosBycontainer(String jfrmWorkflow);
    public List<JbpmFormInfos> getListJbpmFormInfosById(Long jfrmId);
    public List<JbpmFormInfos> getListJbpmFormInfosGenerer(String containerId);
    public List<JbpmFormInfos> getListJbpmFormInfosNoGenerer();
    public List<JbpmFormInfos> getListJbpmFormInfos(String containerId);
}
