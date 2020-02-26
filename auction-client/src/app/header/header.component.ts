import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public breadcrumb: string;

  constructor(private router: Router, public loginService: LoginService) {
    this.router.events.subscribe(_ => {
      this.breadcrumb = this.router.routerState.snapshot.url;
      const length = this.breadcrumb.split('/').length;
      document
        .getElementById('breadcrumb')
        .innerHTML
        = this.breadcrumb
        .split('/')
        .map(
          (element, index) => {
            if ( index === 0 ){
              return '';
            }
            const capitalization = (index + 1 === length) ? 'active text-capitalize' : '';
            const currentPage = (index + 1 === length) ? 'aria-current="page"' : '';
            const link = this.breadcrumb.split('/').reduce((acc, el, i) => {
              if (i <= index) {
                acc += '/' + el;
              }
              return acc;
            });
            const item = (index + 1 === length) ? `${element}` : `<a class="text-capitalize" routerLink="${link}">${element}</a>`;
            return `<li class="breadcrumb-item ${capitalization} " ${currentPage}>
                ${item}
                </li>`;
          }).join('\n');

    });
  }

  ngOnInit() {
  }


  logout() {
    this.loginService.logout();
  }
}
