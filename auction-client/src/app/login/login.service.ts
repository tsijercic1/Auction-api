import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

interface LoginResponseData {
  accessToken: string;
  tokenType: string;
}

interface UserData {
  id: number;
  username: string;
  email: string;
  name: string;
  surname: string;
  token: string;
}

@Injectable({providedIn: 'root'})
export class LoginService {
  private userData: UserData;
  public isLoggedIn: boolean;

  constructor(private http: HttpClient) {

  }

  async login(email: string, password: string) {
    const response = await this.http.post<LoginResponseData>(
      'http://localhost:8080/api/auth/login',
      {
        usernameOrEmail: email,
        password
      });
    response.subscribe(loginResponseData => {
      this.isLoggedIn = true;
      console.log('response on login');
      localStorage.setItem('token', loginResponseData.accessToken);
      this.refresh(loginResponseData.accessToken);
    }, error => {
      localStorage.removeItem('token');
      this.isLoggedIn = false;
    });
    return response;
  }

  get getUserData() {
    return this.userData;
  }

  refresh(token: string) {
    // tslint:disable-next-line:no-unused-expression
    this.http.post<UserData>('http://localhost:8080/api/auth/refresh', {token},{headers:{Authorization: `Bearer ${token}`}}).subscribe(userData => {
      console.log('logged in');
      this.userData = userData;
      localStorage.setItem('token', userData.token);
      this.isLoggedIn = true;
    }, error => {
      localStorage.removeItem('token');
      this.isLoggedIn = false;
    });
  }

  logout() {
    this.isLoggedIn = false;
    this.userData = null;
    localStorage.removeItem('token');
  }
}
