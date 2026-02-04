import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-politica-privacidad-component',
  imports: [],
  templateUrl: './politica-privacidad-component.html',
  styleUrl: './politica-privacidad-component.css',
})
export class PoliticaPrivacidadComponent {
  private router = inject(Router);

  volver() {
    window.history.back();
  }
}
