export class Producto {

  id?: number;
  name : string;
  price: number;

  contructor(name:string, price:number){
    this.name = name;
    this.price = price;
  }

}
