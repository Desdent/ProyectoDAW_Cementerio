import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header-component',
  imports: [RouterLink],
  templateUrl: './header-component.html',
  styleUrl: './header-component.css',
})
export class HeaderComponent {
  email = signal<string | null>(localStorage.getItem('email'));

  constructor() {}

  deleteEmail(): any {
    localStorage.clear();
    this.email.set(null);
  }

  obtainEmail(): string | null {
    return localStorage.getItem('email');
  }
}
