import { Component, inject, signal, WritableSignal } from '@angular/core';
import { LoginService } from '../../core/services/loginService';
import { User } from '../../entity/user';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validadores } from '../../validators/validadores';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-component',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  public loginService = inject(LoginService);
  private router = inject(Router);
  user!: User;
  formUser!: FormGroup;
  error = signal(false);

  constructor() {
    this.user = new User();
    this.formUser = new FormGroup({
      email: new FormControl('', [Validators.required, Validadores.emailValidator]),
      password: new FormControl('', [Validators.required, Validators.minLength(5)]),
    });
  }

  login(): void {
    if (this.formUser.invalid) {
      this.formUser.markAllAsTouched();
      return;
    }

    this.loginService.login(this.formUser.value).subscribe({
      next: (response) => {
        console.log('Éxito:', response);
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('email', response.email);
        this.router.navigate(['/perfil']);
        this.error.set(false);
      },
      error: (err) => {
        console.error('Error:', err);
        if (err.status === 401) {
          this.error.set(true);
        }
      },
    });
  }
}
