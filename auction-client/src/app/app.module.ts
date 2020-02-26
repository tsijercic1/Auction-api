import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { RegistrationComponent } from './registration/registration.component';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { ShopComponent } from './shop/shop.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { ShopListComponent } from './shop/shop-list/shop-list.component';
import { ShopItemComponent } from './shop/shop-list/shop-item/shop-item.component';
import { ProductDetailsComponent } from './shop/product-details/product-details.component';
import { CategoryListComponent } from './shop/category-list/category-list.component';
import { CategoryItemComponent } from './shop/category-list/category-item/category-item.component';
import {CommonModule} from '@angular/common';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    ShopComponent,
    MyAccountComponent,
    ShopListComponent,
    ShopItemComponent,
    ProductDetailsComponent,
    CategoryListComponent,
    CategoryItemComponent,
    NotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
