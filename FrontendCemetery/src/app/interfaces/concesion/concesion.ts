export interface Concesion {
  id: number;
  precio: number;
  fechaInicio: string;
  fechaFin: string;
  vencida: boolean;
  clienteId: number;
  pagoId: number;
  parcelaIds: number[];
}
