import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../../../product.model';


@Component({
  selector: 'app-shop-item',
  templateUrl: './shop-item.component.html',
  styleUrls: ['./shop-item.component.css']
})
export class ShopItemComponent implements OnInit {
  // tslint:disable-next-line:no-input-rename
  @Input('product') product: Product;

  constructor() { }

  ngOnInit() {

  }

  public getPicture() {
    return this.product.getMainPicture();
  }

  public getHeader() {
    return this.product.name;
  }

  public getStartPrice() {
    return this.product.startPrice;
  }
}
