import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Zona } from '../../interfaces/zona/zona';
import { Parcela } from '../../interfaces/parcela/parcela';
import { ParcelaPost } from '../../interfaces/parcela/parcelaPost';
import { ParcelaUpdate } from '../../interfaces/parcela/parcelaUpdate';

@Injectable({
  providedIn: 'root',
})
export class ParcelaService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/parcelas';

  parcelas = signal<Parcela[]>([]);
  amount = signal<number>(0);
  tipos = signal<string[]>([]);

  constructor() {}

  loadAll() {
    this.http.get<Parcela[]>(this.apiUrl).subscribe((data) => {
      this.parcelas.set(data);
      this.amount.set(this.parcelas().length);
    });
  }

  save(parcela: ParcelaPost) {
    return this.http.post<Zona>(this.apiUrl, parcela);
  }

  find(id: number) {
    return this.http.get<Parcela>(`${this.apiUrl}/${id}`);
  }

  findAllByCementerioId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  findAllByConcesionId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/concesion/${id}`);
  }

  findByZonaId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/zona/${id}`);
  }

  getAllTipos() {
    this.http.get<string[]>('http://localhost:8080/api/v1/zonas/tipos').subscribe((data) => {
      this.tipos.set(data);
    });
  }

  update(parcela: ParcelaUpdate, id: number) {
    return this.http.put<Zona>(`${this.apiUrl}/${id}`, parcela);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
