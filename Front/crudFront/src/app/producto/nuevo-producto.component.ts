import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../service/producto.service'
import { Producto } from '../models/producto';
//import { ToastrService } from 'node_modules/ngx-toastr/toastr/toastr.service'
import { Router } from '@angular/router';


@Component({
  selector: 'app-nuevo-producto',
  templateUrl: './nuevo-producto.component.html',
  styleUrls: ['./nuevo-producto.component.css']
})
export class NuevoProductoComponent implements OnInit {

  name: string ="";
  price: number = null;

  constructor(
    private productoService: ProductoService,
   // private toastr: ToastrService,
      private router: Router
    ) { }


  ngOnInit(): void {
  }


  onCreate():void{
    const producto = new Producto(this.name, this.price);
    this.productoService.save(producto)
    .subscribe(
      data =>{
       // this.toastr.success("Producto creado", "Felicidades",{timeOut: 3000});
        this.router.navigate(['/'])
      },
      err => {
        //this.toastr.error(err.error.message, 'Error');
        console.log(err)
        this.router.navigate(['/'])
      }

    )

  }
}
