import { inject, Injectable } from '@angular/core';
import { concesionPost } from '../../interfaces/concesion/concesionPost';
import { PagoPost } from '../../interfaces/pago/pagoPost';
import { Observable } from 'rxjs';
import { Concesion } from '../../interfaces/concesion/concesion';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ConcesionService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/concesiones';

  constructor() {}

  /**
   * Realiza la compra de una concesión enviando los datos de la concesión y el pago.
   * @param concesion Datos de la concesión a crear.
   * @param pago Datos del pago asociado.
   * @returns Observable con la respuesta del servidor.
   */
  comprarConcesion(concesion: concesionPost, pago: PagoPost): Observable<any> {
    // Se envía un objeto compuesto que contiene tanto la información de la concesión como la del pago.
    return this.http.post(`${this.apiUrl}`, {
      concesion: concesion,
      pago: pago,
    });
  }

  /**
   * Obtiene todas las concesiones asociadas a un cliente específico.
   * @param id ID del cliente.
   */
  findAllByCliente(id: number) {
    return this.http.get<Concesion[]>(`${this.apiUrl}/cliente/${id}`);
  }

  /**
   * Obtiene las concesiones de un cliente filtradas por un ayuntamiento específico.
   * @param clienteId ID del cliente.
   * @param aytoId ID del ayuntamiento.
   */
  getConcesionesPorAyuntamiento(clienteId: number, aytoId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/cliente/${clienteId}/ayuntamiento/${aytoId}`);
  }
}
