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

  /**
   * Carga todas las parcelas registradas en el sistema.
   */
  loadAll() {
    this.http.get<Parcela[]>(this.apiUrl).subscribe((data) => {
      this.parcelas.set(data);
      this.amount.set(this.parcelas().length);
    });
  }

  /**
   * Registra una nueva parcela en la base de datos.
   * @param parcela Datos de la parcela.
   */
  save(parcela: ParcelaPost) {
    return this.http.post<Zona>(this.apiUrl, parcela);
  }

  /**
   * Obtiene los detalles de una parcela por su ID.
   * @param id ID de la parcela.
   */
  find(id: number) {
    return this.http.get<Parcela>(`${this.apiUrl}/${id}`);
  }

  /**
   * Recupera todas las parcelas de un cementerio.
   * @param id ID del cementerio.
   */
  findAllByCementerioId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  /**
   * Recupera las parcelas asociadas a una concesión.
   * @param id ID de la concesión.
   */
  findAllByConcesionId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/concesion/${id}`);
  }

  /**
   * Recupera las parcelas de una zona específica.
   * @param id ID de la zona.
   */
  findByZonaId(id: number) {
    return this.http.get<Parcela[]>(`${this.apiUrl}/zona/${id}`);
  }

  /**
   * Obtiene el listado de tipos de zonas permitidos.
   */
  getAllTipos() {
    this.http.get<string[]>('http://localhost:8080/api/v1/zonas/tipos').subscribe((data) => {
      this.tipos.set(data);
    });
  }

  /**
   * Actualiza la información de una parcela.
   * @param parcela Datos actualizados.
   * @param id ID de la parcela.
   */
  update(parcela: ParcelaUpdate, id: number) {
    return this.http.put<Zona>(`${this.apiUrl}/${id}`, parcela);
  }

  /**
   * Elimina una parcela del sistema.
   * @param id ID de la parcela.
   */
  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
