import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListaComponent } from './components/lista/lista.component';

import { FormsModule } from '@angular/forms';
import { PrioridadeComponent } from './components/prioridade/prioridade.component';
import { EstadoComponent } from './components/estado/estado.component'; 

@NgModule({
  declarations: [
    AppComponent,
    ListaComponent,
    PrioridadeComponent,
    EstadoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent] 
})
export class AppModule { }
