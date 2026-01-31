import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Cliente } from '../../interfaces/cliente/cliente';
import { ClientePost } from '../../interfaces/cliente/clientePost';
import { ClienteUpdate } from '../../interfaces/cliente/clienteUpdate';

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

  loadAllByAyuntamiento(id: number) {
    return this.http.get<Cliente[]>(`${this.apiUrl}/ayuntamiento/${id}`);
  }

  loadAllByCementerio(id: number) {
    return this.http.get<Cliente[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  save(cliente: ClientePost) {
    return this.http.post<Cliente>(this.apiUrl, cliente);
  }

  find(id: number) {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  update(cliente: ClienteUpdate, id: number) {
    return this.http.put<Cliente>(`${this.apiUrl}/${id}`, cliente);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
