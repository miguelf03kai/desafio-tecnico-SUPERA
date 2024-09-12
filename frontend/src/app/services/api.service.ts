import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8090';

  constructor(private http: HttpClient) {}

  getListas(): Observable<any> {
    return this.http.get(`${this.baseUrl}/lista`);
  }

  createLista(lista: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/lista`, lista);
  }

  updateLista(lista: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/lista/${lista.listaId}`, lista);
  }

  deleteLista(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/lista/${id}`);
  }

  getItensByLista(listaId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/item?listaId=${listaId}`);
  }

  getItens(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/item`);
  }

  createItem(item: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/item`, item);
  }

  updateItem(item: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/item/${item.itemId}`, item);
  }

  deleteItem(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/item/${id}`);
  }

  getEstados(): Observable<any> {
    return this.http.get(`${this.baseUrl}/estadoitem`);
  }

  getPrioridades(): Observable<any> {
    return this.http.get(`${this.baseUrl}/prioridadeitem`);
  }

  createEstado(estado: any): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/estadoitem`, estado);
  }

  createPrioridade(prioridade: any): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/prioridadeitem`, prioridade);
  }

  updatePrioridade(prioridade: any): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/prioridadeitem/${prioridade.itemPrioridadeId}`, prioridade);
  }

  updateEstado(estado: any): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/estadoitem/${estado.itemEstadoId}`, estado);
  }

  deleteEstado(estadoId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/estadoitem/${estadoId}`);
  }

  deletePrioridade(prioridadeId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/prioridadeitem/${prioridadeId}`);
  }

}
