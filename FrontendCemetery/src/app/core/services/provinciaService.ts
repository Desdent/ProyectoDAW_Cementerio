import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Provincia } from '../../interfaces/provincia';

@Injectable({
  providedIn: 'root',
})
export class ProvinciaService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/provincias';

  provincias = signal<Provincia[]>([]);

  constructor() {}

  /**
   * Solicito la lista de todas las provincias a la API.
   */
  loadAll() {
    this.http.get<Provincia[]>(this.apiUrl).subscribe((data) => {
      this.provincias.set(data);
    });
  }
}
