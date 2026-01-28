import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Servicio } from '../../interfaces/servicio';

@Injectable({
  providedIn: 'root',
})
export class ServicioService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/servicios';

  constructor() {}

  servicios = signal<Servicio[]>([]);
  amount = signal<number>(0);

  loadAll() {
    this.http.get<Servicio[]>(this.apiUrl).subscribe((data) => {
      this.servicios.set(data);
      this.amount.set(this.servicios().length);
    });
  }
}
