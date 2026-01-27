import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home-component/home-component';
import { DashboardAdminComponent } from './features/admin/dashboard-admin-component/dashboard-admin-component';
import { DashboardAyuntamientoComponent } from './features/ayuntamiento/dashboard-ayuntamiento-component/dashboard-ayuntamiento-component';
import { DashboardClienteComponent } from './features/cliente/dashboard-cliente-component/dashboard-cliente-component';
import { LoginComponent } from './features/login-component/login-component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'admin', component: DashboardAdminComponent },
  { path: 'ayuntamiento', component: DashboardAyuntamientoComponent },
  { path: 'cliente', component: DashboardClienteComponent },
  { path: 'login', component: LoginComponent },
];
