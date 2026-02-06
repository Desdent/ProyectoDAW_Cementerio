import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LoginService } from '../../../core/services/loginService';
import { AccesiblityComponent } from '../../../features/accesiblity-component/accesiblity-component';

@Component({
  selector: 'app-header-component',
  imports: [RouterLink],
  templateUrl: './header-component.html',
  styleUrl: './header-component.css',
})
export class HeaderComponent {
  public loginService = inject(LoginService);
  public accesibility = inject(AccesiblityComponent);

  email = signal<string | null>(localStorage.getItem('email'));
  initials = signal<string>((localStorage.getItem('email') || '').substring(0, 2).toUpperCase());

  constructor() {}

  deleteEmail(): any {
    localStorage.clear();
    this.email.set(null);
  }

  obtainEmail(): string | null {
    return localStorage.getItem('email');
  }
}
