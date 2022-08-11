import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {NavItem} from '../nav-item';
import { Router } from '@angular/router';
import { Menu } from 'src/app/utilisateur/models/menu';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {
  @Input() menus:any;
  @ViewChild('childMenu') public childMenu;

  constructor(private router: Router) {
  }

  ngOnInit() {    
  }
}
