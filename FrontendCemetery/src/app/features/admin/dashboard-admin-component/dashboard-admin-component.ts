import { Component, inject } from '@angular/core';
import { CementerioService } from '../../../core/services/cementerioService';

@Component({
  selector: 'app-dashboard-admin-component',
  imports: [],
  templateUrl: './dashboard-admin-component.html',
  styleUrl: './dashboard-admin-component.css',
})
export class DashboardAdminComponent {
  public cementerioService = inject(CementerioService);

  ngOnInit() {
    this.cementerioService.loadAll();
  }
}
