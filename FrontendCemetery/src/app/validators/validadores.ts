import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class Validadores {
  /**
   * Valido que el campo tenga un formato de correo electrónico estándar mediante una expresión regular.
   */
  static emailValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      const valid = emailRegex.test(control.value);

      return valid ? null : { email: { value: control.value } };
    };
  }

  /**
   * Validador de teléfono español
   * 9 dígitos que empiecen por 9, 6 o 7
   */
  static telefono(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const telefonoRegex = /^[679]\d{8}$/;
      const valid = telefonoRegex.test(control.value.toString().replace(/\s/g, ''));

      return valid ? null : { telefono: { value: control.value } };
    };
  }

  /**
   * Validador de número positivo
   * Solo permite números mayores que 0
   */
  static numeroPositivo(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value === null || control.value === undefined || control.value === '') {
        return null;
      }

      const valor = Number(control.value);
      const valid = !isNaN(valor) && valor > 0;

      return valid ? null : { numeroPositivo: { value: control.value } };
    };
  }

  /**
   * Valido el DNI español calculando si la letra corresponde al número introducido mediante el algoritmo de resto 23.
   */
  static dni(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const dniRegex = /^[0-9]{8}[A-Z]$/;
      const dni = control.value.toString().toUpperCase().replace(/\s/g, '');

      if (!dniRegex.test(dni)) {
        return { dni: { value: control.value, error: 'Formato inválido' } };
      }

      const letras = 'TRWAGMYFPDXBNJZSQVHLCKE';
      const numero = parseInt(dni.substr(0, 8), 10);
      const letra = dni.charAt(8);
      // Calculo la posición de la letra en la cadena de control usando el módulo 23.
      const letraCorrecta = letras.charAt(numero % 23);

      if (letra !== letraCorrecta) {
        return { dni: { value: control.value, error: `La letra correcta es ${letraCorrecta}` } };
      }

      return null;
    };
  }

  /**
   * Validador de NIE español
   * Valida formato y letra correcta
   */
  static nie(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const nieRegex = /^[XYZ][0-9]{7}[A-Z]$/;
      const nie = control.value.toString().toUpperCase().replace(/\s/g, '');

      if (!nieRegex.test(nie)) {
        return { nie: { value: control.value, error: 'Formato inválido' } };
      }

      const letras = 'TRWAGMYFPDXBNJZSQVHLCKE';
      let numero = nie.substr(1, 7);

      // El NIE español sustituye la primera letra por un número para el cálculo del algoritmo:
      // X = 0, Y = 1, Z = 2.
      const primeraLetra = nie.charAt(0);
      if (primeraLetra === 'X') numero = '0' + numero;
      else if (primeraLetra === 'Y') numero = '1' + numero;
      else if (primeraLetra === 'Z') numero = '2' + numero;

      const letra = nie.charAt(8);
      const letraCorrecta = letras.charAt(parseInt(numero, 10) % 23);

      if (letra !== letraCorrecta) {
        return { nie: { value: control.value, error: `La letra correcta es ${letraCorrecta}` } };
      }

      return null;
    };
  }

  /**
   * Validador de NIF de empresa/ayuntamiento
   * Formato: letra + 7 dígitos + letra de control
   */
  static nif(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const nif = control.value.toString().toUpperCase().replace(/\s/g, '');
      const nifRegex = /^[A-Z][0-9]{7}[A-Z0-9]$/;

      if (!nifRegex.test(nif)) {
        return { nif: { value: control.value, error: 'Formato inválido' } };
      }

      const tipoOrganizacion = nif.charAt(0);
      const numero = nif.substr(1, 7);
      const control_char = nif.charAt(8);

      // Tipos válidos para entidades públicas/ayuntamientos
      const tiposValidos = ['P', 'Q', 'S', 'W']; // P=Corporación local, Q=Organismo público, S=Órgano Admin, W=Establec. permanente

      if (!tiposValidos.includes(tipoOrganizacion)) {
        return {
          nif: { value: control.value, error: 'Tipo de organización inválido para ayuntamiento' },
        };
      }

      // Calcular letra de control
      const letras = 'JABCDEFGHI';
      const sum = parseInt(numero, 10);
      const letraCorrecta = letras.charAt(sum % 10);

      if (control_char !== letraCorrecta) {
        return {
          nif: {
            value: control.value,
            error: `El carácter de control correcto es ${letraCorrecta}`,
          },
        };
      }

      return null;
    };
  }

  /**
   * Validador de coordenadas
   * Requiere mínimo 3 pares de coordenadas (x,y)
   * Formato esperado: "x1,y1,x2,y2,x3,y3" o similar
   */
  static coordenadas(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const coordenadas = control.value.toString().trim();

      // Eliminar espacios y dividir por comas
      const valores = coordenadas
        .split(',')
        .map((v: string) => v.trim())
        .filter((v: string) => v !== '');

      // Debe haber al menos 6 valores (3 pares x,y)
      if (valores.length < 6) {
        return {
          coordenadas: {
            value: control.value,
            error: `Se requieren al menos 3 puntos (6 valores). Se encontraron ${valores.length} valores`,
          },
        };
      }

      // Debe haber un número par de valores
      if (valores.length % 2 !== 0) {
        return {
          coordenadas: {
            value: control.value,
            error: 'Debe haber un número par de coordenadas (pares x,y)',
          },
        };
      }

      // Validar que todos sean números
      const todosNumeros = valores.every((v: string) => !isNaN(Number(v)));
      if (!todosNumeros) {
        return {
          coordenadas: {
            value: control.value,
            error: 'Todas las coordenadas deben ser números válidos',
          },
        };
      }

      return null;
    };
  }

  /**
   * Validador combinado DNI/NIE
   * Acepta tanto DNI como NIE
   */
  static dniNie(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const valor = control.value.toString().toUpperCase().replace(/\s/g, '');

      // Intentar validar como DNI
      const dniValidator = Validadores.dni();
      const dniError = dniValidator(control);

      if (!dniError) {
        return null; // Es un DNI válido
      }

      // Intentar validar como NIE
      const nieValidator = Validadores.nie();
      const nieError = nieValidator(control);

      if (!nieError) {
        return null; // Es un NIE válido
      }

      // No es ni DNI ni NIE válido
      return { dniNie: { value: control.value, error: 'DNI/NIE inválido' } };
    };
  }

  /**
   * Verifica que la fecha no sea anterior a una fecha de referencia
   */
  static fechaNoAnteriorA(fechaReferencia: Date): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;

      const fechaInput = new Date(control.value);
      // Reseteamos horas para comparar solo días
      const ref = new Date(fechaReferencia.setHours(0, 0, 0, 0));
      const input = new Date(fechaInput.setHours(0, 0, 0, 0));

      return input < ref ? { fechaAnterior: { min: ref, actual: input } } : null;
    };
  }

  /**
   * Verifica que la fecha no sea posterior a una fecha de referencia
   */
  static fechaNoPosteriorA(fechaReferencia: Date): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) return null;

      const fechaInput = new Date(control.value);
      const ref = new Date(fechaReferencia.setHours(0, 0, 0, 0));
      const input = new Date(fechaInput.setHours(0, 0, 0, 0));

      return input > ref ? { fechaPosterior: { max: ref, actual: input } } : null;
    };
  }
}
