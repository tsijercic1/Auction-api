import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {LoginService} from './login.service';
import {Router} from '@angular/router';
import {error} from 'util';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
  }

  async onSubmit(loginForm: NgForm) {
    console.log(loginForm.value);
    const email = loginForm.value.email;
    const password = loginForm.value.password;
    const response = await this.loginService.login(email, password);
    if (response) {
      response.subscribe(userData => {
        console.log(userData);
        loginForm.reset();
        this.router.navigateByUrl('/home');
      }, _ => {
        this.router.navigateByUrl('/login');
      });
    }
  }
}
