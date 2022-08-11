import { Injectable } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { MatIconRegistry } from "@angular/material/icon";


@Injectable({ providedIn: "root" })
export class CustomiconService {
  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer
  ) { }
  init() {
    this.matIconRegistry.addSvgIcon(
      "logo",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/l1.svg"));

    this.matIconRegistry.addSvgIcon(
      "spain",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/spain.svg"));
    this.matIconRegistry.addSvgIcon(
      "france",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/france.svg"));
    this.matIconRegistry.addSvgIcon(
      "england",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/england.svg"));
    this.matIconRegistry.addSvgIcon(
      "signs",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/signs.svg"));
    this.matIconRegistry.addSvgIcon(
      "street-name",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/street-name.svg"));
    this.matIconRegistry.addSvgIcon(
      "guinar",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/food-container.svg"));
    this.matIconRegistry.addSvgIcon(
      "blocks",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/blocks.svg"));
    this.matIconRegistry.addSvgIcon(
      "archives",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/archives.svg"));
    this.matIconRegistry.addSvgIcon(
      "folder",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/folder.svg"));

    // menu du sidebar

    this.matIconRegistry.addSvgIcon(
      "apps_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/apps.svg"));
    this.matIconRegistry.addSvgIcon(
      "files_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/file.svg"));
    this.matIconRegistry.addSvgIcon(
      "home_pi",
      this
        .domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/home.svg"));
    this.matIconRegistry.addSvgIcon(
      "settings_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/setting.svg"));
    this.matIconRegistry.addSvgIcon(
      "users_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/users.svg"));
    this.matIconRegistry.addSvgIcon(
      "workflows_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/workflow.svg"));
    this.matIconRegistry.addSvgIcon(
      "blocks_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/blocks.svg"));
    this.matIconRegistry.addSvgIcon(
      "process_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/process.svg"));
    this.matIconRegistry.addSvgIcon(
      "id",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/id.svg"));
    this.matIconRegistry.addSvgIcon(
      "qr-code_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/qr-code.svg"));
    this.matIconRegistry.addSvgIcon(
      "paiement_pi",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/salary.svg"));

      this.matIconRegistry.addSvgIcon(
        "creer_demande",
        this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/creer_demande.svg"));  
    this.matIconRegistry.addSvgIcon(
      "demande_a_traiter",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/demande_a_traiter.svg"));
      this.matIconRegistry.addSvgIcon(
        "a_corriger",
        this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/a_corriger.svg")); 
    this.matIconRegistry.addSvgIcon(
      "aut_provisoire",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/aut_provisoire.svg"));
    this.matIconRegistry.addSvgIcon(
      "aut_definitive",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/aut_definitive.svg"));
    this.matIconRegistry.addSvgIcon(
      "brouillon",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/brouillon.svg"));
      this.matIconRegistry.addSvgIcon(
        "demande_a_payer",
        this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/demande_a_payer.svg"));
        this.matIconRegistry.addSvgIcon(
          "demande_rejetees",
          this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/demande_rejetees.svg"));
          this.matIconRegistry.addSvgIcon(
            "encours",
            this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/encours.svg"));
            this.matIconRegistry.addSvgIcon(
              "statistics",
              this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/statistics.svg"));


    this.matIconRegistry.addSvgIcon(
      "parametrage",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/tools-solid.svg"));
    this.matIconRegistry.addSvgIcon(
      "fichier",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/copy-solid.svg"));
    this.matIconRegistry.addSvgIcon(
      "user",
      this.domSanitizer.bypassSecurityTrustResourceUrl("./assets/svgicon/users-solid.svg"));
  }
}
