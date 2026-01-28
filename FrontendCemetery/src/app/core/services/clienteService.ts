import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Cliente } from '../../interfaces/cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/v1/clientes';

  constructor() {}

  clientes = signal<Cliente[]>([]);

  amount = signal<number>(0);

  loadAll() {
    this.http.get<Cliente[]>(this.apiUrl).subscribe((data) => {
      this.clientes.set(data);
      this.amount.set(this.clientes().length);
    });
  }
}
