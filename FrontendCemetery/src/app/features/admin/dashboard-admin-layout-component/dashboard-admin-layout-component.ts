import { Component } from '@angular/core';
import { RouterLink, RouterOutlet, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-dashboard-layout-component',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './dashboard-admin-layout-component.html',
  styleUrl: './dashboard-admin-layout-component.css',
})
export class DashboardAdminLayoutComponent {
  elements = Array.from(document.querySelectorAll('.nav-item'));
}
