import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../../entity/user';

export interface LoginResponse {
  token: object;
}

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/auth/login';

  constructor() {}

  login(user: User) {
    return this.http.post<LoginResponse>(this.apiUrl, user);
  }
}
