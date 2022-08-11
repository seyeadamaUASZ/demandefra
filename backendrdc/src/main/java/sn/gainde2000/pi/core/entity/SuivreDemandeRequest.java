package sn.gainde2000.pi.core.entity;

public class SuivreDemandeRequest {
    private String emailEntreprise;
    private String numdemande;

    public SuivreDemandeRequest(String emailEntreprise, String numdemande) {
        this.emailEntreprise = emailEntreprise;
        this.numdemande = numdemande;
    }

    public SuivreDemandeRequest() {
    }

    public String getEmailEntreprise() {
        return emailEntreprise;
    }

    public void setEmailEntreprise(String emailEntreprise) {
        this.emailEntreprise = emailEntreprise;
    }

    public String getNumdemande() {
        return numdemande;
    }

    public void setNumdemande(String numdemande) {
        this.numdemande = numdemande;
    }
}
