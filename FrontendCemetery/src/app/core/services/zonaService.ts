import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Zona } from '../../interfaces/zona/zona';
import { zonaPost } from '../../interfaces/zona/zonaPost';
import { zonaUpdate } from '../../interfaces/zona/zonaUpdate';

@Injectable({
  providedIn: 'root',
})
export class ZonaService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/zonas';

  zonas = signal<Zona[]>([]);
  amount = signal<number>(0);
  tipos = signal<string[]>([]);

  constructor() {}

  loadAll() {
    this.http.get<Zona[]>(this.apiUrl).subscribe((data) => {
      this.zonas.set(data);
      this.amount.set(this.zonas().length);
    });
  }

  save(zona: zonaPost) {
    return this.http.post<Zona>(this.apiUrl, zona);
  }

  find(id: number) {
    return this.http.get<Zona>(`${this.apiUrl}/${id}`);
  }

  findAllByCementerioId(id: number) {
    return this.http.get<Zona[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  getAllTipos() {
    this.http.get<string[]>('http://localhost:8080/api/v1/zonas/tipos').subscribe((data) => {
      this.tipos.set(data);
    });
  }

  update(zona: zonaUpdate, id: number) {
    return this.http.put<Zona>(`${this.apiUrl}/${id}`, zona);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
