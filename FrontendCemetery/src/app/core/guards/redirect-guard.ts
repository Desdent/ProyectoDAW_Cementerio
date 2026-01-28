import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { LoginService } from '../services/loginService';

export const redirectGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  // Si no está autenticado, al Login
  if (!loginService.isAuthenticated()) {
    router.navigate(['/login']);
    return false;
  }

  // Si está autenticado, leemos el rol y redirigimos
  const role = loginService.getRole();

  if (role === 'ROLE_ADMIN') {
    router.navigate(['/admin/dashboard']);
  } else if (role === 'ROLE_AYUNTAMIENTO') {
    router.navigate(['/ayto/dashboard']);
  } else if (role === 'ROLE_CLIENTE') {
    router.navigate(['/cliente/dashboard']);
  } else {
    router.navigate(['/login']);
  }

  return false; // Siempre devuelvos false porque no queiero entrar en la ruta física, solo redirigir
};
