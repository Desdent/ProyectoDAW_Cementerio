import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Ayuntamiento } from '../../interfaces/ayuntamiento/ayuntamiento';
import { ayuntamientoPost } from '../../interfaces/ayuntamiento/ayuntamientoPost';
import { ayuntamientoUpdate } from '../../interfaces/ayuntamiento/ayuntamientoUpdate';

@Injectable({
  providedIn: 'root',
})
export class AyuntamientoService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/ayuntamientos';

  ayuntamientos = signal<Ayuntamiento[]>([]);
  amount = signal<number>(0);

  constructor() {}

  loadAll() {
    this.http.get<Ayuntamiento[]>(this.apiUrl).subscribe((data) => {
      this.ayuntamientos.set(data);
      this.amount.set(this.ayuntamientos().length);
    });
  }

  save(ayuntamiento: ayuntamientoPost) {
    return this.http.post<Ayuntamiento>(this.apiUrl, ayuntamiento);
  }

  find(id: number) {
    return this.http.get<Ayuntamiento>(`${this.apiUrl}/${id}`);
  }

  update(ayuntamiento: ayuntamientoUpdate, id: number) {
    return this.http.put<Ayuntamiento>(`${this.apiUrl}/${id}`, ayuntamiento);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
