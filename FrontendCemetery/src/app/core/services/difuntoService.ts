import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Difunto } from '../../interfaces/difunto/difunto';
import { DifuntoPost } from '../../interfaces/difunto/difuntoPost';
import { DifuntoUpdate } from '../../interfaces/difunto/difuntoUpdate';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
// Esta parte del codigo le dice a angular que cree solo una instancia de este servicio y se podrá inyectar desde cualquier parte
// es un poco como el @Service en spring
export class DifuntoService {
  constructor() {}

  private http = inject(HttpClient);
  // Esta parte del codigo es la forma moderna de inyectar el HttpClient, que es el modulo que permite realizar peticiones HTTP

  private apiUrl = 'http://localhost:8080/api/v1/difuntos';
  // Esto pues la url de la api en cuestion

  difuntos = signal<Difunto[]>([]);

  /**
   * Solicito la lista completa de difuntos a la API y actualizo la señal reactiva.
   */
  loadAll() {
    // Especifico que el tipo esperado es un array de Difunto.
    // Los genéricos <T> solo aceptan tipos, por lo que indico Difunto[].
    this.http.get<Difunto[]>(this.apiUrl).subscribe((data) => {
      this.difuntos.set(data);
      console.log(data);
    });
  }

  /**
   * Envío un archivo de imagen al servidor para asociarlo a la ficha de un difunto.
   * @param file El archivo de imagen.
   */
  subirImagen(file: File) {
    // Utilizo FormData para empaquetar el archivo y enviarlo como multipart/form-data.
    const formData = new FormData();
    formData.append('archivo', file);
    return this.http.post<{ nombreArchivo: string }>(`${this.apiUrl}/upload`, formData);
  }

  /**
   * Obtengo los difuntos que pertenecen a un ayuntamiento concreto.
   * @param id ID del ayuntamiento.
   */
  loadAllByAyuntamiento(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/ayuntamiento/${id}`);
  }

  /**
   * Recupero la lista de personas enterradas en un cementerio específico.
   * @param id ID del cementerio.
   */
  loadAllByCementerio(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  /**
   * Consulto quién está enterrado en una parcela determinada.
   * @param id ID de la parcela.
   */
  loadAllByParcela(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/parcela/${id}`);
  }

  /**
   * Busco los familiares difuntos de un cliente.
   * @param id ID del cliente.
   */
  loadAllByCliente(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/cliente/${id}`);
  }

  /**
   * Guardo los datos de un nuevo difunto en el sistema.
   * @param difunto Datos del difunto.
   */
  save(difunto: DifuntoPost) {
    return this.http.post<DifuntoPost>(this.apiUrl, difunto);
  }

  /**
   * Busco la información detallada de un difunto por su identificador.
   * @param id ID del difunto.
   */
  find(id: number) {
    return this.http.get<Difunto>(`${this.apiUrl}/${id}`);
  }

  /**
   * Actualizo los datos de un difunto existente.
   * @param difunto Datos actualizados.
   * @param id ID del difunto.
   */
  update(difunto: DifuntoUpdate, id: number) {
    return this.http.put<DifuntoUpdate>(`${this.apiUrl}/${id}`, difunto);
  }

  /**
   * Elimino el registro de un difunto del sistema.
   * @param id ID del difunto.
   */
  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  /**
   * Inicio el trámite de exhumación para retirar a un difunto de su parcela.
   * @param id ID del difunto.
   */
  exhumar(id: number) {
    return this.http.delete(`${this.apiUrl}/exhumar/${id}`);
  }

  /**
   * Filtro los difuntos de un cliente según el ayuntamiento al que pertenecen.
   * @param clienteId ID del cliente.
   * @param aytoId ID del ayuntamiento.
   */
  getDifuntosPorAyuntamiento(clienteId: number, aytoId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `http://localhost:8080/api/v1/difuntos/cliente/${clienteId}/ayuntamiento/${aytoId}`,
    );
  }
}
