import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../../entity/user';
import { LoginResponse } from '../../interfaces/longinResponse';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/auth/login';

  login(user: User) {
    return this.http.post<LoginResponse>(this.apiUrl, user);
  }

  // Método que usará el Guard
  isAuthenticated(): boolean {
    // Devuelve true si el token existe
    return !!localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  hasRole(expectedRole: string): boolean {
    return this.getRole() === expectedRole;
  }
}
