import { Component, OnInit } from '@angular/core';
import {Product} from '../../product.model';

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit {

  private product: Product;
  constructor() {
    this.product = new Product();
    this.product.name = 'Shoeees';
    this.product.startPrice = 100;
    this.product.pictures = new Array<string>();
    this.product.pictures.push('https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Black_Converse_sneakers.JPG/1200px-Black_Converse_sneakers.JPG');
  }


  ngOnInit() {
  }

}
