import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { TokenPayload } from '../../interfaces/tokenPayload';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  /**
   * Extrae el ID del usuario autenticado desde el token JWT almacenado.
   * @returns El ID numérico del usuario o 0 si no hay token o es inválido.
   */
  getUsuarioId(): number {
    const token = localStorage.getItem('token');
    if (!token) return 0;

    try {
      // jwtDecode procesa el payload del token sin necesidad de enviarlo al servidor.
      const decoded = jwtDecode<TokenPayload>(token);
      return decoded.id;
    } catch (error) {
      console.error('Error decodificando el token:', error);
      return 0;
    }
  }
}
