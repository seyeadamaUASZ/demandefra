
package sn.gainde2000.pi.core.service;
import sn.gainde2000.pi.core.entity.Suivredemande;
import java.util.List;
import java.util.Optional;

public interface ISuivredemandeService {

Optional<Suivredemande> findById(Long id);
List<Suivredemande> findAll();
Suivredemande save(Suivredemande suivredemande);
void delete(Suivredemande suivredemande);
Iterable<Suivredemande> listSuivredemande(Long poOwner);
Iterable<Suivredemande> listSuivredemandeById(Long owner);
Suivredemande getOneSuivredemande(Long id);
}