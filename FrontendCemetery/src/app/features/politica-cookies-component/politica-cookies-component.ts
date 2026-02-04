import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-politica-cookies-component',
  imports: [],
  templateUrl: './politica-cookies-component.html',
  styleUrl: './politica-cookies-component.css',
})
export class PoliticaCookiesComponent {
  private router = inject(Router);

  volver() {
    window.history.back();
  }
}
