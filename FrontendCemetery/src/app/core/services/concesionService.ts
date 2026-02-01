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

  comprarConcesion(concesion: concesionPost, pago: PagoPost): Observable<any> {
    return this.http.post(`${this.apiUrl}`, {
      concesion: concesion,
      pago: pago,
    });
  }

  findAllByCliente(id: number) {
    return this.http.get<Concesion[]>(`${this.apiUrl}/cliente/${id}`);
  }
}
