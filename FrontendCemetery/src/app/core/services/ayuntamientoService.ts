import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Ayuntamiento } from '../../interfaces/ayuntamiento';

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
}
