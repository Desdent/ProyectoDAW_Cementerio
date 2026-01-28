import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home-component/home-component';
import { DashboardAdminLayoutComponent } from './features/admin/dashboard-admin-layout-component/dashboard-admin-layout-component';
import { DashboardAyuntamientoComponent } from './features/ayuntamiento/dashboard-ayuntamiento-component/dashboard-ayuntamiento-component';
import { DashboardClienteComponent } from './features/cliente/dashboard-cliente-component/dashboard-cliente-component';
import { LoginComponent } from './features/login-component/login-component';
import { authGuard } from './core/guards/auth-guard';
import { redirectGuard } from './core/guards/redirect-guard';
import { roleGuard } from './core/guards/role-guard';
import { logoutGuard } from './core/guards/logout-guard';
import { CementeriosAdminComponent } from './features/admin/dashboard-admin-layout-component/cementerios-admin-component/cementerios-admin-component';
import { MainPanelAdminComponent } from './features/admin/dashboard-admin-layout-component/main-panel-admin-component/main-panel-admin-component';
import { AyuntamientosAdminComponent } from './features/admin/dashboard-admin-layout-component/ayuntamientos-admin-component/ayuntamientos-admin-component';
import { ClientesAdminComponent } from './features/admin/dashboard-admin-layout-component/clientes-admin-component/clientes-admin-component';
import { StatsComponent } from './features/admin/dashboard-admin-layout-component/stats-component/stats-component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'perfil', canActivate: [redirectGuard], component: HomeComponent }, // El component es solo para que no de error, nunca se va a acceder a el
  // ADMIN
  {
    path: 'admin/dashboard',
    component: DashboardAdminLayoutComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
    children: [
      {
        path: '',
        component: MainPanelAdminComponent,
      },
      {
        path: 'cementerios',
        component: CementeriosAdminComponent,
      },
      {
        path: 'ayuntamientos',
        component: AyuntamientosAdminComponent,
      },
      {
        path: 'clientes',
        component: ClientesAdminComponent,
      },
      {
        path: 'stats',
        component: StatsComponent,
      },
    ],
  },
  {
    path: 'admin/dashboard/cementerios',
    component: CementeriosAdminComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  // AYUNTAMIENTO
  {
    path: 'ayuntamiento/dashboard',
    component: DashboardAyuntamientoComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_AYUNTAMIENTO' },
  },
  // CLIENTE
  {
    path: 'cliente/dashboard',
    component: DashboardClienteComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_CLIENTE' },
  },
  // LOGIN/LOGOUT
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'logout', canActivate: [logoutGuard], component: HomeComponent }, // El component es solo para que no de error, nunca se va a acceder a el
];
