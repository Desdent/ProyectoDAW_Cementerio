import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { TokenPayload } from '../../interfaces/tokenPayload';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  getUsuarioId(): number {
    const token = localStorage.getItem('token');
    if (!token) return 0;

    try {
      const decoded = jwtDecode<TokenPayload>(token);
      return decoded.id;
    } catch (error) {
      console.error('Error decodificando el token:', error);
      return 0;
    }
  }
}
