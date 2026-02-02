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

  /**
   * Cargo todos los ayuntamientos registrados y actualizo el recuento total.
   */
  loadAll() {
    this.http.get<Ayuntamiento[]>(this.apiUrl).subscribe((data) => {
      this.ayuntamientos.set(data);
      this.amount.set(this.ayuntamientos().length);
    });
  }

  /**
   * Registro un nuevo ayuntamiento en el sistema.
   * @param ayuntamiento Datos del ayuntamiento a crear.
   */
  save(ayuntamiento: ayuntamientoPost) {
    return this.http.post<Ayuntamiento>(this.apiUrl, ayuntamiento);
  }

  /**
   * Busco un ayuntamiento específico por su identificador.
   * @param id ID del ayuntamiento.
   */
  find(id: number) {
    return this.http.get<Ayuntamiento>(`${this.apiUrl}/${id}`);
  }

  /**
   * Actualizo la información de un ayuntamiento existente.
   * @param ayuntamiento Datos actualizados.
   * @param id ID del ayuntamiento a modificar.
   */
  update(ayuntamiento: ayuntamientoUpdate, id: number) {
    return this.http.put<Ayuntamiento>(`${this.apiUrl}/${id}`, ayuntamiento);
  }

  /**
   * Elimino un ayuntamiento del sistema.
   * @param id ID del ayuntamiento a borrar.
   */
  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
