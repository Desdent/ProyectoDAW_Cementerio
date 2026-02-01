import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Difunto } from '../../interfaces/difunto/difunto';
import { DifuntoPost } from '../../interfaces/difunto/difuntoPost';
import { DifuntoUpdate } from '../../interfaces/difunto/difuntoUpdate';

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

  loadAll() {
    // los genericos <T> solo aceptan tipos, no variables, osea que en este caso hay que decirlo que el tipo (de lo que va a recibir) es un array de objetos Difunto -> Difunto[]
    this.http.get<Difunto[]>(this.apiUrl).subscribe((data) => {
      this.difuntos.set(data);
      console.log(data);
    });
  }

  subirImagen(file: File) {
    const formData = new FormData();
    formData.append('archivo', file);
    return this.http.post<{ nombreArchivo: string }>(`${this.apiUrl}/upload`, formData);
  }

  loadAllByAyuntamiento(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/ayuntamiento/${id}`);
  }

  loadAllByCementerio(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/cementerio/${id}`);
  }

  loadAllByCliente(id: number) {
    return this.http.get<Difunto[]>(`${this.apiUrl}/cliente/${id}`);
  }

  save(difunto: DifuntoPost) {
    return this.http.post<DifuntoPost>(this.apiUrl, difunto);
  }

  find(id: number) {
    return this.http.get<Difunto>(`${this.apiUrl}/${id}`);
  }

  update(difunto: DifuntoUpdate, id: number) {
    return this.http.put<DifuntoUpdate>(`${this.apiUrl}/${id}`, difunto);
  }

  delete(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
