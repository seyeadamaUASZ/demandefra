package sn.gainde2000.pi.core.service;

import sn.gainde2000.pi.core.entity.Signature;
import sn.gainde2000.pi.core.entity.Utilisateur;

public interface ISignatureService {
	Signature save(Signature signature);
	Signature verification(Long owner, String code);
	Signature getOneSignature(Long id);
}
