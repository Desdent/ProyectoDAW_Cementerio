import { AbstractControl, ValidationErrors } from '@angular/forms';

export class Validadores {
  public static emailValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value as string;

    if (!value) {
      return null;
    }

    const tieneArroba = value.includes('@');
    const tienePunto = value.includes('.');

    return tieneArroba && tienePunto ? null : { emailInvalido: true };
  }
}
