import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../login/login.service';

interface RegistrationResponseData {
  accessToken: string;
  tokenType: string;
}

@Injectable({providedIn: 'root'})
export class RegistrationService {
  constructor(private http: HttpClient, private loginService: LoginService) {

  }

  async register(name: any, surname: any, email: string, password: string) {
    const response = await this.http.post<RegistrationResponseData>('http://localhost:8080/api/auth/register', {
      name,
      surname,
      username: 'test',
      email,
      password
    });
    response
      .subscribe(object => {
      this.loginService.refresh(object.accessToken);
    });
    return response;
  }
}
