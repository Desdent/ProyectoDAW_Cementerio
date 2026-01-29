import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Ciudad } from '../../interfaces/ciudad';

@Injectable({
  providedIn: 'root',
})
export class CiudadService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/ciudades';

  constructor() {}

  ciudades = signal<Ciudad[]>([]);

  loadAll() {
    this.http.get<Ciudad[]>(this.apiUrl).subscribe((data) => {
      this.ciudades.set(data);
      console.log(data);
    });
  }

  loadByProvinciaId(id: number) {
    const urlFiltrada = `${this.apiUrl}/provincia/${id}`;
    this.http.get<Ciudad[]>(urlFiltrada).subscribe((data) => {
      this.ciudades.set(data);
      console.log(data);
    });
  }
}
