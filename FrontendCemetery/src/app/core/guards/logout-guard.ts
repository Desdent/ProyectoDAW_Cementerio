import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/loginService';
import { inject } from '@angular/core';

export const logoutGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  loginService.logout();

  router.navigate(['/login']);

  return false; // Evita que la ruta se cargue realmente
};
