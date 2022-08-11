package sn.gainde2000.pi.ged.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.PrivilegeSigner;
import sn.gainde2000.pi.ged.repository.PrivilegeSignerRepository;
import sn.gainde2000.pi.ged.service.IPrivilegeSignerServce;
@Service
public class PrivilegeSignerServiceImpl implements IPrivilegeSignerServce{
	@Autowired
  private PrivilegeSignerRepository PrivilegeSigner;
	@Override
	public PrivilegeSigner save(PrivilegeSigner p) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.save(p);
	}
	@Override
	public List<Document> listDocumentSigner(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentSigner(id);
	}
	@Override
	public List<Document> listDocumentCertifier(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentCertifier(id);
	}
	@Override
	public List<Document> listDocumentConsulter(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentConsulter(id);
	}
	@Override
	public PrivilegeSigner findByPrivByDoc(Long idD,Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.findByPrivByDoc(idD,id);
	}
	@Override
	public List<Document> listDocumentApprouver(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentApprouver(id);
	}
	@Override
	public List<Document> listDocumentDejaCertifier(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentDejaCertifier(id);
	}
	@Override
	public List<Document> listDocumentDejaApprouver(Long id) {
		// TODO Auto-generated method stub
		return PrivilegeSigner.listDocumentDejaApprouver(id);
	}

}
