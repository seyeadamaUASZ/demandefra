import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';
import { NotificationService } from 'src/app/shared/services/notification.service';
import { TypeConnexion } from '../../models/TypeConnexion';
import { ParametreService } from '../../services/parametre.service';

@Component({
  selector: 'app-type-connexion',
  templateUrl: './type-connexion.component.html',
  styleUrls: ['./type-connexion.component.scss']
})
export class TypeConnexionComponent implements OnInit {
  typeConnexion;
  displayedColumns: string[] = ['libelle', 'description', 'action'];
  dataSource: MatTableDataSource<TypeConnexion>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private paramService: ParametreService, private translate: TranslateService,
    private notification:NotificationService

  ) { }

  ngOnInit() {
    this.getTypeConnexion();
  }


  getTypeConnexion() {
    this.paramService.getTypeConnexion().subscribe(data => {
      console.log("-------------" + data);
      this.typeConnexion = data.data;
      this.dataSource = new MatTableDataSource<TypeConnexion>(data.data);
      this.dataSource.paginator = this.paginator;
    });
  }
  updateType(element) {
    this.paramService.updateTypeConnexion(element).subscribe(data => {
      this.translate.get('typeconnexion.updateOk').subscribe((res: string) => {
        this.notification.success(res);
      });
    })
    this.getTypeConnexion();
  }
}
