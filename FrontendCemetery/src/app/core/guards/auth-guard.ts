import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';
import { LoginService } from '../services/loginService';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(LoginService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    router.navigate(['/perfil']);
    return false;
  }
  return true;
};
