import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard-layout-ayto-component',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './dashboard-layout-ayto-component.html',
  styleUrl: './dashboard-layout-ayto-component.css',
})
export class DashboardLayoutAytoComponent {}
