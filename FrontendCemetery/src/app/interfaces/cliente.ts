export interface Cliente {
  id: number;
  email: string;
  role: string;
  dni: string;
  nombre: string;
  apellido1: string;
  apellido2?: string;
  telefono: string;
  direccion: string;
  localidad: string;
  provincia: string;
}
