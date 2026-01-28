import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/loginService';
import { inject } from '@angular/core';

export const roleGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  const token = localStorage.getItem('token');
  if (!token) {
    router.navigate(['/login']);
    return false;
  }

  const userRole = loginService.getRole();
  const expectedRole = route.data['expectedRole'];

  // Si el token no tiene el rol esperado, bloquea
  if (userRole !== expectedRole) {
    console.error('Acceso denegado: Se requiere rol', expectedRole);
    router.navigate(['/login']);
    return false;
  }

  return true;
};
