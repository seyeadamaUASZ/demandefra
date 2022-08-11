package sn.gainde2000.pi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sn.gainde2000.pi.core.entity.Signature;
import sn.gainde2000.pi.core.entity.Utilisateur;
import sn.gainde2000.pi.core.repository.SignatureRepository;
import sn.gainde2000.pi.core.service.ISignatureService;

@Service
public class SignatureServiceImpl implements ISignatureService {
	@Autowired
	SignatureRepository signatureRepository;

	@Override
	public Signature save(Signature signature) {
		return signatureRepository.save(signature);
	}

	@Override
	public Signature verification(Long owner, String code) {
		return signatureRepository.verification(owner, code);
	}

	@Override
	public Signature getOneSignature(Long id) {
		return signatureRepository.findById(id).get();
	}
}
