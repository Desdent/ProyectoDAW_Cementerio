import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard-layout-cliente-component',
  standalone: true,
  imports: [RouterOutlet, RouterLinkActive, RouterLink],
  templateUrl: './dashboard-layout-cliente-component.html',
  styleUrl: './dashboard-layout-cliente-component.css',
})
export class DashboardLayoutClienteComponent {}
