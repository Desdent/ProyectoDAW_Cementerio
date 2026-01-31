export interface DifuntoPost {
  nombre: string;
  apellido1: string;
  apellido2: string;
  yearNacimiento: number;
  yearDefuncion: number;
  fechaEntierro: Date;
  mensaje?: string;
  foto?: string;
  parcelaId: number;
}
