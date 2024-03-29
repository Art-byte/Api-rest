import { Component, OnInit } from '@angular/core';
import { Producto } from '../models/producto'
import { ProductoService } from '../service/producto.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-producto',
  templateUrl: './lista-producto.component.html',
  styleUrls: ['./lista-producto.component.css']
})
export class ListaProductoComponent implements OnInit {


  productos: Producto[] = [];

  constructor(
    private productoService: ProductoService,
    private route :Router

    ) { }

  ngOnInit(): void {
    this.cargarProductos()
  }

  cargarProductos(): void{
    this.productoService.lista()
    .subscribe(
      data => {this.productos = data;},
      err => console.log(err)
    )
  }

  borrar(id: number){
    this.productoService.delete(id)
    .subscribe(
      data => {
        this.cargarProductos()
      },
      err =>{
        console.log(err)
      }
    )
    }
}
