import {Component, Input, OnInit} from '@angular/core';
import {Category} from './category.model';

@Component({
  selector: 'app-category-item',
  templateUrl: './category-item.component.html',
  styleUrls: ['./category-item.component.css']
})
export class CategoryItemComponent implements OnInit {
  // tslint:disable-next-line:no-input-rename
  @Input('category')
  public category: Category;
  @Input('number')
  public number: number;
  public clicked: boolean;

  constructor() {
    this.clicked = false;
  }

  ngOnInit() {
  }

  public toggle() {
    this.clicked = !this.clicked;
  }
}
