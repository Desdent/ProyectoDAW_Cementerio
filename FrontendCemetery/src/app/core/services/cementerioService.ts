import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Cementerio } from '../../models/cementerio';

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
}
