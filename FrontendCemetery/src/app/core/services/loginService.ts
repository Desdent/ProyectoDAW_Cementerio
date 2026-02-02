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

  /**
   * Realiza la petición de inicio de sesión.
   * @param user Objeto con las credenciales del usuario.
   * @returns Observable con la respuesta de autenticación (token, rol, etc).
   */
  login(user: User) {
    return this.http.post<LoginResponse>(this.apiUrl, user);
  }

  /**
   * Verifica si el usuario está autenticado comprobando la existencia del token en el almacenamiento local.
   * @returns True si existe un token, false en caso contrario.
   */
  isAuthenticated(): boolean {
    // Devuelve true si el token existe
    return !!localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
  }

  /**
   * Obtiene el rol del usuario almacenado en el localStorage.
   * @returns El rol del usuario o null si no existe.
   */
  getRole(): string | null {
    return localStorage.getItem('role');
  }

  /**
   * Comprueba si el usuario tiene un rol específico.
   * @param expectedRole El rol que se espera que tenga el usuario.
   */
  hasRole(expectedRole: string): boolean {
    return this.getRole() === expectedRole;
  }
}
