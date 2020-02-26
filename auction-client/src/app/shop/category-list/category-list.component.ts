import { Component, OnInit } from '@angular/core';
import {Category} from './category-item/category.model';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  public categories: Array<Category>;
  constructor() {
    this.categories = new Array<Category>(
      new Category(1, 'Computer', new Array<string>('Mouse', 'Keyboard', 'Monitor')),
      new Category(2, 'Fashion', new Array<string>('T-Shirt', 'Jeans', 'Trousers')));
  }

  ngOnInit() {
  }

}
