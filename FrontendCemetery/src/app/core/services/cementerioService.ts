import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Cementerio } from '../../interfaces/cementerio/cementerio';
import { CementerioPost } from '../../interfaces/cementerio/cementerioPost';
import { CementerioUpdate } from '../../interfaces/cementerio/cementerioUpdate';

@Injectable({
  providedIn: 'root',
})
export class CementerioService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/cementerios';

  constructor() {}

  cementerios = signal<Cementerio[]>([]);
  amount = signal<number>(0);

  loadAll() {
    this.http.get<Cementerio[]>(this.apiUrl).subscribe((data) => {
      this.cementerios.set(data);
      this.amount.set(this.cementerios().length);
      console.log(data);
      console.log(this.amount);
    });
  }

  save(cementerio: CementerioPost) {
    return this.http.post<Cementerio>(this.apiUrl, cementerio);
  }

  find(id: number) {
    return this.http.get<Cementerio>(`${this.apiUrl}/${id}`);
  }

  update(cementerio: CementerioUpdate, id: number) {
    return this.http.put<Cementerio>(`${this.apiUrl}/${id}`, cementerio);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
