import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Producto } from '../models/producto'

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  productUrl = "http://localhost:8080/producto/";

  constructor(private httpClient: HttpClient) { }

  public lista(): Observable<Producto[]>{
    return this.httpClient.get<Producto[]>(this.productUrl + 'lista');
  }

  public detail(id :number): Observable<Producto>{
    return this.httpClient.get<Producto>(this.productUrl + 'detalle/' + id);
  }

  public save(producto : Producto): Observable<any>{
    return this.httpClient.post<any>(this.productUrl + 'create', producto);
  }

  public update(id: number, producto: Producto):Observable<any>{
    return this.httpClient.put<any>(this.productUrl + 'update/' + id, producto);
  }

  public delete(id: number): Observable<any>{
    return this.httpClient.delete<any>(this.productUrl+ 'delete/' + id);
  }
}
