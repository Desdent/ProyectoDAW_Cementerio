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

  cementerios = signal<Cementerio[]>([]);
  amount = signal<number>(0);

  constructor() {}

  /**
   * Carga todos los cementerios desde la API y actualiza las señales reactivas.
   */
  loadAll() {
    this.http.get<Cementerio[]>(this.apiUrl).subscribe((data) => {
      this.cementerios.set(data);
      this.amount.set(data.length);
    });
  }

  /**
   * Obtiene los cementerios asociados a un ayuntamiento específico.
   * @param id ID del ayuntamiento.
   * @returns Observable con el array de cementerios.
   */
  loadAllByAyuntamiento(id: number) {
    return this.http.get<Cementerio[]>(`${this.apiUrl}/ayuntamiento/${id}`);
  }

  /**
   * Busca el cementerio al que pertenece una concesión.
   * @param id ID de la concesión.
   * @returns Observable con el objeto cementerio.
   */
  findByConcesion(id: number) {
    return this.http.get<Cementerio>(`${this.apiUrl}/concesion/${id}`);
  }

  /**
   * Sube un archivo de imagen (mapa) al servidor.
   * @param file El archivo a subir.
   * @returns Observable con el nombre del archivo guardado.
   */
  subirImagen(file: File) {
    const formData = new FormData();
    formData.append('archivo', file);
    return this.http.post<{ nombreArchivo: string }>(`${this.apiUrl}/upload`, formData);
  }

  /**
   * Crea un nuevo registro de cementerio.
   * @param cementerio Datos del cementerio a crear.
   * @returns Observable con el cementerio creado.
   */
  save(cementerio: CementerioPost) {
    return this.http.post<Cementerio>(this.apiUrl, cementerio);
  }

  /**
   * Busca un cementerio por su identificador único.
   * @param id ID del cementerio.
   * @returns Observable con los datos del cementerio.
   */
  find(id: number) {
    return this.http.get<Cementerio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Actualiza los datos de un cementerio existente.
   * @param cementerio Datos actualizados.
   * @param id ID del cementerio a modificar.
   * @returns Observable con el cementerio actualizado.
   */
  update(cementerio: CementerioUpdate, id: number) {
    return this.http.put<Cementerio>(`${this.apiUrl}/${id}`, cementerio);
  }

  /**
   * Elimina un cementerio del sistema.
   * @param id ID del cementerio a eliminar.
   * @returns Observable de la operación.
   */
  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
