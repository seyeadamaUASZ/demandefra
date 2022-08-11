package sn.gainde2000.pi.ged.service;


import java.util.List;



import sn.gainde2000.pi.ged.entity.Document;
import sn.gainde2000.pi.ged.entity.PrivilegeSigner;

public interface IPrivilegeSignerServce {

	 public PrivilegeSigner save(PrivilegeSigner p);
	 public  List<Document> listDocumentSigner(Long id);
	 public  List<Document> listDocumentCertifier(Long id);
	 public  List<Document> listDocumentDejaCertifier(Long id);

	 public  List<Document> listDocumentConsulter(Long id);
	 public  List<Document> listDocumentApprouver(Long id);
	 public  List<Document> listDocumentDejaApprouver(Long id);

	 public PrivilegeSigner findByPrivByDoc(Long idD,Long id);
	   
}
