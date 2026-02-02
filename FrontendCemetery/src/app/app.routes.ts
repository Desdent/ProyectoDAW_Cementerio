import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home-component/home-component';
import { DashboardAdminLayoutComponent } from './features/admin/dashboard-admin-layout-component/dashboard-admin-layout-component';
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
import { MainPanelAytoComponent } from './features/ayuntamiento/main-panel-ayto-component/main-panel-ayto-component';
import { CementeriosAytoComponent } from './features/ayuntamiento/cementerios-ayto-component/cementerios-ayto-component';
import { ClientesAytoComponent } from './features/ayuntamiento/clientes-ayto-component/clientes-ayto-component';
import { DashboardLayoutAytoComponent } from './features/ayuntamiento/dashboard-layout-ayto-component/dashboard-layout-ayto-component';
import { MainClienteComponent } from './features/cliente/main-cliente-component/main-cliente-component';
import { DashboardLayoutClienteComponent } from './features/cliente/dashboard-layout-cliente-component/dashboard-layout-cliente-component';
import { CementeriosClienteComponent } from './features/cliente/cementerios-cliente-component/cementerios-cliente-component';
import { DifuntosClienteComponent } from './features/cliente/difuntos-cliente-component/difuntos-cliente-component';
import { ConcesionesClienteComponent } from './features/cliente/concesiones-cliente-component/concesiones-cliente-component';
import { SearchComponent } from './features/search-component/search-component';
import { PanelCementerioComponent } from './features/panel-cementerio-component/panel-cementerio-component';
import { CarritoComponent } from './features/carrito-component/carrito-component';

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
    path: 'ayto/dashboard',
    component: DashboardLayoutAytoComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_AYUNTAMIENTO' },
    children: [
      {
        path: '',
        component: MainPanelAytoComponent,
      },
      {
        path: 'cementerios',
        component: CementeriosAytoComponent,
      },
      {
        path: 'clientes',
        component: ClientesAytoComponent,
      },
    ],
  },
  // CLIENTE
  {
    path: 'cliente/dashboard',
    component: DashboardLayoutClienteComponent,
    canActivate: [roleGuard],
    data: { expectedRole: 'ROLE_CLIENTE' },
    children: [
      {
        path: '',
        component: MainClienteComponent,
      },
      {
        path: 'cementerios',
        component: CementeriosClienteComponent,
      },

      {
        path: 'difuntos',
        component: DifuntosClienteComponent,
      },
      {
        path: 'concesiones',
        component: ConcesionesClienteComponent,
      },
    ],
  },
  // BUSCADOR
  { path: 'search', component: SearchComponent },
  // PANEL CEMENTERIO
  { path: 'cementerio/:id', component: PanelCementerioComponent },
  // CARRITO
  { path: 'carrito', component: CarritoComponent },
  // LOGIN/LOGOUT
  { path: 'login', component: LoginComponent, canActivate: [authGuard] },
  { path: 'logout', canActivate: [logoutGuard], component: HomeComponent }, // El component es solo para que no de error, nunca se va a acceder a el
];
