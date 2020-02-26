import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {LoginService} from '../login/login.service';
import {Router} from '@angular/router';
import {RegistrationService} from './registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private registrationService: RegistrationService,  private loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }

  async onSubmit(registrationForm: NgForm) {
    console.log(registrationForm.value);
    const firstName = registrationForm.value.first_name;
    const lastName = registrationForm.value.last_name;
    const email = registrationForm.value.email;
    const password = registrationForm.value.password;
    const response = await this.registrationService.register(firstName, lastName, email, password);
    if (response) {
      response.subscribe(userData => {
        console.log(userData);
        registrationForm.reset();
        this.router.navigateByUrl('/home');
      }, _ => {
        this.router.navigateByUrl('/register');
      });
    }
  }
}
