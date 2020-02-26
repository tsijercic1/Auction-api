import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {map} from 'rxjs/operators';
import {LoginService} from './login/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private loginService: LoginService) {
    if (localStorage.getItem('token') !== null) {
      loginService.refresh(localStorage.getItem('token'));
    // localStorage.setItem('token', loginService.getUserData.token);
    }
  }


}
