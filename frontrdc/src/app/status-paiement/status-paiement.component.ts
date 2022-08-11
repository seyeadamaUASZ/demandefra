import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { PaiementService } from '../paiement/service/paiement.service';

@Component({
  selector: 'app-status-paiement',
  templateUrl: './status-paiement.component.html',
  styleUrls: ['./status-paiement.component.scss']
})
export class StatusPaiementComponent implements OnInit {
  idFacture: any;

  constructor(private service: PaiementService,
              private activatedRoute: ActivatedRoute,
              private router: Router
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.idFacture = params['idFacture'];
    });
  }

  ngOnInit(): void {
    this.service.getMessageSucces(this.idFacture).subscribe(
      (data:any) => {
        if(data.data.paiStatusTransaction == "sucess") {
          if(data.data.paiReferencePaiement){
            Swal.fire({
              title: 'Votre Paiement a été effectué avec succès.',
              text: 'Référence Paiement : '+data.data.paiReferencePaiement,
              icon: 'success',
              confirmButtonColor: "GREEN",
              confirmButtonText: 'CONTINUER',
            }).then((result) => {
              if (result.isConfirmed) {
                this.router.navigate([''])
              } else if (result.isDenied) {
              }
            });
          }
        } else{
          Swal.fire({
            title: 'Paiement non effectif.',
            icon: 'error',
            confirmButtonColor: "GREEN",
            confirmButtonText: 'REPRENDRE',
          }).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate([''])
            } else if (result.isDenied) {
            }
          });
        }
      }
    )
  }
}
