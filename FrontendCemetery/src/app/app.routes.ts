import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home-component/home-component';
import { DashboardAdminComponent } from './features/admin/dashboard-admin-component/dashboard-admin-component';
import { DashboardAyuntamientoComponent } from './features/ayuntamiento/dashboard-ayuntamiento-component/dashboard-ayuntamiento-component';
import { DashboardClienteComponent } from './features/cliente/dashboard-cliente-component/dashboard-cliente-component';
import { LoginComponent } from './features/login-component/login-component';
import { authGuard } from './core/guards/auth-guard';
import { redirectGuard } from './core/guards/redirect-guard';
import { roleGuard } from './core/guards/role-guard';
import { logoutGuard } from './core/guards/logout-guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'perfil', canActivate: [redirectGuard], component: HomeComponent }, // El component es solo para que no de error, nunca se va a acceder a el
  {
    path: 'admin/dashboard',
    component: DashboardAdminComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  {
    path: 'ayuntamiento/dashboard',
    component: DashboardAyuntamientoComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_AYUNTAMIENTO' },
  },
  {
    path: 'cliente/dashboard',
    component: DashboardClienteComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_CLIENTE' },
  },
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'logout', canActivate: [logoutGuard], component: HomeComponent }, // El component es solo para que no de error, nunca se va a acceder a el
];
